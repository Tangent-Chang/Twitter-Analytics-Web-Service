package Twitter.Database;

import Twitter.Service.TweetContent;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by YHWH on 10/23/15.
 */
public class DAO {
    private String dbType; //HBase or MySQL
    private Configuration config = null;
    private static HikariDataSource hikari = null;

    //setup which DB type this DAO object is going to deal with
    public DAO(String dbType){
        //System.out.printf("DAO: initializing...\n");
        this.dbType = dbType;
        if(this.dbType.equals("MySQL")){
            initialPool();
        }
    }
    private static void initialPool(){
        if(hikari == null){
            hikari = new HikariDataSource();
            hikari.setMaximumPoolSize(100);
            hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
            hikari.addDataSourceProperty("serverName", "localhost");
            hikari.addDataSourceProperty("port", "3306");
            hikari.addDataSourceProperty("databaseName", "teamproject");
            hikari.addDataSourceProperty("user", "client");
            hikari.addDataSourceProperty("password", "123456");
        }
    }

    //call DB proccessor to retrieve data from DB
    public ArrayList<TweetContent> retrieveTweet(String userId, String tweetTime){
        String separator = "|";
        ArrayList<TweetContent> tweetResults = null;

        if(dbType.equals("HBase")){
            String rowKey = "\"" + pad(userId) + separator + tweetTime + "\"";
            //System.out.printf("DAO: rowkey = %s\n", rowKey);
            //System.out.printf("DAO: calling HBase...\n");
            tweetResults = retrieveDataFromHBase(rowKey);
        }
        else if(dbType.equals("MySQL")){
            String idWithTime = pad(userId) + separator + tweetTime;
            //System.out.printf("DAO: idWithTime = %s\n", idWithTime);
            //System.out.printf("DAO: calling MySQL...\n");
            tweetResults = retrieveDataFromMySql(idWithTime);
        }
        return tweetResults;
    }

    private ArrayList<TweetContent> retrieveDataFromMySql(String idWithTime){

        //System.out.printf("DAO: retrieving data...\n");

        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<TweetContent> tweetResults = new ArrayList<TweetContent>();

        String query = "SELECT TWEETID_SCORE_TEXT FROM QUERY2 WHERE USER_TIME=?";

        try{
            conn = hikari.getConnection();

            stmt = conn.prepareStatement(query);
            stmt.setString(1, idWithTime);

            System.out.printf("DAO: executing query...\n");
            ResultSet rs = stmt.executeQuery();
            System.out.printf("DAO: get result...\n");
            while(rs.next()){
                TweetContent tweet = new TweetContent(rs.getString(1));
                //System.out.printf("DAO: data retrieved: %s\n", tweet.getLine());
                tweetResults.add(tweet);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if(conn != null){
                try{
                    conn.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(stmt != null){
                try{
                    stmt.close();
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return tweetResults;
    }
    private ArrayList<TweetContent> retrieveDataFromHBase(String rowKey){

        connectHBase();

        ArrayList<TweetContent> tweetResults = new ArrayList<TweetContent>();

        //System.out.printf("DAO: retrieving data...\n");

        try{
            HTable table = new HTable(config, "teamproject");

            Get g = new Get(rowKey.getBytes());
            Result rs = table.get(g);
            //System.out.printf("DAO: get result...\n");

            byte[] value = rs.getValue(Bytes.toBytes("tweets"), Bytes.toBytes("value"));
            String valueStr = Bytes.toString(value);
            //System.out.printf("DAO: result = %s\n", valueStr);

            tweetResults.add(new TweetContent(valueStr));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return tweetResults;
    }

    public void connectHBase(){
        try{
            config = HBaseConfiguration.create();
            config.clear();
            config.set("hbase.zookeeper.quorum", "ec2-52-91-44-225.compute-1.amazonaws.com");
            config.set("hbase.zookeeper.property.clientPort","2181");
            config.set("hbase.master", "ec2-52-91-44-225.compute-1.amazonaws.com:60000");
            //System.out.printf("DAO: %d, connecting HBase...\n", Thread.currentThread().getId());
            HBaseAdmin.checkHBaseAvailable(config);

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    //9223372036854775807 is Long.MAX_VALUE, length is 19. negative may be 20
    private String pad(String userId){
        final Integer MAX_LENGTH = 20;
        Integer length = userId.length();

        if (length == MAX_LENGTH) {
            return userId;
        }

        return String.format("%20s", userId);
    }
}

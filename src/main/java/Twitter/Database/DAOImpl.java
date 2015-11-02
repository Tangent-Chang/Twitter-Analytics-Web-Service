package Twitter.Database;

import Twitter.Service.TweetContent;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by YHWH on 10/23/15.
 */
public class DAOImpl {
    private String dbType;
    private ArrayList<TweetContent> tweetResults;
    //should try multi connection, that is connection pool
    private Configuration config = null;
    private Connection conn = null;
    private static ConnectionPool connPool = null;

    //setup which DB type this DAO object is going to deal with
    public DAOImpl(String dbType){
        //System.out.printf("DAO: initializing...\n");
        this.dbType = dbType;
        try{
            if(connPool == null){
                this.connPool = new ConnectionPool();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    //call DB proccessor to retrieve data from DB
    public ArrayList<TweetContent> retrieveTweet(String userId, String tweetTime){
        String separator = "|";

        tweetResults = new ArrayList<TweetContent>();

        if(dbType.equals("HBase")){
            String rowKey = "\"" + pad(userId) + separator + tweetTime + "\"";
            //System.out.printf("DAO: rowkey = %s\n", rowKey);
            //System.out.printf("DAO: calling HBase...\n");
            retrieveDataFromHBase(rowKey);

        }
        else if(dbType.equals("MySQL")){
            String idWithTime = pad(userId) + separator + tweetTime;
            //System.out.printf("DAO: idWithTime = %s\n", idWithTime);
            //System.out.printf("DAO: calling MySQL...\n");
            retrieveDataFromMySql(idWithTime);
        }

        return tweetResults;
    }

    private void retrieveDataFromMySql(String idWithTime){

        //System.out.printf("DAO: retrieving data...\n");

        String query = "SELECT TWEETID_SCORE_TEXT FROM TWEETS WHERE USER_TIME=?";

        try{
            if(conn==null || conn.isClosed()){
                connectMySql();
            }

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, idWithTime);
            //System.out.printf("DAO: executing query...\n");
            ResultSet rs = stmt.executeQuery();
            //System.out.printf("DAO: get result...\n");
            while(rs.next()){
                TweetContent tweet = new TweetContent(rs.getString(1));
                //System.out.printf("DAO: data retrieved: %s\n", tweet.getLine());
                tweetResults.add(tweet);
            }
            connPool.closeConnection(conn);

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    private void retrieveDataFromHBase(String rowKey){

        connectHBase();

        //System.out.printf("DAO: retrieving data...\n");

        try{
            HTable table = new HTable(config, "teamproject");

            Get g = new Get(rowKey.getBytes());
            Result rs = table.get(g);
            System.out.printf("DAO: get result...\n");

            byte[] value = rs.getValue(Bytes.toBytes("tweets"), Bytes.toBytes("value"));
            String valueStr = Bytes.toString(value);
            System.out.printf("DAO: result = %s\n", valueStr);

            tweetResults.add(new TweetContent(valueStr));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void connectHBase(){
        try{
            config = HBaseConfiguration.create();
            config.clear();
            config.set("hbase.zookeeper.quorum", "ec2-52-91-232-33.compute-1.amazonaws.com");
            config.set("hbase.zookeeper.property.clientPort","2181");
            config.set("hbase.master", "ec2-52-91-232-33.compute-1.amazonaws.com:60000");
            System.out.printf("DAO: connecting HBase...\n");
            HBaseAdmin.checkHBaseAvailable(config);

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void connectMySql(){
        try{
            conn = connPool.getConnection();

            /*Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/teamproject";
            String user = "client";
            String password = "123456";
            System.out.printf("DAO: connecting MySQL...\n");
            conn = DriverManager.getConnection(url, user, password);*/
            if(conn.isClosed()){
                System.out.printf("DAO: don't get MySQL connection...\n");
            }
            else{
                System.out.printf("DAO: get MySQL connection...\n");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        /*catch (ClassNotFoundException e){
            e.printStackTrace();
        }*/

    }
    public void disconnectMysql(){
        try{
            connPool.closeConnection(conn);
        }
        catch (SQLException e){
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

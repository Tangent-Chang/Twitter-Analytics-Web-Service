package Twitter.Phase1.Q2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
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
    private static Configuration config = null;
    private static Connection conn = null;

    public DAOImpl(String dbType){
        this.dbType = dbType;
        System.out.printf("DAO: initializing...\n");
    }

    public ArrayList<TweetContent> retrieveTweet(String userId, String tweetTime){
        String separator = "|";

        tweetResults = new ArrayList<TweetContent>();

        if(dbType.equals("HBase")){
            String rowKey = "\"" + pad(userId) + separator + tweetTime + "\"";
            System.out.printf("DAO: rowkey = %s\n", rowKey);
            System.out.printf("DAO: calling HBase...\n");
            retrieveDataFromHBase(rowKey);

        }
        else if(dbType.equals("MySQL")){
            String idWithTime = pad(userId) + separator + tweetTime;
            System.out.printf("DAO: idWithTime = %s\n", idWithTime);
            System.out.printf("DAO: calling MySQL...\n");
            retrieveDataFromMySql(idWithTime);
        }

        return tweetResults;
    }

    private void retrieveDataFromMySql(String idWithTime){

        System.out.printf("DAO: retrieving data...\n");

        String query = "SELECT TWEETID_SCORE_TEXT FROM TWEETS WHERE USER_TIME=?";

        try{
            if(conn==null || conn.isClosed()){
                connectMySql();
            }

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, idWithTime);
            System.out.printf("DAO: executing query...\n");
            ResultSet rs = stmt.executeQuery();
            System.out.printf("DAO: get result...\n");
            while(rs.next()){
                TweetContent tweet = new TweetContent(rs.getString(1));
                System.out.printf("DAO: data retrieved: %s\n", tweet.getLine());
                tweetResults.add(tweet);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    private void retrieveDataFromHBase(String rowKey){

        connectHBase();

        /*Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "<EC2_MASTER_PUBLIC_IP_ADDRESS>");
        config.set("hbase.zookeeper.property.clientPort","2181");
        this.conf = config;
        this.tokenizer = ObjectFactory.createOpenNLPTokenizer();
        HBaseAdmin.checkHBaseAvailable(this.conf);*/

        System.out.printf("DAO: retrieving data...\n");

        try{
            HTable table = new HTable(config, "teamproject");

            Get g = new Get(rowKey.getBytes());
            //Get g = new Get(Bytes.toBytes(rowKey));
            Result rs = table.get(g);
            System.out.printf("DAO: get result...\n");


            byte[] value = rs.getValue(Bytes.toBytes("tweets"), Bytes.toBytes("value"));
            String valueStr = Bytes.toString(value);
            System.out.printf("DAO: result = %s\n", valueStr);

            /*for(KeyValue kv : rs.raw()){
                System.out.print(new String(kv.getRow()) + " " );
                System.out.print(new String(kv.getFamily()) + ":" );
                System.out.print(new String(kv.getQualifier()) + " " );
                System.out.print(kv.getTimestamp() + " " );
                System.out.println(new String(kv.getValue()));
                String line = kv.getValue().toString();
            }*/
            tweetResults.add(new TweetContent(valueStr));
        }
        catch(IOException e){
            e.printStackTrace();
        }

        //connection
        //PreparedStatement sql query
        //process sql result
        //save result to the arraylist
    }

    public void connectHBase(){
        /* When you create a HBaseConfiguration, it reads in whatever you've set
  into your hbase-site.xml and in hbase-default.xml, as long as these can
  be found on the CLASSPATH */
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
            Class.forName("com.mysql.jdbc.Driver");
            //String url = "jdbc:mysql://ec2-52-23-165-85.compute-1.amazonaws.com:3306/teamproject";
            String url = "jdbc:mysql://localhost:3306/teamproject";
            String user = "client";
            String password = "123456";
            System.out.printf("DAO: connecting MySQL...\n");
            conn = DriverManager.getConnection(url, user, password);
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
        catch (ClassNotFoundException e){
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

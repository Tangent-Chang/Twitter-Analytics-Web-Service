package Twitter.Database;

import Twitter.Service.TweetContent;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

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

            //System.out.printf("DAO: executing query...\n");
            ResultSet rs = stmt.executeQuery();
            //System.out.printf("DAO: get result...\n");
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

        HConnection connection = connectHBase();
        //connectHBase();

        ArrayList<TweetContent> tweetResults = new ArrayList<TweetContent>();

        //System.out.printf("DAO: retrieving data...\n");

        try{
            HTable table = new HTable(config, "teamproject");
            /*HTableInterface table = connection.getTable("teamproject");
            // use the table as needed, for a single operation and a single thread
            table.close();
            connection.close();*/

            Get g = new Get(rowKey.getBytes());
            Result rs = table.get(g);
            //System.out.printf("DAO: get result...\n");

            byte[] value = rs.getValue(Bytes.toBytes("tweets"), Bytes.toBytes("value"));
            String valueStr = Bytes.toString(value);
            //System.out.printf("DAO: result = %s\n", valueStr);

            tweetResults.add(new TweetContent(valueStr));

            // Use the connection to your hearts' delight and then when done...
            HConnectionManager.deleteConnection(config, true);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return tweetResults;
    }

    private HConnection connectHBase(){
    //private void connectHBase(){
        HConnection  connection = null;
        try{
            config = HBaseConfiguration.create();
            config.clear();
            config.set("hbase.zookeeper.quorum", "ec2-54-175-139-164.compute-1.amazonaws.com");
            config.set("hbase.zookeeper.property.clientPort","2181");
            config.set("hbase.master", "ec2-54-175-139-164.compute-1.amazonaws.com:60000");
            //System.out.printf("DAO: %d, connecting HBase...\n", Thread.currentThread().getId());
            HBaseAdmin.checkHBaseAvailable(config);
            //Configuration newConfig = new Configuration(originalConf);
            connection = HConnectionManager.getConnection(config);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return connection;
    }

    public HashMap<String, ArrayList<TweetContent>> retrieveImpactTweet(String startDate, String endDate, String id, String qty){
        HashMap<String, ArrayList<TweetContent>> impactResutls = null;
        BigDecimal userId = new BigDecimal(id);
        int tweetQty = Integer.parseInt(qty);

        if(dbType.equals("MySQL")){
            impactResutls = retrieveImpactFromMySql(startDate, endDate, userId, tweetQty);
        }
        else if(dbType.equals("HBase")){
            impactResutls = retrieveImpactFromHBase(startDate, endDate, id, tweetQty);
        }

        return impactResutls;
    }

    private HashMap<String, ArrayList<TweetContent>> retrieveImpactFromMySql(String startDate, String endDate, BigDecimal userId, int tweetQty){
        HashMap<String, ArrayList<TweetContent>> impactResults = new HashMap<String, ArrayList<TweetContent>>();
        ArrayList<TweetContent> positiveResults = new ArrayList<TweetContent>();
        ArrayList<TweetContent> negativeResults = new ArrayList<TweetContent>();

        Connection conn = null;
        PreparedStatement stmt = null;
        String query =
                "SELECT CREATED_AT,SCORE,TWEET_ID,CENSORED_TEXT " +
                "FROM QUERY3 " +
                "WHERE USER_ID=? AND CREATED_AT >= ? AND CREATED_AT <= ? " +
                "ORDER BY ABS(SCORE) DESC, TWEET_ID ASC ";

        try{
            conn = hikari.getConnection();

            stmt = conn.prepareStatement(query);
            stmt.setBigDecimal(1, userId);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);

            ResultSet rs = stmt.executeQuery();
            int positiveCount = 0;
            int negativeCount = 0;
            while(rs.next()){
                String createdAt = rs.getString(1);
                int score = rs.getInt(2);
                String tweetId = rs.getString(3);
                String text = rs. getString(4);

                // no need to use sort function, since retrieved data is already sorted
                if(score > 0 && positiveCount < tweetQty){
                    TweetContent one = new TweetContent(createdAt, String.valueOf(score), tweetId, text);
                    positiveResults.add(one);
                    positiveCount++;
                }
                else if(score < 0 && negativeCount < tweetQty){
                    TweetContent one = new TweetContent(createdAt, String.valueOf(score), tweetId, text);
                    negativeResults.add(one);
                    negativeCount++;
                }
            }
            impactResults.put("Positive", positiveResults);
            impactResults.put("Negative", negativeResults);

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

        return impactResults;
    }
    private HashMap<String, ArrayList<TweetContent>> retrieveImpactFromHBase(String startDate, String endDate, String userId, int tweetQty ){
        connectHBase();
        HashMap<String, ArrayList<TweetContent>> impactResults = new HashMap<String, ArrayList<TweetContent>>();
        ArrayList<TweetContent> positiveResults = new ArrayList<TweetContent>();
        ArrayList<TweetContent> negativeResults = new ArrayList<TweetContent>();

        try{
            HTable table = new HTable(config, "QUERY3");

            Scan s = new Scan();
            s.setCacheBlocks(false);   // don’t set to true for MR jobs

            SingleColumnValueFilter scvf1 = new SingleColumnValueFilter(Bytes.toBytes("tweets"), Bytes.toBytes("user_id"), CompareFilter.CompareOp.EQUAL, new BinaryComparator(userId.getBytes()));
            SingleColumnValueFilter scvf2 = new SingleColumnValueFilter(Bytes.toBytes("tweets"), Bytes.toBytes("created_at"), CompareFilter.CompareOp.GREATER_OR_EQUAL, new BinaryComparator(startDate.getBytes()));
            SingleColumnValueFilter scvf3 = new SingleColumnValueFilter(Bytes.toBytes("tweets"), Bytes.toBytes("created_at"), CompareFilter.CompareOp.GREATER_OR_EQUAL, new BinaryComparator(endDate.getBytes()));

            FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL);
            fl.addFilter(scvf1);
            fl.addFilter(scvf2);
            fl.addFilter(scvf3);
            s.setFilter(fl);
            ResultScanner rs = table.getScanner(s);

            int positiveCount = 0;
            int negativeCount = 0;
            ArrayList<String[]> unsortPosRecords = new ArrayList<String[]>();
            ArrayList<String[]> unsortNegRecords = new ArrayList<String[]>();
            for (Result r : rs){
                if(r != null){

                    //System.out.printf("DAO: one result...\n", r);
                    //byte[] value = r.getValue(Bytes.toBytes("tweets"), Bytes.toBytes("follower"));
                    //String follower_id = Bytes.toString(value);
                    //followers_id.add(follower_id);
                    //System.out.printf("DAO: %s\n", valueStr);

                    byte[] value1 = r.getValue(Bytes.toBytes("tweets"), Bytes.toBytes("created_at"));
                    byte[] value2 = r.getValue(Bytes.toBytes("tweets"), Bytes.toBytes("score"));
                    byte[] value3 = r.getRow(); //tweetId is rowkey
                    byte[] value4 = r.getValue(Bytes.toBytes("tweets"), Bytes.toBytes("text"));

                    String createdAt = Bytes.toString(value1);
                    int score = Bytes.toInt(value2);
                    //String score = Bytes.toString(value2);
                    String tweetId = Bytes.toString(value3);
                    String text = Bytes.toString(value4);

                    if(score > 0){
                    //if(score > 0 && positiveCount < tweetQty){
                        String[] one = {createdAt, String.valueOf(score), tweetId, text};
                        unsortPosRecords.add(one);

                        /*TweetContent one = new TweetContent(createdAt, String.valueOf(score), tweetId, text);
                        positiveResults.add(one);
                        positiveCount++;*/
                    }
                    else if(score < 0){
                    //else if(score < 0 && negativeCount < tweetQty){
                        String[] one = {createdAt, String.valueOf(score), tweetId, text};
                        unsortNegRecords.add(one);

                        /*TweetContent one = new TweetContent(createdAt, String.valueOf(score), tweetId, text);
                        negativeResults.add(one);
                        negativeCount++;*/
                    }
                }
            }
            rs.close();

            unsortPosRecords = sortImpactResult(unsortPosRecords);
            unsortNegRecords = sortImpactResult(unsortNegRecords);

            for(String[] each : unsortPosRecords){
                if(positiveCount < tweetQty){
                    positiveResults.add(new TweetContent(each[0], each[1], each[2], each[3]));
                    positiveCount++;
                }
            }
            for(String[] each : unsortNegRecords){
                if(negativeCount < tweetQty){
                    negativeResults.add(new TweetContent(each[0], each[1], each[2], each[3]));
                    negativeCount++;
                }

            }

            impactResults.put("Positive", positiveResults);
            impactResults.put("Negative", negativeResults);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally {

        }
        return impactResults;
    }
    private ArrayList<String[]> sortImpactResult(ArrayList<String[]> records){
        //ArrayList<TweetContent> results = new ArrayList<TweetContent>();

        /*Arrays.sort(data, new Comparator<String[]>() {
            @Override
            public int compare(final String[] entry1, final String[] entry2) {
                final String time1 = entry1[0];
                final String time2 = entry2[0];
                return time1.compareTo(time2);
            }
        });*/

        // didn't deal with plus and minus sign
        Collections.sort(records, new Comparator<String[]>() {
            public int compare(String[] o1, String[] o2) {
                int comp = o1[1].compareTo(o2[1]);
                if(comp==0){
                    comp = o1[2].compareTo(o2[2]);
                }
                return  comp;
            }
        });

        return records;
    }

    public ArrayList<TweetContent> retrieveHashtagResults(String hashTag, String qty){
        ArrayList<TweetContent> tagResults = null;
        int quantity = Integer.parseInt(qty);
        if(dbType.equals("MySQL")){
            System.out.printf("DAO: going to retrieveHashtagDataFromMysql...\n");
            tagResults = retrieveHashtagDataFromMysql(hashTag, quantity);
        }
        else if(dbType.equals("HBase")){
            tagResults = retrieveHashtagDataFromHBase(hashTag, qty);
        }

        return tagResults;
    }
    private ArrayList<TweetContent> retrieveHashtagDataFromMysql(String hashTag, int qty){
        ArrayList<TweetContent> tagResults = new ArrayList<TweetContent>();

        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "SELECT ALLVALUE FROM QUERY4 WHERE HASHTAG=?";

        try{
            //System.out.printf("DAO: going to connect mysql...\n");
            conn = hikari.getConnection();
            //System.out.printf("DAO: got connect mysql...\n");

            stmt = conn.prepareStatement(query);
            stmt.setString(1, hashTag);
            //System.out.printf("DAO: going to execute query...\n");
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                String raw = rs.getString(1);
                //System.out.printf("DAO: %s\n", raw);
                String[] records = raw.split("\n");
                //String[] records = raw.split("\n2014-");

                //System.out.printf("DAO: qty = %d, records.length = %d\n", qty, records.length);
                for(int i = 0; i < qty; i++){
                    if(i < records.length){
                        //tagResults.add(new TweetContent("q4", "2014-"+records[i]));
                        tagResults.add(new TweetContent("q4", records[i]));
                        //System.out.printf("DAO: %s\n",records[i]);
                    }

                }

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

        return tagResults;
    }
    private ArrayList<TweetContent> retrieveHashtagDataFromHBase(String hashTag, String qty){
        ArrayList<TweetContent> tagResults = null;

        connectHBase();

        try{
            HTable table = new HTable(config, "QUERY3");

            Scan s = new Scan();

            SingleColumnValueFilter scvf = new SingleColumnValueFilter(Bytes.toBytes("tweets"), Bytes.toBytes("hashtag"), CompareFilter.CompareOp.EQUAL, new BinaryComparator(hashTag.getBytes()));
            s.setFilter(scvf);
            ResultScanner rs = table.getScanner(s);
        }
        catch(Exception e){
            e.printStackTrace();
        }


        return tagResults;
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

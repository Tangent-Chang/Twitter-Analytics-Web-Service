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

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.*;

/**
 * Created by YHWH on 10/23/15.
 */
public class DAO {
    private String dbType; //HBase or MySQL
    private Configuration config = null;
    private static HikariDataSource hikari = null;
    private static Hashtable<BigInteger, String> wholeCountData = null;

    //setup which DB type this DAO object is going to deal with
    public DAO(String dbType){
        this.dbType = dbType;
        if(this.dbType.equals("MySQL")){
            initialPool();
        }
        if(wholeCountData == null){
            wholeCountData = new Hashtable<BigInteger, String>();
        }
    }

    /**
     * DB connector
     */

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
            hikari.addDataSourceProperty("useUnicode", "true");
            hikari.addDataSourceProperty("characterEncoding", "utf8");

        }
    }
    //private HConnection connectHBase(){
    private void connectHBase(){
        //HConnection  connection = null;
        try{
            config = HBaseConfiguration.create();
            config.clear();
            config.set("hbase.zookeeper.quorum", "ec2-54-86-68-59.compute-1.amazonaws.com");
            config.set("hbase.zookeeper.property.clientPort","2181");
            config.set("hbase.master", "ec2-54-86-68-59.compute-1.amazonaws.com:60000");
            //System.out.printf("DAO: %d, connecting HBase...\n", Thread.currentThread().getId());
            HBaseAdmin.checkHBaseAvailable(config);
            //Configuration newConfig = new Configuration(originalConf);
            //connection = HConnectionManager.getConnection(config);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        //return connection;
    }

    /**
     * Service handler, call related DB processor
     */
    public ArrayList<TweetContent> retrieveTweet(String userId, String tweetTime){
        String separator = "|";
        ArrayList<TweetContent> tweetResults = null;

        if(dbType.equals("HBase")){
            String rowKey = "\"" + pad(userId) + separator + tweetTime + "\"";
            tweetResults = retrieveDataFromHBase(rowKey);
        }
        else if(dbType.equals("MySQL")){
            String idWithTime = pad(userId) + separator + tweetTime;
            tweetResults = retrieveDataFromMySql(idWithTime);
        }
        return tweetResults;
    }

    public HashMap<String, ArrayList<TweetContent>> retrieveImpactTweet(String startDate, String endDate, String id, String qty){
        HashMap<String, ArrayList<TweetContent>> impactResults = null;
        BigDecimal userId = new BigDecimal(id);
        int tweetQty = Integer.parseInt(qty);

        if(dbType.equals("MySQL")){
            impactResults = retrieveImpactFromMySql(startDate, endDate, userId, tweetQty);
        }
        else if(dbType.equals("HBase")){
            impactResults = retrieveImpactFromHBase(startDate, endDate, id, tweetQty);
        }

        return impactResults;
    }

    public ArrayList<TweetContent> retrieveHashtagResults(String hashTag, String qty){
        ArrayList<TweetContent> tagResults = null;
        int quantity = Integer.parseInt(qty);

        if(dbType.equals("MySQL")){
            tagResults = retrieveHashtagDataFromMysql(hashTag, quantity);
        }
        else if(dbType.equals("HBase")){
            tagResults = retrieveHashtagDataFromHBase(hashTag, quantity);
        }

        return tagResults;
    }

    public String retrieveCount(String useridMin, String useridMax){
        String count = "0";

        if(wholeCountData.size()<1){
            loadAllCountData();
        }

        if(dbType.equals("MySQL")){
            //int result = retrieveCountFromMysql(useridMin, useridMax);
            int result = retrieveCountFromMemory(useridMin, useridMax);
            count = String.valueOf(result);
        }
        else if(dbType.equals("HBase")){
            int result = retrieveCountFromHBase(useridMin, useridMax);
            count = String.valueOf(result);
        }
        return count;
    }

    /**
     * Memory
     */
    private void loadAllCountData(){

        Connection conn = null;
        Statement stmt = null;
        String query = "SELECT * FROM QUERY5; ";

        //int count = 0;
        try{
            conn = hikari.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                String userId = rs.getString(1);
                String prevCount = rs.getString(2);
                String count = rs.getString(3);
                wholeCountData.put(new BigInteger(userId), prevCount +":"+ count);
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
    }

    private int retrieveCountFromMemory(String useridMin, String useridMax){
        BigInteger minUserId = new BigInteger(useridMin);
        BigInteger maxUserId = new BigInteger(useridMax);
        int count = 0;
        int[] minData = new int[2];
        int[] maxData = new int[2];

        while(minUserId.compareTo(maxUserId) < 0){
            if(wholeCountData.containsKey(minUserId)){
                String raw = wholeCountData.get(minUserId);
                String[] data = raw.split(":");
                minData[0] = Integer.parseInt(data[0]); //prevCount
                minData[1] = Integer.parseInt(data[1]); //selfCount
                break;
            }
            else{
                minUserId = minUserId.add(BigInteger.ONE);
            }
        }

        while(maxUserId.compareTo(minUserId) > 0){
            if(wholeCountData.containsKey(maxUserId)){
                String raw = wholeCountData.get(maxUserId);
                String[] data = raw.split(":");
                maxData[0] = Integer.parseInt(data[0]); //prevCount
                maxData[1] = Integer.parseInt(data[1]); //selfCount
                break;
            }
            else{
                maxUserId = maxUserId.subtract(BigInteger.ONE);
            }
        }

        count = maxData[0] - minData[0] + maxData[1]; //maxPrev - minPrev + maxSelf
        return count;
    }


    /**
     * MySQL
     */
    private ArrayList<TweetContent> retrieveDataFromMySql(String idWithTime){
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<TweetContent> tweetResults = new ArrayList<TweetContent>();

        String query = "SELECT TWEETID_SCORE_TEXT FROM QUERY2 WHERE USER_TIME=?";

        try{
            conn = hikari.getConnection();

            stmt = conn.prepareStatement(query);
            stmt.setString(1, idWithTime);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                TweetContent tweet = new TweetContent(rs.getString(1));
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

    private ArrayList<TweetContent> retrieveHashtagDataFromMysql(String hashTag, int qty){
        ArrayList<TweetContent> tagResults = new ArrayList<TweetContent>();

        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "SELECT ALLVALUE FROM QUERY4 WHERE HASHTAG=?";

        try{
            conn = hikari.getConnection();

            stmt = conn.prepareStatement(query);
            stmt.setString(1, hashTag);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                String raw = rs.getString(1);
                raw = raw.replaceAll("\t\n", "\n"); //reducer would add \t automatically if there is \n in the end of string
                String[] preProcessRecords = raw.split("\n");

                String[] records = mergeExtraRecords(preProcessRecords);

                for(int i = 0; i < qty; i++){
                    if(i < records.length){
                        tagResults.add(new TweetContent("q4", records[i]));
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

    private int retrieveCountFromMysql(String useridMin, String useridMax){
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "SELECT prevCount, count FROM QUERY5 WHERE userId=? AND userId=?";

        int count = 0;
        try{
            conn = hikari.getConnection();

            stmt = conn.prepareStatement(query);
            stmt.setString(1, useridMin);
            stmt.setString(2, useridMax);
            ResultSet rs = stmt.executeQuery();

            int[] counts = new int[4]; //minPrev, minSelf, maxPrev, maxSelf
            int i = 0;
            while(rs.next()){
                counts[i] = rs.getInt(1);    //minPrev or maxPrev
                counts[i+1] = rs.getInt(2);  //minSelf or maxSelf
                i+=2;
            }

            count = counts[2] - counts[0] + counts[3]; //maxPrev - minPrev + maxSelf
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
        return count;
    }

    /**
     * HBase
     */
    private ArrayList<TweetContent> retrieveDataFromHBase(String rowKey){

        //HConnection connection = connectHBase();
        connectHBase();

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
            //HConnectionManager.deleteConnection(config, true);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return tweetResults;
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
            SingleColumnValueFilter scvf3 = new SingleColumnValueFilter(Bytes.toBytes("tweets"), Bytes.toBytes("created_at"), CompareFilter.CompareOp.LESS_OR_EQUAL, new BinaryComparator(endDate.getBytes()));

            FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL);
            fl.addFilter(scvf1);
            fl.addFilter(scvf2);
            fl.addFilter(scvf3);
            s.setFilter(fl);
            //System.out.printf("DAO: going to execute query...\n");
            ResultScanner rs = table.getScanner(s);
            //System.out.printf("DAO: got data...\n");

            int positiveCount = 0;
            int negativeCount = 0;
            ArrayList<String[]> unsortPosRecords = new ArrayList<String[]>();
            ArrayList<String[]> unsortNegRecords = new ArrayList<String[]>();
            for (Result r : rs){
                //System.out.print("DAO: inside for each...");
                //System.out.print(r);
                if(r != null){
                    //System.out.print("DAO: inside if not null...");
                    //System.out.printf("DAO: one result...\n", r);
                    //byte[] value = r.getValue(Bytes.toBytes("tweets"), Bytes.toBytes("follower"));
                    //String follower_id = Bytes.toString(value);
                    //followers_id.add(follower_id);
                    //System.out.printf("DAO: %s\n", valueStr);
                    //System.out.printf("DAO: one result = %s\n", r);

                    byte[] value1 = r.getValue(Bytes.toBytes("tweets"), Bytes.toBytes("created_at"));
                    byte[] value2 = r.getValue(Bytes.toBytes("tweets"), Bytes.toBytes("score"));
                    byte[] value3 = r.getRow(); //tweetId is rowkey
                    byte[] value4 = r.getValue(Bytes.toBytes("tweets"), Bytes.toBytes("text"));

                    String createdAt = Bytes.toString(value1);
                    int score = Bytes.toInt(value2);
                    //String score = Bytes.toString(value2);
                    String tweetId = Bytes.toString(value3);
                    String text = Bytes.toString(value4);
                    System.out.printf("DAO: format one result = %s, %d, %s, %s\n", createdAt, score, tweetId, text);

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

    private ArrayList<TweetContent> retrieveHashtagDataFromHBase(String hashTag, int qty){
        ArrayList<TweetContent> tagResults = null;

        connectHBase();

        try{
            HTable table = new HTable(config, "QUERY4");

            Get g = new Get(hashTag.getBytes());
            Result rs = table.get(g);

            if(rs!=null){
                byte[] value = rs.getValue(Bytes.toBytes("tweets"), Bytes.toBytes("allvalue"));
                String raw = Bytes.toString(value);
                raw = raw.replaceAll("\t\n", "\n"); //reducer would add \t automatically if there is \n in the end of string
                String[] preProcessRecords = raw.split("\n");

                String[] records = mergeExtraRecords(preProcessRecords);

                for(int i = 0; i < qty; i++){
                    if(i < records.length){
                        tagResults.add(new TweetContent("q4", records[i]));
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return tagResults;
    }

    private int retrieveCountFromHBase(String useridMin, String useridMax){
        return 0;
    }

    /**
     * Helper Function
     */
    //9223372036854775807 is Long.MAX_VALUE, length is 19. negative may be 20
    private String pad(String userId){
        final Integer MAX_LENGTH = 20;
        Integer length = userId.length();

        if (length == MAX_LENGTH) {
            return userId;
        }

        return String.format("%20s", userId);
    }

    private String[] mergeExtraRecords(String[] records){
        ArrayList<String> correctRecords = new ArrayList<String>();
        String bufferStr = "";

        for(int i = 0; i < records.length; i++){
            if(records.length > 5 && records[i].substring(0, 5).equals("2014-")){
                // means this line is a new record, so we know the bufferStr is end, and can be put into formal list now
                // then treat this line as bufferStr
                if(!bufferStr.isEmpty()){
                    correctRecords.add(bufferStr);
                    bufferStr = "";
                }
                bufferStr = records[i];
            }
            else{
                // means this line is part of old record, should be appended into bufferStr
                bufferStr = bufferStr +"\n"+ records[i];
            }
        }
        // in case there is only one line
        if(!bufferStr.isEmpty()){
            correctRecords.add(bufferStr);
        }

        return correctRecords.toArray(new String[correctRecords.size()]);
    }

    private ArrayList<String[]> sortImpactResult(ArrayList<String[]> records){

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
}

package Twitter.Phase1.Q2;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by YHWH on 10/23/15.
 */
public class DAOImpl {
    private String dbType;
    private ArrayList<TweetContent> tweetResults = new ArrayList<TweetContent>();

    public DAOImpl(String dbType){
        this.dbType = dbType;
    }

    public ArrayList<TweetContent> retrieveTweet(BigInteger userId, String tweetTime){

        if(dbType.equals("HBase")){
            retrieveDataFromMySql(userId, tweetTime);
        }
        else if(dbType.equals("MySQL")){
            retrieveDataFromHBase(userId, tweetTime);
        }

        return tweetResults;
    }

    private void retrieveDataFromMySql(BigInteger userId, String tweetTime){
        TweetContent tweet1 = new TweetContent(new BigInteger("472550295504179209"), 1, "Who would like to join me in watching the hockey game");
        TweetContent tweet2 = new TweetContent(new BigInteger("472550295504179210"), 3, "Who would like to join me in watching the hockey game");
        TweetContent tweet3 = new TweetContent(new BigInteger("472550295504179211"), 5, "Who would like to join me in watching the hockey game");

        tweetResults.add(tweet3);
        tweetResults.add(tweet1);
        tweetResults.add(tweet2);

        //connection
        //PreparedStatement sql query
        //process sql result
        //save result to the arraylist
    }
    private void retrieveDataFromHBase(BigInteger userId, String tweetTime){
        TweetContent tweet1 = new TweetContent(new BigInteger("472550295504179209"), 1, "Who would like to join me in watching the hockey game");
        TweetContent tweet2 = new TweetContent(new BigInteger("472550295504179210"), 3, "Who would like to join me in watching the hockey game");
        TweetContent tweet3 = new TweetContent(new BigInteger("472550295504179211"), 5, "Who would like to join me in watching the hockey game");

        tweetResults.add(tweet3);
        tweetResults.add(tweet1);
        tweetResults.add(tweet2);

        //connection
        //PreparedStatement sql query
        //process sql result
        //save result to the arraylist
    }
}

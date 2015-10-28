package Twitter.Phase1.Q2;

import java.math.BigInteger;

/**
 * Created by YHWH on 10/23/15.
 */
public class TweetContent {
    private String line; //include tweetId, score, tweetText


    public TweetContent(String line){
        this.line = line;
    }

    public String getLine(){
        return line;
    }

    /*private BigInteger tweetId;
    private int score;
    private String tweetText;

    public TweetContent(BigInteger tweetId, int score, String tweetText){
        this.tweetId = tweetId;
        this.score = score;
        this.tweetText = tweetText;
    }

    public BigInteger getTweetId(){
        return tweetId;
    }
    public int getScore(){
        return score;
    }
    public String getTweetText(){
        return tweetText;
    }*/

}

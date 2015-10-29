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
        /*String decoded = line.replaceAll("\\\\r", "\r");
        decoded = decoded.replaceAll("\\\\n", "\n");
        return decoded;
        //return line;*/


        StringBuffer s = new StringBuffer();
        String[] tweets = line.split("\n");
        for (String tweet : tweets) {
            String decoded = tweet.replaceAll("\\\\r", "\r");
            decoded = decoded.replaceAll("\\\\n", "\n");
            s.append(decoded).append(";");
        }

        return s.toString();
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

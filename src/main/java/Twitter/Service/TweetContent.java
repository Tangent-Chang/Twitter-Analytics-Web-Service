package Twitter.Service;

/**
 * Created by YHWH on 10/23/15.
 */
public class TweetContent {
    private String line; //include tweetId, score, tweetText


    public TweetContent(String line){
        this.line = line;
    }

    public String getLine(){

        StringBuffer s = new StringBuffer();
        String[] tweets = line.split("\n");
        for (String tweet : tweets) {
            String decoded = tweet.replaceAll("\\\\r", "\r");
            decoded = decoded.replaceAll("\\\\n", "\n");
            s.append(decoded).append(";");
        }

        return s.toString();
    }

}

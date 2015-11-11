package Twitter.Service;

/**
 * Created by YHWH on 10/23/15.
 */
public class TweetContent {
    private String line; //include tweetId, sensitive score, tweetText
    private String q3result; //include create date, impact score, tweet id, tweet text
    private String q4result;


    public TweetContent(String line){
        this.line = line;
    }
    public TweetContent(String createDate, String impactScore, String tweetId, String tweetText){
        this.q3result = createDate +","+ impactScore +","+ tweetId +","+ tweetText;
    }
    public TweetContent(String q4, String value){
        this.q4result = value;
    }

    public String getLine(){

        StringBuffer s = new StringBuffer();

        if (line.length() > 0 && line.charAt(0)=='"') {
            line = line.substring(1, line.length());
        }
        if (line.length() > 0 && line.charAt(line.length()-1)=='"') {
            line = line.substring(0, line.length()-1);
        }

        String[] tweets = line.split("\n");
        for (String tweet : tweets) {
            //String decoded = decode(tweet); //use this line or next four lines

            String decoded = tweet.replaceAll("\\\\r", "\r");
            decoded = decoded.replaceAll("\\\\n", "\n");
            decoded = decoded.replaceAll("\\\\\"", "\"");  //double quote
            decoded = decoded.replaceAll("\\\\\'", "\'"); //single quote
            s.append(decoded).append("\n");
        }

        return s.toString();
    }

    public String getQ3Result(){

        return decode(q3result);
    }

    public String getQ4result(){
        return decode(q4result);
    }

    private String decode(String str){
        //StringBuffer s = new StringBuffer();
        String decoded = str.replaceAll("\\\\r", "\r");
        decoded = decoded.replaceAll("\\\\n", "\n");
        decoded = decoded.replaceAll("\\\\\"", "\"");  //double quote
        decoded = decoded.replaceAll("\\\\\'", "\'"); //single quote
        decoded = decoded.replaceAll("\t\n", "\n");
        //s.append(decoded).append(";");
        //s.append(decoded).append("\n");
        return decoded;
    }

}

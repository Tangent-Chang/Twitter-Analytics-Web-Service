package etl;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import twitter.Tweet;

/**
 * The Class TweetParser.
 */
public class TweetParser {
  
  /**
   * Instantiates a new tweet parser.
   */
  public TweetParser() {
    gson = new Gson();
  }
  
  /**
   * Parses the.
   *
   * @param input the input
   * @return the tweet
   */
  public Tweet parse(String input) {
    Tweet tweet = null;
    try {
      tweet = gson.fromJson(input, Tweet.class);
    } catch (JsonParseException e) {
      System.err.println(e);
    }
    
    return tweet;
  }
  
  /** The gson. */
  private Gson gson;
}

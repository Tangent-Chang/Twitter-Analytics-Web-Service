package etl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import twitter.Tweet;

/**
 * The Class Mapper. 
 */
public class Mapper {
  
  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    Mapper mapper = new Mapper();
    mapper.map();
  }
  
  /**
   * Instantiates a new mapper.
   */
  public Mapper() {
    tweetParser = new TweetParser();
    timeFilter = new TimeFilter();
    sentimentScoreCalculator = new SentimentScoreCalculator();
    textCensor = new TextCensor();
  }

  /**
   * Map.
   */
  public void map() {
    String line;
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out, Charset.forName("UTF-8")))) {
      while ((line = reader.readLine()) != null) {
        try {
          Tweet tweet = tweetParser.parse(line);
          if (tweet == null) {
            continue; // filter out malformed tweet
          }
          
          long userId = tweet.getUser().getId();
          long tweetId = tweet.getId();
  
          String createdAt = timeFilter.parseDate(tweet.getCreatedAt());
          if (createdAt == null) {
            continue; // filter out incorrect time
          }
          
          String text = tweet.getText();
          long sentimentScore = sentimentScoreCalculator.calculate(text);
          long followersCount = tweet.getUser().getFollowersCount();
          long score = sentimentScore * (1 + followersCount);
          text = textCensor.censor(text);
          text = Escape.encode(text);
          
          StringBuilder buffer = new StringBuilder();
          buffer.append(userId)    // This
          		.append('|')         // is
          		.append(createdAt)   // the
          		.append('|')         // MR
          		.append(tweetId)     // key
          		.append('\t')        // Now follows data
          		.append(score)
          		.append('\t')
              .append(text)
              .append('\n');
  
//          String[] temp = buffer.toString().split("\\|");
          writer.write(buffer.toString());
        } catch (Exception e) {
          //
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /** The tweet parser. */
  private TweetParser tweetParser;
  
  /** The time filter. */
  private TimeFilter timeFilter;
  
  /** The sentiment score calculator. */
  private SentimentScoreCalculator sentimentScoreCalculator;
  
  /** The text censor. */
  private TextCensor textCensor;
}

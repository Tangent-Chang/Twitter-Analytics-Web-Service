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
  
          String timeStamp = timeFilter.format(tweet.getCreatedAt());
          if (timeStamp == null) {
            continue; // filter out incorrect or early time tweet
          }
  
          String userIdWithPadding = UserIdPadder.pad(tweet.getUser().getIdStr());
          
          String text = tweet.getText();
          String sentimentScore = sentimentScoreCalculator.calculate(text);
          text = textCensor.censor(text);
          text = Escape.encode(text);
          
          StringBuffer buffer = new StringBuffer();
          buffer.append(userIdWithPadding).append("|").append(timeStamp)
                .append("\t")
                .append(tweet.getIdStr()).append("\t")
                .append(sentimentScore).append("\t")
                .append(text)
                .append("\n");
  
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

  /**
   * Builds the tweet text.
   *
   * @param textWords the text words
   * @return the string
   */
  public String buildTweetText(String[] textWords) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < textWords.length; i++) {
      builder.append(textWords[i]);
      if (i != textWords.length - 1) {
        builder.append(" ");
      }
    }

    return builder.toString();
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

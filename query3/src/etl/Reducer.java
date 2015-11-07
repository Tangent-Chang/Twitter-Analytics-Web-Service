package etl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

/**
 * The Class Reducer.
 */
public class Reducer {
  
  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    try {
      Reducer reducer = new Reducer();
      reducer.reduce();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
  
  /**
   * Instantiates a new reducer.
   */
  public Reducer() {
  }
  
  static Pattern tabPattern = Pattern.compile("\t");
  static Pattern pipePattern = Pattern.compile("\\|");
  
  /**
   * Reduce.
   */
  public void reduce() {
    String line;

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out, Charset.forName("UTF-8")))) {
      
    	String lastTweetId = "";
    	
      while ((line = reader.readLine()) != null) {
        try {
        	String[] parts = tabPattern.split(line, 3);
        	String[] key = pipePattern.split(parts[0]);
        	String userId = key[0];
        	String createdAt = key[1];
        	String tweetId = key[2];
        	String score = parts[1];
          String text = Escape.decodeBackslashT(parts[2]);
          
          if (tweetId.equals(lastTweetId)) {
          	continue;
          } else {
          	lastTweetId = tweetId;
          }
          
          StringBuilder buffer = new StringBuilder();
          buffer.append(userId)
          		.append('\t')
          		.append(createdAt)
          		.append('\t')
          		.append(tweetId)
          		.append('\t')
          		.append(score)
          		.append("\t")
          		.append('"')
          		.append(text)
          		.append('"')
          		.append('\n');
          writer.write(buffer.toString());
        } catch (Exception e) {
//          System.err.println(e);
        }
      }
    } catch (Exception e) {
      System.err.println(e);
    }
  }
}

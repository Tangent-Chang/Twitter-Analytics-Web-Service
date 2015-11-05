package etl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

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

    currentKey = null;
    currentTweets = new TreeMap<Long, String>();
  }
  
  /**
   * Reduce.
   */
  public void reduce() {
    String line;

    try {
      reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));
      writer = new BufferedWriter(new OutputStreamWriter(System.out, Charset.forName("UTF-8")));
      
      while ((line = reader.readLine()) != null) {
        try {
          String[] parts = line.split("\t");
          String key = parts[0];
          Long tweetId = Long.parseLong(parts[1]);
          String score = parts[2];
          String text = Escape.decodeBackslashT(parts[3]);
          
          String value = tweetId.toString() + ":" + score + ":" + text;
          if (!key.equals(currentKey)) {
            printLastKeyValue();
            resetCurrentKeyValue(key);
          }
          
          if (!currentTweets.containsKey(tweetId)) {
            currentTweets.put(tweetId, value);
          }
        } catch (Exception e) {
//          System.err.println(e);
        }
      }
      printLastKeyValue();
      writer.flush();
    } catch (Exception e) {
      System.err.println(e);
    }
  }
  
  /**
   * Prints the last key value.
   */
  public void printLastKeyValue() {
    if (currentKey == null) {
      return;
    }
    
    StringBuffer result = new StringBuffer();
    result.append("\"").append(currentKey).append("\"\t\""); // have to use \t
    
    Iterator<?> iterator = currentTweets.entrySet().iterator();
    while(iterator.hasNext()) {
      Entry entry = (Entry) iterator.next();
      result.append(entry.getValue());
      if(iterator.hasNext()) {
        result.append("\n"); // for mapreduce
//        result.append(";"); // for reference server
      }
    }
    
    result.append("\"\n"); // for mapreduce
//    result.append(";\"\n"); // for reference server
    
    try {
      writer.write(result.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Reset current key value.
   *
   * @param key the key
   */
  public void resetCurrentKeyValue(String key) {
    currentKey = key;
    currentTweets.clear();
  }
  
  public void print() {
    try {
      reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));
      writer = new BufferedWriter(new OutputStreamWriter(System.out, Charset.forName("UTF-8")));
      String line;
      
      while ((line = reader.readLine()) != null) {
        writer.write(line);
        writer.write("\n");
      }
      writer.flush();
    } catch (Exception e) {
      System.out.println(e);
    }
    
  }
  /** The reader. */
  private BufferedReader reader;
  
  /** The writer. */
  private BufferedWriter writer;
  
  /** The current key. */
  private String currentKey;
  
  /** The current tweets. */
  private TreeMap<Long, String> currentTweets;
}

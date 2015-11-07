package etl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MapReduceTest {

  @Before
  public void setUp() throws Exception {
    mapper = new Mapper();
  }

  @After
  public void tearDown() throws Exception {
  }

  public Boolean isSameAsReferenceAnswer(String userId, String tweetTime, String mapReduceResult) {
    URLConnection connection = null;
    try {
//      URL url = new URL("http://ec2-54-147-18-88.compute-1.amazonaws.com/q2?userid="
//                                + userId + "&tweet_time=" + tweetTime);
      
      URL url = new URL("http://ec2-54-210-181-235.compute-1.amazonaws.com/q2?userid="
        + userId + "&tweet_time=" + tweetTime);
      
      connection = url.openConnection();
      connection.setConnectTimeout(1000);
      connection.setReadTimeout(1000);
    } catch (MalformedURLException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    String line = null;
    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
      String decoded = Escape.decodeLineBreak(mapReduceResult);
      decoded = Escape.decodeQuote(decoded);

      StringBuffer buffer = new StringBuffer();
      int i = 0;
      while ((line = in.readLine()) != null) {
        if (i >= 2) {
          buffer.append("\n");
        }
        
        if (i >= 1) {
          buffer.append(line);
        }
        
        i++;
      }

      if (!buffer.toString().equals(decoded)) {
        System.out.println(buffer.toString());
        System.out.println(decoded);
        return false;
      }
    } catch (IOException e) {
      System.out.println(e);
    }
    
    return true;
  }
  
  @Test
  public void testGenerateOutput() {
    try {
      FileInputStream input = new FileInputStream("slice_test.txt");
      //FileInputStream input = new FileInputStream("slice.txt");sliceM
      //FileInputStream input = new FileInputStream("sliceM.txt");
      PrintStream output = new PrintStream(new FileOutputStream("mapper_output.txt"));
      System.setIn(input);
      System.setOut(output);
    } catch (Exception ex) {
      System.out.println(ex);
    }
    
    mapper.map();
    
    try {
//      FileInputStream input = new FileInputStream("mapper_output_sorted.txt");
      FileInputStream input = new FileInputStream("mapper_output.txt");
      System.setIn(input);
      
      PrintStream output = new PrintStream(new FileOutputStream("output.txt"));
      System.setOut(output);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    reducer = new Reducer();
    reducer.reduce();
//    reducer.print();
  }
  
  @Test
  public void testIfSameAsReferenceServer() {

    StringBuffer oneMapReduceResult = new StringBuffer();
    try (BufferedReader reader = new BufferedReader(new FileReader("output.txt"))) {
      int lineNumber = 1;
      
      String line = null;
      while ((line = reader.readLine()) != null) {
        oneMapReduceResult.append(line);
        if (!line.endsWith("\"")) {
          continue;
        }
        
        try {
          String[] keyValueOfReducer = oneMapReduceResult.toString().split("\\t");
          String userId = keyValueOfReducer[0].substring(1, 21).trim();
          String tweetTime = keyValueOfReducer[0].substring(22, 41);
          String valueOfReducer = keyValueOfReducer[1].substring(1, keyValueOfReducer[1].length() - 1);
          
          if (!isSameAsReferenceAnswer(userId, tweetTime, valueOfReducer)) {
            System.out.println("error lineNumber=" + lineNumber);
          }
          lineNumber++;
          oneMapReduceResult.delete(0, oneMapReduceResult.length());
        } catch (Exception e) {
          System.out.println("******** maybe multiple tweets, check manually ******* lineNumber=" + lineNumber);
          System.out.println(oneMapReduceResult.toString());
          System.out.println(e);
          e.printStackTrace();
        }
      }
      System.out.println("count = " + lineNumber);
    } catch (Exception ex) {
      System.out.println(oneMapReduceResult.toString());
      System.out.println(ex);
      ex.printStackTrace();
    }
  }
  
  private Mapper mapper;
  private Reducer reducer;
}

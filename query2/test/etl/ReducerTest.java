package etl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import etl.Reducer;

/**
 * The Class ReducerTest.
 */
public class ReducerTest {

  /**
   * Sets the up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {

  }

  /**
   * Tear down.
   *
   * @throws Exception the exception
   */
  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test.
   */
  @Test
  public void test() {
    try {
      FileInputStream input = new FileInputStream("mapper_output.txt");
      System.setIn(input);
      
      PrintStream output = new PrintStream(new FileOutputStream("output.txt"));
      System.setOut(output);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    reducer = new Reducer();
    reducer.reduce();
  }

  /** The reducer. */
  private Reducer reducer;
}

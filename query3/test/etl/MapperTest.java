package etl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import etl.Mapper;

/**
 * The Class MapperTest.
 */
public class MapperTest {

  /**
   * Sets the up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    mapper = new Mapper();
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
      FileInputStream input = new FileInputStream("slice_test.txt");
      PrintStream output = new PrintStream(new FileOutputStream("mapper_output.txt"));
      System.setIn(input);
      System.setOut(output);
    } catch (Exception ex) {
      System.out.println(ex);
    }
    
    mapper.map();
  }

  /** The mapper. */
  private Mapper mapper;
}

package etl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import etl.Escape;

/**
 * The Class EscapeTest.
 */
public class EscapeTest {

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
  //@Test
  public void test() {
    String input = "aaa\nbbb\rccc\"ddd\'eee\tfff";
    System.out.println(input);
    String result = Escape.encode(input);
    System.out.println(result);
  }

  /**
   * Test decode.
   */
  @Test
  public void testDecode() {
    String input = "aaa\\nbbb\\rccc\\rddd\nfff\\neee\\rgggg\niiii\\nhhhh";
    System.out.println(input);
    String result = Escape.decodeLineBreak(input);
    System.out.println(result);
  }
  
  /**
   * Test decode t.
   */
  @Test
  public void testDecodeT() {
    String input = "aaa\\nbbb\\rccc\\\"ddd\\\'eee\\tfff";
    System.out.println(input);
    String result = Escape.decodeBackslashT(input);
    System.out.println(result);
  }
  
}

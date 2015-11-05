package etl;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import etl.TextCensor;

/**
 * The Class TextCensorTest.
 */
public class TextCensorTest {

  /**
   * Sets the up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    textCensor = new TextCensor();
  }
  
  /**
 * Test.
 */
@Test
  public void test() {
    System.out.println("Int64.MaxValue = " + Long.MAX_VALUE);
    String text = "DicK!apple@Bloody_a$shiT%anal*Fuck?*&^%_!";
    String expected = "D**K!apple@B****y_a$s**T%a**l*F**k?*&^%_!";
    
    String censored = textCensor.censor(text);
    assertTrue(censored.equals(expected));
  }

  //@Test
  public void generateDictionary() {
    textCensor.generateDictionary();
  }

  /** The text censor. */
  private TextCensor textCensor;
}

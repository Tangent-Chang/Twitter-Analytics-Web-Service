package etl;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import etl.SentimentScoreCalculator;

/**
 * The Class SentimentScoreCalculatorTest.
 */
public class SentimentScoreCalculatorTest {

  /**
   * Sets the up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    calculator = new SentimentScoreCalculator();
  }

  /**
   * Tear down.
   *
   * @throws Exception the exception
   */
  @After
  public void tearDown() throws Exception {
  }
  
  //@Test
  public void generateTable() {
    calculator.generateDictionaryCode();
  }
  
  /**
   * Test two involved words.
   */
  @Test
  public void testTwoInvolvedWords() {
    // distort -2
    // glamourous 3
    String text = "dIsTort*glAmouROus";
    assertEquals(1, calculator.calculate(text));
  }
  
  /**
   * Test two uninvolved words.
   */
  @Test
  public void testTwoUninvolvedWords() {
    String text = "a!b";
    assertEquals(0, calculator.calculate(text));
  }
  
  /**
   * Test one involved word and one uninvolved word.
   */
  @Test
  public void testOneInvolvedWordAndOneUninvolvedWord() {
    String text = "distort#ccc";
    assertEquals(-2, calculator.calculate(text));
  }
  
  /** The calculator. */
  private SentimentScoreCalculator calculator;
}

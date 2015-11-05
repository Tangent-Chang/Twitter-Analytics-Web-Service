package etl;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import etl.UserIdPadder;

/**
 * The Class UserIdPadderTest.
 */
public class UserIdPadderTest {

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
    String padded = UserIdPadder.pad("123");
    assertEquals("                 123", padded);
  }

}

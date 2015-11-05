package etl;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import etl.TimeFilter;

/**
 * The Class TimeFilterTest.
 */
public class TimeFilterTest {

  /**
   * Sets the up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    timeFilter = new TimeFilter();
  }

  /**
   * Test no need filtered date1.
   */
  @Test
  public void testNoNeedFilteredDate1() {
    String date = "Thu May 15 3:02:23 +0000 2014";
    assertEquals("2014-05-15", timeFilter.parseDate(date));
  }
  
  /**
   * Malformat date should be filtered.
   */
  @Test
  public void malformatDateShouldBeFiltered() {
    String date = "Sun 20 May 00:00:00 +0000 2014";
    assertNull(timeFilter.parseDate(date));
  }
  
  /** The time filter. */
  private TimeFilter timeFilter;

}

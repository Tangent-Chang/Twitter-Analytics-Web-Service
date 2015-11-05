package etl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * The Class TimeFilter.
 */
public class TimeFilter {
  
  /**
   * Instantiates a new time filter.
   */
  public TimeFilter() {
    inputFormat = new SimpleDateFormat(DATE_FORMAT_INPUT);
    inputFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    
    outputFormat = new SimpleDateFormat(DATE_FORMAT_OUTPUT);
    outputFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    try {
      startDate = inputFormat.parse(START_DATE);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Format.
   *
   * @param createdAt the created at
   * @return the string
   */
  public String format(String createdAt) {
    Date date = null;
    try {
      date = inputFormat.parse(createdAt);
    } catch (ParseException e) {
      return null; // if parse exception, filter it
    }
    
    if (date.before(startDate)) {
      return null; // if before the date, ignore
    }
    
    return outputFormat.format(date);
  }

  /** The date format input. */
  // e.g. Thu May 15 09:02:20 +0000 2014
  private final String DATE_FORMAT_INPUT = "EEE MMM dd HH:mm:ss Z yyyy";
  
  /** The date format output. */
  // e.g. 2014-05-31+01:29:04
  private final String DATE_FORMAT_OUTPUT = "yyyy-MM-dd+HH:mm:ss";
  
  /** The input format. */
  private SimpleDateFormat inputFormat;
  
  /** The output format. */
  private SimpleDateFormat outputFormat;
  
  /** The start date. */
  private final String START_DATE = "Sun Apr 20 00:00:00 +0000 2014";
  
  /** The start date. */
  private Date startDate;

}

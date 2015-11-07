package etl;

import java.util.regex.Pattern;

/**
 * The Class Escape.
 */
public class Escape {
	
	static final Pattern SLASH = Pattern.compile("\\\\");
	static final Pattern CR = Pattern.compile("\\r");
	static final Pattern LF = Pattern.compile("\\n");
	static final Pattern DQ = Pattern.compile("\"");
	static final Pattern SQ = Pattern.compile("\'");
	static final Pattern TAB = Pattern.compile("\\t");
	
	static final Pattern S_CR = Pattern.compile("\\\\r");
	static final Pattern S_LF = Pattern.compile("\\\\n");
	static final Pattern S_DQ = Pattern.compile("\\\\\"");
	static final Pattern S_SQ = Pattern.compile("\\\\\'");
	static final Pattern S_TAB = Pattern.compile("\\\\t");
  
  /**
   * Encode.
   *
   * @param input the input
   * @return the string
   */
  public static String encode(String input) {
  	String encoded = input;
  	encoded = SLASH.matcher(encoded).replaceAll("\\\\\\\\");
  	encoded = CR.matcher(encoded).replaceAll("\\\\r");
    encoded = LF.matcher(encoded).replaceAll("\\\\n");
    encoded = DQ.matcher(encoded).replaceAll("\\\\\"");// \\\"
    encoded = SQ.matcher(encoded).replaceAll("\\\\\'");
    encoded = TAB.matcher(encoded).replaceAll("\\\\t");
    return encoded;
  }
  
  /**
   * Decode.
   *
   * @param input the input
   * @return the string
   */
  public static String decodeLineBreak(String input) {
    String decoded = S_CR.matcher(input).replaceAll("\r");
    decoded = S_LF.matcher(decoded).replaceAll("\n");
    return decoded;
  }
  
  public static String decodeQuote(String input) {
    String decoded = S_DQ.matcher(input).replaceAll("\"");
    decoded = S_SQ.matcher(decoded).replaceAll("\'");
    return decoded;
  }
  
  /**
   * Decode backslash t.
   *
   * @param input the input
   * @return the string
   */
  public static String decodeBackslashT(String input) {
    String decoded = S_TAB.matcher(input).replaceAll("\t");
    return decoded;
  }
}

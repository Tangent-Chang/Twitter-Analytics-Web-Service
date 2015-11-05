package etl;

/**
 * The Class Escape.
 */
public class Escape {
  
  /**
   * Encode.
   *
   * @param input the input
   * @return the string
   */
  public static String encode(String input) {
    String encoded = input.replaceAll("\\r", "\\\\r");
    encoded = encoded.replaceAll("\\n", "\\\\n");
    encoded = encoded.replaceAll("\"", "\\\\\"");// \\\"
    encoded = encoded.replaceAll("\'", "\\\\\'");
    encoded = encoded.replaceAll("\t", "\\\\t");
    return encoded;
  }
  
  /**
   * Decode.
   *
   * @param input the input
   * @return the string
   */
  public static String decodeLineBreak(String input) {
    String decoded = input.replaceAll("\\\\r", "\r");
    decoded = decoded.replaceAll("\\\\n", "\n");
    return decoded;
  }
  
  public static String decodeQuote(String input) {
    String decoded = input.replaceAll("\\\\\"", "\"");
    decoded = decoded.replaceAll("\\\\\'", "\'");
    return decoded;
  }
  
  /**
   * Decode backslash t.
   *
   * @param input the input
   * @return the string
   */
  public static String decodeBackslashT(String input) {
    String decoded = input.replaceAll("\\\\t", "\t");
    return decoded;
  }
}

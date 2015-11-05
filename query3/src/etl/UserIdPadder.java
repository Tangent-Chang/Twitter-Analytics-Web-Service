package etl;

/**
 * The Class UserIdPadder.
 */
public class UserIdPadder {
  
  /**
   * Pad.
   *
   * @param userId the user id
   * @return the string
   */
  //9223372036854775807 is Long.MAX_VALUE, length is 19. negative may be 20
  static public String pad(String userId){
    final Integer MAX_LENGTH = 20;
    Integer length = userId.length();
    
    if (length == MAX_LENGTH) {
      return userId;
    }
    
    return String.format("%20s", userId);
  }
}

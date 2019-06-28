package com.loeffler.utilitylibrary.Logging;

import static com.loeffler.utilitylibrary.Logging.LogLevel.CRITICAL;
import static com.loeffler.utilitylibrary.Logging.LogLevel.DEBUG;
import static com.loeffler.utilitylibrary.Logging.LogLevel.ERROR;
import static com.loeffler.utilitylibrary.Logging.LogLevel.INFO;
import static com.loeffler.utilitylibrary.Logging.LogLevel.WARNING;

/**
 *  <p><strong>LogLevel</strong></p>
 *  <em>@author</em>  John Loeffler
 *  
 *  <strong>Contact</strong> 
 *    <em>@Email</em>     John.Loeffler@gmail.com
 *    <em>@Twitter</em>   @ThisDotJohn
 *    <em>@LinkedIn</em>  LinkedIn.com/in/JohnLoeffler
 *    <em>@Github</em>    Github.com/JohnLoeffler
 *    <em>@Bitbucket</em> Bitbucket.org/JohnLoeffler
 * 
 *  <strong>@Description</strong>: A custom LogLevel for use with the Logger; 
 *    values so far:
 *    0:  TEST    - USED FOR RECORDING UNIT TESTING
 *    1:  INFO    - GENERAL INFO LOG
 *    2:  DEBUG   - USED WHEN DEBUG
 *    3:  WARNING - USED WHEN AN UNEXPECTED RESULT OCCURS, BUT DOESN'T RESULT IN
 *                    A CRASH
 *    4:  ERROR   - USED WHEN EXCEPTIONS, ASSERTIONS, OR OTHER ERROR CHECK FAILS
 *    5:  CRITICAL- USED WHEN AN ERROR CANNOT BE RECOVERED FROM AND THE PROGRAM
 *                    MUST SHUT DOWN INSTEAD
 */
public enum LogLevel {
  TEST(0), INFO(1), DEBUG(2), WARNING(3), ERROR(4), CRITICAL(5);
  /**
   * The actual log value as an int
   */
  protected int Level;
  
  /** Default Constructor */
  LogLevel  (int level) {this.Level = level;}
  
  /**
   * Returns the LogLevel indicated by the parameter. Works on a modulus, so if 
   *  log level isn't valid, will cycle back around go 
   * @param lvl The int representing the LogLevel
   * @return ( [0|DEFAULT]:INFO [1]:DEBUG [2]:WARNING [3]:ERROR [4]:CRITICAL )
   */
  public static LogLevel GetLevel(int lvl){
    switch(Math.abs(lvl%6)){
      case 1: return INFO;
      case 2: return DEBUG;
      case 3: return WARNING;
      case 4: return ERROR;
      case 5: return CRITICAL;
      default:return TEST;
    }
  }
  /**
   * Returns the log level indicated by the name passed as a string. Not case
   *  sensitive, though you do have to spell the log level correctly
   * @param lvl A string with the name of the log level
   * @return    The LogLevel indicated by the name passed to the method, will 
   *              always return INFO on empty or invalid string.
   */
  public static LogLevel GetLevel(String lvl){
    if(lvl == null){
      return INFO;
    }else if(lvl.trim().isEmpty()){
      return INFO;
    }
    switch(lvl.toUpperCase().trim()){
      case "TEST"     : return TEST;
      case "DEBUG"    : return DEBUG;
      case "WARNING"  : return WARNING;
      case "ERROR"    : return ERROR;
      case "CRITICAL" : return CRITICAL;
      default:
      case "INFO"     : return INFO;
    }
  }
}

package com.loeffler.utilitylibrary;
/**
 *    <p><strong>Statics</strong></p>
 *    <p>A collection of useful methods independent of any one class</p>
 *    <em>@author</em>    John Loeffler
 *  
 *    <strong>Contact Info:</strong> 
 *    <em>@Email</em>     John.Loeffler@gmail.com
 *    <em>@Twitter</em>   @ThisDotJohn
 *    <em>@LinkedIn</em>  LinkedIn.com/in/JohnLoeffler
 *    <em>@Github</em>    Github.com/JohnLoeffler
 *    <em>@Bitbucket</em> Bitbucket.com/JohnLoeffler
 */
import java.util.Calendar;
import java.util.Locale;

public class Statics {
  private static final Calendar CAL = Calendar.getInstance(Locale.US);
  
  /**
   * Gets the current working directory
   * @return A String of the current working directory
   */
  public static String CWD(){return System.getProperty("user.dir");}
  /**
   *  Gets the current date and time in yyyy-mm-dd::HH:MM:SS format
   *  @return A string of the timestamp
   */
  public static String GetTimestamp(){
    return CAL.getTime().toString();
  }
  /* The following three methods are useful for logging purposes */
  /**
   * Identifies class from which this method is called (Used for logs)
   * @return A String of the class from which this method is called
   */
  public static String Class(){
    String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
    return fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
  }
  /**
   * Identifies method in which this method is being called (Used for logs)
   * @return A String of the method from which this method is called
   */  
  public static String Method(){
    return Thread.currentThread().getStackTrace()[2].getMethodName();
  }
    /**
   * Identifies line number on which this method is being called (Used for logs)
   * @return A String of the line number from which this method is called
   */
  public static String Line(){
    return String.valueOf(Thread.currentThread().getStackTrace()[2].getLineNumber());
  }
}

package com.loeffler.utilitylibrary.Logging;
import com.loeffler.utilitylibrary.Statics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *  <p><strong>Logger</strong></p>
 *  <em>@author</em>  John Loeffler
 *  
 *  <strong>Contact</strong> 
 *    <em>@Email</em>     John.Loeffler@gmail.com
 *    <em>@Twitter</em>   @ThisDotJohn
 *    <em>@LinkedIn</em>  LinkedIn.com/in/JohnLoeffler
 *    <em>@Github</em>    github.com/JohnLoeffler
 *    <em>@Website</em>   JohnLoeffler.com
 */
public class Logger {
  private File            Info, Debug, Warning, Error, Critical, Test;
  private LogLevel        CurrentLevel;
  private static Logger   logger = null;
  private boolean         NoLogging;
  
  public Logger() throws Exception{
    File LogDirectory = new File(String.format("%s\\Logs\\", Statics.CWD()));
    if(!LogDirectory.exists()){
      try{
        LogDirectory.mkdirs();
      }catch(SecurityException se){
        System.err.println("Unable to create new log directory@ "
          + LogDirectory.getAbsolutePath());
        System.err.println("\tSecurityException thrown: " + se.getMessage());
        for(StackTraceElement ste: se.getStackTrace()){
          
        }
      }
    }
    Info      = new File(String.format("%s\\%s", LogDirectory, "Info.log"));
    Debug     = new File(String.format("%s\\%s", LogDirectory, "Debug.log"));
    Warning   = new File(String.format("%s\\%s", LogDirectory, "Warning.log"));
    Error     = new File(String.format("%s\\%s", LogDirectory, "Error.log"));
    Critical  = new File(String.format("%s\\%s", LogDirectory, "Critical.log"));
    Test      = new File(String.format("%s\\%s", LogDirectory, "Test.log"));
    
    this.NoLogging  = false;
  }
  
  public static Logger GetInstance(){
    if(logger == null){
      try {
        logger = new Logger();
      } catch (Exception ex) {
        System.err.println(String.format("Exception thrown while creating JLogger"));
      }
    }
    return logger;
  }
  /**
   * Used when just wanting to log to Info
   * @param Class     The Class from which this log message originated
   * @param function  The function from which this log message originated
   * @param line      The line number from which this log message originated
   * @param msg       The message to add to the log file.
   */
  public void Log(String Class, String function, String line, String msg){
    if(NoLogging){
      return;
    }
    try(BufferedWriter bw = new BufferedWriter(new FileWriter(this.Info,true))){
      StringBuilder sb = new StringBuilder();
      sb.append("[");
      sb.append(Statics.GetTimestamp());
      sb.append("]::");
      
      sb.append("|");
      sb.append(Class);
      sb.append("::");
      
      sb.append(function);
      sb.append("|::(");
      
      sb.append(line);
      sb.append(")| ");
      
      sb.append(msg);
      bw.write(sb.toString());
      bw.newLine();
    }catch(IOException ioe){
      System.err.println("Exception thrown while writing to log file: " + ioe.getMessage());
    }finally{
      //this.InfoLock.unlock();
    }
  }
    /**
   * Used when just wanting to log to Info
   * @param Class     The Class from which this log message originated
   * @param function  The function from which this log message originated
   * @param line      The line number from which this log message originated
   * @param msg       The message to add to the log file.
   * @param lvl       The log level of this message
   */
  public synchronized void Log(String Class, String function,String line,String msg,int lvl){
    if(NoLogging){
      return;
    }
    File outfile = GetFile(LogLevel.GetLevel(lvl));

    try(BufferedWriter bw = new BufferedWriter(new FileWriter(outfile, true))){
      StringBuilder sb = new StringBuilder();
      sb.append("[");
      sb.append(Statics.GetTimestamp());
      sb.append("]::");
      
      sb.append("|");
      sb.append(Class);
      sb.append(".");
      
      sb.append(function);
      sb.append("|(");
      
      sb.append(line);
      sb.append(")| ");
      
      sb.append(msg);
      bw.write(sb.toString());
      bw.newLine();
    }catch(IOException ioe){
      System.err.println("Exception thrown while writing to log file: " + ioe.getMessage());
    }finally{

    }
  }
  /**
   * Turn the logger by passing 'true' as param. Turn it back on with 'false'
   * @param val A boolean to switch logger off and on
   */
  public void SetNoLogging(boolean val)   {this.NoLogging = val;  }
  
  public void SetLevel    (int lvl)       {this.CurrentLevel = LogLevel.GetLevel(lvl);}
  
  public void SetLevel    (String lvl)    {this.CurrentLevel = LogLevel.GetLevel(lvl);}
  
  private File GetFile    (LogLevel level){
    switch(level){
      case TEST:      return this.Test;
      case DEBUG:     return this.Debug;
      case ERROR:     return this.Error;
      case WARNING:   return this.Warning;
      case CRITICAL:  return this.Critical;
      default:
      case INFO:      return this.Info;
    }
  } 
  
}

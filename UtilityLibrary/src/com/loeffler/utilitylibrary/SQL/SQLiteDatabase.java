/*
 * 
 * 
 * 
 */

package com.loeffler.utilitylibrary.SQL;

/**
 *  <p><strong>SQLiteDatabase</strong></p>
 *  <em>@author</em>  John Loeffler
 *  
 *  <strong>Contact</strong> 
 *    <em>@Email</em>     John.Loeffler@gmail.com
 *    <em>@Twitter</em>   @ThisDotJohn
 *    <em>@LinkedIn</em>  LinkedIn.com/in/JohnLoeffler
 *    <em>@Github</em>    github.com/JohnLoeffler
 *    <em>@Website</em>   JohnLoeffler.com
 */
abstract public class SQLiteDatabase extends Database{
  public SQLiteDatabase(String url) throws Exception{
    super("org.sqlite.JDBC", String.format("JDBC:sqlite:%s", url));
  }
  
  abstract public int insertEntry(Object o);
}

package com.loeffler.utilitylibrary.SQL;

import com.loeffler.utilitylibrary.Statics;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

/**
 *  <p><strong>SQLStatics</strong></p>
 *  <p>A derived class from Statics that handles SQL operations</p>
 *  <em>@author</em>  John Loeffler
 *  
 *  <strong>Contact Info:</strong> 
 *    <em>@Email</em>     John.Loeffler@gmail.com
 *    <em>@Twitter</em>   @ThisDotJohn
 *    <em>@LinkedIn</em>  LinkedIn.com/in/JohnLoeffler
 *    <em>@Github</em>    github.com/JohnLoeffler
 *    <em>@Bitbucket</em> Bitbucket.org/JohnLoeffler
 */
public class SQLStatics extends Statics{
  //TODO  Add Javadoc
  public static Connection ConnectToSQLiteDB(String filepath){
    File f = new File(filepath);
    if(!(f.exists() || f.isDirectory())){
      //TODO  Add Error messaging to new Log system
      return null;
    }
    Connection conn = null;
    try{
      conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s", filepath));
      if(conn == null || conn.isClosed()){
        //TODO  Add Error messaging to new Log system
      }else{
        
        
      }
    }catch(SQLException sqle){
      //TODO  Add Error messaging to new Log system
    } finally {
      try {
        if (conn != null) {
            conn.close();
        }
      } catch (SQLException ex) {
        //TODO  Add Error messaging to new Log system
      }
    }
    try {
      if(conn.isClosed()){
      //TODO  Add Error messaging to new Log system
      }
    } catch (SQLException ex) {
    //TODO  Add Error messaging to new Log system
    }
    return conn;
  }
  //TODO  Add Javadoc
  public static Connection CreateNewSQLiteDatabase(String filename){
    Connection conn = null;
    String url = "jdbc:sqlite:" + CWD() + filename;
    try {
      conn = DriverManager.getConnection(url);
      if (conn != null) {
        DatabaseMetaData meta = conn.getMetaData();
        //TODO  Add Info messaging to new Log system
      }
    } catch (SQLException e) {
      //TODO  Add error messaging to new Log system
    }finally{
      try{
        if(conn != null){
          conn.close();
        }
      }catch(SQLException sqle){
        //TODO  Add error messaging to new Log system
      }
    }
    return conn;
  }
  //TODO  Add Javadoc
  public static boolean ExecuteDynamicStatement(Connection conn, String sqlStatement){
    if(conn == null || sqlStatement.isEmpty()){
      return false;
    }
    try{
      Statement statement = conn.createStatement();
      try{
        statement.execute(sqlStatement);
        return true;
      }catch(SQLException sqle){
        //TODO  Add error messaging to new Log system
        return false;
      }
    }catch(SQLException sqle){
      //TODO  Add error messaging to new Log system
      return false;
    }
  }
  
  
}


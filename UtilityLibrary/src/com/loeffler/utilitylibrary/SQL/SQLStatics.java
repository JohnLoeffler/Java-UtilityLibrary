package com.loeffler.utilitylibrary.SQL;

import com.loeffler.utilitylibrary.Statics;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;
import java.util.logging.Level;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;
import java.util.logging.Logger;

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
 *    <em>@Website</em>   JohnLoeffler.com
 */
public class SQLStatics extends Statics{
  
  public static Connection ConnectToSQLiteDB(String filepath){
    File f = new File(filepath);
    if(!(f.exists() || f.isDirectory())){
      //LOG.log(SEVERE, String.format("Error! Database at %s does not exist!", filepath));
      return null;
    }
    Connection conn = null;
    try{
      conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s", filepath));
      if(conn == null || conn.isClosed()){
        //LOG.log(SEVERE, String.format("Database Connection failed to Open"));
      }else{
        
        
      }
    }catch(SQLException sqle){
      //LOG.log(SEVERE, String.format("Database Connection failed: %s\n\t%s",filepath, sqle.getMessage()));
    } finally {
      try {
        if (conn != null) {
            conn.close();
        }
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }
    }
    try {
      if(conn.isClosed()){
      //  LOG.log(SEVERE, String.format("Database Connectiion Closed Out"));
      }
    } catch (SQLException ex) {
    //  LOG.log(SEVERE, String.format("Database Connectiion Is NULL"));
    }
    return conn;
  }
  
  public static Connection CreateNewSQLiteDatabase(String filename){
    Connection conn = null;
    String url = "jdbc:sqlite:" + CWD() + filename;
    try {
      conn = DriverManager.getConnection(url);
      if (conn != null) {
        DatabaseMetaData meta = conn.getMetaData();
        System.out.println("The driver name is " + meta.getDriverName());
        System.out.println("A new database has been created.");
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }finally{
      try{
        if(conn != null){
          conn.close();
        }
      }catch(SQLException sqle){
        System.out.println(sqle.getMessage());
      }
    }
    return conn;
  }

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
//        LOG.log(WARNING, String.format("Statement threw Exception on executing this statement: %s \n\t", sqlStatement));
//        LOG.log(WARNING, String.format("%s ", sqle.getMessage()));
        return false;
      }
    }catch(SQLException sqle){
//      LOG.log(SEVERE, String.format("Connection threw Exception when creating Statement: %s ", sqle.getMessage()));
      return false;
    }
  }
  
  
}


/*
 * 
 * 
 * 
 */

package com.loeffler.utilitylibrary.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import static java.util.logging.Level.INFO;
import java.util.logging.Logger;

/**
 *  <p><strong>Database</strong></p>
 *  <em>@author</em>  John Loeffler
 *  
 *  <strong>Contact</strong> 
 *    <em>@Email</em>     John.Loeffler@gmail.com
 *    <em>@Twitter</em>   @ThisDotJohn
 *    <em>@LinkedIn</em>  LinkedIn.com/in/JohnLoeffler
 *    <em>@Github</em>    github.com/JohnLoeffler
 *    <em>@Website</em>   JohnLoeffler.com
 */
abstract class Database {
  private static final Logger LOG = Logger.getLogger(Database.class.getName());
  protected String      DBDriver  = "";
  protected String      DBUrl     = null;
  protected int         Timeout   = 30;
  protected Connection  Conn      = null;
  protected Statement   Stmt      = null;
  protected PreparedStatement PS  = null;
  protected int         NextPKey  = 1;
  public  Database(){}
  
  public  Database(String driver, String url)throws Exception{
    Initialize(driver, url);
  }
  
  private void Initialize(String driver, String url)throws Exception{
    setDBDriver(driver);
    setDBUrl(url);
    setConnection();
    setStatement();
  }
  
  public boolean ShutdownDatabase(){
    if(this.Conn != null){
      try {
        Conn.close();
        return true;
      } catch (SQLException ex) {
        LOG.log(INFO, String.format("Connection threw an SQLException when shutting down database: %s", ex.getMessage()));
        return false;
      }
    }
    return true;
  }

  public void setDBDriver(String DBDriver) {
    this.DBDriver = DBDriver;
  }

  public void setDBUrl(String DBUrl) {
    this.DBUrl = DBUrl;
  }

  public void setTimeout(int Timeout) {
    this.Timeout = Timeout;
  }

  public void setConnection() throws Exception{
    Class.forName(DBDriver);
    Conn = DriverManager.getConnection(DBUrl);
  }

  public void setStatement() throws Exception{
    if(Conn == null){
      setConnection();
    }
    Stmt = Conn.createStatement();
    Stmt.setQueryTimeout(Timeout);
  }
  
  public void setPreparedStatement(String sql) throws Exception {
    if(Conn == null){
      setConnection();
    }
    PS = Conn.prepareStatement(sql);
    PS.setQueryTimeout(Timeout);
  }

  public String getDBDriver() {
    return DBDriver;
  }

  public String getDBUrl() {
    return DBUrl;
  }

  public int getTimeout() {
    return Timeout;
  }

  public Connection getConnection() {
    return Conn;
  }

  public Statement getStatement() {
    return Stmt;
  }
  
  public void executeStatement(String sqlStatement)throws SQLException{
    Stmt.execute(sqlStatement);
  }
  
  public void executeStmt(String[] instructions) throws SQLException {
    for (String s: instructions) {
      executeStatement(s);
    }
  }
  
  public ResultSet executeQuery(String query) throws SQLException{
    return Stmt.executeQuery(query);
  }
  
  public void closeConnection(){
    try{ Conn.close(); } catch (Exception whatever){}
  }
  
}

package com.loeffler.utilitylibrary.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.loeffler.utilitylibrary.Logging.Logger;

/**
 *  <p><strong>Database</strong></p>
 *  <em>@author</em>  John Loeffler
 *  
 *  <strong>Contact</strong> 
 *    <em>@Email</em>     John.Loeffler@gmail.com
 *    <em>@Twitter</em>   @ThisDotJohn
 *    <em>@LinkedIn</em>  LinkedIn.com/in/JohnLoeffler
 *    <em>@Github</em>    Github.com/JohnLoeffler
 *    <em>@Bitbucket</em> Bitbucket.org/JohnLoeffler
 */
abstract class Database {
  protected static      Logger LOG = Logger.GetInstance();
  protected String      DBDriver  = "";
  protected String      DBUrl     = null;
  protected int         Timeout   = 30;
  protected Connection  Conn      = null;
  protected Statement   Stmt      = null;
  protected PreparedStatement PS  = null;
  protected int         NextPKey  = 1;
  /**
  * Default constructor
  */
  public  Database(){}
  //  TODO Add Javadocs
  public  Database(String driver, String url)throws Exception{
    Initialize(driver, url);
  }
  //  TODO Add Javadocs
  private void Initialize(String driver, String url)throws Exception{
    setDBDriver(driver);
    setDBUrl(url);
    setConnection();
    setStatement();
  }
  //  TODO Add Javadocs
  public boolean ShutdownDatabase(){
    if(this.Conn != null){
      try {
        Conn.close();
        return true;
      } catch (SQLException ex) {
        //  TODO  Update the logging for thrown exceptions
        return false;
      }
    }
    return true;
  }
  //  TODO Add Javadocs
  public void setDBDriver(String DBDriver) {
    this.DBDriver = DBDriver;
  }
  //  TODO Add Javadocs
  public void setDBUrl(String DBUrl) {
    this.DBUrl = DBUrl;
  }
  //  TODO Add Javadocs
  public void setTimeout(int Timeout) {
    this.Timeout = Timeout;
  }
  //  TODO Add Javadocs
  public void setConnection() throws Exception{
    Class.forName(DBDriver);
    Conn = DriverManager.getConnection(DBUrl);
  }
  //  TODO Add Javadocs
  public void setStatement() throws Exception{
    if(Conn == null){
      setConnection();
    }
    Stmt = Conn.createStatement();
    Stmt.setQueryTimeout(Timeout);
  }
  //  TODO Add Javadocs
  public void setPreparedStatement(String sql) throws Exception {
    if(Conn == null){
      setConnection();
    }
    PS = Conn.prepareStatement(sql);
    PS.setQueryTimeout(Timeout);
  }
  //  TODO Add Javadocs
  public String getDBDriver() {
    return DBDriver;
  }
  //  TODO Add Javadocs
  public String getDBUrl() {
    return DBUrl;
  }
  //  TODO Add Javadocs
  public int getTimeout() {
    return Timeout;
  }
  //  TODO Add Javadocs
  public Connection getConnection() {
    return Conn;
  }
  //  TODO Add Javadocs
  public Statement getStatement() {
    return Stmt;
  }
  //  TODO Add Javadocs
  public void executeStatement(String sqlStatement)throws SQLException{
    Stmt.execute(sqlStatement);
  }
  //  TODO Add Javadocs
  public void executeStmt(String[] instructions) throws SQLException {
    for (String s: instructions) {
      executeStatement(s);
    }
  }
  //  TODO Add Javadocs
  public ResultSet executeQuery(String query) throws SQLException{
    return Stmt.executeQuery(query);
  }
  //  TODO Add Javadocs
  public void closeConnection(){
    try{ Conn.close(); } catch (Exception whatever){}
  }
}

package org.autumn.framework.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author wincher
 * @since 2018/12/6
 * <p> org.autumn.framework.util <p>
 */
public class DBUtil {
  
  private static final String driver = "com.mysql.jdbc.Driver";
  private static final String url = "jdbc:mysql://localhost:3306/my_framework";
  private static final String username = "root";
  private static final String password = "";
  
  private static ThreadLocal<Connection> connContainer = new ThreadLocal<>();
  
  public static Connection getConnection() {
    Connection conn = connContainer.get();
    try {
      if (null == conn) {
        Class.forName(driver);
        conn = DriverManager.getConnection(url, username, password);
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      connContainer.set(conn);
    }
    return conn;
  }
  
  public static void closeConnection() {
    Connection conn = connContainer.get();
    try {
      if (null != conn) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      connContainer.remove();
    }
  }
}

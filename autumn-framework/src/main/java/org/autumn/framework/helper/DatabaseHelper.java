package org.autumn.framework.helper;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.autumn.framework.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author wincher
 * @since 2018/11/23
 * <p> org.autumn.framework.helper <p>
 */
public final class DatabaseHelper {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
  private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();
  
  private static final QueryRunner QUERY_RUNNER = new QueryRunner();
  
  private static final String DRIVER;
  private static final String URL;
  private static final String USERNAME;
  private static final String PASSWORD;
  private static Connection conn;
  
  static {
    Properties conf = PropsUtil.loadProps("config.properties");
    DRIVER = conf.getProperty("jdbc.driver");
    URL = conf.getProperty("jdbc.url");
    USERNAME = conf.getProperty("jdbc.username");
    PASSWORD = conf.getProperty("jdbc.password");
    
    try {
      Class.forName(DRIVER);
    } catch (ClassNotFoundException e) {
      LOGGER.error("can not load jdbc driver", e);
    }
  }
  
  /**
   * get connection
   * @return
   */
  public static Connection getConnection() {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    } catch (SQLException e) {
      LOGGER.error("get connection failure", e);
    } finally {
      CONNECTION_HOLDER.set(conn);
    }
    return conn;
  }
  
  /**
   * close connection
   */
  public static void cloaseConnection() {
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        LOGGER.error("close connection failure", e);
        throw new RuntimeException(e);
      } finally {
        CONNECTION_HOLDER.remove();
      }
    }
  }
  
  /**
   * 查询返回实体列表
   * @param entityClass
   * @param sql
   * @param params
   * @param <T>
   * @return
   */
  public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
    List<T> entityList;
    try {
      Connection conn = getConnection();
      entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
    } catch (SQLException e) {
      LOGGER.error("query entity list failure", e);
      throw  new RuntimeException(e);
    } finally {
      cloaseConnection();
    }
    return entityList;
  }
  
  /**
   * 查询实体
   * @param entityClass
   * @param sql
   * @param params
   * @param <T>
   * @return
   */
  public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
    T entity;
    try {
      Connection conn = getConnection();
      entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
    } catch (SQLException e) {
      LOGGER.error("query entity failure", e);
      throw  new RuntimeException(e);
    } finally {
      cloaseConnection();
    }
    return entity;
  }
  
  /**
   * 查询返回Map类型列表
   * @param sql
   * @param params
   * @return
   */
  public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
    List<Map<String, Object>> result;
    try {
      Connection conn = getConnection();
      result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
    } catch (SQLException e) {
      LOGGER.error("execute query failure", e);
      throw  new RuntimeException(e);
    } finally {
      cloaseConnection();
    }
    return result;
  }
  
  /**
   * 执行更新语句
   * @param sql
   * @param params
   * @return
   */
  public static int executeUpdate(String sql, Object... params) {
    int rows = 0;
    try {
      Connection conn = getConnection();
      rows = QUERY_RUNNER.update(conn, sql, params);
    } catch (SQLException e) {
      LOGGER.error("exexute update failure", e);
      throw new RuntimeException(e);
    } finally {
      cloaseConnection();
    }
    return rows;
  }
  
  /**
   * 执行sql文件
   * @param filePath
   */
  public static void excuteSqlFile(String filePath) {
    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    try {
      String sql;
      while ((sql = reader.readLine()) != null) {
        if (!"".equals(sql.replaceAll(" ", ""))) {
          executeUpdate(sql);
        }
      }
    } catch (IOException e) {
      LOGGER.error("excute sql file failure", e);
      throw new RuntimeException(e);
    }
  }
  
  public static void beginTransaction() {
    Connection conn = getConnection();
    if (null != conn) {
      try {
        conn.setAutoCommit(false);
      } catch (SQLException e) {
        LOGGER.error("begin transaction failure", e);
        throw new RuntimeException(e);
      } finally {
        CONNECTION_HOLDER.set(conn);
      }
    }
  }
  
  public static void commitTranscation() {
    Connection conn = getConnection();
    if (null != conn) {
      try {
        conn.commit();
        conn.close();
      } catch (SQLException e) {
        LOGGER.error("commit transaction failure", e);
        throw new RuntimeException(e);
      } finally {
        CONNECTION_HOLDER.remove();
      }
    }
  }
  
  /**
   * rollback transaction
   */
  public static void rollbackTransaction() {
    Connection conn = getConnection();
    if (null != conn) {
      try {
        conn.rollback();
      } catch (SQLException e) {
        LOGGER.error("rollback transaction failure", e);
        throw new RuntimeException(e);
      } finally {
        CONNECTION_HOLDER.remove();
      }
    }
  }
}

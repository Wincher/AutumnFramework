package org.autumn.framework.helper;

import org.autumn.framework.ConfigConstant;
import org.autumn.framework.util.PropsUtil;

import java.util.Properties;

/**
 * 配置文件工具类
 * @author wincher
 * @since 2018/9/2
 * <p> org.autumn.framework.helper <p>
 */
public final class ConfigHelper {
  private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);
  
  /**
   * get jdbc driver
   * @return
   */
  public static String getJdbcDriver() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
  }
  
  /**
   * get jdbc url
   * @return
   */
  public static String getJdbcUrl() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
  }
  
  /**
   * get jdbc username
   * @return
   */
  public static String getJdbcUsername() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
  }
  
  /**
   * get jdbc password
   * @return
   */
  public static String getJdbcPassword() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.PASSWORD);
  }
  
  /**
   * get app base package
   * @return
   */
  public static String getAppBasePackage() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
  }
  
  /**
   * get app jsp path
   * @return
   */
  public static String getAppJspPath() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH, "/WEB_INF/views/");
  }
  
  /**
   * get app asset path
   * @return
   */
  public static String getAssetJspPath() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH, "/asset/");
  }
  
  /**
   * get app asset path
   * @return
   */
  public static int getAppUploadLimit() {
    return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.APP_UPLOAD_LIMIT, 10);
  }
}

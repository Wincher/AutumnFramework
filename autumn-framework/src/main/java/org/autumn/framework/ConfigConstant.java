package org.autumn.framework;

/**
 * 配置相关常量
 * @author wincher
 * @since 2018/9/2
 * <p> org.autumn.framework <p>
 */
public interface ConfigConstant {
  String CONFIG_FILE = "autumn.properties";
  String JDBC_DRIVER = "autumn.framework.jdbc.drive";
  String JDBC_URL = "autumn.framework.jdbc.url";
  String JDBC_USERNAME = "autumn.framework.jdbc.username";
  String PASSWORD = "autumn.framework.jdbc.password";
  
  String APP_BASE_PACKAGE = "autumn.framework.app.base_package";
  String APP_JSP_PATH = "autumn.framework.app.jsp_path";
  String APP_ASSET_PATH = "autumn.framework.app.asset_path";
  
  String APP_UPLOAD_LIMIT = "autumn.framework.app.upload_limit";
  
}

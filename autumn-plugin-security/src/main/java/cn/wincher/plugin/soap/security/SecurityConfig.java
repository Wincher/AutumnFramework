package cn.wincher.plugin.soap.security;

import org.autumn.framework.helper.ConfigHelper;
import org.autumn.framework.util.ReflectionUtil;

/**
 * @author wincher
 * @since 2019-01-21
 * <p> cn.wincher.plugin.security <p>
 */
public class SecurityConfig {
  public static boolean isCache() {
    return ConfigHelper.getBoolean(SecurityConstant.CACHEABLE);
  }
  
  public static String getRealms() {
    return ConfigHelper.getString(SecurityConstant.REALMS);
  }
  
  public static AutumnSecurity getSmartSecurity() {
    String className = ConfigHelper.getString(SecurityConstant.AUTUMN_SECURITY);
    return (AutumnSecurity) ReflectionUtil.newInstance(className);
  }
  
  public static String getJdbcPermissionsQuery() {
    return ConfigHelper.getString(SecurityConstant.JDBC_PERMISSION_QUERY);
  }
  
  public static String getJdbcRolesQuery() {
    return ConfigHelper.getString(SecurityConstant.JDBC_ROLES_QUERY);
  }
  
  public static String getJdbcAuthcQuery() {
    return ConfigHelper.getString(SecurityConstant.JDBC_AUTHC_QUERY);
  }
}

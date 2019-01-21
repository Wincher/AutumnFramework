package cn.wincher.plugin.security;

/**
 * @author wincher
 * @since 2019-01-21
 * <p> cn.wincher.plugin.security <p>
 */
public class SecurityConfig {
  public static boolean isCache() {
    return false;
  }
  
  public static String getRealms() {
    return null;
  }
  
  public static AutumnSecurity getSmartSecurity() {
    return null;
  }
}

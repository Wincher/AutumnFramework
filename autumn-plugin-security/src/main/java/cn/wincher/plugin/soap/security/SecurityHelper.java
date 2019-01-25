package cn.wincher.plugin.soap.security;

import cn.wincher.plugin.soap.security.exception.AuthcException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @author wincher
 * @since 2019-01-23
 * <p> cn.wincher.plugin.security <p>
 */
public final class SecurityHelper {
  private static final Logger LOGGER = LoggerFactory.getLogger(SecurityHelper.class);
  
  /**
   * login
   */
  public static void login(String username, String password) throws AuthcException {
    Subject currentUser  = SecurityUtils.getSubject();
    if (Objects.isNull(currentUser)) {
      UsernamePasswordToken token = new UsernamePasswordToken(username, password);
      try {
        currentUser.login(token);
      } catch (AuthenticationException e) {
        LOGGER.error("login failure", e);
        throw new AuthcException(e);
      }
    }
  }
  
  
  /**
   * logout
   */
  public static void logout() {
    Subject currentUser  = SecurityUtils.getSubject();
    if (Objects.nonNull(currentUser)) {
      currentUser.logout();
    }
  }
}

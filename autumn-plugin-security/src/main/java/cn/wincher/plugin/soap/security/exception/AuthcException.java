package cn.wincher.plugin.soap.security.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author wincher
 * @since 2019-01-23
 * <p> cn.wincher.plugin.security.exception <p>
 */
public class AuthcException extends Exception {
  
  public AuthcException(AuthenticationException e) {
  }
}

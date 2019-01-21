package cn.wincher.plugin.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.Realm;

/**
 * @author wincher
 * @since 2019-01-21
 * <p> cn.wincher.plugin.security <p>
 */
public class AutumnCustomRealm implements Realm {
  public AutumnCustomRealm(AutumnSecurity autumnSecurity) {
  
  }
  
  @Override
  public String getName() {
    return null;
  }
  
  @Override
  public boolean supports(AuthenticationToken authenticationToken) {
    return false;
  }
  
  @Override
  public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
    return null;
  }
}

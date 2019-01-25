package cn.wincher.plugin.soap.security;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.autumn.framework.util.CollectionUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author wincher
 * @since 2019-01-21
 * <p> cn.wincher.plugin.security <p>
 */
public class AutumnCustomRealm extends AuthorizingRealm {
  
  private final AutumnSecurity autumnSecurity;
  
  public AutumnCustomRealm(AutumnSecurity autumnSecurity) {
    this.autumnSecurity = autumnSecurity;
    super.setName(SecurityConstant.REALMS_CUSTOM);
    super.setCredentialsMatcher(new MD5CredentialsMatcher());
  }
  
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
    if (Objects.isNull(authenticationToken)) {
      throw new AuthenticationException("parameter token is null");
    }
    String username = ((UsernamePasswordToken) authenticationToken).getUsername();
    String password = autumnSecurity.getPassword(username);
  
    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
    authenticationInfo.setPrincipals(new SimplePrincipalCollection(username, super.getName()));
    authenticationInfo.setCredentials(password);
    return authenticationInfo;
  }
  
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    if (Objects.isNull(principalCollection)) {
      throw new AuthorizationException("parameter principals is null");
    }
    String username = (String) super.getAvailablePrincipal(principalCollection);
    Set<String> roleNameSet = autumnSecurity.getRoleNameSet(username);
    Set<String> permissionNameSet = new HashSet<>();
    if (CollectionUtil.isNotEmpty(roleNameSet)) {
      for (String roleName : roleNameSet) {
        Set<String> currentPermisisonNameSet = autumnSecurity.getPermissionNameSet(roleName);
        permissionNameSet.addAll(currentPermisisonNameSet);
      }
    }
    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    authorizationInfo.setRoles(roleNameSet);
    authorizationInfo.setStringPermissions(permissionNameSet);
    return authorizationInfo;
  }
}

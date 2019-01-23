package cn.wincher.plugin.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.autumn.framework.util.CodecUtil;

/**
 * @author wincher
 * @since 2019-01-22
 * <p> cn.wincher.plugin.security <p>
 */
public class MD5CredentialsMatcher implements CredentialsMatcher {
  @Override
  public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
    String submitted = String.valueOf(((UsernamePasswordToken)authenticationToken).getPassword());
    String encrypted = String.valueOf(authenticationInfo.getCredentials());
    return CodecUtil.md5(submitted).equals(encrypted);
  }
}

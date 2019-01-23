package cn.wincher.plugin.security;

import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.autumn.framework.helper.DatabaseHelper;

/**
 * @author wincher
 * @since 2019-01-21
 * <p> cn.wincher.plugin.security <p>
 */
public class AutumnJdbcRealm extends JdbcRealm {
  
  public AutumnJdbcRealm() {
    super.setDataSource(DatabaseHelper.getDataSource());
    super.setAuthenticationQuery(SecurityConfig.getJdbcAuthcQuery());
    super.setUserRolesQuery(SecurityConfig.getJdbcRolesQuery());
    super.setPermissionsQuery(SecurityConfig.getJdbcPermissionsQuery());
    super.setPermissionsLookupEnabled(true);
    super.setCredentialsMatcher(new MD5CredentialsMatcher());
  }
}

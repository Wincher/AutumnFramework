package cn.wincher.plugin.soap.security;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.CachingSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author wincher
 * @since 2019-01-21
 * <p> cn.wincher.plugin.security <p>
 */
public class AutumnSecurityFilter extends ShiroFilter {
  
  @Override
  public void init() throws Exception {
    super.init();
    WebSecurityManager webSecurityManager = super.getSecurityManager();
    //set realm, support multiple realms, split by ',' by order.
    setRealms(webSecurityManager);
    //set cache
    setCache(webSecurityManager);
  }
  
  private void setRealms(WebSecurityManager webSecurityManager) {
    //read config smart.plugin.security.realms
    String securityRealms = SecurityConfig.getRealms();
    if (Objects.nonNull(securityRealms)) {
      //split by ','
      String[] securityRealmArray = securityRealms.split(",");
      if (0 < securityRealmArray.length) {
        //realm 具有唯一性和顺序性
        Set<Realm> realms = new LinkedHashSet<>();
        for (String securityRealm : securityRealmArray) {
          if (securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_JDBC)) {
            addJdbcRealm(realms);
          } else if (securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_CUSTOM)) {
            //add custom Realm need to implement AutumnSecurity
            addCustomRealm(realms);
          }
          RealmSecurityManager realmSecurityManager = (RealmSecurityManager) webSecurityManager;
          realmSecurityManager.setRealms(realms);
        }
      }
    }
  }
  
  private void addJdbcRealm(Set<Realm> realms) {
    AutumnJdbcRealm autumnJdbcRealm = new AutumnJdbcRealm();
    realms.add(autumnJdbcRealm);
  }
  
  private void addCustomRealm(Set<Realm> realms) {
    //read config smart.plugin.security.custom.class
    AutumnSecurity autumnSecurity = SecurityConfig.getSmartSecurity();
    
    AutumnCustomRealm autumnCustomRealm = new AutumnCustomRealm(autumnSecurity);
    realms.add(autumnCustomRealm);
  }
  
  private void setCache(WebSecurityManager webSecurityManager) {
    //read smart.plugin.security.cache config
    if (SecurityConfig.isCache()) {
      CachingSecurityManager cachingSecurityManager = (CachingSecurityManager) webSecurityManager;
      //use ram cache
      CacheManager cacheManager = new MemoryConstrainedCacheManager();
      cachingSecurityManager.setCacheManager(cacheManager);
    }
  }
  
  
}

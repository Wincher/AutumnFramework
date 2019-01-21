package cn.wincher.plugin.security;

import org.apache.shiro.web.env.EnvironmentLoaderListener;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * @author wincher
 * @since 2019-01-21
 * <p> cn.wincher.plugin.security <p>
 */
public class AutumnSercurityPlugin implements ServletContainerInitializer {
  @Override
  public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
    ctx.setInitParameter("shiroConfigLocations", "class-path:autumn-security.ini");
    
    ctx.addListener(EnvironmentLoaderListener.class);
    
    FilterRegistration.Dynamic autumnSecuirtyFilter = ctx.addFilter("AutumnSecurityFilter", AutumnSecurityFilter.class);
    autumnSecuirtyFilter.addMappingForUrlPatterns(null, false, "/*");
  }
}

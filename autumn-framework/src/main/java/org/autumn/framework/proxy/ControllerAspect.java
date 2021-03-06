package org.autumn.framework.proxy;

import org.autumn.framework.annotation.Aspect;
import org.autumn.framework.annotation.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author wincher
 * @since 2018/9/29
 * <p> org.autumn.framework.proxy <p>
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);
  
  private long begin;
  
  @Override
  public void before(Class<?> cls, Method method, Object[] params) {
    LOGGER.debug("----- begin ------");
    LOGGER.debug(String.format("class: %s", cls.getName()));
    LOGGER.debug(String.format("method: %s", method.getName()));
    begin = System.currentTimeMillis();
  }
  
  @Override
  public void after(Class<?> cls, Method method, Object[] params, Object result) {
    LOGGER.debug(String.format("time: %dms", System.currentTimeMillis() - begin));
    LOGGER.debug("----- end ------");
  }
}

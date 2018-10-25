package org.autumn.framework.helper;

import org.autumn.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wincher
 * @since 2018/9/4
 * <p> org.autumn.framework.helper <p>
 */
public final class BeanHelper {
  
  private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();
  
  static {
    Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
    beanClassSet.stream().forEach(aClass -> {
      Object obj = ReflectionUtil.newInstance(aClass);
      BEAN_MAP.put(aClass, obj);
    });
  }
  
  public static Map<Class<?>, Object> getBeanMap() {
    return BEAN_MAP;
  }
  
  public static <T> T getBean(Class<T> cls) {
    if (!BEAN_MAP.containsKey(cls)) {
      throw new RuntimeException("can not get bean by class: " + cls);
    }
    return (T) BEAN_MAP.get(cls);
  }
  
  public static void setBean(Class<?> cls, Object obj) {
    BEAN_MAP.put(cls, obj);
  }
  
}

package org.autumn.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wincher
 * @since 2018/9/4
 * <p> org.autumn.framework.util <p>
 */
public final class ReflectionUtil {
  
  private static Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);
  
  /**
   *
   * @param cls
   * @return
   */
  public static Object newInstance(Class<?> cls) {
    Object instance;
    try {
      instance = cls.newInstance();
    } catch (IllegalAccessException e) {
      LOGGER.error("new instance failure", e);
      throw new RuntimeException(e);
    } catch (InstantiationException e) {
      LOGGER.error("new instance failure", e);
      throw new RuntimeException(e);
    }
    return instance;
  }
  
  public static Object newInstance(String className) {
    try {
      Class clazz = Class.forName(className);
      return newInstance(clazz);
    } catch (ClassNotFoundException e) {
      LOGGER.error("new instance failure", e);
      throw new RuntimeException(e);
    }
  }
  
  public static Object invokeMethod(Object obj, Method method, Object... args) {
    Object result;
    try {
      method.setAccessible(true);
      result = method.invoke(obj, args);
    } catch (IllegalAccessException e) {
      LOGGER.error("invoke method failure", e);
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      LOGGER.error("invoke method failure", e);
      throw new RuntimeException(e);
    }
    return result;
  }
  
  public static void setField(Object obj, Field field, Object value) {
    field.setAccessible(true);
    try {
      field.set(obj, value);
    } catch (IllegalAccessException e) {
      LOGGER.error("set field failure", e);
      throw new RuntimeException(e);
    }
  }
}

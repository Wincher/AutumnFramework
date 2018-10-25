package org.autumn.framework.helper;

import org.autumn.framework.annotation.Controller;
import org.autumn.framework.annotation.Service;
import org.autumn.framework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wincher
 * @since 2018/9/4
 * <p> org.autumn.framework.helper <p>
 */
public final class ClassHelper {
  
  private static final Set<Class<?>> CLASS_SET;
  
  static {
    String basePackage = ConfigHelper.getAppBasePackage();
    CLASS_SET = ClassUtil.getClassSet(basePackage);
  }
  
  /**
   *
   * @return
   */
  public static Set<Class<?>> getClassSet() {
    return CLASS_SET;
  }
  
  /**
   *
   * @return
   */
  public static Set<Class<?>> getServiceClassSet() {
    return CLASS_SET.stream().filter(aClass -> aClass.isAnnotationPresent(Service.class)).collect(Collectors.toSet());
  }
  
  /**
   *
   * @return
   */
  public static Set<Class<?>> getControllerClassSet() {
    return CLASS_SET.stream().filter(aClass -> aClass.isAnnotationPresent(Controller.class)).collect(Collectors.toSet());
  }
  
  /**
   *
   * @return
   */
  public static Set<Class<?>> getBeanClassSet() {
    Set<Class<?>> classSet = new HashSet<>();
    classSet.addAll(getControllerClassSet());
    classSet.addAll(getServiceClassSet());
    return classSet;
  }
  
  /**
   * 获取父类或接口所有子类或实现类
   * @param superClass
   * @return
   */
  public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
    return CLASS_SET.stream().filter( aClass -> superClass.isAssignableFrom(aClass) && !superClass.equals(aClass)).collect(Collectors.toSet());
  }
  
  /**
   * 获取带有某注解的所有类
   * @param annotationClass
   * @return
   */
  public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
    return CLASS_SET.stream().filter( aClass -> aClass.isAnnotationPresent(annotationClass)).collect(Collectors.toSet());
  }
  
}

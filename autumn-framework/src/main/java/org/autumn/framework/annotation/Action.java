package org.autumn.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wincher
 * @since 2018/9/3
 * <p> org.autumn.framework.annotation <p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
  /**
   * 请求类型与路径
   * @return
   */
  String value();
}

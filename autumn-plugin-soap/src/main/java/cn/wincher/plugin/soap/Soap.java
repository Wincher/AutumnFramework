package cn.wincher.plugin.soap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wincher
 * @since 2019-01-25
 * <p> cn.wincher.plugin <p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Soap {
  /**
   * service name
   */
  String value() default "";
}

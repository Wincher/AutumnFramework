package org.autumn.framework.annotation;

import java.lang.annotation.*;

/**
 * @author wincher
 * @since 2018/9/29
 * <p> org.autumn.framework.annotation <p>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
  Class<? extends Annotation> value();
}

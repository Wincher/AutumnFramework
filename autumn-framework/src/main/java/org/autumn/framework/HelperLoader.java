package org.autumn.framework;

import org.autumn.framework.helper.*;
import org.autumn.framework.util.ClassUtil;

import java.util.Arrays;

/**
 * @author wincher
 * @since 2018/9/5
 * <p> org.autumn.framework <p>
 */
public final class HelperLoader {

  public static void init() {
    Class<?>[] classList = {
        ClassHelper.class,
        BeanHelper.class,
        AopHelper.class,
        IocHelper.class,
        ControllerHelper.class,
    };
    Arrays.stream(classList).forEach(aClass -> ClassUtil.loadClass(aClass.getName()));
  }

}

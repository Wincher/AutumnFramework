package org.autumn.framework.util;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;

/**
 * @author wincher
 * @since 2018/9/5
 * <p> org.autumn.framework.util <p>
 */
public final class ArrayUtil {
  
  /**
   *
   * @param array
   * @return
   */
  public static boolean isNotEmpty(Object[] array) {
    return !isEmpty(array);
  }
  
  /**
   *
   * @param array
   * @return
   */
  public static boolean isEmpty(Object[] array) {
    return ArrayUtils.isEmpty(array);
  }
}

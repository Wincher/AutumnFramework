package org.autumn.framework.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @author wincher
 * @since 2018/8/25
 * <p> cn.wincher.util <p>
 */
public class CollectionUtil {
  
  /**
   * 判断Collection是否为空
   * @param collection
   * @return
   */
  public static boolean isEmpty(Collection<?> collection) {
    return CollectionUtils.isEmpty(collection);
  }
  
  /**
   * 判断Collection是否非空
   * @param collection
   * @return
   */
  public static boolean isNotEmpty(Collection<?> collection) {
    return !isEmpty(collection);
  }
  
  /**
   * 判断Map是否为空
   * @param map
   * @return
   */
  public static boolean isEmpty(Map<?, ?> map) {
    return MapUtils.isEmpty(map);
  }
  
  /**
   * 判断Map是否非空
   * @param map
   * @return
   */
  public static boolean isNotEmpty(Map<?, ?> map) {
    return !isEmpty(map);
  }
}

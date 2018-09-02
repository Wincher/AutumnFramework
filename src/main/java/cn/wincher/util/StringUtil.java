package cn.wincher.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author huwq
 * @since 2018/8/25
 * <p> cn.wincher.util <p>
 */
public class StringUtil {
  
  /**
   * 判断字符串是否为空
   * @param str
   * @return
   */
  public static boolean isEmpty(String str) {
    if (str != null) {
      str = str.trim();
    }
    return StringUtils.isEmpty(str);
  }
  
  /**
   * 判断字符串是否非空
   * @param str
   * @return
   */
  public static boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }
}

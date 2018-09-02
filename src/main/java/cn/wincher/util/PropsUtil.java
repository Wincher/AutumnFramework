package cn.wincher.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性文件工具类
 * @author huwq
 * @since 2018/8/25
 * <p> cn.wincher.util <p>
 */
public final class PropsUtil {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);
  
  /**
   * load props file
   * @param fileName
   * @return
   */
  public static Properties loadProps(String fileName) {
    Properties props = null;
    try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
      if (null == is) {
        throw new FileNotFoundException(fileName + " file is not found");
      }
      props = new Properties();
      props.load(is);
    } catch (IOException e) {
      LOGGER.error("load properties file failure", e);
      e.printStackTrace();
    }
    return props;
  }
  
  /**
   * 获取字符型属性(默认值为空字符串)
   * @param props
   * @param key
   * @return
   */
  public static String getString(Properties props, String key) {
    return getString(props, key, "");
  }
  
  /**
   * 获取字符型属性(指定默认值)
   * @param props
   * @param key
   * @param defaultValue
   * @return
   */
  private static String getString(Properties props, String key, String defaultValue) {
    String value = defaultValue;
    if (props.containsKey(key)) {
      value = props.getProperty(key);
    }
    return value;
  }
  
  /**
   * 获取数值型属性(默认值为0)
   * @param props
   * @param key
   * @return
   */
  public static int getInt(Properties props, String key) {
    return getInt(props, key, 0);
  }
  
  /**
   * 获取数值型属性(指定默认值)
   * @param props
   * @param key
   * @param defaultValue
   * @return
   */
  private static int getInt(Properties props, String key, int defaultValue) {
    int value = defaultValue;
    if (props.containsKey(key)) {
      value = CastUtil.castInt(props.getProperty(key));
    }
    return value;
  }
  
  /**
   * 获取布尔型属性(默认值为false)
   * @param props
   * @param key
   * @return
   */
  public static boolean getBoolean(Properties props, String key) {
    return getBoolean(props, key, false);
  }
  
  /**
   * 获取布尔型属性(指定默认值)
   * @param props
   * @param key
   * @param defaultValue
   * @return
   */
  private static boolean getBoolean(Properties props, String key, boolean defaultValue) {
    boolean value = defaultValue;
    if (props.containsKey(key)) {
      value = CastUtil.castBoolean(props.getProperty(key));
    }
    return value;
  }
  
  
}

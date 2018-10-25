package org.autumn.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author wincher
 * @since 2018/9/6
 * <p> org.autumn.framework <p>
 */
public class StreamUtil {
  
  private static Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);
  
  /**
   *
   * @param is
   * @return
   */
  public static String getString(ServletInputStream is) {
    StringBuilder sb = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
      String line;
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
    } catch (IOException e) {
      LOGGER.error("get string from input stream failure", e);
      throw new RuntimeException(e);
    }
    return sb.toString();
  }
}

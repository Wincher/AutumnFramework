package org.autumn.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import java.io.*;

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
  
  /**
   * copy stream
   */
  public static void copyStream(InputStream inputStream, OutputStream outputStream) {
    try {
      int length;
      byte[] buffer = new byte[4 * 1024];
      while ((length = inputStream.read(buffer, 0, buffer.length))!= -1) {
        outputStream.write(buffer, 0, length);
      }
      outputStream.flush();
    } catch (IOException e) {
      LOGGER.error("copy stream failure", e);
      throw new RuntimeException(e);
    } finally {
      try {
        inputStream.close();
        outputStream.close();
      } catch (IOException e) {
        LOGGER.error("close stream failure", e);
      }
    }
  }
}

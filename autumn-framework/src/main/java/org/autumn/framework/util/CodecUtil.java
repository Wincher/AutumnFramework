package org.autumn.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author wincher
 * @since 2018/9/6
 * <p> org.autumn.framework.util <p>
 */
public final class CodecUtil {
  
  private static Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);
  
  /**
   *
   * @param source
   * @return
   */
  public static String encodeUrl(String source) {
    String target;
    try {
      target = URLEncoder.encode(source, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      LOGGER.error("encode url failure", e);
      throw new RuntimeException(e);
    }
    return target;
  }
  
  /**
   *
   * @param source
   * @return
   */
  public static String decodeUrl(String source) {
    String target;
    try {
      target = URLDecoder.decode(source, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      LOGGER.error("decode url failure", e);
      throw new RuntimeException(e);
    }
    return target;
  }
}

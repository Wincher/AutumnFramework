package org.autumn.framework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author wincher
 * @since 2018/9/6
 * <p> org.autumn.framework.util <p>
 */
public final class JsonUtil {
  
  private static Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
  
  private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  
  /**
   *
   * @param object
   * @return
   */
  public static <T> String toJson(T object) {
    String json;
    try {
      json = OBJECT_MAPPER.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      LOGGER.error("convert POJO to JSON failure", e);
      throw new RuntimeException(e);
    }
  
    return json;
  }
  
  /**
   *
   * @param json
   * @param type
   * @param <T>
   * @return
   */
  public static <T> T fromJson(String json, Class<T> type) {
    T pojo;
    try {
      pojo = OBJECT_MAPPER.readValue(json, type);
    } catch (IOException e) {
      LOGGER.error("convert JSON to POJO failure", e);
      throw new RuntimeException(e);
    }
  
    return pojo;
  }
}

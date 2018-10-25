package org.autumn.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wincher
 * @since 2018/9/6
 * <p> org.autumn.framework.bean <p>
 */
public class View {
  
  private String path;
  
  private Map<String, Object> model;
  
  /**
   *
   * @param path
   */
  public View(String path) {
    this.path = path;
    this.model = new HashMap<>();
  }
  
  public View addModel(String key, Object value) {
    model.put(key, value);
    return this;
  }
  
  public String getPath() {
    return path;
  }
  
  public Map<String, Object> getModel() {
    return model;
  }
}

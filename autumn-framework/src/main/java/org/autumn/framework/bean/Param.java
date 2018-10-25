package org.autumn.framework.bean;

import org.autumn.framework.util.CastUtil;

import java.util.Map;

/**
 * @author wincher
 * @since 2018/9/6
 * <p> org.autumn.framework.bean <p>
 */
public class Param {
  
  private Map<String, Object> paramMap;
  
  public Param(Map<String, Object> paramMap) {
    this.paramMap = paramMap;
  }
  
  /**
   *
   * @param name
   * @return
   */
  public long getLong(String name) {
    return CastUtil.castLong(paramMap.get(name));
  }
  
  /**
   *
   * @return
   */
  public Map<String, Object> getParamMap() {
    return paramMap;
  }
}

package org.autumn.framework.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author wincher
 * @since 2018/9/5
 * <p> org.autumn.framework.bean <p>
 */
public class Request {

  private String requestMethod;
  
  private String requestPath;
  
  public Request(String requestMethod, String requestPath) {
    this.requestMethod = requestMethod;
    this.requestPath = requestPath;
  }
  
  public String getRequestMethod() {
    return requestMethod;
  }
  
  public String getRequestPath() {
    return requestPath;
  }
  
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }
  
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }
}

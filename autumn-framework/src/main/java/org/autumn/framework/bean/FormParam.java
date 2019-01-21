package org.autumn.framework.bean;

/**
 * @author wincher
 * @since 2019/1/9
 * <p> org.autumn.framework.bean <p>
 */
public class FormParam {
  
  private String fieldName;
  private Object fieldValue;
  
  public FormParam(String fieldName, Object fieldValue) {
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }
  
  public String getFieldName() {
    return fieldName;
  }
  
  public Object getFieldValue() {
    return fieldValue;
  }
}

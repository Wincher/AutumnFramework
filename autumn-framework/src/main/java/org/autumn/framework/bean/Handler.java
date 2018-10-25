package org.autumn.framework.bean;

import java.lang.reflect.Method;

/**
 * @author wincher
 * @since 2018/9/5
 * <p> org.autumn.framework.bean <p>
 */
public class Handler {
  
  /**
   * Controller class
   */
  private Class<?> controllerClass;
  
  /**
   * Action method
   */
  private Method actionMethod;
  
  public Handler(Class<?> controllerClass, Method actionMethod) {
    this.controllerClass = controllerClass;
    this.actionMethod = actionMethod;
  }
  
  public Class<?> getControllerClass() {
    return controllerClass;
  }
  
  public Method getActionMethod() {
    return actionMethod;
  }
}

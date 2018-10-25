package org.autumn.framework.helper;

import org.autumn.framework.annotation.Action;
import org.autumn.framework.bean.Handler;
import org.autumn.framework.bean.Request;
import org.autumn.framework.util.ArrayUtil;
import org.autumn.framework.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wincher
 * @since 2018/9/5
 * <p> org.autumn.framework.helper <p>
 */
public final class ControllerHelper {
  
  /**
   * request, handler map
   */
  private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();
  
  static {
    Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
    if (CollectionUtil.isNotEmpty(controllerClassSet)) {
      controllerClassSet.stream().forEach(controllerClass -> {
        Method[] methods = controllerClass.getDeclaredMethods();
        if (ArrayUtil.isNotEmpty(methods)) {
          Arrays.stream(methods).filter(method -> method.isAnnotationPresent(Action.class)).forEach(method -> {
            Action action = method.getAnnotation(Action.class);
            String mapping = action.value();
            if (mapping.matches("\\w+:/\\w*")) {
              String[] array = mapping.split(":");
              if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                Request request = new Request(array[0], array[1]);
                Handler handler = new Handler(controllerClass, method);
                ACTION_MAP.put(request, handler);
              }
            }
          });
        }
      });
    }
  }
  
  /**
   * get handler
   * @param requestMethod
   * @param requestPath
   * @return
   */
  public static Handler getHandler(String requestMethod, String requestPath) {
    return ACTION_MAP.get(new Request(requestMethod, requestPath));
  }
}

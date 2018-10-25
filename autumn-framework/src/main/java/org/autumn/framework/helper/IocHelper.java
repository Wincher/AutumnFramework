package org.autumn.framework.helper;

import org.autumn.framework.annotation.Inject;
import org.autumn.framework.util.ArrayUtil;
import org.autumn.framework.util.CollectionUtil;
import org.autumn.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

/**
 * @author wincher
 * @since 2018/9/4
 * <p> org.autumn.framework.helper <p>
 */
public final class IocHelper {

  static {
    Map<Class<?>,  Object> beanMap = BeanHelper.getBeanMap();
    if (CollectionUtil.isNotEmpty(beanMap)) {
      beanMap.entrySet().stream().forEach( entry -> {
        Class<?> beanClass = entry.getKey();
        Object beanInstance = entry.getValue();
        Field[] beanFields = beanClass.getDeclaredFields();
        if (ArrayUtil.isNotEmpty(beanFields)) {
          Arrays.stream(beanFields).forEach(field -> {
            if (field.isAnnotationPresent(Inject.class)){
              Class<?> beanFieldClass = field.getType();
              Object beanFieldInstance = beanMap.get(beanFieldClass);
              if (null != beanFieldInstance) {
                ReflectionUtil.setField(beanInstance, field, beanFieldInstance);
              }
            }
            
          });
        }
      });
    }
  }

}

package org.autumn.framework.helper;

import org.autumn.framework.annotation.Aspect;
import org.autumn.framework.proxy.AspectProxy;
import org.autumn.framework.proxy.Proxy;
import org.autumn.framework.proxy.ProxyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author wincher
 * @since 2018/9/29
 * <p> org.autumn.framework.helper <p>
 */
public final class AopHelper {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);
  
  private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
    Class<? extends Annotation> annotation = aspect.value();
    if ( null != annotation && !annotation.equals(Aspect.class)) {
      return ClassHelper.getClassSetByAnnotation(annotation);
    }
    return null;
  }
  
  private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
    Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
    Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
    for (Class<?> proxyClass : proxyClassSet) {
      if (proxyClass.isAnnotationPresent(Aspect.class)) {
        Aspect aspect = proxyClass.getAnnotation(Aspect.class);
        Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
        proxyMap.put(proxyClass, targetClassSet);
      }
    }
    return proxyMap;
  }
  
  private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
    Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
    for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
      Class<?> proxyClass = proxyEntry.getKey();
      Set<Class<?>> targetClassSet = proxyEntry.getValue();
      for (Class<?> targetClass : targetClassSet) {
        Proxy proxy = (Proxy) proxyClass.newInstance();
        if (targetMap.containsKey(targetClass)) {
          targetMap.get(targetClass).add(proxy);
        } else {
          List<Proxy> proxyList = new ArrayList<>();
          proxyList.add(proxy);
          targetMap.put(targetClass, proxyList);
        }
      }
    }
    return targetMap;
  }
  
  static {
    try {
      Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
      Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
      for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
        Class<?> targetClass = targetEntry.getKey();
        List<Proxy> proxyList = targetEntry.getValue();
        Object proxy = ProxyManager.create(targetClass, proxyList);
        BeanHelper.setBean(targetClass, proxy);
      }
    } catch (Exception e) {
      LOGGER.error("aop failure", e);
    }
  }
}

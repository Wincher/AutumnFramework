package org.autumn.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import java.util.List;

/**
 * @author wincher
 * @since 2018/9/13
 * <p> org.autumn.framework.proxy <p>
 */
public class ProxyManager {
  @SuppressWarnings("unchecked")
  public static <T> T create(final Class<?> targetClass, final List<Proxy> proxyList) {
    return (T)Enhancer.create(targetClass, (MethodInterceptor) (targetObject, targetMethod, methodParams, methodProxy) ->
        new ProxyChain(targetClass, targetObject, targetMethod, methodProxy, methodParams, proxyList).doProxyChain());
  }
}

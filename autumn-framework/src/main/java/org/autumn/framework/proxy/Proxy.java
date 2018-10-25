package org.autumn.framework.proxy;

/**
 * @author wincher
 * @since 2018/9/12
 * <p> org.autumn.framework.proxy <p>
 */

public interface Proxy {
  /**
   * 执行链式代理
   * @param proxyChain
   * @return
   * @throws Throwable
   */
  Object doProxy(ProxyChain proxyChain) throws Throwable;
  
  
}

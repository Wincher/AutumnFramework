package org.autumn.framework.proxy;

import org.autumn.framework.annotation.Transaction;
import org.autumn.framework.helper.DatabaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author wincher
 * @since 2018/11/23
 * <p> org.autumn.framework.proxy <p>
 */
public class TransactionProxy implements Proxy {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);
  private static final ThreadLocal<Boolean> FLAG_HOLDER = ThreadLocal.withInitial( () -> false);
  
  @Override
  public Object doProxy(ProxyChain proxyChain) throws Throwable {
    Object result;
    boolean flag = FLAG_HOLDER.get();
    Method method = proxyChain.getTargetMethod();
    if (!flag && method.isAnnotationPresent(Transaction.class)) {
      FLAG_HOLDER.set(true);
      try {
        DatabaseHelper.beginTransaction();
        LOGGER.debug("begin transaction");
        result = proxyChain.doProxyChain();
        DatabaseHelper.commitTranscation();
        LOGGER.debug("commit transaction");
      } catch (Exception e) {
        DatabaseHelper.rollbackTransaction();
        LOGGER.debug("rollback transaction");
        throw e;
      } finally {
        FLAG_HOLDER.remove();
      }
    } else {
      result = proxyChain.doProxyChain();
    }
    return result;
  }
}

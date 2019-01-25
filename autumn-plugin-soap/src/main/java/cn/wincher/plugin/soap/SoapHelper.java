package cn.wincher.plugin.soap;

import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wincher
 * @since 2019-01-25
 * <p> cn.wincher.plugin.soap <p>
 */
public final class SoapHelper {
  private static final List<Interceptor<? extends Message>> inInterceptorList = new ArrayList<>();
  private static final List<Interceptor<? extends Message>> outInterceptorList = new ArrayList<>();
  
  static {
    //add logging interceptor
    if (SoapConfig.isLog()) {
      LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
      inInterceptorList.add(loggingInInterceptor);
      LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
      outInterceptorList.add(loggingOutInterceptor);
    }
  }
  
  public static <T> T createClient(String wsdl, Class<? extends T> interfacClass) {
    ClientProxyFactoryBean factoryBean = new ClientProxyFactoryBean();
    factoryBean.setAddress(wsdl);
    factoryBean.setServiceClass(interfacClass);
    factoryBean.setInInterceptors(inInterceptorList);
    factoryBean.setOutInterceptors(outInterceptorList);
    return factoryBean.create(interfacClass);
  }
  
  public static void publishService(String address, Class<?> soapInterfaceClass, Object soapInstance) {
  
  }
}

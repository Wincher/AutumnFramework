package cn.wincher.plugin.soap;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;
import org.autumn.framework.helper.BeanHelper;
import org.autumn.framework.helper.ClassHelper;
import org.autumn.framework.util.CollectionUtil;
import org.autumn.framework.util.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import java.util.Set;

/**
 * @author wincher
 * @since 2019-01-26
 * <p> cn.wincher.plugin.soap <p>
 */
@WebServlet(urlPatterns = SoapConstant.SERVLET_URL, loadOnStartup = 0)
public class SoapServlet extends CXFNonSpringServlet {
  
  @Override
  protected void loadBus(ServletConfig sc) {
    super.loadBus(sc);
    Bus bus = getBus();
    BusFactory.setDefaultBus(bus);
    publishSoapService();
  }
  
  private void publishSoapService() {
    Set<Class<?>> soapClassSet = ClassHelper.getClassSetByAnnotation(Soap.class);
    if (CollectionUtil.isNotEmpty(soapClassSet)) {
      for (Class<?> soapClass : soapClassSet) {
        String address = getAddress(soapClass);
        Class<?> soapInterfaceClass = getSoapInterfaceCLass(soapClass);
        Object soapInstance = BeanHelper.getBean(soapClass);
        SoapHelper.publishService(address, soapInterfaceClass, soapInstance);
      }
    }
  }
  
  private Class<?> getSoapInterfaceCLass(Class<?> soapClass) {
    return soapClass.getInterfaces()[0];
  }
  
  private String getAddress(Class<?> soapClass) {
    String address;
    String value = soapClass.getAnnotation(Soap.class).value();
    if (StringUtil.isNotEmpty(value)) {
      address = value;
    } else {
      address = getSoapInterfaceCLass(soapClass).getSimpleName();
    }
    if (!address.startsWith("/")) {
      address = "/" + address;
    }
    address = address.replaceAll("\\/+", "/");
    return address;
  }
}

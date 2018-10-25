package org.autumn.framework;

import org.autumn.framework.bean.Data;
import org.autumn.framework.bean.Handler;
import org.autumn.framework.bean.Param;
import org.autumn.framework.bean.View;
import org.autumn.framework.helper.BeanHelper;
import org.autumn.framework.helper.ConfigHelper;
import org.autumn.framework.helper.ControllerHelper;
import org.autumn.framework.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wincher
 * @since 2018/9/6
 * <p> org.autumn.framework <p>
 */
public class DispatcherServlet extends HttpServlet {
  
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String requestMethod = req.getMethod().toLowerCase();
    String requestPath = req.getPathInfo();
    Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
    if (null != handler) {
      Class<?> controllerClass = handler.getControllerClass();
      Object controllerBean = BeanHelper.getBean(controllerClass);
      Map<String, Object> paramMap = new HashMap<>();
      Enumeration<String> paramNames = req.getParameterNames();
      while (paramNames.hasMoreElements()) {
        String paramName = paramNames.nextElement();
        String paramValue = req.getParameter(paramName);
        paramMap.put(paramName, paramValue);
      }
      String body = CodecUtil.decodeUrl(StreamUtil.getString(req.getInputStream()));
      if (StringUtil.isNotEmpty(body)) {
        String[] params = StringUtil.splitString(body, "&");
        if (ArrayUtil.isNotEmpty(params)) {
          Arrays.stream(params).forEach( param -> {
            String[] array = StringUtil.splitString(param, "=");
            if (ArrayUtil.isNotEmpty(array)) {
              paramMap.put(array[0], array[1]);
            }
          });
        }
      }
      Param param = new Param(paramMap);
      Method actionMethod = handler.getActionMethod();
      Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
      if (result instanceof View) {
        View view = (View) result;
        String path = view.getPath();
        if (StringUtil.isNotEmpty(path)) {
          if (path.startsWith("/")) {
            resp.sendRedirect(req.getContextPath() + path);
          } else {
            Map<String, Object> model = view.getModel();
            model.entrySet().stream().forEach(entry -> {
              req.setAttribute(entry.getKey(), entry.getValue());
            });
          }
          req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
        }
      } else if (result instanceof Data) {
        Data data = (Data) result;
        Object model = data.getModel();
        if (null != model) {
          resp.setContentType("application/json");
          resp.setCharacterEncoding("UTF-8");
          PrintWriter writer = resp.getWriter();
          String json = JsonUtil.toJson(model);
          writer.write(json);
          writer.flush();
          writer.close();
        }
      }
    }
  }
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    HelperLoader.init();
    //get servletContext used to register servlet
    ServletContext servletContext =config.getServletContext();
    ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
    jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
    ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
    defaultServlet.addMapping(ConfigHelper.getAssetJspPath() + "*");
  }
}

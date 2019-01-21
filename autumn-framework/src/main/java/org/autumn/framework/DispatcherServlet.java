package org.autumn.framework;

import org.autumn.framework.bean.Data;
import org.autumn.framework.bean.Handler;
import org.autumn.framework.bean.Param;
import org.autumn.framework.bean.View;
import org.autumn.framework.helper.*;
import org.autumn.framework.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
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
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
  
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ServletHelper.init(req, resp);
    try {
      String requestMethod = req.getMethod().toLowerCase();
      String requestPath = req.getPathInfo();
  
      if (requestPath.equals("/favicon.ico")) {
        return;
      }
  
      Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
      if (null != handler) {
        Class<?> controllerClass = handler.getControllerClass();
        Object controllerBean = BeanHelper.getBean(controllerClass);
    
        Param param;
        if (UploadHelper.isMultipart(req)) {
          param = UploadHelper.createParam(req);
        } else {
          param = RequestHelper.createParam(req);
        }
        Method actionMethod = handler.getActionMethod();
        Object result;
        if (param.isEmpty()) {
          result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
        } else {
          result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
        }
        if (result instanceof View) {
          handleViewResult((View) result, resp, req);
        } else if (result instanceof Data) {
          handleDataResult((Data) result, resp);
        }
      }
    } finally {
      ServletHelper.destory();
    }
  }
  
  private void handleDataResult(Data result, HttpServletResponse resp) throws IOException {
    Data data = result;
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
  
  private void handleViewResult(View result, HttpServletResponse resp, HttpServletRequest req) throws IOException, ServletException {
    View view = result;
    String path = view.getPath();
    if (StringUtil.isNotEmpty(path)) {
      if (path.startsWith("/")) {
        resp.sendRedirect(req.getContextPath() + path);
      } else {
        Map<String, Object> model = view.getModel();
        model.entrySet().stream().forEach(entry -> {
          req.setAttribute(entry.getKey(), entry.getValue());
        });
        req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
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
  
    UploadHelper.init(servletContext);
  }
}

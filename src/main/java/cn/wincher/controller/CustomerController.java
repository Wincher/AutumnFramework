package cn.wincher.controller;

import cn.wincher.model.Customer;
import cn.wincher.service.CustomerService;
import org.autumn.framework.annotation.Action;
import org.autumn.framework.annotation.Controller;
import org.autumn.framework.bean.Data;
import org.autumn.framework.bean.FileParam;
import org.autumn.framework.bean.Param;
import org.autumn.framework.bean.View;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author wincher
 * @since 2018/8/23
 * <p> cn.wincher.controller <p>
 */
@Controller
public class CustomerController {
  
  private CustomerService customerService;
  
  /**
   * 进入 创建客户 页面
   * @param req
   * @param resp
   * @throws ServletException
   * @throws IOException
   */
//  @Override
//  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//    List<Customer> customerList = customerService.getCustomerList();
//    req.setAttribute("customerList", customerList);
//    req.getRequestDispatcher("/WEB-INF/views/customer.jsp").forward(req, resp);
//  }
  
  /**
   * deal create customer request
   */
  @Action("post:/customer_create")
  public Data createSubmit(Param param) {
    Map<String, Object> fieldMap = param.getFieldMap();
    FileParam fileParam = param.getFile("photo");
    boolean result = customerService.createCustomer(fieldMap, fileParam);
    return new Data(result);
  }
  
  /**
   * enter customer list page
   */
  @Action("get:/customer")
  public View index() {
    List<Customer> customerList = customerService.getCustomerList();
    return new View("customer.jsp").addModel("customerList", customerList);
  }


}

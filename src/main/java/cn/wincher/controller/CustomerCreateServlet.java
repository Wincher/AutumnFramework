package cn.wincher.controller;

import cn.wincher.model.Customer;
import cn.wincher.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author huwq
 * @since 2018/8/23
 * <p> cn.wincher.controller <p>
 */
@WebServlet("/customer")
public class CustomerCreateServlet extends HttpServlet {
  
  private CustomerService customerService;
  
  @Override
  public void init() throws ServletException {
    customerService = new CustomerService();
  }
  
  /**
   * 进入 创建客户 页面
   * @param req
   * @param resp
   * @throws ServletException
   * @throws IOException
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    List<Customer> customerList = customerService.getCustomerList();
    req.setAttribute("customerList", customerList);
    req.getRequestDispatcher("/WEB-INF/views/customer.jsp").forward(req, resp);
  }
  
  /**
   * 处理 创建客户 请求
   * @param req
   * @param resp
   * @throws ServletException
   * @throws IOException
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // todo:
  }
}

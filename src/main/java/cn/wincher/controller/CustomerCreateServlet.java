package cn.wincher.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author huwq
 * @since 2018/8/23
 * <p> cn.wincher.controller <p>
 */
@WebServlet("/customer_create")
public class CustomerCreateServlet extends HttpServlet {
  /**
   * 进入 创建客户 页面
   * @param req
   * @param resp
   * @throws ServletException
   * @throws IOException
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // todo:
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

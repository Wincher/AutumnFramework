package cn.wincher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * @author huwq
 * @since 2018/8/23
 * <p> cn.wincher <p>
 */
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String currentTime = dateFormat.format(Date.from(Instant.now()));
    req.setAttribute("currentTime", currentTime);
    req.getRequestDispatcher("/WEB-INF/jsp/hello.jsp").forward(req, resp);
  }
}

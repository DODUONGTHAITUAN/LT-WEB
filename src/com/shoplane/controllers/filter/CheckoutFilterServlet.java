package com.shoplane.controllers.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shoplane.dao.UserDAO;
import com.shoplane.models.Order;
import com.shoplane.models.User;
import com.shoplane.utils.Constants;

@WebFilter("/checkout/*")
public class CheckoutFilterServlet implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    Filter.super.init(filterConfig);
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
      throws IOException, ServletException {
    // Convert to Http
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    String filterMsg = "";

    // Get data from session
    UserDAO userDAO = new UserDAO();
    User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
    @SuppressWarnings("unchecked")
    List<Order> orders = (List<Order>) request.getSession().getAttribute("orders");

    if (user != null) {
      // Check user has created in db
      boolean isExist = userDAO.isExistsByEmail(user.getEmail());
      if (isExist) {
        if (user.getRole().getRoleId().equals(Constants.USER_ROLE)) {
          // Ordered
          if (orders != null) {
            chain.doFilter(request, response);
            return;
          } else {
            // Not order
            filterMsg = "Hãy đặt hàng trước!";
            request.getSession().setAttribute("filterMsg", filterMsg);
            response.sendRedirect(request.getContextPath() + "/");
            return;
          }
        } else {
          // Not a normal user
          filterMsg = "Hãy đăng nhập với tài khoản khách hàng để truy cập đường dẫn này!";
          request.getSession().setAttribute("filterMsg", filterMsg);
          response.sendRedirect(request.getContextPath() + "/system/");
          return;
        }
      } else {
        // Clear session
        request.getSession().setAttribute(Constants.USER_SESSION, null);
      }
    }
    // user isn't exits
    filterMsg = "Trước tiên bạn cần đăng nhập!";
    request.getSession().setAttribute("filterMsg", filterMsg);
    response.sendRedirect(request.getContextPath() + "/login");
  }
}

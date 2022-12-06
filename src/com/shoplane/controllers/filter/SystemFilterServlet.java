package com.shoplane.controllers.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shoplane.models.User;
import com.shoplane.utils.Constants;

@WebFilter("/system/*")
public class SystemFilterServlet implements Filter {

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
    User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
    if (user != null) {
      if (!user.getRole().getRoleId().equals(Constants.USER_ROLE)) {
        chain.doFilter(request, response);
      } else {
        filterMsg = "Đường dẫn không hợp lệ!";
        request.getSession().setAttribute("filterMsg", filterMsg);
        response.sendRedirect(request.getContextPath() + "/");
      }
    } else {
      // user isn't exits
      filterMsg = "Trước tiên bạn cần đăng nhập!";
      request.getSession().setAttribute("filterMsg", filterMsg);
      response.sendRedirect(request.getContextPath() + "/login");
    }
  }
}

package com.shoplane.controllers.client.home;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shoplane.services.client.HomeService;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public HomeServlet() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HomeService homeService = new HomeService(request, response);
    homeService.fetchData();
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}

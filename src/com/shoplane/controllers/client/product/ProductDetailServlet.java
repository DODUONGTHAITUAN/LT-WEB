package com.shoplane.controllers.client.product;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shoplane.dao.OptionDAO;
import com.shoplane.dao.ProductDAO;
import com.shoplane.services.client.ProductService;

@WebServlet(urlPatterns = { "/product-detail", "/product-detail/" })
public class ProductDetailServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  OptionDAO optionDAO = null;
  ProductDAO productDAO = null;

  public ProductDetailServlet() {
    super();
    optionDAO = new OptionDAO();
    this.productDAO = new ProductDAO();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ProductService productService = new ProductService(request, response);
    productService.getProductDetail();
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ProductService productService = new ProductService(request, response);
    productService.addProductItemToCart();
  }

}

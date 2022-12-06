package com.shoplane.services.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shoplane.dao.CategoryDAO;
import com.shoplane.dao.ProductDAO;
import com.shoplane.dao.ProductTypeDAO;
import com.shoplane.models.Category;
import com.shoplane.models.Product;
import com.shoplane.models.ProductType;
import com.shoplane.services.SuperService;
import com.shoplane.utils.Constants;

public class HomeService extends SuperService {

  ProductTypeDAO productTypeDAO = null;
  ProductDAO productDAO = null;
  CategoryDAO categoryDAO = null;

  public HomeService(HttpServletRequest request, HttpServletResponse response) {
    super(request, response);
    this.productTypeDAO = new ProductTypeDAO();
    this.productDAO = new ProductDAO();
    this.categoryDAO = new CategoryDAO();
  }

  public void fetchData() throws IOException {
    try {
      super.setEncoding(Constants.UTF8);
      String url = "/pages/default/home/index.jsp";

      // Get Header data (Category, ProductType)
      ProductType shirtProductType = productTypeDAO.find(Constants.SHIRT);
      List<Category> shirtCategories = categoryDAO.findByProductType(shirtProductType);
      ProductType shortProductType = productTypeDAO.find(Constants.SHORT);
      List<Category> shortCategories = categoryDAO.findByProductType(shortProductType);

      // Get shirt, short products data
      Map<String, Object> params = new HashMap<>();
      params.put("isDelete", (byte) 0);
      params.put("productType", shirtProductType);

      // shirts
      List<Product> shirtProducts = new ArrayList<>();
      shirtProducts = productDAO.paginationByProductTypeAndIsDeleted(params, 1, 12, Constants.ASC);

      // shorts
      params.replace("productType", shortProductType);
      List<Product> shortProducts = new ArrayList<>();
      shortProducts = productDAO.paginationByProductTypeAndIsDeleted(params, 1, 12, Constants.ASC);

      // Set data header
      super.getSession().setAttribute("shirtCategories", shirtCategories);
      super.getSession().setAttribute("shortCategories", shortCategories);
      super.setAttribute("shirtProducts", shirtProducts);
      super.setAttribute("shortProducts", shortProducts);

      // Forward to page
      super.forwardToPage(url);
    } catch (Exception e) {
      super.log(e.getMessage());
      String error = super.getContextPath() + "/500";
      super.redirectToPage(error);
    }
  }

}

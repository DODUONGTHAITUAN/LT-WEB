package com.shoplane.services.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shoplane.dao.RoleDAO;
import com.shoplane.dao.UserDAO;
import com.shoplane.models.Role;
import com.shoplane.models.User;
import com.shoplane.services.SuperService;
import com.shoplane.utils.Bcrypt;
import com.shoplane.utils.Constants;
import com.shoplane.utils.Helper;

public class CustomerService extends SuperService {

  UserDAO userDAO = null;
  RoleDAO roleDAO = null;

  public CustomerService(HttpServletRequest request, HttpServletResponse response) {
    super(request, response);
    this.userDAO = new UserDAO();
    this.roleDAO = new RoleDAO();
  }

  // [GET] CustomerLoginServlet
  public void getLoginForm() throws ServletException, IOException {
    try {
      super.setEncoding(Constants.UTF8);
      HttpSession session = request.getSession();
      String url = "/default/account/loginAccount.jsp";
      User user = (User) session.getAttribute("user");

      if (user != null) {
        url = "./account";
        super.redirectToPage(url);
        return;
      }
      super.forwardToPage(url);

    } catch (Exception e) {
      super.log(e.getMessage());
      String error = "/500";
      this.redirectToPage(error);
    }
  }

  // [POST] CustomerLoginServlet
  public void postLogin() throws IOException, ServletException {
    try {
      super.setEncoding(Constants.UTF8);
      String url = "./account";
      String nextUrl = request.getParameter("caller");
      String email = request.getParameter("email").trim();
      String pwdNotHash = request.getParameter("password").trim();

      // Check Url
      if (!nextUrl.equals("") && !nextUrl.equals(url)) {
        url = nextUrl;
      }

      // Get user by email
      User u = this.userDAO.findByEmail(email);
      // Check user
      if (u != null) {
        // Check pwd
        if (Bcrypt.checkpwd(pwdNotHash, u.getPassword())) {
          request.getSession().setAttribute("user", u);
          super.redirectToPage(url);
        } else {
          super.setAttribute("errMsg", "*Mật khẩu không chính xác");
          this.getLoginForm();
        }
      } else {
        super.setAttribute("errMsg", "*Email không tồn tại");
        this.getLoginForm();
      }
    } catch (Exception e) {
      super.log(e.getMessage());
      String error = "/500";
      this.redirectToPage(error);
    }
  }

  // [GET] CustomerRegisterServlet
  public void getRegisterForm() throws ServletException, IOException {
    try {
      String url = "/default/account/registerAccount.jsp";
      super.forwardToPage(url);
    } catch (Exception e) {
      super.log(e.getMessage());
      String error = "/500";
      this.redirectToPage(error);
    }
  }

  // [POST] CustomerRegisterServlet
  public void postRegisterForm() throws IOException {

    try {
      // init services
      SendMail sm = new SendMail();
      // Get Session
      HttpSession session = request.getSession();

      String url = request.getContextPath() + "/verify";
      // Get User
      User user = this.createUser(request);
      // Set code
      user.setCode(sm.getRandom());

      // send mail code to user
      boolean isSended = sm.sendMail(user);

      if (isSended) {
        System.out.println("Send mail success");
      } else {
        System.out.println("Send mail fail");
      }

      // set user to session
      session.setAttribute("user", user);
      super.redirectToPage(url);
    } catch (Exception e) {
      super.log(e.getMessage());
      String error = "/500";
      super.redirectToPage(error);
    }
  }

  private User createUser(HttpServletRequest request) {

    // Get Role
    Role r = this.roleDAO.find(Constants.USER_ROLE);

    // Get param from sign up form
    String userId = Helper.getRandom();
    String fullName = request.getParameter("fullName").trim();
    String phonenumber = request.getParameter("phonenumber").trim();
    String address = request.getParameter("address").trim();
    String email = request.getParameter("email").trim();
    String pwd = request.getParameter("password").trim();

    // Get hashed pwd
    String pwdHashed = Bcrypt.hashpwd(pwd);
    if (pwdHashed == null) {
      pwdHashed = "";
    }

    // Get user
    User user = new User(userId, fullName, address, phonenumber, email, pwdHashed);
    user.setRole(r);

    return user;
  }

  // [GET] CustomerVerifyCodeServlet
  public void getVerifyForm() throws ServletException, IOException {
    try {
      super.setEncoding(Constants.UTF8);
      String url = "/default/account/verifyAccount.jsp";
      super.forwardToPage(url);
    } catch (Exception e) {
      super.log(e.getMessage());
      String error = "/500";
      super.redirectToPage(error);
    }
  }

  // [POST] CustomerVerifyCodeServlet
  public void postVerifyForm() throws IOException {
    try {
      String url = "./login";
      String code = request.getParameter("code").trim();
      User user = (User) this.request.getSession().getAttribute("user");
      if (code.equals(user.getCode())) {
        this.userDAO.create(user);
        this.request.getSession().removeAttribute("user");
      } else {
        super.setAttribute("errMsg", "*Mã xác nhận không đúng vui lòng nhập lại");
        this.getVerifyForm();
        return;
      }
      super.redirectToPage(url);
    } catch (Exception e) {
      super.log(e.getMessage());
      String error = "/500";
      super.redirectToPage(error);
    }
  }

  // [GET] CustomerLogoutServlet
  public void logout() throws IOException, ServletException {
    try {
      this.request.getSession().removeAttribute("user");
      super.redirectToPage("./login");
    } catch (Exception e) {
      super.log(e.getMessage());
      String error = "/500";
      super.redirectToPage(error);
    }
  }
}
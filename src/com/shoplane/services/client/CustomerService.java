package com.shoplane.services.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shoplane.dao.RoleDAO;
import com.shoplane.dao.UserDAO;
import com.shoplane.models.Role;
import com.shoplane.models.User;
import com.shoplane.services.SendMail;
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
      String url = "/pages/default/account/loginAccount.jsp";

      User user = (User) super.getSession().getAttribute(Constants.USER_SESSION);

      if (user != null) {
        Role role = user.getRole();
        if (role.getRoleId().equals(Constants.ADMIN_ROLE) || role.getRoleId().equals((Constants.EMPLOYEE_ROLE))) {
          url = super.getContextPath() + "/system";
        } else {
          url = super.getContextPath() + "/account";
        }
        super.redirectToPage(url);
        return;
      }
      super.forwardToPage(url);
      // reomve att session
      super.getSession().setAttribute("status", null);
      super.getSession().removeAttribute("status");

    } catch (Exception e) {
      super.log(e.getMessage());
      String error = super.getContextPath() + "/500";
      this.redirectToPage(error);
    }
  }

  // [POST] CustomerLoginServlet
  public void postLogin() throws IOException, ServletException {
    try {
      // Set encoding
      super.setEncoding(Constants.UTF8);

      // Get params
      String url = super.getContextPath() + "/account";
      String nextUrl = request.getParameter("caller");
      String email = request.getParameter("email").trim();
      String pwdNotHash = request.getParameter("password").trim();

      // Check Url
      if (!nextUrl.equals("") && !nextUrl.equals(url)) {
        url = nextUrl;
      }

      // Get user by email
      User u = this.userDAO.findByEmail(email);
      // User is not exist
      if (u != null) {
        // Check pwd
        if (Bcrypt.checkpwd(pwdNotHash, u.getPassword())) {
          // keep user data
          super.setAttribute("errMsg", "");
          super.getSession().setAttribute("user", u);
          // Get user role
          Role role = u.getRole();
          // Check role
          if (role.getRoleId().equals(Constants.ADMIN_ROLE) || role.getRoleId().equals((Constants.EMPLOYEE_ROLE))) {
            url = super.getContextPath() + "/system";
          }
          super.redirectToPage(url);
        } else {
          // Password invalid
          super.setAttribute("errMsg", "*Mật khẩu không chính xác");
          this.getLoginForm();
        }
      } else {
        super.setAttribute("errMsg", "*Email không tồn tại");
        this.getLoginForm();
      }
    } catch (Exception e) {
      super.log(e.getMessage());
      String error = super.getContextPath() + "/500";
      this.redirectToPage(error);
    }
  }

  // [GET] CustomerRegisterServlet
  public void getRegisterForm() throws ServletException, IOException {
    try {
      super.setEncoding(Constants.UTF8);
      String url = "/pages/default/account/registerAccount.jsp";
      String loginUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
          + request.getContextPath() + "/login";
      super.forwardToPage(url);
    } catch (Exception e) {
      super.log(e.getMessage());
      String error = super.getContextPath() + "/500";
      this.redirectToPage(error);
    }
  }

  // [POST] CustomerRegisterServlet
  public void postRegisterForm() throws IOException {
    try {
      super.setEncoding(Constants.UTF8);
      // Link
      String url = super.getContextPath() + "/verify";

      // Get param from sign up form
      String userId = Helper.getRandom();
      String fullName = request.getParameter("fullName").trim();
      String phonenumber = request.getParameter("phonenumber").trim();
      String address = request.getParameter("address").trim();
      String email = request.getParameter("email").trim();
      String pwd = request.getParameter("password").trim();

      User userExits = this.userDAO.findByEmail(email);

      if (userExits == null) {
        // init services
        SendMail sm = new SendMail();
        // Get Role
        Role role = this.roleDAO.find(Constants.USER_ROLE);
        // Get hashed pwd
        String pwdHashed = Bcrypt.hashpwd(pwd);
        if (pwdHashed == null) {
          pwdHashed = "";
        }

        // Get user
        User user = new User();
        user.setUserId(userId);
        user.setFullname(fullName);
        user.setAddress(address);
        user.setPhonenumber(phonenumber);
        user.setEmail(email);
        user.setPassword(pwdHashed);
        user.setRole(role);
        // Set code
        user.setCode(Helper.getRandom());

        // send mail code to user
        boolean isSended = sm.sendOTPSignUpAccount(user);

        if (isSended) {
          System.out.println("Send mail success");
        } else {
          System.out.println("Send mail fail");
        }

        // set user to session
        super.getSession().setAttribute("user", user);

        super.redirectToPage(url);
      } else {
        String errMsg = "* Email đã tồn tại";
        super.setAttribute("errMsg", errMsg);
        this.getRegisterForm();

      }

    } catch (Exception e) {
      super.log(e.getMessage());
      String error = super.getContextPath() + "/500";
      super.redirectToPage(error);
    }
  }

  // [GET] CustomerVerifyCodeServlet
  public void getVerifyForm() throws ServletException, IOException {
    try {
      super.setEncoding(Constants.UTF8);
      String url = "/pages/default/account/verifyAccount.jsp";
      super.forwardToPage(url);
    } catch (Exception e) {
      super.log(e.getMessage());
      String error = super.getContextPath() + "/500";
      super.redirectToPage(error);
    }
  }

  // [POST] CustomerVerifyCodeServlet
  public void postVerifyForm() throws IOException {
    try {
      super.setEncoding(Constants.UTF8);
      String status = "";
      String url = super.getContextPath() + "/login";
      String loginUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
          + request.getContextPath() + "/login";
      String code = super.getParameter("code").trim();
      User user = (User) super.getSession().getAttribute(Constants.USER_SESSION);
      if (code.equals(user.getCode())) {
        this.userDAO.create(user);
        status = "success";
        // set Session
        super.getSession().setAttribute(Constants.USER_SESSION, null);
        super.getSession().setAttribute("status", status);

        // Send mail
        SendMail sendMail = new SendMail();
        sendMail.sendSignUpSuccess(user.getEmail(), loginUrl);
        super.redirectToPage(url);
        return;
      }
      // else
      super.setAttribute("errMsg", "* Mã xác nhận không đúng vui lòng nhập lại");
      this.getVerifyForm();

    } catch (Exception e) {
      super.log(e.getMessage());
      String error = super.getContextPath() + "/500";
      super.redirectToPage(error);
    }
  }

  // [GET] CustomerLogoutServlet
  public void logout() throws IOException, ServletException {
    try {
      // Set encoding
      super.setEncoding(Constants.UTF8);
      // link
      String url = super.getContextPath() + "/login";
      // Delete current user logged
      super.getSession().removeAttribute(Constants.USER_SESSION);

      // Redirect /login
      super.redirectToPage(url);

    } catch (Exception e) {
      super.log(e.getMessage());
      String error = super.getContextPath() + "/500";
      super.redirectToPage(error);
    }
  }
}

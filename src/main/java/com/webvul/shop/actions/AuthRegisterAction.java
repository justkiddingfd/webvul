package com.webvul.shop.actions;

import com.webvul.shop.service.AuthService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

@Action(
  value = "auth-register",
  results = {
    @Result(name = "input",   location = "/WEB-INF/content/auth.jsp"),
    @Result(name = "success", type = "redirectAction", params = {"actionName", "products"})
  }
)
public final class AuthRegisterAction extends BaseAction {
  private final AuthService auth = new AuthService();
  private String email;
  private String password;
  // [A04-MassAssignment] Tham số role nhận từ form — user có thể tự set role=admin
  private String role;

  @Override
  public String execute() {
    if (email == null || email.isBlank() || password == null || password.isBlank()) {
      addActionError("Vui lòng nhập email và mật khẩu.");
      return INPUT;
    }
    if (password.trim().length() < 8) {
      addActionError("Mật khẩu cần tối thiểu 8 ký tự.");
      return INPUT;
    }

    try {
      // VULNERABLE: role lấy từ request parameter, không validate giá trị
      String userRole = (role != null && !role.isBlank()) ? role : "student";
      var u = auth.register(email.trim(), password, userRole);
      session.put("userId", u.id());
      session.put("userEmail", u.email());
      session.put("userRole", u.role());
      return SUCCESS;
    } catch (Exception e) {
      addActionError("Không thể đăng ký. Email có thể đã tồn tại.");
      return INPUT;
    }
  }

  public void setEmail(String email)   { this.email = email; }
  public void setPassword(String p)    { this.password = p; }
  public void setRole(String role)     { this.role = role; }
}

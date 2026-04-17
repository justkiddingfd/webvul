package com.webvul.shop.actions;

import com.webvul.shop.repo.UserRepository;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.mindrot.jbcrypt.BCrypt;

// [A07-AuthFailures]
//   1. Username Enumeration: thông báo lỗi khác nhau cho "email không tồn tại" vs "sai mật khẩu"
//   2. Open Redirect: tham số returnUrl không được validate — redirect đến bất kỳ domain nào
//   3. Không có brute-force protection (rate limiting / account lockout)
@Action(
  value = "auth-login",
  results = {
    @Result(name = "input",    location = "/WEB-INF/content/auth.jsp"),
    @Result(name = "success",  type = "redirectAction", params = {"actionName", "products"}),
    // VULNERABLE: redirect đến URL tùy ý từ user input
    @Result(name = "redirect", type = "redirect", params = {"location", "${returnUrl}"})
  }
)
public final class AuthLoginAction extends BaseAction {
  private final UserRepository users = new UserRepository();
  private String email;
  private String password;
  // [A07-OpenRedirect] returnUrl không được kiểm tra domain
  private String returnUrl;

  @Override
  public String execute() {
    if (email == null || email.isBlank() || password == null || password.isBlank()) {
      addActionError("Vui lòng nhập email và mật khẩu.");
      return INPUT;
    }

    // VULNERABLE: thông báo lỗi khác nhau — Username Enumeration
    var userOpt = users.findByEmail(email.trim());
    if (userOpt.isEmpty()) {
      addActionError("Email này không tồn tại trong hệ thống.");
      return INPUT;
    }
    var user = userOpt.get();
    if (!BCrypt.checkpw(password, user.passwordHash())) {
      addActionError("Mật khẩu không đúng. Vui lòng thử lại.");
      return INPUT;
    }

    session.put("userId", user.id());
    session.put("userEmail", user.email());
    session.put("userRole", user.role());

    // VULNERABLE: redirect không validate URL — Open Redirect
    if (returnUrl != null && !returnUrl.isBlank()) {
      return "redirect";
    }
    return SUCCESS;
  }

  public void setEmail(String email)   { this.email = email; }
  public void setPassword(String p)    { this.password = p; }
  public void setReturnUrl(String url) { this.returnUrl = url; }
  public String getReturnUrl()         { return returnUrl; }
}

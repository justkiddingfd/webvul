package com.webvul.shop.actions;

import com.webvul.shop.repo.TokenRepository;
import com.webvul.shop.repo.UserRepository;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Random;

// [A07-WeakReset] Token chỉ 6 chữ số (1,000,000 khả năng) — brute-force được
// Token hiển thị luôn trên màn hình (thay vì gửi email) — lộ token
@Action(
  value = "forgot-password",
  results = {
    @Result(name = "success", location = "/WEB-INF/content/forgot-password.jsp"),
    @Result(name = "input",   location = "/WEB-INF/content/forgot-password.jsp")
  }
)
public final class ForgotPasswordAction extends BaseAction {
  private final UserRepository users = new UserRepository();
  private final TokenRepository tokens = new TokenRepository();

  private String email;
  private String generatedToken;

  @Override
  public String execute() {
    if (email == null || email.isBlank()) return INPUT;

    var user = users.findByEmail(email.trim());
    if (user.isPresent()) {
      // VULNERABLE: token chỉ 6 chữ số, không có expiry
      int token = new Random().nextInt(900000) + 100000;
      generatedToken = String.valueOf(token);
      tokens.save(email.trim(), generatedToken);
      // VULNERABLE: hiển thị token trực tiếp thay vì gửi email bí mật
      addActionMessage("Token đặt lại mật khẩu của bạn là: <strong>" + generatedToken + "</strong>");
    } else {
      // VULNERABLE: thông báo khác nhau — username enumeration
      addActionMessage("Email <strong>" + email + "</strong> không tồn tại trong hệ thống.");
    }
    return SUCCESS;
  }

  public void setEmail(String email) { this.email = email; }
  public String getEmail()           { return email; }
  public String getGeneratedToken()  { return generatedToken; }
}

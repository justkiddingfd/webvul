package com.webvul.shop.actions;

import com.webvul.shop.repo.TokenRepository;
import com.webvul.shop.repo.UserRepository;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.mindrot.jbcrypt.BCrypt;

// [A07-WeakReset] Không có rate limiting — brute-force token (6 chữ số)
// Không có expiry — token tồn tại mãi mãi
@Action(
  value = "reset-password",
  results = {
    @Result(name = "success", location = "/WEB-INF/content/reset-password.jsp"),
    @Result(name = "input",   location = "/WEB-INF/content/reset-password.jsp")
  }
)
public final class ResetPasswordAction extends BaseAction {
  private final UserRepository users = new UserRepository();
  private final TokenRepository tokens = new TokenRepository();

  private String email;
  private String token;
  private String newPassword;

  @Override
  public String execute() {
    if (email == null || email.isBlank() || token == null || token.isBlank()) return INPUT;
    if (newPassword == null || newPassword.length() < 4) {
      addActionError("Mật khẩu mới cần ít nhất 4 ký tự.");
      return INPUT;
    }

    // VULNERABLE: không có rate limiting — có thể brute-force token
    var storedToken = tokens.findToken(email.trim());
    if (storedToken.isEmpty() || !storedToken.get().equals(token.trim())) {
      addActionError("Token không đúng."); // VULNERABLE: thông báo lộ trạng thái
      return INPUT;
    }

    String hash = BCrypt.hashpw(newPassword, BCrypt.gensalt(10));
    users.updatePassword(email.trim(), hash);
    tokens.deleteByEmail(email.trim());
    addActionMessage("Mật khẩu đã được đặt lại thành công. Bạn có thể đăng nhập ngay.");
    return SUCCESS;
  }

  public void setEmail(String email)           { this.email = email; }
  public void setToken(String token)           { this.token = token; }
  public void setNewPassword(String p)         { this.newPassword = p; }
  public String getEmail()                     { return email; }
  public String getToken()                     { return token; }
}

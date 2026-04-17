package com.webvul.shop.actions;

import com.webvul.shop.model.User;
import com.webvul.shop.repo.UserRepository;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

// [A01-IDOR] Bất kỳ user đã đăng nhập nào cũng xem được profile của user khác qua ?id=
@Action(
  value = "profile",
  results = {
    @Result(name = "success", location = "/WEB-INF/content/profile.jsp"),
    @Result(name = "error",   location = "/WEB-INF/content/error.jsp"),
    @Result(name = "login",   type = "redirectAction", params = {"actionName", "auth"})
  }
)
public final class ProfileAction extends BaseAction {
  private final UserRepository users = new UserRepository();

  private String id;
  private User profileUser;

  @Override
  public String execute() {
    if (!isLoggedIn()) return "login";
    if (id == null || id.isBlank()) {
      // Mặc định xem profile của chính mình
      id = session.get("userId").toString();
    }
    // VULNERABLE: không kiểm tra id == session userId — bất kỳ user nào cũng xem được
    profileUser = users.findById(id).orElse(null);
    if (profileUser == null) {
      addActionError("Không tìm thấy người dùng.");
      return ERROR;
    }
    return SUCCESS;
  }

  public void setId(String id)         { this.id = id; }
  public User getProfileUser()         { return profileUser; }
}

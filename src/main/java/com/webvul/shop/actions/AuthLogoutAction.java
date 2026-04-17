package com.webvul.shop.actions;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

@Action(
  value = "auth-logout",
  results = {
    @Result(name = "success", type = "redirectAction", params = {"actionName", "products"})
  }
)
public final class AuthLogoutAction extends BaseAction {
  @Override
  public String execute() {
    if (session != null) {
      session.remove("userId");
      session.remove("userEmail");
      session.remove("userRole");
    }
    return SUCCESS;
  }
}


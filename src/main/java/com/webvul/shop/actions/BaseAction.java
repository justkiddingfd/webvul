package com.webvul.shop.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.webvul.shop.service.CartService;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public abstract class BaseAction extends ActionSupport implements SessionAware {
  protected Map<String, Object> session;
  protected final CartService cartService = new CartService();

  @Override
  public void setSession(Map<String, Object> session) {
    this.session = session;
  }

  public boolean isLoggedIn() {
    return session != null && session.get("userId") != null;
  }

  public boolean isAdmin() {
    Object v = session == null ? null : session.get("userRole");
    return "admin".equals(v);
  }

  public String getUserEmail() {
    Object v = session == null ? null : session.get("userEmail");
    return v == null ? null : v.toString();
  }

  public int getCartCount() {
    return session == null ? 0 : cartService.countItems(session);
  }
}


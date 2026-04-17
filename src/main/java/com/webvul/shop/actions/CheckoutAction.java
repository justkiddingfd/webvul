package com.webvul.shop.actions;

import com.webvul.shop.model.Order;
import com.webvul.shop.service.CheckoutService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Map;

@Action(
  value = "checkout",
  results = {
    @Result(name = "input", location = "/WEB-INF/content/cart.jsp"),
    @Result(name = "success", location = "/WEB-INF/content/checkout.jsp")
  }
)
public final class CheckoutAction extends CartAction {
  private final CheckoutService checkout = new CheckoutService();

  private String receiverName;
  private String receiverEmail;
  private String receiverAddress;
  private String orderId;

  @Override
  public String execute() {
    if (receiverName == null || receiverName.isBlank()) {
      addActionError("Vui lòng nhập tên người nhận.");
      super.execute();
      return INPUT;
    }
    if (receiverEmail == null || receiverEmail.isBlank()) {
      addActionError("Vui lòng nhập email người nhận.");
      super.execute();
      return INPUT;
    }
    if (receiverAddress == null || receiverAddress.isBlank()) {
      addActionError("Vui lòng nhập địa chỉ người nhận.");
      super.execute();
      return INPUT;
    }

    Map<String, Integer> cart = cartService.snapshot(session);
    if (cart.isEmpty()) {
      addActionError("Giỏ hàng đang trống.");
      super.execute();
      return INPUT;
    }

    String userId = session.get("userId") == null ? null : session.get("userId").toString();
    Order order = checkout.createOrder(userId, receiverName.trim(), receiverEmail.trim(), receiverAddress.trim(), cart);
    cartService.clear(session);
    orderId = order.id();
    return SUCCESS;
  }

  public void setReceiverName(String receiverName) {
    this.receiverName = receiverName;
  }

  public void setReceiverEmail(String receiverEmail) {
    this.receiverEmail = receiverEmail;
  }

  public void setReceiverAddress(String receiverAddress) {
    this.receiverAddress = receiverAddress;
  }

  public String getOrderId() {
    return orderId;
  }
}


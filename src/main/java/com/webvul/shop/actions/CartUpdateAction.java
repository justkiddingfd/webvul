package com.webvul.shop.actions;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

@Action(
  value = "cart-update",
  results = {
    @Result(name = "success", type = "redirectAction", params = {"actionName", "cart"}),
    @Result(name = "error", type = "redirectAction", params = {"actionName", "cart"})
  }
)
public final class CartUpdateAction extends BaseAction {
  private String productId;
  private int quantity;

  @Override
  public String execute() {
    if (productId == null || productId.isBlank()) return ERROR;
    cartService.update(session, productId, quantity);
    return SUCCESS;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}


package com.webvul.shop.actions;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

@Action(
  value = "cart-remove",
  results = {
    @Result(name = "success", type = "redirectAction", params = {"actionName", "cart"}),
    @Result(name = "error", type = "redirectAction", params = {"actionName", "cart"})
  }
)
public final class CartRemoveAction extends BaseAction {
  private String productId;

  @Override
  public String execute() {
    if (productId == null || productId.isBlank()) return ERROR;
    cartService.remove(session, productId);
    return SUCCESS;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }
}


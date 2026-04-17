package com.webvul.shop.actions;

import com.webvul.shop.model.Order;
import com.webvul.shop.repo.OrderRepository;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

@Action(
  value = "order",
  results = {
    @Result(name = "success", location = "/WEB-INF/content/order.jsp"),
    @Result(name = "error", location = "/WEB-INF/content/error.jsp")
  }
)
public final class OrderAction extends BaseAction {
  private final OrderRepository orders = new OrderRepository();
  private String id;
  private Order order;

  @Override
  public String execute() {
    if (id == null || id.isBlank()) {
      addActionError("Thiếu mã đơn hàng.");
      return ERROR;
    }
    order = orders.findById(id).orElse(null);
    if (order == null) {
      addActionError("Không tìm thấy đơn hàng.");
      return ERROR;
    }
    return SUCCESS;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Order getOrder() {
    return order;
  }
}


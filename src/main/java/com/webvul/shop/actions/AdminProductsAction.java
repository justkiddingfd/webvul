package com.webvul.shop.actions;

import com.webvul.shop.model.Product;
import com.webvul.shop.repo.ProductRepository;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

@Action(
  value = "admin-products",
  results = {
    @Result(name = "success", location = "/WEB-INF/content/admin-products.jsp"),
    @Result(name = "error", location = "/WEB-INF/content/error.jsp")
  }
)
public final class AdminProductsAction extends BaseAction {
  private final ProductRepository products = new ProductRepository();
  private List<Product> items;

  @Override
  public String execute() {
    if (!isAdmin()) {
      addActionError("Bạn cần đăng nhập bằng tài khoản admin để truy cập trang này.");
      return ERROR;
    }
    items = products.listAll();
    return SUCCESS;
  }

  public List<Product> getItems() {
    return items;
  }
}

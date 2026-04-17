package com.webvul.shop.actions;

import com.webvul.shop.model.Product;
import com.webvul.shop.repo.ProductRepository;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.math.BigDecimal;

@Action(
  value = "admin-product",
  results = {
    @Result(name = "success", location = "/WEB-INF/content/admin-product.jsp"),
    @Result(name = "error", location = "/WEB-INF/content/error.jsp")
  }
)
public final class AdminProductAction extends BaseAction {
  private final ProductRepository products = new ProductRepository();

  private String id;
  private Product product;

  @Override
  public String execute() {
    if (!isAdmin()) {
      addActionError("Bạn cần đăng nhập bằng tài khoản admin để truy cập trang này.");
      return ERROR;
    }

    if (id == null || id.isBlank()) {
      product = new Product(null, "", "", BigDecimal.ZERO, "", null);
      return SUCCESS;
    }

    product = products.findById(id).orElse(null);
    if (product == null) {
      addActionError("Không tìm thấy sản phẩm.");
      return ERROR;
    }
    return SUCCESS;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Product getProduct() {
    return product;
  }
}

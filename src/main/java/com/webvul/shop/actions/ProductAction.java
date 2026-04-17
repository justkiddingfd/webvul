package com.webvul.shop.actions;

import com.webvul.shop.model.Product;
import com.webvul.shop.model.Review;
import com.webvul.shop.repo.ProductRepository;
import com.webvul.shop.repo.ReviewRepository;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

@Action(
  value = "product",
  results = {
    @Result(name = "success", location = "/WEB-INF/content/product.jsp"),
    @Result(name = "error", location = "/WEB-INF/content/error.jsp")
  }
)
public final class ProductAction extends BaseAction {
  private final ProductRepository products = new ProductRepository();
  private final ReviewRepository reviewRepo = new ReviewRepository();
  private String id;
  private Product product;
  private List<Review> reviews;

  @Override
  public String execute() {
    if (id == null || id.isBlank()) {
      addActionError("Thiếu mã sản phẩm.");
      return ERROR;
    }
    product = products.findById(id).orElse(null);
    if (product == null) {
      addActionError("Không tìm thấy sản phẩm.");
      return ERROR;
    }
    reviews = reviewRepo.listByProduct(id);
    return SUCCESS;
  }

  public void setId(String id)     { this.id = id; }
  public Product getProduct()      { return product; }
  public List<Review> getReviews() { return reviews; }
}

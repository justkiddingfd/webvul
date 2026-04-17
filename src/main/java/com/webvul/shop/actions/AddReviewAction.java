package com.webvul.shop.actions;

import com.webvul.shop.repo.ReviewRepository;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

// [A03-XSS] Stored XSS: nội dung review lưu thô, hiển thị không escape trên product.jsp
@Action(
  value = "add-review",
  results = {
    @Result(name = "success", type = "redirectAction",
            params = {"actionName", "product", "id", "${productId}"})
  }
)
public final class AddReviewAction extends BaseAction {
  private final ReviewRepository reviews = new ReviewRepository();

  private String productId;
  private String reviewerName;
  private String content;

  @Override
  public String execute() {
    if (productId == null || productId.isBlank()) return ERROR;
    String name = (reviewerName == null || reviewerName.isBlank()) ? "Ẩn danh" : reviewerName;
    // VULNERABLE: content không được sanitize — Stored XSS
    reviews.create(productId, name, content == null ? "" : content);
    return SUCCESS;
  }

  public void setProductId(String productId)   { this.productId = productId; }
  public void setReviewerName(String name)     { this.reviewerName = name; }
  public void setContent(String content)       { this.content = content; }
  public String getProductId()                 { return productId; }
}

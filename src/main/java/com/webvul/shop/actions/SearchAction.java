package com.webvul.shop.actions;

import com.webvul.shop.model.Product;
import com.webvul.shop.repo.ProductRepository;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

// [A03-SQLi] Endpoint tìm kiếm sản phẩm — SQL Injection qua tham số q
@Action(
  value = "search",
  results = {
    @Result(name = "success", location = "/WEB-INF/content/search.jsp"),
    @Result(name = "error",   location = "/WEB-INF/content/search.jsp")
  }
)
public final class SearchAction extends BaseAction {
  private final ProductRepository products = new ProductRepository();

  private String q;
  private List<Product> results;
  private String sqlError;

  @Override
  public String execute() {
    try {
      // VULNERABLE: gọi searchUnsafe() — nối chuỗi SQL trực tiếp
      results = products.searchUnsafe(q);
    } catch (RuntimeException e) {
      sqlError = e.getMessage();
      return ERROR;
    }
    return SUCCESS;
  }

  public void setQ(String q)       { this.q = q; }
  public String getQ()             { return q; }
  public List<Product> getResults(){ return results; }
  public String getSqlError()      { return sqlError; }
}

package com.webvul.shop.actions;

import com.webvul.shop.model.Product;
import com.webvul.shop.repo.ProductRepository;

import java.util.List;

public final class ProductsAction extends BaseAction {
  private final ProductRepository products = new ProductRepository();
  private List<Product> items;

  @Override
  public String execute() {
    items = products.listAll();
    return SUCCESS;
  }

  public List<Product> getItems() {
    return items;
  }
}


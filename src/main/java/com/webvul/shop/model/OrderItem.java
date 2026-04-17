package com.webvul.shop.model;

import com.webvul.shop.util.Money;

import java.math.BigDecimal;

public record OrderItem(String id, String orderId, String productId, String productName, BigDecimal unitPrice, int quantity) {
  public String getId() {
    return id;
  }

  public String getOrderId() {
    return orderId;
  }

  public String getProductId() {
    return productId;
  }

  public String getProductName() {
    return productName;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public String getUnitPriceVnd() {
    return Money.vnd(unitPrice);
  }

  public int getQuantity() {
    return quantity;
  }
}


package com.webvul.shop.model;

import com.webvul.shop.util.Money;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record Order(
  String id,
  String userId,
  String receiverName,
  String receiverEmail,
  String receiverAddress,
  BigDecimal totalAmount,
  Instant createdAt,
  List<OrderItem> items
) {
  public String getId() {
    return id;
  }

  public String getUserId() {
    return userId;
  }

  public String getReceiverName() {
    return receiverName;
  }

  public String getReceiverEmail() {
    return receiverEmail;
  }

  public String getReceiverAddress() {
    return receiverAddress;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public String getTotalAmountVnd() {
    return Money.vnd(totalAmount);
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public List<OrderItem> getItems() {
    return items;
  }
}


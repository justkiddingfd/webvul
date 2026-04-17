package com.webvul.shop.model;

import com.webvul.shop.util.Money;

import java.math.BigDecimal;
import java.time.Instant;

public record Product(String id, String name, String description, BigDecimal price, String imageUrl, Instant createdAt) {
  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getPriceVnd() {
    return Money.vnd(price);
  }

  public String getPriceVndInput() {
    return Money.vndInput(price);
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}


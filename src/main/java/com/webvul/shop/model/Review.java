package com.webvul.shop.model;

import java.time.Instant;

public record Review(String id, String productId, String reviewerName, String content, Instant createdAt) {
  public String getId()           { return id; }
  public String getProductId()    { return productId; }
  public String getReviewerName() { return reviewerName; }
  public String getContent()      { return content; }
  public Instant getCreatedAt()   { return createdAt; }
}

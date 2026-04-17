package com.webvul.shop.model;

import java.time.Instant;

public record User(String id, String email, String passwordHash, String role, Instant createdAt) {
  public String getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public String getRole() {
    return role;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}


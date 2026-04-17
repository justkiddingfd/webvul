package com.webvul.shop.repo;

import com.webvul.shop.app.Db;
import com.webvul.shop.model.Review;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ReviewRepository {
  private static volatile boolean schemaReady;
  private final DataSource ds;

  public ReviewRepository() {
    this.ds = Db.dataSource();
  }

  public List<Review> listByProduct(String productId) {
    ensureSchema();
    String sql = "SELECT id, product_id, reviewer_name, content, created_at FROM reviews WHERE product_id = ? ORDER BY created_at DESC";
    try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, productId);
      try (ResultSet rs = ps.executeQuery()) {
        List<Review> out = new ArrayList<>();
        while (rs.next()) {
          out.add(new Review(
            rs.getString("id"),
            rs.getString("product_id"),
            rs.getString("reviewer_name"),
            rs.getString("content"),
            RowMappers.instant(rs, "created_at")
          ));
        }
        return out;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  // [A03-XSS] content lưu thô, không sanitize
  public void create(String productId, String reviewerName, String content) {
    ensureSchema();
    String sql = "INSERT INTO reviews (id, product_id, reviewer_name, content) VALUES (?, ?, ?, ?)";
    try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, UUID.randomUUID().toString());
      ps.setString(2, productId);
      ps.setString(3, reviewerName);
      ps.setString(4, content);
      ps.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void ensureSchema() {
    if (schemaReady) return;
    synchronized (ReviewRepository.class) {
      if (schemaReady) return;
      String ddl =
          "CREATE TABLE IF NOT EXISTS reviews ("
              + "id CHAR(36) PRIMARY KEY,"
              + "product_id CHAR(36) NOT NULL,"
              + "reviewer_name VARCHAR(120) NOT NULL,"
              + "content TEXT NOT NULL,"
              + "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"
              + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
      String idx = "CREATE INDEX idx_reviews_product_id ON reviews(product_id)";
      try (Connection c = ds.getConnection(); Statement st = c.createStatement()) {
        st.execute(ddl);
        try {
          st.execute(idx);
        } catch (SQLException ignored) {
        }
        schemaReady = true;
      } catch (SQLException e) {
        schemaReady = true;
      }
    }
  }
}

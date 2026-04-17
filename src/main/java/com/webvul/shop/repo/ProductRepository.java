package com.webvul.shop.repo;

import com.webvul.shop.app.Db;
import com.webvul.shop.model.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ProductRepository {
  private final DataSource ds;

  public ProductRepository() {
    this.ds = Db.dataSource();
  }

  public List<Product> listAll() {
    String sql = "SELECT id, name, description, price, image_url, created_at FROM products ORDER BY created_at DESC";
    try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      List<Product> out = new ArrayList<>();
      while (rs.next()) {
        out.add(new Product(
          rs.getString("id"),
          rs.getString("name"),
          rs.getString("description"),
          rs.getBigDecimal("price"),
          rs.getString("image_url"),
          RowMappers.instant(rs, "created_at")
        ));
      }
      return out;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public Optional<Product> findById(String id) {
    String sql = "SELECT id, name, description, price, image_url, created_at FROM products WHERE id = ?";
    try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) return Optional.empty();
        return Optional.of(new Product(
          rs.getString("id"),
          rs.getString("name"),
          rs.getString("description"),
          rs.getBigDecimal("price"),
          rs.getString("image_url"),
          RowMappers.instant(rs, "created_at")
        ));
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  // [A03-SQLi] VULNERABLE: nối chuỗi trực tiếp vào SQL — SQL Injection
  public List<Product> searchUnsafe(String keyword) {
    if (keyword == null) keyword = "";
    String sql = "SELECT id, name, description, price, image_url, created_at FROM products"
               + " WHERE name LIKE '%" + keyword + "%'"
               + " OR description LIKE '%" + keyword + "%'"
               + " ORDER BY created_at DESC";
    try (Connection c = ds.getConnection();
         Statement st = c.createStatement();
         ResultSet rs = st.executeQuery(sql)) {
      List<Product> out = new ArrayList<>();
      while (rs.next()) {
        out.add(new Product(
          rs.getString("id"),
          rs.getString("name"),
          rs.getString("description"),
          rs.getBigDecimal("price"),
          rs.getString("image_url"),
          RowMappers.instant(rs, "created_at")
        ));
      }
      return out;
    } catch (Exception e) {
      throw new RuntimeException("SQL Error: " + e.getMessage(), e);
    }
  }

  public String create(String name, String description, java.math.BigDecimal price) {
    return create(name, description, price, null);
  }

  public String create(String name, String description, java.math.BigDecimal price, String imageUrl) {
    String sql = "INSERT INTO products (id, name, description, price, image_url) VALUES (?, ?, ?, ?, ?)";
    String id = UUID.randomUUID().toString();
    try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, id);
      ps.setString(2, name);
      ps.setString(3, description);
      ps.setBigDecimal(4, price);
      ps.setString(5, imageUrl);
      ps.executeUpdate();
      return id;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void update(String id, String name, String description, java.math.BigDecimal price) {
    update(id, name, description, price, null);
  }

  public void update(String id, String name, String description, java.math.BigDecimal price, String imageUrl) {
    boolean hasImage = imageUrl != null && !imageUrl.isBlank();
    String sql = hasImage
      ? "UPDATE products SET name = ?, description = ?, price = ?, image_url = ? WHERE id = ?"
      : "UPDATE products SET name = ?, description = ?, price = ? WHERE id = ?";
    try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, name);
      ps.setString(2, description);
      ps.setBigDecimal(3, price);
      if (hasImage) {
        ps.setString(4, imageUrl);
        ps.setString(5, id);
      } else {
        ps.setString(4, id);
      }
      ps.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}


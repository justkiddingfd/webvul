package com.webvul.shop.repo;

import com.webvul.shop.app.Db;
import com.webvul.shop.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public final class UserRepository {
  private final DataSource ds;

  public UserRepository() {
    this.ds = Db.dataSource();
  }

  public Optional<User> findByEmail(String email) {
    String sql = "SELECT id, email, password_hash, role, created_at FROM users WHERE email = ?";
    try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, email);
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) return Optional.empty();
        return Optional.of(new User(
          rs.getString("id"),
          rs.getString("email"),
          rs.getString("password_hash"),
          rs.getString("role"),
          RowMappers.instant(rs, "created_at")
        ));
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public Optional<User> findById(String id) {
    String sql = "SELECT id, email, password_hash, role, created_at FROM users WHERE id = ?";
    try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) return Optional.empty();
        return Optional.of(new User(
          rs.getString("id"),
          rs.getString("email"),
          rs.getString("password_hash"),
          rs.getString("role"),
          RowMappers.instant(rs, "created_at")
        ));
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void updatePassword(String email, String newPasswordHash) {
    String sql = "UPDATE users SET password_hash = ? WHERE email = ?";
    try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, newPasswordHash);
      ps.setString(2, email);
      ps.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void create(User user) {
    String sql = "INSERT INTO users (id, email, password_hash, role) VALUES (?, ?, ?, ?)";
    try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, user.id());
      ps.setString(2, user.email());
      ps.setString(3, user.passwordHash());
      ps.setString(4, user.role());
      ps.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}


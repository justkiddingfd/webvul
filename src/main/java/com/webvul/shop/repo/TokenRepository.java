package com.webvul.shop.repo;

import com.webvul.shop.app.Db;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public final class TokenRepository {
  private final DataSource ds;

  public TokenRepository() {
    this.ds = Db.dataSource();
  }

  // [A07-WeakReset] Không kiểm tra expiry, token chỉ 6 chữ số
  public void save(String email, String token) {
    deleteByEmail(email);
    String sql = "INSERT INTO password_reset_tokens (email, token) VALUES (?, ?)";
    try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, email);
      ps.setString(2, token);
      ps.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public Optional<String> findToken(String email) {
    String sql = "SELECT token FROM password_reset_tokens WHERE email = ?";
    try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, email);
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) return Optional.empty();
        return Optional.of(rs.getString("token"));
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void deleteByEmail(String email) {
    String sql = "DELETE FROM password_reset_tokens WHERE email = ?";
    try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, email);
      ps.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

package com.webvul.shop.repo;

import com.webvul.shop.app.Db;
import com.webvul.shop.model.Order;
import com.webvul.shop.model.OrderItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class OrderRepository {
  private final DataSource ds;

  public OrderRepository() {
    this.ds = Db.dataSource();
  }

  public void create(Order order) {
    String oSql = "INSERT INTO orders (id, user_id, receiver_name, receiver_email, receiver_address, total_amount) VALUES (?, ?, ?, ?, ?, ?)";
    String iSql = "INSERT INTO order_items (id, order_id, product_id, product_name, unit_price, quantity) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection c = ds.getConnection()) {
      c.setAutoCommit(false);
      try (PreparedStatement ps = c.prepareStatement(oSql)) {
        ps.setString(1, order.id());
        ps.setString(2, order.userId());
        ps.setString(3, order.receiverName());
        ps.setString(4, order.receiverEmail());
        ps.setString(5, order.receiverAddress());
        ps.setBigDecimal(6, order.totalAmount());
        ps.executeUpdate();
      }

      try (PreparedStatement ps = c.prepareStatement(iSql)) {
        for (OrderItem it : order.items()) {
          ps.setString(1, it.id());
          ps.setString(2, order.id());
          ps.setString(3, it.productId());
          ps.setString(4, it.productName());
          ps.setBigDecimal(5, it.unitPrice());
          ps.setInt(6, it.quantity());
          ps.addBatch();
        }
        ps.executeBatch();
      }

      c.commit();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public Optional<Order> findById(String id) {
    String oSql = "SELECT id, user_id, receiver_name, receiver_email, receiver_address, total_amount, created_at FROM orders WHERE id = ?";
    String iSql = "SELECT id, order_id, product_id, product_name, unit_price, quantity FROM order_items WHERE order_id = ?";

    try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(oSql)) {
      ps.setString(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) return Optional.empty();
        String orderId = rs.getString("id");
        List<OrderItem> items = new ArrayList<>();
        try (PreparedStatement ips = c.prepareStatement(iSql)) {
          ips.setString(1, orderId);
          try (ResultSet irs = ips.executeQuery()) {
            while (irs.next()) {
              items.add(new OrderItem(
                irs.getString("id"),
                irs.getString("order_id"),
                irs.getString("product_id"),
                irs.getString("product_name"),
                irs.getBigDecimal("unit_price"),
                irs.getInt("quantity")
              ));
            }
          }
        }
        return Optional.of(new Order(
          orderId,
          rs.getString("user_id"),
          rs.getString("receiver_name"),
          rs.getString("receiver_email"),
          rs.getString("receiver_address"),
          rs.getBigDecimal("total_amount"),
          RowMappers.instant(rs, "created_at"),
          items
        ));
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static BigDecimal sumTotal(List<OrderItem> items) {
    BigDecimal total = BigDecimal.ZERO;
    for (OrderItem it : items) {
      total = total.add(it.unitPrice().multiply(BigDecimal.valueOf(it.quantity())));
    }
    return total;
  }
}


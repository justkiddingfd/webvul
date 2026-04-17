package com.webvul.shop.service;

import com.webvul.shop.model.Order;
import com.webvul.shop.model.OrderItem;
import com.webvul.shop.model.Product;
import com.webvul.shop.repo.OrderRepository;
import com.webvul.shop.repo.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class CheckoutService {
  private final ProductRepository products;
  private final OrderRepository orders;

  public CheckoutService() {
    this.products = new ProductRepository();
    this.orders = new OrderRepository();
  }

  public Order createOrder(String userId, String receiverName, String receiverEmail, String receiverAddress, Map<String, Integer> cart) {
    List<OrderItem> items = new ArrayList<>();
    for (var e : cart.entrySet()) {
      String productId = e.getKey();
      int qty = e.getValue() == null ? 0 : e.getValue();
      if (qty <= 0) continue;
      Product p = products.findById(productId).orElseThrow();
      items.add(new OrderItem(
        UUID.randomUUID().toString(),
        null,
        p.id(),
        p.name(),
        p.price(),
        qty
      ));
    }
    BigDecimal total = OrderRepository.sumTotal(items);
    Order order = new Order(
      UUID.randomUUID().toString(),
      userId,
      receiverName,
      receiverEmail,
      receiverAddress,
      total,
      null,
      items
    );
    orders.create(order);
    return order;
  }
}


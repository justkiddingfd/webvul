package com.webvul.shop.service;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CartServiceTest {
  @Test
  void addUpdateRemoveAndCount() {
    CartService cart = new CartService();
    Map<String, Object> session = new HashMap<>();

    cart.add(session, "p1", 1);
    cart.add(session, "p1", 2);
    cart.add(session, "p2", 3);

    assertThat(cart.countItems(session)).isEqualTo(6);
    assertThat(cart.snapshot(session)).containsEntry("p1", 3).containsEntry("p2", 3);

    cart.update(session, "p1", 1);
    assertThat(cart.countItems(session)).isEqualTo(4);
    assertThat(cart.snapshot(session)).containsEntry("p1", 1).containsEntry("p2", 3);

    cart.remove(session, "p2");
    assertThat(cart.countItems(session)).isEqualTo(1);
    assertThat(cart.snapshot(session)).containsOnlyKeys("p1");

    cart.clear(session);
    assertThat(cart.countItems(session)).isEqualTo(0);
  }
}


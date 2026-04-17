package com.webvul.shop.service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class CartService {
  public Map<String, Integer> getCart(Map<String, Object> session) {
    Object v = session.get("cart");
    if (v instanceof Map<?, ?> m) {
      Map<String, Integer> out = new LinkedHashMap<>();
      for (var e : m.entrySet()) {
        if (e.getKey() instanceof String k && e.getValue() instanceof Integer qty) out.put(k, qty);
      }
      session.put("cart", out);
      return out;
    }
    Map<String, Integer> fresh = new LinkedHashMap<>();
    session.put("cart", fresh);
    return fresh;
  }

  public void add(Map<String, Object> session, String productId, int quantity) {
    if (quantity <= 0) return;
    Map<String, Integer> cart = getCart(session);
    cart.put(productId, cart.getOrDefault(productId, 0) + quantity);
  }

  public void update(Map<String, Object> session, String productId, int quantity) {
    Map<String, Integer> cart = getCart(session);
    if (quantity <= 0) {
      cart.remove(productId);
      return;
    }
    cart.put(productId, quantity);
  }

  public void remove(Map<String, Object> session, String productId) {
    getCart(session).remove(productId);
  }

  public int countItems(Map<String, Object> session) {
    Map<String, Integer> cart = getCart(session);
    int total = 0;
    for (int q : cart.values()) total += q;
    return total;
  }

  public Map<String, Integer> snapshot(Map<String, Object> session) {
    return Collections.unmodifiableMap(new LinkedHashMap<>(getCart(session)));
  }

  public void clear(Map<String, Object> session) {
    getCart(session).clear();
  }
}


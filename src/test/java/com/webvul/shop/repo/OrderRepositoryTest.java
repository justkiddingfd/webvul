package com.webvul.shop.repo;

import com.webvul.shop.model.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderRepositoryTest {
  @Test
  void sumTotalCalculatesLineTotals() {
    List<OrderItem> items = List.of(
      new OrderItem("i1", "o1", "p1", "A", new BigDecimal("10.50"), 2),
      new OrderItem("i2", "o1", "p2", "B", new BigDecimal("5.00"), 3)
    );

    assertThat(OrderRepository.sumTotal(items)).isEqualByComparingTo(new BigDecimal("36.00"));
  }
}


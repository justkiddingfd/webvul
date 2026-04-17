package com.webvul.shop.actions;

import com.webvul.shop.model.Product;
import com.webvul.shop.repo.ProductRepository;
import com.webvul.shop.util.Money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartAction extends BaseAction {
  public record CartLine(Product product, int quantity, BigDecimal lineTotal) {
    public Product getProduct() {
      return product;
    }

    public int getQuantity() {
      return quantity;
    }

    public BigDecimal getLineTotal() {
      return lineTotal;
    }

    public String getLineTotalVnd() {
      return Money.vnd(lineTotal);
    }
  }

  private final ProductRepository products = new ProductRepository();
  private List<CartLine> lines;
  private BigDecimal total;

  @Override
  public String execute() {
    Map<String, Integer> cart = cartService.snapshot(session);
    lines = new ArrayList<>();
    total = BigDecimal.ZERO;
    for (var e : cart.entrySet()) {
      Product p = products.findById(e.getKey()).orElse(null);
      if (p == null) continue;
      int qty = e.getValue() == null ? 0 : e.getValue();
      if (qty <= 0) continue;
      BigDecimal lt = p.price().multiply(BigDecimal.valueOf(qty));
      lines.add(new CartLine(p, qty, lt));
      total = total.add(lt);
    }
    return SUCCESS;
  }

  public List<CartLine> getLines() {
    return lines;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public String getTotalVnd() {
    return Money.vnd(total);
  }
}


package com.webvul.shop.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public final class Money {
  private static final ThreadLocal<NumberFormat> VND =
      ThreadLocal.withInitial(
          () -> {
            NumberFormat f = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            f.setMaximumFractionDigits(0);
            f.setMinimumFractionDigits(0);
            return f;
          });

  private Money() {}

  public static String vnd(BigDecimal amount) {
    if (amount == null) return "";
    return VND.get().format(amount);
  }

  public static String vndInput(BigDecimal amount) {
    if (amount == null) return "";
    return amount.setScale(0, RoundingMode.HALF_UP).toPlainString();
  }
}

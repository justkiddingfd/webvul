package com.webvul.shop.app;

public final class AppConfig {
  private AppConfig() {}

  public static String getEnv(String key, String defaultValue) {
    String v = System.getenv(key);
    if (v == null) return defaultValue;
    String t = v.trim();
    return t.isEmpty() ? defaultValue : t;
  }

  public static int getEnvInt(String key, int defaultValue) {
    String v = System.getenv(key);
    if (v == null) return defaultValue;
    try {
      return Integer.parseInt(v.trim());
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  public static DbConfig db() {
    return new DbConfig(
      getEnv("DB_HOST", "localhost"),
      getEnvInt("DB_PORT", 3306),
      getEnv("DB_NAME", "webvul_shop"),
      getEnv("DB_USER", "webvul"),
      getEnv("DB_PASSWORD", "webvul_pass")
    );
  }
}


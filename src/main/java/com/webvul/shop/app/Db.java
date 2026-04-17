package com.webvul.shop.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.time.Duration;

public final class Db {
  private static volatile HikariDataSource dataSource;

  private Db() {}

  public static DataSource dataSource() {
    if (dataSource != null) return dataSource;
    synchronized (Db.class) {
      if (dataSource != null) return dataSource;

      DbConfig cfg = AppConfig.db();
      HikariConfig hc = new HikariConfig();
      hc.setDriverClassName("com.mysql.cj.jdbc.Driver");
      hc.setJdbcUrl(cfg.jdbcUrl());
      hc.setUsername(cfg.user());
      hc.setPassword(cfg.password());
      hc.setMaximumPoolSize(10);
      hc.setMinimumIdle(0);
      hc.setConnectionTimeout(Duration.ofSeconds(10).toMillis());
      hc.setValidationTimeout(Duration.ofSeconds(5).toMillis());
      hc.setIdleTimeout(Duration.ofSeconds(30).toMillis());
      hc.setMaxLifetime(Duration.ofMinutes(10).toMillis());
      hc.setPoolName("webvul-shop");

      dataSource = new HikariDataSource(hc);
      return dataSource;
    }
  }

  public static void shutdown() {
    HikariDataSource ds = dataSource;
    if (ds == null) return;
    synchronized (Db.class) {
      ds = dataSource;
      if (ds == null) return;
      try {
        ds.close();
      } finally {
        dataSource = null;
      }
    }
  }
}


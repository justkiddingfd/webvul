package com.webvul.shop.web;

import com.webvul.shop.app.Db;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public final class AppLifecycleListener implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent sce) {}

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    try {
      Db.shutdown();
    } catch (Throwable ignored) {
    }

    try {
      com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
    } catch (Throwable ignored) {
    }
  }
}

package com.webvul.shop.app;

public record DbConfig(String host, int port, String name, String user, String password) {
  public String jdbcUrl() {
    return "jdbc:mysql://" + host + ":" + port + "/" + name
      + "?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC"
      + "&useSSL=false&allowPublicKeyRetrieval=true";
  }
}


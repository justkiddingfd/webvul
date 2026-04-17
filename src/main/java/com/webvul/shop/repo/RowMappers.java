package com.webvul.shop.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

final class RowMappers {
  private RowMappers() {}

  static Instant instant(ResultSet rs, String col) throws SQLException {
    var ts = rs.getTimestamp(col);
    return ts == null ? null : ts.toInstant();
  }
}


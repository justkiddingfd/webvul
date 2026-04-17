package com.webvul.shop.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class UploadsServlet extends HttpServlet {
  private Path baseDir;

  @Override
  public void init() throws ServletException {
    String dir = System.getenv("UPLOAD_DIR");
    if (dir == null || dir.isBlank()) dir = System.getProperty("java.io.tmpdir") + "/webvul-uploads";
    baseDir = Path.of(dir).toAbsolutePath().normalize();
    try {
      Files.createDirectories(baseDir);
    } catch (IOException e) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String pathInfo = req.getPathInfo();
    if (pathInfo == null || pathInfo.isBlank() || "/".equals(pathInfo)) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    String name = pathInfo.startsWith("/") ? pathInfo.substring(1) : pathInfo;
    if (name.contains("/") || name.contains("\\") || name.contains("..")) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    Path file = baseDir.resolve(name).normalize();
    if (!file.startsWith(baseDir) || !Files.exists(file) || !Files.isRegularFile(file)) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    String ct = Files.probeContentType(file);
    if (ct == null || !ct.startsWith("image/")) ct = "application/octet-stream";
    resp.setContentType(ct);
    resp.setHeader("X-Content-Type-Options", "nosniff");
    resp.setHeader("Cache-Control", "private, max-age=86400");
    resp.setContentLengthLong(Files.size(file));
    Files.copy(file, resp.getOutputStream());
  }
}

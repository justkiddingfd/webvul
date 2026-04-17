package com.webvul.shop.actions;

import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.io.File;
import java.nio.file.Files;

// [A01-PathTraversal] Path Traversal: filename không được normalize
// Khai thác: /download.action?file=../../../../../../etc/passwd
@Action(
  value = "download",
  results = {
    @Result(name = "error", location = "/WEB-INF/content/error.jsp")
  }
)
public final class DownloadAction extends BaseAction {
  private String file;

  @Override
  public String execute() {
    if (file == null || file.isBlank()) {
      addActionError("Thiếu tên file.");
      return ERROR;
    }
    String uploadDir = System.getenv("UPLOAD_DIR");
    if (uploadDir == null || uploadDir.isBlank()) {
      uploadDir = System.getProperty("java.io.tmpdir") + "/webvul-uploads";
    }
    // VULNERABLE: nối chuỗi trực tiếp, không normalize — Path Traversal
    File target = new File(uploadDir + "/" + file);
    if (!target.exists() || !target.isFile()) {
      addActionError("File không tồn tại: " + target.getAbsolutePath());
      return ERROR;
    }
    try {
      byte[] bytes = Files.readAllBytes(target.toPath());
      HttpServletResponse resp = ServletActionContext.getResponse();
      resp.setContentType("application/octet-stream");
      resp.setHeader("Content-Disposition", "attachment; filename=" + target.getName());
      resp.setContentLength(bytes.length);
      resp.getOutputStream().write(bytes);
      resp.getOutputStream().flush();
      return null;
    } catch (Exception e) {
      addActionError("Không thể đọc file: " + e.getMessage());
      return ERROR;
    }
  }

  public void setFile(String file) { this.file = file; }
}

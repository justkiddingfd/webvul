package com.webvul.shop.actions;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

// [A10-SSRF] Admin nhập URL tuỳ ý — server fetch về, có thể truy cập internal network
// Khai thác: url=http://169.254.169.254/latest/meta-data/ (AWS metadata)
//            url=http://localhost:8080/debug.action
@Action(
  value = "admin-fetch",
  results = {
    @Result(name = "success", location = "/WEB-INF/content/admin-fetch.jsp"),
    @Result(name = "input",   location = "/WEB-INF/content/admin-fetch.jsp"),
    @Result(name = "error",   location = "/WEB-INF/content/admin-fetch.jsp")
  }
)
public final class AdminFetchAction extends BaseAction {
  private String url;
  private String fetchedContent;
  private String fetchError;

  @Override
  public String execute() {
    if (!isAdmin()) {
      addActionError("Chỉ admin mới truy cập được.");
      return ERROR;
    }
    if (url == null || url.isBlank()) return INPUT;

    try {
      // VULNERABLE: fetch bất kỳ URL nào, kể cả internal — SSRF
      URL target = new URL(url);
      URLConnection conn = target.openConnection();
      conn.setConnectTimeout(5000);
      conn.setReadTimeout(5000);
      conn.setRequestProperty("User-Agent", "WebVulShop/1.0");
      byte[] raw = conn.getInputStream().readAllBytes();
      fetchedContent = new String(raw, StandardCharsets.UTF_8);
      if (fetchedContent.length() > 50000) {
        fetchedContent = fetchedContent.substring(0, 50000) + "\n... [đã cắt bớt]";
      }
    } catch (Exception e) {
      fetchError = e.getClass().getSimpleName() + ": " + e.getMessage();
      return ERROR;
    }
    return SUCCESS;
  }

  public void setUrl(String url)          { this.url = url; }
  public String getUrl()                  { return url; }
  public String getFetchedContent()       { return fetchedContent; }
  public String getFetchError()           { return fetchError; }
}

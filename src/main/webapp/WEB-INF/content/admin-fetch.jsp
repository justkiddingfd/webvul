<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Fetch URL — Admin</title>
  <link rel="stylesheet" href="<s:url value='/assets/styles.css' />" />
</head>
<body>
  <jsp:include page="_layout.jsp" />
  <div class="container">
    <div class="banner" style="border-left:4px solid #e53; background:#fff5f5">
      <div style="font-weight:650; color:#e53">[A10] Server-Side Request Forgery (SSRF)</div>
      <div class="muted" style="font-size:12px; margin-top:4px">
        Server fetch nội dung từ URL tùy ý. Payload thử:<br/>
        <code>http://169.254.169.254/latest/meta-data/</code> (AWS metadata)<br/>
        <code>http://localhost:8080/debug.action</code> (internal service)<br/>
        <code>file:///etc/passwd</code> (local file via file:// scheme)
      </div>
    </div>

    <div class="card" style="margin-top:16px">
      <div class="card-body">
        <div style="font-weight:700; font-size:18px">Admin — Fetch URL</div>
        <form action="<s:url action='admin-fetch' />" method="post" style="margin-top:12px">
          <div class="form-row">
            <div>
              <label class="muted" style="font-size:12px">URL cần fetch</label>
              <input name="url" type="text" value="<s:property value='url' />" placeholder="https://..." style="font-family:monospace" />
            </div>
            <button class="btn btn-primary" type="submit">Fetch</button>
          </div>
        </form>
      </div>
    </div>

    <jsp:include page="_messages.jsp" />

    <s:if test="fetchError != null">
      <div class="banner" style="margin-top:12px; border-left:4px solid #e53; background:#fff5f5">
        <div style="font-weight:650; color:#e53">Lỗi:</div>
        <pre style="font-size:12px; margin-top:4px"><s:property value="fetchError" /></pre>
      </div>
    </s:if>

    <s:if test="fetchedContent != null">
      <div class="card" style="margin-top:12px">
        <div class="card-body">
          <div style="font-weight:650; font-size:13px; margin-bottom:8px">Nội dung từ: <code><s:property value="url" /></code></div>
          <pre style="font-size:12px; white-space:pre-wrap; word-break:break-all; max-height:500px; overflow:auto; background:#f8f8f8; padding:12px; border-radius:4px"><s:property value="fetchedContent" /></pre>
        </div>
      </div>
    </s:if>
  </div>
</body>
</html>

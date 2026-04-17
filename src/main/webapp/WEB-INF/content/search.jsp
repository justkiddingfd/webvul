<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Tìm kiếm — WebVul Shop</title>
  <link rel="stylesheet" href="<s:url value='/assets/styles.css' />" />
</head>
<body>
  <jsp:include page="_layout.jsp" />
  <div class="container">
    <div class="banner" style="border-left:4px solid #e53; background:#fff5f5">
      <div style="font-weight:650; color:#e53">[A03] SQL Injection</div>
      <div class="muted" style="font-size:12px; margin-top:4px">
        Payload thử: <code>' OR '1'='1</code> &nbsp;|&nbsp;
        <code>' UNION SELECT id,email,password_hash,role,created_at FROM users-- </code>
      </div>
    </div>

    <form action="<s:url action='search' />" method="get" style="margin-top:16px; display:flex; gap:8px">
      <input name="q" type="text" value="<s:property value='q' />" placeholder="Nhập từ khoá..." style="flex:1" />
      <button class="btn btn-primary" type="submit">Tìm kiếm</button>
    </form>

    <jsp:include page="_messages.jsp" />

    <s:if test="sqlError != null">
      <div class="banner" style="margin-top:12px; border-left:4px solid #e53; background:#fff5f5">
        <div style="font-weight:650; color:#e53">SQL Error (lộ thông tin lỗi):</div>
        <pre style="margin-top:4px; font-size:12px; white-space:pre-wrap"><s:property value="sqlError" /></pre>
      </div>
    </s:if>

    <s:if test="results != null">
      <div class="muted" style="margin-top:12px; font-size:13px">
        Tìm thấy <s:property value="results.size()" /> kết quả cho "<s:property value="q" />"
      </div>
      <div class="grid" style="margin-top:12px">
        <s:iterator value="results">
          <div class="card">
            <div class="card-body">
              <div style="font-weight:650"><s:property value="name" /></div>
              <div class="muted" style="font-size:13px; margin-top:4px"><s:property value="description" /></div>
              <div style="margin-top:8px; display:flex; gap:8px; align-items:center">
                <div class="pill" style="font-size:12px"><s:property value="priceVnd" /></div>
                <a class="btn" href="<s:url action='product'><s:param name='id' value='%{id}' /></s:url>">Xem</a>
              </div>
            </div>
          </div>
        </s:iterator>
      </div>
    </s:if>
  </div>
</body>
</html>

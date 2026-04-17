<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Debug — WebVul Shop</title>
  <link rel="stylesheet" href="<s:url value='/assets/styles.css' />" />
</head>
<body>
  <jsp:include page="_layout.jsp" />
  <div class="container">
    <div class="banner" style="border-left:4px solid #e53; background:#fff5f5">
      <div style="font-weight:650; color:#e53">[A05] Security Misconfiguration</div>
      <div class="muted" style="font-size:12px; margin-top:4px">
        Endpoint <code>/debug.action</code> không yêu cầu xác thực — lộ toàn bộ biến môi trường
        và system properties (DB credentials, secret keys, internal paths).
      </div>
    </div>

    <div class="card" style="margin-top:16px">
      <div class="card-body">
        <div style="font-weight:700; font-size:16px">Environment Variables</div>
        <table class="table" style="margin-top:8px; font-size:12px; font-family:monospace">
          <thead><tr><th>Key</th><th>Value</th></tr></thead>
          <tbody>
            <s:iterator value="envVars">
              <tr>
                <td><s:property value="key" /></td>
                <td><s:property value="value" /></td>
              </tr>
            </s:iterator>
          </tbody>
        </table>
      </div>
    </div>

    <div class="card" style="margin-top:16px">
      <div class="card-body">
        <div style="font-weight:700; font-size:16px">System Properties</div>
        <table class="table" style="margin-top:8px; font-size:12px; font-family:monospace">
          <thead><tr><th>Key</th><th>Value</th></tr></thead>
          <tbody>
            <s:iterator value="sysProps">
              <tr>
                <td><s:property value="key" /></td>
                <td style="word-break:break-all"><s:property value="value" /></td>
              </tr>
            </s:iterator>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</body>
</html>

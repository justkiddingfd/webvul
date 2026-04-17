<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Hồ sơ người dùng</title>
  <link rel="stylesheet" href="<s:url value='/assets/styles.css' />" />
</head>
<body>
  <jsp:include page="_layout.jsp" />
  <div class="container" style="max-width:560px">
    <div class="banner" style="border-left:4px solid #e53; background:#fff5f5">
      <div style="font-weight:650; color:#e53">[A01] IDOR — Broken Access Control</div>
      <div class="muted" style="font-size:12px; margin-top:4px">
        Thêm <code>?id=&lt;uuid&gt;</code> vào URL để xem profile của bất kỳ user nào.
        Enumerate user IDs từ order details hoặc brute-force UUID.
      </div>
    </div>
    <jsp:include page="_messages.jsp" />
    <div class="card" style="margin-top:16px">
      <div class="card-body">
        <div style="font-weight:800; font-size:20px">Hồ sơ người dùng</div>
        <div class="banner" style="margin-top:12px">
          <table style="width:100%; border-collapse:collapse; font-size:14px">
            <tr><td class="muted" style="padding:4px 8px 4px 0; width:120px">ID</td>
                <td style="padding:4px 0"><code><s:property value="profileUser.id" /></code></td></tr>
            <tr><td class="muted" style="padding:4px 8px 4px 0">Email</td>
                <td style="padding:4px 0"><s:property value="profileUser.email" /></td></tr>
            <tr><td class="muted" style="padding:4px 8px 4px 0">Role</td>
                <td style="padding:4px 0"><span class="pill"><s:property value="profileUser.role" /></span></td></tr>
            <tr><td class="muted" style="padding:4px 8px 4px 0">Ngày tạo</td>
                <td style="padding:4px 0"><s:property value="profileUser.createdAt" /></td></tr>
          </table>
        </div>
      </div>
    </div>
  </div>
</body>
</html>

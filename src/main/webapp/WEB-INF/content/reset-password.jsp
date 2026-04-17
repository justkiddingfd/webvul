<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Đặt lại mật khẩu</title>
  <link rel="stylesheet" href="<s:url value='/assets/styles.css' />" />
</head>
<body>
  <jsp:include page="_layout.jsp" />
  <div class="container" style="max-width:520px">
    <div class="banner" style="border-left:4px solid #e53; background:#fff5f5">
      <div style="font-weight:650; color:#e53">[A07] Brute-Force Token — Không có Rate Limiting</div>
      <div class="muted" style="font-size:12px; margin-top:4px">
        Token 6 chữ số + không có rate limiting = có thể brute-force tối đa 1,000,000 lần thử.
        Token không có expiry — tồn tại mãi mãi cho đến khi sử dụng.
      </div>
    </div>
    <jsp:include page="_messages.jsp" />
    <div class="card" style="margin-top:16px">
      <div class="card-body">
        <div style="font-weight:800; font-size:20px">Đặt lại mật khẩu</div>
        <form action="<s:url action='reset-password' />" method="post" style="margin-top:12px">
          <div class="form-row">
            <div>
              <label class="muted" style="font-size:12px">Email</label>
              <input name="email" type="email" value="<s:property value='email' />" />
            </div>
            <div>
              <label class="muted" style="font-size:12px">Token (6 chữ số)</label>
              <input name="token" type="text" value="<s:property value='token' />" placeholder="123456" maxlength="6" style="font-family:monospace; letter-spacing:4px" />
            </div>
            <div>
              <label class="muted" style="font-size:12px">Mật khẩu mới</label>
              <input name="newPassword" type="password" />
            </div>
            <button class="btn btn-primary" type="submit">Đặt lại mật khẩu</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</body>
</html>

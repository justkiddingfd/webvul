<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Quên mật khẩu</title>
  <link rel="stylesheet" href="<s:url value='/assets/styles.css' />" />
</head>
<body>
  <jsp:include page="_layout.jsp" />
  <div class="container" style="max-width:520px">
    <div class="banner" style="border-left:4px solid #e53; background:#fff5f5">
      <div style="font-weight:650; color:#e53">[A07] Weak Password Reset + Username Enumeration</div>
      <div class="muted" style="font-size:12px; margin-top:4px">
        Token chỉ 6 chữ số (brute-force được). Token hiển thị luôn trên màn hình.
        Thông báo lỗi khác nhau lộ sự tồn tại của email trong hệ thống.
      </div>
    </div>
    <jsp:include page="_messages.jsp" />
    <div class="card" style="margin-top:16px">
      <div class="card-body">
        <div style="font-weight:800; font-size:20px">Quên mật khẩu</div>
        <form action="<s:url action='forgot-password' />" method="post" style="margin-top:12px">
          <div class="form-row">
            <div>
              <label class="muted" style="font-size:12px">Email tài khoản</label>
              <input name="email" type="email" value="<s:property value='email' />" />
            </div>
            <button class="btn btn-primary" type="submit">Gửi token đặt lại</button>
          </div>
        </form>
        <div style="margin-top:12px; font-size:13px">
          <a href="<s:url action='reset-password' />">Đã có token? Đặt lại mật khẩu →</a>
        </div>
      </div>
    </div>
  </div>
</body>
</html>

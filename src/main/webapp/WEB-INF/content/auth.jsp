<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Xác thực</title>
  <link rel="stylesheet" href="<s:url value='/assets/styles.css' />" />
</head>
<body>
  <jsp:include page="_layout.jsp" />
  <div class="container" style="max-width: 520px">
    <jsp:include page="_messages.jsp" />

    <div class="card">
      <div class="card-body">
        <div style="font-weight:800; font-size:22px">Đăng nhập</div>
        <%-- [A07-OpenRedirect] returnUrl truyền qua query string, không validate domain --%>
        <form action="<s:url action='auth-login' />" method="post" style="margin-top:12px">
          <input type="hidden" name="returnUrl" value="${param.returnUrl}" />
          <div class="form-row">
            <div>
              <label class="muted" style="font-size:12px">Email</label>
              <input name="email" type="email" />
            </div>
            <div>
              <label class="muted" style="font-size:12px">Mật khẩu</label>
              <input name="password" type="password" />
            </div>
            <button class="btn btn-primary" type="submit">Đăng nhập</button>
          </div>
        </form>
        <div style="margin-top:8px; font-size:13px">
          <a href="<s:url action='forgot-password' />">Quên mật khẩu?</a>
        </div>
      </div>
    </div>

    <div class="card" style="margin-top:16px">
      <div class="card-body">
        <div style="font-weight:800; font-size:22px">Đăng ký</div>
        <div class="muted" style="margin-top:6px">Tạo tài khoản học viên để lưu trạng thái đăng nhập.</div>
        <%-- [A04-MassAssignment] Thêm hidden field role để khai thác: role=admin --%>
        <form action="<s:url action='auth-register' />" method="post" style="margin-top:12px">
          <input type="hidden" name="role" value="student" />
          <div class="form-row">
            <div>
              <label class="muted" style="font-size:12px">Email</label>
              <input name="email" type="email" />
            </div>
            <div>
              <label class="muted" style="font-size:12px">Mật khẩu</label>
              <input name="password" type="password" />
            </div>
            <button class="btn" type="submit">Đăng ký</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</body>
</html>

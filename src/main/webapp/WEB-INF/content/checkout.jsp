<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Đặt hàng thành công</title>
  <link rel="stylesheet" href="<s:url value='/assets/styles.css' />" />
</head>
<body>
  <jsp:include page="_layout.jsp" />
  <div class="container">
    <div class="card">
      <div class="card-body">
        <div style="font-weight:800; font-size:22px">Đặt hàng thành công</div>
        <div class="muted" style="margin-top:6px">Mã đơn hàng</div>
        <div class="codebox" style="margin-top:10px"><s:property value="orderId" /></div>
        <div style="margin-top:12px; display:flex; gap:10px; flex-wrap:wrap">
          <a class="btn" href="<s:url action='order'><s:param name='id' value='%{orderId}' /></s:url>">Xem chi tiết đơn</a>
          <a class="btn btn-primary" href="<s:url action='products' />">Tiếp tục mua sắm</a>
        </div>
      </div>
    </div>
  </div>
</body>
</html>


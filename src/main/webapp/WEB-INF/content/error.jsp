<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Có lỗi xảy ra</title>
  <link rel="stylesheet" href="<s:url value='/assets/styles.css' />" />
</head>
<body>
  <jsp:include page="_layout.jsp" />
  <div class="container" style="max-width: 720px">
    <div class="card">
      <div class="card-body">
        <div style="font-weight:900; font-size:26px">Có lỗi xảy ra</div>
        <jsp:include page="_messages.jsp" />
        <div style="margin-top:12px; display:flex; gap:10px; flex-wrap:wrap">
          <a class="btn btn-primary" href="<s:url action='products' />">Về cửa hàng</a>
        </div>
      </div>
    </div>
  </div>
</body>
</html>


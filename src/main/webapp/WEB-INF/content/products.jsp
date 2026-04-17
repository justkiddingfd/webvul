<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>WebVul Shop — Cửa hàng</title>
  <link rel="stylesheet" href="<s:url value='/assets/styles.css' />" />
</head>
<body>
  <jsp:include page="_layout.jsp" />
  <div class="container">
    <div class="banner">
      <div style="font-weight: 650">Môi trường training</div>
      <div class="muted">Không dùng dữ liệu thật. Dữ liệu và đơn hàng chỉ phục vụ mục đích học tập.</div>
    </div>
    <jsp:include page="_messages.jsp" />
    <%-- [A03-SQLi] Form tìm kiếm — query truyền thẳng vào SQL --%>
    <form action="<s:url action='search' />" method="get" style="margin-bottom:16px; display:flex; gap:8px">
      <input name="q" type="text" placeholder="Tìm kiếm sản phẩm..." style="flex:1" />
      <button class="btn btn-primary" type="submit">Tìm kiếm</button>
    </form>
    <div class="grid">
      <s:iterator value="items">
        <div class="card">
          <s:if test="imageUrl != null && imageUrl.trim().length() > 0">
            <img class="product-cover" src="<s:property value='imageUrl' />" alt="<s:property value='name' />" />
          </s:if>
          <s:else>
            <div class="product-cover-fallback"></div>
          </s:else>
          <div class="card-body">
            <div style="display:flex; justify-content:space-between; gap:12px; align-items:flex-start">
              <div style="font-weight:650"><s:property value="name" /></div>
              <div class="pill" style="font-size:12px"><s:property value="priceVnd" /></div>
            </div>
            <div class="muted" style="margin-top:8px; font-size:13px; line-height:1.4">
              <s:property value="description" />
            </div>
            <div style="display:flex; gap:10px; margin-top:12px">
              <a class="btn" href="<s:url action='product'><s:param name='id' value='%{id}' /></s:url>">Xem</a>
              <form action="<s:url action='cart-add' />" method="post" style="display:inline">
                <input type="hidden" name="productId" value="<s:property value='id' />" />
                <input type="hidden" name="quantity" value="1" />
                <button class="btn btn-primary" type="submit">Thêm giỏ</button>
              </form>
            </div>
          </div>
        </div>
      </s:iterator>
    </div>
  </div>
</body>
</html>


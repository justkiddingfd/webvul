<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Đơn hàng</title>
  <link rel="stylesheet" href="<s:url value='/assets/styles.css' />" />
</head>
<body>
  <jsp:include page="_layout.jsp" />
  <div class="container">
    <div class="card">
      <div class="card-body">
        <div style="display:flex; justify-content:space-between; gap:12px; align-items:flex-start; flex-wrap:wrap">
          <div>
            <div style="font-weight:800; font-size:22px">Đơn hàng</div>
            <div class="muted" style="margin-top:6px">Mã đơn: <s:property value="order.id" /></div>
          </div>
          <div class="pill">Tổng: <s:property value="order.totalAmountVnd" /></div>
        </div>
        <div class="banner" style="margin-top:14px">
          <div style="font-weight:650">Người nhận</div>
          <div style="margin-top:6px"><s:property value="order.receiverName" /></div>
          <div class="muted" style="margin-top:2px"><s:property value="order.receiverEmail" /></div>
          <div class="muted" style="margin-top:6px"><s:property value="order.receiverAddress" /></div>
        </div>
        <table class="table" style="margin-top:12px">
          <thead>
            <tr>
              <th>Sản phẩm</th>
              <th>Đơn giá</th>
              <th>Số lượng</th>
            </tr>
          </thead>
          <tbody>
            <s:iterator value="order.items">
              <tr>
                <td><s:property value="productName" /></td>
                <td><s:property value="unitPriceVnd" /></td>
                <td><s:property value="quantity" /></td>
              </tr>
            </s:iterator>
          </tbody>
        </table>
        <div style="margin-top:12px">
          <a class="btn btn-primary" href="<s:url action='products' />">Về cửa hàng</a>
        </div>
      </div>
    </div>
  </div>
</body>
</html>


<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Giỏ hàng — Thanh toán</title>
  <link rel="stylesheet" href="<s:url value='/assets/styles.css' />" />
</head>
<body>
  <jsp:include page="_layout.jsp" />
  <div class="container">
    <jsp:include page="_messages.jsp" />
    <div class="two-col">
      <div class="card">
        <div class="card-body">
          <div style="font-weight:700; font-size:18px">Giỏ hàng</div>
          <s:if test="lines.size() == 0">
            <div class="banner" style="margin-top:12px">
              <div>Giỏ hàng đang trống.</div>
              <div style="margin-top:10px">
                <a class="btn btn-primary" href="<s:url action='products' />">Đi mua sắm</a>
              </div>
            </div>
          </s:if>
          <s:else>
            <table class="table" style="margin-top:12px">
              <thead>
                <tr>
                  <th>Sản phẩm</th>
                  <th>Đơn giá</th>
                  <th>Số lượng</th>
                  <th>Thành tiền</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <s:iterator value="lines">
                  <tr>
                    <td>
                      <div style="font-weight:650"><s:property value="product.name" /></div>
                      <div class="muted" style="font-size:12px"><s:property value="product.description" /></div>
                    </td>
                    <td><s:property value="product.priceVnd" /></td>
                    <td>
                      <form action="<s:url action='cart-update' />" method="post" style="display:flex; gap:8px; align-items:center">
                        <input type="hidden" name="productId" value="<s:property value='product.id' />" />
                        <input type="number" name="quantity" min="0" value="<s:property value='quantity' />" style="max-width:120px" />
                        <button class="btn" type="submit">Cập nhật</button>
                      </form>
                    </td>
                    <td><s:property value="lineTotalVnd" /></td>
                    <td>
                      <form action="<s:url action='cart-remove' />" method="post">
                        <input type="hidden" name="productId" value="<s:property value='product.id' />" />
                        <button class="btn" type="submit">Xoá</button>
                      </form>
                    </td>
                  </tr>
                </s:iterator>
              </tbody>
            </table>
          </s:else>
        </div>
      </div>
      <div class="card">
        <div class="card-body">
          <div style="font-weight:700; font-size:18px">Thanh toán</div>
          <div class="banner" style="margin-top:12px">
            <div class="muted">Tổng tiền</div>
            <div style="font-weight:800; font-size:22px"><s:property value="totalVnd" /></div>
          </div>
          <form action="<s:url action='checkout' />" method="post" style="margin-top:12px">
            <div class="form-row">
              <div>
                <label class="muted" style="font-size:12px">Tên người nhận</label>
                <input name="receiverName" />
              </div>
              <div>
                <label class="muted" style="font-size:12px">Email người nhận</label>
                <input name="receiverEmail" type="email" />
              </div>
              <div>
                <label class="muted" style="font-size:12px">Địa chỉ người nhận</label>
                <textarea name="receiverAddress"></textarea>
              </div>
              <button class="btn btn-primary" type="submit">Tạo đơn</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</body>
</html>


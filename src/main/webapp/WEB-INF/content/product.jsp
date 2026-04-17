<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title><s:property value="product.name" /> — Chi tiết</title>
  <link rel="stylesheet" href="<s:url value='/assets/styles.css' />" />
</head>
<body>
  <jsp:include page="_layout.jsp" />
  <div class="container">
    <a class="pill" href="<s:url action='products' />">← Quay lại cửa hàng</a>
    <jsp:include page="_messages.jsp" />
    <div class="two-col" style="margin-top:16px">
      <div class="card">
        <s:if test="product.imageUrl != null && product.imageUrl.trim().length() > 0">
          <img class="product-cover product-cover-lg" src="<s:property value='product.imageUrl' />" alt="<s:property value='product.name' />" />
        </s:if>
        <s:else>
          <div class="product-cover-fallback product-cover-lg"></div>
        </s:else>
      </div>
      <div class="card">
        <div class="card-body">
          <div style="font-weight:700; font-size:20px"><s:property value="product.name" /></div>
          <div class="muted" style="margin-top:6px"><s:property value="product.description" /></div>
          <div style="margin-top:12px; display:flex; gap:10px; align-items:center">
            <div class="pill">Giá: <s:property value="product.priceVnd" /></div>
          </div>
          <div style="margin-top:14px">
            <form action="<s:url action='cart-add' />" method="post">
              <input type="hidden" name="productId" value="<s:property value='product.id' />" />
              <div class="form-row">
                <div>
                  <label class="muted" style="font-size:12px">Số lượng</label>
                  <input type="number" name="quantity" value="1" min="1" />
                </div>
                <button class="btn btn-primary" type="submit">Thêm vào giỏ</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>

    <%-- [A03-XSS] Stored XSS: review.content hiển thị bằng ${...} — không escape HTML --%>
    <div class="card" style="margin-top:20px">
      <div class="card-body">
        <div style="font-weight:700; font-size:18px">Đánh giá sản phẩm</div>
        <s:if test="reviews != null && reviews.size() > 0">
          <s:iterator value="reviews">
            <div class="banner" style="margin-top:10px">
              <div style="font-weight:650; font-size:13px"><s:property value="reviewerName" /></div>
              <%-- VULNERABLE: dùng EL expression ${} thay vì <s:property> — không escape XSS --%>
              <div style="margin-top:4px; font-size:13px">${review.content}</div>
            </div>
          </s:iterator>
        </s:if>
        <s:else>
          <div class="muted" style="margin-top:8px; font-size:13px">Chưa có đánh giá nào.</div>
        </s:else>

        <form action="<s:url action='add-review' />" method="post" style="margin-top:14px">
          <input type="hidden" name="productId" value="<s:property value='product.id' />" />
          <div class="form-row">
            <div>
              <label class="muted" style="font-size:12px">Tên của bạn</label>
              <input name="reviewerName" type="text" placeholder="Ẩn danh" />
            </div>
            <div>
              <label class="muted" style="font-size:12px">Nội dung đánh giá</label>
              <input name="content" type="text" placeholder="Nhập đánh giá..." />
            </div>
            <button class="btn" type="submit">Gửi đánh giá</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</body>
</html>


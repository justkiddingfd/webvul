<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Admin — Sản phẩm</title>
  <link rel="stylesheet" href="<s:url value='/assets/styles.css' />" />
</head>
<body>
  <jsp:include page="_layout.jsp" />
  <div class="container" style="max-width: 820px">
    <jsp:include page="_messages.jsp" />

    <div class="card">
      <div class="card-body">
        <div style="font-weight:800; font-size:22px">Sản phẩm</div>
        <div class="muted" style="margin-top:6px">Nhập giá theo VNĐ (ví dụ: 590000 hoặc 590.000).</div>

        <form action="<s:url action='admin-product-save' />" method="post" enctype="multipart/form-data" style="margin-top:12px">
          <input type="hidden" name="id" value="<s:property value='product.id' />" />

          <div class="form-row">
            <div>
              <label class="muted" style="font-size:12px">Ảnh sản phẩm</label>
              <s:if test="product.imageUrl != null && product.imageUrl.trim().length() > 0">
                <img class="product-cover product-cover-lg" style="margin-top:10px" src="<s:property value='product.imageUrl' />" alt="<s:property value='product.name' />" />
              </s:if>
              <s:else>
                <div class="product-cover-fallback product-cover-lg" style="margin-top:10px"></div>
              </s:else>
              <%-- [A08-UnrestrictedUpload] Bỏ accept — cho phép upload mọi loại file --%>
              <input type="file" name="image" style="margin-top:10px" />
            </div>
            <div>
              <label class="muted" style="font-size:12px">Tên sản phẩm</label>
              <input name="name" value="<s:property value='product.name' />" />
            </div>
            <div>
              <label class="muted" style="font-size:12px">Giá (VNĐ)</label>
              <input name="priceVnd" value="<s:property value='product.priceVndInput' />" />
            </div>
            <div>
              <label class="muted" style="font-size:12px">Mô tả</label>
              <textarea name="description"><s:property value="product.description" /></textarea>
            </div>
            <div style="display:flex; gap:10px; flex-wrap:wrap">
              <button class="btn btn-primary" type="submit">Lưu</button>
              <a class="btn" href="<s:url action='admin-products' />">Quay lại</a>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</body>
</html>

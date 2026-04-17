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
  <div class="container">
    <jsp:include page="_messages.jsp" />

    <div class="card">
      <div class="card-body" style="display:flex; justify-content:space-between; gap:12px; align-items:center; flex-wrap:wrap">
        <div>
          <div style="font-weight:800; font-size:22px">Quản trị sản phẩm</div>
          <div class="muted" style="margin-top:6px">Chỉ admin có quyền thêm/cập nhật sản phẩm.</div>
        </div>
        <a class="btn btn-primary" href="<s:url action='admin-product' />">Thêm sản phẩm</a>
      </div>
    </div>

    <div class="card" style="margin-top:16px">
      <div class="card-body">
        <table class="table">
          <thead>
            <tr>
              <th>Tên</th>
              <th>Giá</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <s:iterator value="items">
              <tr>
                <td>
                  <div style="display:flex; gap:12px; align-items:flex-start">
                    <s:if test="imageUrl != null && imageUrl.trim().length() > 0">
                      <img class="thumb" src="<s:property value='imageUrl' />" alt="<s:property value='name' />" />
                    </s:if>
                    <s:else>
                      <div class="thumb thumb-fallback"></div>
                    </s:else>
                    <div>
                      <div style="font-weight:650"><s:property value="name" /></div>
                      <div class="muted" style="font-size:12px"><s:property value="description" /></div>
                    </div>
                  </div>
                </td>
                <td><s:property value="priceVnd" /></td>
                <td style="text-align:right">
                  <a class="btn" href="<s:url action='admin-product'><s:param name='id' value='%{id}' /></s:url>">Sửa</a>
                </td>
              </tr>
            </s:iterator>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</body>
</html>

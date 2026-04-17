<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="header">
  <div class="header-inner">
    <div class="brand">
      <div class="brand-badge"></div>
      <div>
        <div style="font-weight: 650; line-height: 1">WebVul Shop</div>
        <div class="muted" style="font-size: 12px">Training shop demo</div>
      </div>
    </div>
    <div class="nav">
      <a class="pill" href="<s:url action='products' />">Cửa hàng</a>
      <a class="pill" href="<s:url action='search' />">Tìm kiếm</a>
      <a class="pill" href="<s:url action='cart' />">Giỏ hàng (<s:property value='cartCount' />)</a>
      <s:if test="admin">
        <a class="pill" href="<s:url action='debug' />" style="color:#e53">Debug</a>
        <a class="pill" href="<s:url action='admin-products' />">Admin</a>
        <a class="pill" href="<s:url action='admin-fetch' />">Fetch URL</a>
        <a class="pill" href="<s:url action='admin-cmd' />" style="color:#e53">Terminal</a>
      </s:if>
      <s:if test="loggedIn">
        <a class="pill" href="<s:url action='profile' />"><s:property value='userEmail' /></a>
        <form action="<s:url action='auth-logout' />" method="post" style="display:inline">
          <button class="btn" type="submit">Đăng xuất</button>
        </form>
      </s:if>
      <s:else>
        <a class="btn" href="<s:url action='auth' />">Đăng nhập</a>
      </s:else>
    </div>
  </div>
</div>

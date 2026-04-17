<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Admin — Command Injection</title>
  <link rel="stylesheet" href="<s:url value='/assets/styles.css' />" />
</head>
<body>
  <jsp:include page="_layout.jsp" />
  <div class="container">
    <div class="banner" style="border-left:4px solid #e53; background:#fff5f5">
      <div style="font-weight:650; color:#e53">[A03] Command Injection — OS Command Execution</div>
      <div class="muted" style="font-size:12px; margin-top:4px">
        Lệnh nhập vào được truyền trực tiếp vào <code>sh -c &lt;cmd&gt;</code> mà không sanitize.<br/>
        Payload: <code>id</code> &nbsp;|&nbsp;
        <code>cat /etc/passwd</code> &nbsp;|&nbsp;
        <code>ls /; whoami</code> &nbsp;|&nbsp;
        <code>ping -c 1 127.0.0.1 &amp;&amp; id</code>
      </div>
    </div>

    <div class="card" style="margin-top:16px">
      <div class="card-body">
        <div style="font-weight:800; font-size:20px">Admin — Thực thi lệnh hệ thống</div>
        <form action="<s:url action='admin-cmd' />" method="post" style="margin-top:12px">
          <div class="form-row">
            <div>
              <label class="muted" style="font-size:12px">Lệnh (sh -c)</label>
              <input name="cmd" type="text" value="<s:property value='cmd' />"
                     placeholder="vd: id, ls /, cat /etc/hostname"
                     style="font-family:monospace" autofocus />
            </div>
            <button class="btn btn-primary" type="submit">Thực thi</button>
          </div>
        </form>
      </div>
    </div>

    <jsp:include page="_messages.jsp" />

    <s:if test="cmdOutput != null">
      <div class="card" style="margin-top:12px">
        <div class="card-body">
          <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:8px">
            <div style="font-weight:650; font-size:13px">
              Output — <code><s:property value="cmd" /></code>
            </div>
            <span class="pill" style="font-size:11px">
              exit code: <s:property value="exitCode" />
            </span>
          </div>
          <pre style="font-size:12px; white-space:pre-wrap; word-break:break-all;
                      background:#1e1e1e; color:#d4d4d4; padding:14px; border-radius:6px;
                      max-height:500px; overflow:auto; margin:0"><s:property value="cmdOutput" /></pre>
        </div>
      </div>
    </s:if>
  </div>
</body>
</html>

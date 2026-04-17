<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:if test="hasActionErrors()">
  <div class="msg msg-error">
    <s:iterator value="actionErrors">
      <div><s:property /></div>
    </s:iterator>
  </div>
</s:if>
<s:if test="hasActionMessages()">
  <div class="msg msg-ok">
    <s:iterator value="actionMessages">
      <div><s:property /></div>
    </s:iterator>
  </div>
</s:if>

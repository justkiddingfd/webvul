package com.webvul.shop.actions;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Map;
import java.util.TreeMap;

// [A05-Misconfiguration] Endpoint debug không yêu cầu auth, lộ thông tin hệ thống
@Action(
  value = "debug",
  results = {
    @Result(name = "success", location = "/WEB-INF/content/debug.jsp")
  }
)
public final class DebugAction extends BaseAction {
  private Map<String, String> envVars;
  private Map<String, String> sysProps;

  @Override
  public String execute() {
    // VULNERABLE: không có auth check — bất kỳ ai cũng truy cập được
    envVars = new TreeMap<>(System.getenv());
    sysProps = new TreeMap<>();
    System.getProperties().forEach((k, v) -> sysProps.put(String.valueOf(k), String.valueOf(v)));
    return SUCCESS;
  }

  public Map<String, String> getEnvVars()  { return envVars; }
  public Map<String, String> getSysProps() { return sysProps; }
}

package com.webvul.shop.actions;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

// [A03-CmdInjection] Admin có thể chạy lệnh OS tuỳ ý — Command Injection
// Khai thác: cmd=id ; cat /etc/passwd
//            cmd=ping+-c+1+127.0.0.1 | whoami
//            cmd=ls /
@Action(
  value = "admin-cmd",
  results = {
    @Result(name = "success", location = "/WEB-INF/content/admin-cmd.jsp"),
    @Result(name = "input",   location = "/WEB-INF/content/admin-cmd.jsp"),
    @Result(name = "error",   location = "/WEB-INF/content/admin-cmd.jsp")
  }
)
public final class AdminCmdAction extends BaseAction {
  private String cmd;
  private String cmdOutput;
  private int exitCode = -1;

  @Override
  public String execute() {
    if (!isAdmin()) {
      addActionError("Chỉ admin mới có quyền truy cập.");
      return ERROR;
    }
    if (cmd == null || cmd.isBlank()) return INPUT;

    try {
      // VULNERABLE: truyền cmd trực tiếp vào shell — Command Injection
      ProcessBuilder pb = new ProcessBuilder("sh", "-c", cmd);
      pb.redirectErrorStream(true);
      Process proc = pb.start();

      StringBuilder sb = new StringBuilder();
      try (BufferedReader br = new BufferedReader(
               new InputStreamReader(proc.getInputStream(), StandardCharsets.UTF_8))) {
        String line;
        while ((line = br.readLine()) != null) {
          sb.append(line).append("\n");
          if (sb.length() > 100_000) { sb.append("[output truncated]"); break; }
        }
      }
      exitCode = proc.waitFor();
      cmdOutput = sb.toString();
    } catch (Exception e) {
      cmdOutput = "Lỗi: " + e.getMessage();
      return ERROR;
    }
    return SUCCESS;
  }

  public void setCmd(String cmd)   { this.cmd = cmd; }
  public String getCmd()           { return cmd; }
  public String getCmdOutput()     { return cmdOutput; }
  public int getExitCode()         { return exitCode; }
}

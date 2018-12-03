package com.hyc.newsmallexcellent.base.bean;

public class BaseRequestBean<D> {

  private int status; // 请求状态

  private String msg; // 请求响应结果

  private D data; // 请求响应：服务器返回的数据

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public D getData() {
    return data;
  }

  public void setData(D data) {
    this.data = data;
  }
}

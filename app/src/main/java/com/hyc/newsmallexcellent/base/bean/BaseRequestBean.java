package com.hyc.newsmallexcellent.base.bean;

public class BaseRequestBean<D> {

  private int status;

  private String msg;

  private D data;

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

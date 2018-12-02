package com.hyc.newsmallexcellent.bean;

public class LoginActionBean {

  /**
   * id : 2
   * accountname : 15273311295
   * password :
   * role : 用户
   * isAuthentication : 0
   * status : 0
   * remark : null
   */

  private int id;
  private String accountname;
  private String password;
  private String role;
  private int isAuthentication;
  private int status;
  private Object remark;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAccountname() {
    return accountname;
  }

  public void setAccountname(String accountname) {
    this.accountname = accountname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public int getIsAuthentication() {
    return isAuthentication;
  }

  public void setIsAuthentication(int isAuthentication) {
    this.isAuthentication = isAuthentication;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Object getRemark() {
    return remark;
  }

  public void setRemark(Object remark) {
    this.remark = remark;
  }
}

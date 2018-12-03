package com.hyc.newsmallexcellent.bean;

import com.google.gson.annotations.SerializedName;

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
  @SerializedName("role") private String nickname;
  private int isAuthentication;
  private int status;
  @SerializedName("remark") private String headUrl;

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

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getHeadUrl() {
    return headUrl;
  }

  public void setHeadUrl(String headUrl) {
    this.headUrl = headUrl;
  }
}

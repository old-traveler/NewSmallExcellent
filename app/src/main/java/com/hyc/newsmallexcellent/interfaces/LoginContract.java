package com.hyc.newsmallexcellent.interfaces;

import com.hyc.newsmallexcellent.base.interfaces.ILoading;

public interface LoginContract {

  interface Presenter{
    void login();
  }

  interface View extends ILoading {
    String getUsername();
    String getPassword();
    boolean verificationInput();
    void onLoginSuccess();
  }

}

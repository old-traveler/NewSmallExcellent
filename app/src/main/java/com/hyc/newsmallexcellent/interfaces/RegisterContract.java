package com.hyc.newsmallexcellent.interfaces;

import com.hyc.newsmallexcellent.base.interfaces.ILoading;

public interface RegisterContract {

  public interface IRegisterPresenter {

    void sendVerificationCode();

    void registerNewUser();

  }

  public interface IRegisterView extends ILoading {

    String getUsername();

    String getPassword();

    String getVerificationCode();

    void onSendVerificationCodeSuccess();

    void onRegisterSuccess();

    boolean verificationInput();



  }


}

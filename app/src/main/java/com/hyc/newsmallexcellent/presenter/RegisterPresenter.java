package com.hyc.newsmallexcellent.presenter;

import android.text.TextUtils;
import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.interfaces.RegisterContract;
import com.hyc.newsmallexcellent.model.UserModel;

public class RegisterPresenter extends BasePresenter<RegisterContract.IRegisterView>
    implements RegisterContract.IRegisterPresenter {

  private UserModel userModel = new UserModel();

  @Override
  public void sendVerificationCode() {
    if (TextUtils.isEmpty(mvpView.getUsername())) {
      ToastHelper.toast("请输入手机号");
    } else {
      mvpView.showLoadingView();
      addDisposable(userModel.verificationUserPhone(mvpView.getUsername())
          .subscribe(new BaseRequestConsumer<Object>(mvpView) {
            @Override
            protected void onRequestSuccess(Object data) {
              mvpView.onSendVerificationCodeSuccess();
            }
          }, new BaseErrorConsumer(mvpView)));
    }
  }

  @Override
  public void registerNewUser() {
    if (mvpView.verificationInput()) {
      mvpView.showLoadingView();
      addDisposable(userModel.register(mvpView.getUsername(), mvpView.getPassword(),
          mvpView.getVerificationCode())
          .subscribe(new BaseRequestConsumer<Object>(mvpView) {

            @Override
            protected void onRequestSuccess(Object data) {
              mvpView.onRegisterSuccess();
            }
          }, new BaseErrorConsumer(mvpView)));
    }
  }
}

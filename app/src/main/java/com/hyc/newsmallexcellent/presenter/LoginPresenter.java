package com.hyc.newsmallexcellent.presenter;

import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.bean.LoginActionBean;
import com.hyc.newsmallexcellent.interfaces.LoginContract;
import com.hyc.newsmallexcellent.model.UserModel;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

  private UserModel userModel = new UserModel();

  @Override
  public void login() {
    if (mvpView.verificationInput()){
      mvpView.showLoadingView();
      addDisposable(userModel.login(mvpView.getUsername(),mvpView.getPassword())
      .subscribe(new BaseRequestConsumer<LoginActionBean>(mvpView){

        @Override
        protected void onRequestSuccess(LoginActionBean data) {
          mvpView.onLoginSuccess();
        }
      },new BaseErrorConsumer(mvpView)));
    }
  }

}

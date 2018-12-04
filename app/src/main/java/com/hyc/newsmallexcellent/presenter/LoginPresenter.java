package com.hyc.newsmallexcellent.presenter;

import android.util.Log;

import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.bean.LoginActionBean;
import com.hyc.newsmallexcellent.interfaces.LoginContract;
import com.hyc.newsmallexcellent.model.UserModel;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

  private UserModel userModel = new UserModel();

  @Override
  public void login() {
    if (mvpView.verificationInput()){
      addDisposable(userModel.login(mvpView.getUsername(), mvpView.getPassword(),
          new BaseRequestConsumer<LoginActionBean>(mvpView) {
            @Override
            protected void onRequestSuccess(LoginActionBean data) {
              userModel.cacheUserInfo(data);
                Log.d("TAG",data.getNickname().toString());
              mvpView.onLoginSuccess();
            }
          },mvpView));
    }
  }

}

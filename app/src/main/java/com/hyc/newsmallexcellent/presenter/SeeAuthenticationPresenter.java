package com.hyc.newsmallexcellent.presenter;

import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.bean.AuthenticationBean;
import com.hyc.newsmallexcellent.interfaces.SeeAuthentication;
import com.hyc.newsmallexcellent.model.UserModel;

public class SeeAuthenticationPresenter extends BasePresenter<SeeAuthentication.IView>
    implements SeeAuthentication.IPresenter {
  private UserModel userModel = new UserModel();

  @Override
  public void fetchAllAuthentication() {
    mvpView.showLoadingView();
    addDisposable(userModel.findAllAuthentication(20, mvpView.getCurPage()).subscribe(
        new BaseRequestConsumer<AuthenticationBean>(mvpView) {
          @Override
          protected void onRequestSuccess(AuthenticationBean data) {
            mvpView.loadAuthentication(data);
          }
        }, new BaseErrorConsumer(mvpView)));
  }
}

package com.hyc.newsmallexcellent.presenter;

import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.interfaces.DealAuthenticationContact;
import com.hyc.newsmallexcellent.model.UserModel;

public class DealAuthenticationPresenter extends BasePresenter<DealAuthenticationContact.IView>
    implements DealAuthenticationContact.IPresenter {

  private UserModel userModel = new UserModel();

  @Override
  public void dealAuthentication(boolean isPass) {
    mvpView.showLoadingView();
    addDisposable(userModel.dealAuthentication(mvpView.getListBean().getId(),
        mvpView.getListBean().getUserId(), mvpView.getListBean().getAuthenticationType(),
        isPass ? 2 : 3, mvpView.getResult()).subscribe(new BaseRequestConsumer<Object>(mvpView) {
      @Override
      protected void onRequestSuccess(Object data) {
        mvpView.onDealSuccess();
      }
    },new BaseErrorConsumer(mvpView)));
  }
}

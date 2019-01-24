package com.hyc.newsmallexcellent.presenter;

import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.bean.FootPrintBean;
import com.hyc.newsmallexcellent.interfaces.FootprintContact;
import com.hyc.newsmallexcellent.model.UserModel;

public class FootprintPresenter extends BasePresenter<FootprintContact.IView>
    implements FootprintContact.IPresenter {

  UserModel userModel = new UserModel();

  @Override
  public void fetchFootPrint() {
    mvpView.showLoadingView();
    addDisposable(
        userModel.findAllFootprint(userModel.getCurUserId(), 20, mvpView.getCurPage()).subscribe(
            new BaseRequestConsumer<FootPrintBean>(mvpView) {
              @Override
              protected void onRequestSuccess(FootPrintBean data) {
                mvpView.loadFootPrint(data);
              }
            }, new BaseErrorConsumer(mvpView)));
  }

  @Override
  public void deleteFootprint(int id, int position) {
    mvpView.showLoadingView();
    addDisposable(userModel.deleteFootprint(id).subscribe(new BaseRequestConsumer<Object>(mvpView) {
      @Override
      protected void onRequestSuccess(Object data) {
        mvpView.onSuccessDeleteFootPrint(position);
      }
    }, new BaseErrorConsumer(mvpView)));
  }
}

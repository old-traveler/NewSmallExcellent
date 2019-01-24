package com.hyc.newsmallexcellent.presenter;

import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.bean.ApplyBean;
import com.hyc.newsmallexcellent.interfaces.MyApplyContact;
import com.hyc.newsmallexcellent.model.JobModel;
import com.hyc.newsmallexcellent.model.UserModel;

public class MyApplyPresenter extends BasePresenter<MyApplyContact.IView>
    implements MyApplyContact.IPresenter {

  private JobModel jobModel = new JobModel();

  private UserModel userModel = new UserModel();

  @Override
  public void fetchApplyInfo(boolean isLoadMore) {
    mvpView.showLoadingView();
    addDisposable(jobModel.findAllApply(userModel.getCurUserId(),20,mvpView.getCurPage()).subscribe(
        new BaseRequestConsumer<ApplyBean>(mvpView) {
          @Override
          protected void onRequestSuccess(ApplyBean data) {
            mvpView.loadApplyInfo(isLoadMore,data);
          }
        },new BaseErrorConsumer(mvpView)));
  }

  @Override
  public void cancelApply(int id, int position) {
    mvpView.showLoadingView();
    addDisposable(jobModel.cancelApply(id).subscribe(new BaseRequestConsumer<Object>(mvpView) {
      @Override
      protected void onRequestSuccess(Object data) {
        mvpView.onSuccessCancelApply(position);
      }
    },new BaseErrorConsumer(mvpView)));

  }

}

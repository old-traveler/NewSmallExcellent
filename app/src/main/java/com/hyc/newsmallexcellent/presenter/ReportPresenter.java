package com.hyc.newsmallexcellent.presenter;

import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.interfaces.ReportContact;
import com.hyc.newsmallexcellent.model.JobModel;
import com.hyc.newsmallexcellent.model.UserModel;

public class ReportPresenter extends BasePresenter<ReportContact.IView> implements ReportContact.IPresenter {


  UserModel userModel = new UserModel();

  JobModel jobModel = new JobModel();


  @Override
  public void report() {
    mvpView.showLoadingView();
    addDisposable(jobModel.reportUser(userModel.getCurUserId(),mvpView.getBereportUserId(),mvpView.getReportContent())
    .subscribe(new BaseRequestConsumer<Object>(mvpView) {
      @Override
      protected void onRequestSuccess(Object data) {
        mvpView.onSuccessSendReport();
      }
    },new BaseErrorConsumer(mvpView)));
  }

}

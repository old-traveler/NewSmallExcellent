package com.hyc.newsmallexcellent.presenter;

import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.interfaces.ApplyJobContact;
import com.hyc.newsmallexcellent.model.JobModel;
import com.hyc.newsmallexcellent.model.UserModel;

public class ApplyJobPresenter extends BasePresenter<ApplyJobContact.IView>
    implements ApplyJobContact.IPresenter {

  JobModel jobModel = new JobModel();

  UserModel userModel = new UserModel();

  @Override
  public void applyJob() {
    addDisposable(
        jobModel.applyJob(userModel.getCurUserId(), mvpView.getJobId(), mvpView.getApplyInfo())
            .subscribe(new BaseRequestConsumer<Object>(mvpView) {

              @Override
              protected void onRequestSuccess(Object data) {
                mvpView.onSuccessSendApply();
              }
            }, new BaseErrorConsumer(mvpView)));
  }
}

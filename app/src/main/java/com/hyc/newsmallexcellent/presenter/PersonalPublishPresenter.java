package com.hyc.newsmallexcellent.presenter;

import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.bean.JobBean;
import com.hyc.newsmallexcellent.interfaces.PersonalPublishContact;
import com.hyc.newsmallexcellent.model.UserModel;

public class PersonalPublishPresenter extends BasePresenter<PersonalPublishContact.IView>
    implements PersonalPublishContact.IPresenter {

  UserModel userModel = new UserModel();

  @Override
  public void fetchPublishJob(boolean isLoadMore) {
    addDisposable(userModel.findUserJob(mvpView.getUserId(), 20, mvpView.getCurPage()).subscribe(
        new BaseRequestConsumer<JobBean>(mvpView) {
          @Override
          protected void onRequestSuccess(JobBean data) {
            mvpView.loadJobInfo(data,isLoadMore);
          }
        }, new BaseErrorConsumer(mvpView)));
  }

  @Override
  public void revokeJob(int id, int position) {
    mvpView.showLoadingView();
    addDisposable(userModel.revokeJob(id).subscribe(new BaseRequestConsumer<Object>(mvpView) {
      @Override
      protected void onRequestSuccess(Object data) {
        mvpView.onSuccessDeleteJob(position);
      }
    },new BaseErrorConsumer(mvpView)));
  }
}

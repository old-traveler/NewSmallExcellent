package com.hyc.newsmallexcellent.presenter;

import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.bean.ResumeInfoBean;
import com.hyc.newsmallexcellent.interfaces.ResumeContract;
import com.hyc.newsmallexcellent.model.UserModel;

public class ResumePresenter extends BasePresenter<ResumeContract.IView> implements
    ResumeContract.IPresenter {

  private UserModel userModel = new UserModel();

  @Override
  public void fetchResumeData() {
    mvpView.showLoadingView();
    addDisposable(userModel.getUserResumeInfo(mvpView.getResumeUserId())
        .subscribe(new BaseRequestConsumer<ResumeInfoBean>(mvpView){

          @Override
          protected void onRequestSuccess(ResumeInfoBean data) {
            mvpView.onSuccessGetResume(data);
            if (userModel.isCurUser(mvpView.getResumeUserId())){
              mvpView.enableEditResume();
            }
          }
        },new BaseErrorConsumer(mvpView){
          @Override
          public void accept(Throwable throwable) throws Exception {
            super.accept(throwable);
            mvpView.onFailGetResume();
          }
        }));
  }

  @Override
  public void changeResumeData() {

  }
}

package com.hyc.newsmallexcellent.presenter;

import android.Manifest;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.bean.BaseRequestBean;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.bean.ResumeInfoBean;
import com.hyc.newsmallexcellent.interfaces.ResumeContract;
import com.hyc.newsmallexcellent.model.UserModel;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.util.Map;

public class ResumePresenter extends BasePresenter<ResumeContract.IView> implements
    ResumeContract.IPresenter {

  private UserModel userModel = new UserModel();

  private int resumeId;

  @Override
  public void fetchResumeData() {
    mvpView.showLoadingView();
    addDisposable(userModel.getUserResumeInfo(mvpView.getResumeUserId())
        .subscribe(new BaseRequestConsumer<ResumeInfoBean>(mvpView) {

          @Override
          protected void onRequestSuccess(ResumeInfoBean data) {
            resumeId = data.getId();
            mvpView.onSuccessGetResume(data);
          }
        }, new BaseErrorConsumer(mvpView) {
          @Override
          public void accept(Throwable throwable) throws Exception {
            super.accept(throwable);
            mvpView.onFailGetResume();
          }
        }));
  }

  public boolean isEnableChangeResume() {
    return userModel.isCurUser(mvpView.getResumeUserId());
  }

  @Override
  public void changeResumeData() {
    Map<String, Object> map = mvpView.getResumeInfo();
    if (TextUtils.isEmpty((String) map.get("headPhoto"))) {
      map.remove("headPhoto");
      updateResume(map);
      return;
    }
    mvpView.showLoadingView();
    addDisposable(userModel.uploadImage(map)
        .subscribe(new BaseRequestConsumer<String>(mvpView) {

          @Override
          public void accept(BaseRequestBean<String> baseRequestBean) throws Exception {
            super.accept(baseRequestBean);
            map.put("headPhoto", baseRequestBean.getMsg());
            updateResume(map);
          }

          @Override
          protected void onRequestSuccess(String data) {

          }
        }, new BaseErrorConsumer(mvpView)));
  }

  private void updateResume(Map<String, Object> map) {
    mvpView.showLoadingView();
    addDisposable(userModel.updateUserResume(map, resumeId, userModel.getCurUserId())
        .subscribe(new BaseRequestConsumer<Object>(mvpView) {

          @Override
          protected void onRequestSuccess(Object data) {
            mvpView.onSuccessChangeResume();
          }
        }, new BaseErrorConsumer(mvpView)));
  }

  @Override
  public void requestPermission(FragmentActivity fragmentActivity) {
    addDisposable(new RxPermissions(fragmentActivity)
        .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .subscribe(granted -> {
          if (granted) {
            mvpView.requestPermissionSuccess();
          } else {
            mvpView.requestPermissionFail();
          }
        }));
  }
}

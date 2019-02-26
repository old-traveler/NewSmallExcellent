package com.hyc.newsmallexcellent.presenter;

import android.Manifest;
import android.support.v4.app.FragmentActivity;

import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.bean.UploadImageBean;
import com.hyc.newsmallexcellent.interfaces.AuthenticationContact;
import com.hyc.newsmallexcellent.model.UserModel;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationPresenter extends BasePresenter<AuthenticationContact.View>
    implements AuthenticationContact.Authentication {

  UserModel userModel = new UserModel();

  private List<String> image;

  @Override
  public void authentication() {
    if (mvpView.verificationInput()) {
      image = new ArrayList<>();
      mvpView.showLoadingView();
      uploadImage(mvpView.photoOne());
      uploadImage(mvpView.photoTwo());
    }
  }

  private void uploadImage(String path){
    addDisposable(userModel.uploadImage(path).subscribe(
        new BaseRequestConsumer<UploadImageBean>(mvpView) {
          @Override
          protected void onRequestSuccess(UploadImageBean data) {
            image.add(data.getUrl());
            if (image.size() == 2){
              upload();
            }
          }
        }
        ,new BaseErrorConsumer(mvpView){
          @Override
          public void accept(Throwable throwable) throws Exception {
            super.accept(throwable);
            onUnsubscribe();
          }
        }));
  }

  private void upload(){
    addDisposable(userModel.userAuthentication(userModel.getCurUserId(), mvpView.userName(),
        mvpView.authenticationType(), image.get(0), image.get(1))
        .subscribe(new BaseRequestConsumer<Object>(mvpView) {
          @Override
          protected void onRequestSuccess(Object data) {
            mvpView.onChangeSuccess();
          }
        }, new BaseErrorConsumer(mvpView)));
  }


  @Override
  public void requestPermission(FragmentActivity fragmentActivity, boolean isFirst) {
    addDisposable(new RxPermissions(fragmentActivity)
        .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .subscribe(granted -> {
          if (granted) {
            mvpView.requestPermissionSuccess(isFirst);
          } else {
            mvpView.requestPermissionFail();
          }
        }));
  }
}

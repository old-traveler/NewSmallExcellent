package com.hyc.newsmallexcellent.presenter;

import android.Manifest;
import android.support.v4.app.FragmentActivity;

import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.interfaces.AuthenticationContact;
import com.hyc.newsmallexcellent.model.UserModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class AuthenticationPresenter extends BasePresenter<AuthenticationContact.View>
        implements AuthenticationContact.Authentication{

    UserModel userModel = new UserModel();


    @Override
    public void authentication() {
        if (mvpView.verificationInput()) {
            mvpView.showLoadingView();
            addDisposable(userModel.userAuthentication(userModel.getCurUserId(), mvpView.userName(),
                    mvpView.authenticationType(),mvpView.photoOne(),mvpView.photoTwo()).subscribe(new BaseRequestConsumer<Object>(mvpView) {
                @Override
                protected void onRequestSuccess(Object data) {
                    mvpView.onChangeSuccess();
                }
            }, new BaseErrorConsumer(mvpView)));
        }
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

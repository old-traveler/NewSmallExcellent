package com.hyc.newsmallexcellent.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.alibaba.idst.nls.internal.protocol.Content;
import com.hyc.newsmallexcellent.SmallExcellentApplication;
import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.interfaces.ReleasePositionContract;
import com.hyc.newsmallexcellent.model.UserModel;
import com.hyc.newsmallexcellent.view.MapActivity;
import com.hyc.newsmallexcellent.view.ReleasePositionActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class ReleasePositionPresenter extends BasePresenter<ReleasePositionContract.View>
        implements ReleasePositionContract.ReleasePositionPresenter {

    private UserModel userModel = new UserModel();

    @Override
    public void releasePosition() {
        if (mvpView.verificationInput()){
            mvpView.showLoadingView();
            addDisposable(userModel.postJob(
                    userModel.getCurUserId(),mvpView.getJobTitle(),mvpView.getJobDescribe(),
                    mvpView.getJobCategory(),mvpView.getJobSalary(),mvpView.getJobSalaryUnit(),mvpView.getJobCount(),
                    mvpView.getWorkingHours(),mvpView.getWorkingDays(),mvpView.getContact(),mvpView.getTelephone(),
                    mvpView.getCDate(),mvpView.getIssuePlace()).subscribe(new BaseRequestConsumer<Object>(mvpView) {
                        @Override
                        protected void onRequestSuccess(Object data) {
                            mvpView.onReleaseSuccess();
                        }
                    },new BaseErrorConsumer(mvpView)));
        }
    }

    @Override
    public void accessRequest(FragmentActivity activity) {
        addDisposable(new RxPermissions(activity)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        Intent intent = new Intent(activity,MapActivity.class);
                        activity.startActivityForResult(intent,1);
                    } else {
                        Toast.makeText(activity, "权限申请失败", Toast.LENGTH_SHORT).show();
                    }
                }));

    }
}

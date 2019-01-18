package com.hyc.newsmallexcellent.presenter;

import android.Manifest;
import android.support.v4.app.FragmentActivity;
import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.bean.JobBean;
import com.hyc.newsmallexcellent.interfaces.MainContact;
import com.hyc.newsmallexcellent.model.JobModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class MainPresenter extends BasePresenter<MainContact.IView> implements MainContact.IPresenter{


  private JobModel jobModel = new JobModel();

  @Override
  public void fetchRecommendJob(double latitude, double longitude) {
    mvpView.showLoadingView();
    addDisposable(jobModel.queryNearbyJob(latitude,longitude).subscribe(
        new BaseRequestConsumer<JobBean>(mvpView) {
          @Override
          protected void onRequestSuccess(JobBean data) {
            mvpView.loadJobInfo(data);
          }
        },new BaseErrorConsumer(mvpView)));
  }

  @Override
  public void startLocation() {
    addDisposable(new RxPermissions((FragmentActivity) getContext())
        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION)
        .subscribe(granted -> {
          if (granted) {
            mvpView.initMapView();
          } else {
            ToastHelper.longTimeToast("软件无法正常工作，请先打开定位权限");
          }
        }));
  }
}

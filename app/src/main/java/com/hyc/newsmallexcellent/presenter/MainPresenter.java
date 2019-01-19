package com.hyc.newsmallexcellent.presenter;

import android.Manifest;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.util.Pair;
import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.bean.JobBean;
import com.hyc.newsmallexcellent.interfaces.MainContact;
import com.hyc.newsmallexcellent.model.JobModel;
import com.hyc.newsmallexcellent.util.BitmapUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class MainPresenter extends BasePresenter<MainContact.IView>
    implements MainContact.IPresenter {

  private JobModel jobModel = new JobModel();

  @Override
  public void fetchRecommendJob() {
    mvpView.showLoadingView();
    addDisposable(jobModel.queryNearbyJob(mvpView.getCurLocation().latitude,
        mvpView.getCurLocation().longitude,mvpView.getCurPager()).subscribe(
        new BaseRequestConsumer<JobBean>(mvpView) {
          @Override
          protected void onRequestSuccess(JobBean data) {
            mvpView.loadJobInfo(data);
            loadJobMapPoint(data.getList());
          }
        }, new BaseErrorConsumer(mvpView){
          @Override
          public void accept(Throwable throwable) throws Exception {
            super.accept(throwable);
            mvpView.loadJobFail();
          }
        }));
  }

  private void loadJobMapPoint(List<JobBean.ListBean> list) {
    addDisposable(BitmapUtil.loadMapMarker(list)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(listBeanBitmapPair -> {
          mvpView.loadJobMapPoint(listBeanBitmapPair);
        }));
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

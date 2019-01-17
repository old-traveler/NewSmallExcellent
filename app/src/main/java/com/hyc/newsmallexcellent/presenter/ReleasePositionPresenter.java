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
import com.hyc.newsmallexcellent.bean.CategoryBean;
import com.hyc.newsmallexcellent.interfaces.ReleasePositionContract;
import com.hyc.newsmallexcellent.model.JobModel;
import com.hyc.newsmallexcellent.model.UserModel;
import com.hyc.newsmallexcellent.view.MapActivity;
import com.hyc.newsmallexcellent.view.ReleasePositionActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

public class ReleasePositionPresenter extends BasePresenter<ReleasePositionContract.View>
    implements ReleasePositionContract.ReleasePositionPresenter {

  private UserModel userModel = new UserModel();

  private JobModel jobModel = new JobModel();

  private List<String> categoryTitles;

  @Override
  public void releasePosition() {
    if (mvpView.verificationInput()) {
      mvpView.showLoadingView();
      addDisposable(jobModel.publishJob(2, mvpView.getJobInfo()).subscribe(
          new BaseRequestConsumer<Object>(mvpView) {
            @Override
            protected void onRequestSuccess(Object data) {
              mvpView.onReleaseSuccess();
            }
          }, new BaseErrorConsumer(mvpView)));
    }
  }

  @Override
  public void accessRequest(FragmentActivity activity) {
    addDisposable(new RxPermissions(activity)
        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION)
        .subscribe(granted -> {
          if (granted) {
            Intent intent = new Intent(activity, MapActivity.class);
            activity.startActivityForResult(intent, 1001);
          } else {
            Toast.makeText(activity, "权限申请失败", Toast.LENGTH_SHORT).show();
          }
        }));
  }

  @Override
  public void fetchJobCategory() {
    if (categoryTitles != null) {
      mvpView.loadJobCategory(categoryTitles);
      return;
    }
    mvpView.showLoadingView();
    addDisposable(
        jobModel.queryAllCategory().subscribe(new BaseRequestConsumer<List<CategoryBean>>(mvpView) {

          @Override
          protected void onRequestSuccess(List<CategoryBean> data) {
            if (data.size() > 0) {
              List<String> strings = new ArrayList<>();
              for (CategoryBean dataBean : data) {
                strings.add(dataBean.getCategory());
              }
              categoryTitles = strings;
              mvpView.loadJobCategory(strings);
            }
          }
        }, new BaseErrorConsumer(mvpView)));
  }

  @Override
  public void detachView() {
    super.detachView();
    if (categoryTitles != null) {
      categoryTitles.clear();
    }
  }
}

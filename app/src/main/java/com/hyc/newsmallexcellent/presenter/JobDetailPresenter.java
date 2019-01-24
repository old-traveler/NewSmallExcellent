package com.hyc.newsmallexcellent.presenter;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.bean.JobBean;
import com.hyc.newsmallexcellent.interfaces.JobDetailContact;
import com.hyc.newsmallexcellent.model.JobModel;
import com.hyc.newsmallexcellent.model.UserModel;
import com.hyc.newsmallexcellent.other.route.RouteActivity;

public class JobDetailPresenter extends BasePresenter<JobDetailContact.IView>
    implements JobDetailContact.IPresenter {

  private JobModel jobModel = new JobModel();

  private UserModel userModel = new UserModel();

  @Override
  public void uploadFootprint(int id, String jobTitle) {
    addDisposable(jobModel.uploadFootprint(userModel.getCurUserId(), id, jobTitle).subscribe());
  }

  @Override
  public void fetchJobInfoById(int jobId) {
    mvpView.showLoadingView();
    addDisposable(jobModel.findJobById(jobId).subscribe(
        new BaseRequestConsumer<JobBean.ListBean>(mvpView) {
          @Override
          protected void onRequestSuccess(JobBean.ListBean data) {
            mvpView.initViewWithBundle(data);
          }
        },new BaseErrorConsumer(mvpView)));
  }

  @Override
  public void startRoutePlan() {
    if (mvpView.getEndLatlng() == null){
      return;
    }
    LatLng latLng = userModel.getUserLatLng();
    RouteActivity.mStartPoint = new LatLonPoint(latLng.latitude,latLng.longitude);
    RouteActivity.mStartPoint_bus = RouteActivity.mStartPoint;
    RouteActivity.mEndPoint = mvpView.getEndLatlng();
    mvpView.onGoToRoutePlan();
  }
}

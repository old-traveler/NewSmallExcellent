package com.hyc.newsmallexcellent.presenter;

import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.bean.CategoryBean;
import com.hyc.newsmallexcellent.interfaces.JobsClassificationContract;
import com.hyc.newsmallexcellent.model.JobModel;
import java.util.ArrayList;
import java.util.List;

public class JobsClassificationPresenter extends BasePresenter<JobsClassificationContract.View>
    implements JobsClassificationContract.JobsClassification {

  private JobModel jobModel = new JobModel();

  @Override
  public void jobsClassification() {
    if (mvpView.verificationInput()) {
      mvpView.showLoadingView();
      addDisposable(jobModel.jobsClassification(mvpView.getCategory())
          .subscribe(new BaseRequestConsumer<Object>(mvpView) {
            @Override
            protected void onRequestSuccess(Object data) {
              mvpView.addClassificationSuccess();
            }
          }, new BaseErrorConsumer(mvpView)));
    }
  }

  @Override
  public void deleteClassification(String category) {
    mvpView.showLoadingView();
    addDisposable(jobModel.deleteClassification(category)
        .subscribe(new BaseRequestConsumer<Object>(mvpView) {
          @Override
          protected void onRequestSuccess(Object data) {
            mvpView.deleteCategory(category);
          }
        }, new BaseErrorConsumer(mvpView)));
  }

  @Override
  public void queryCategory() {
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
              mvpView.onQuerySuccess(strings);
            }
          }
        }, new BaseErrorConsumer(mvpView)));
  }
}

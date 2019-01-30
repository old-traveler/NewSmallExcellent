package com.hyc.newsmallexcellent.presenter;

import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.bean.BaseRequestBean;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.bean.ApplyBean;
import com.hyc.newsmallexcellent.interfaces.MyApplyContact;
import com.hyc.newsmallexcellent.model.JobModel;
import com.hyc.newsmallexcellent.model.UserModel;
import io.reactivex.Observable;

public class MyApplyPresenter extends BasePresenter<MyApplyContact.IView>
    implements MyApplyContact.IPresenter {

  private JobModel jobModel = new JobModel();

  private UserModel userModel = new UserModel();

  @Override
  public void fetchApplyInfo(boolean isLoadMore) {
    mvpView.showLoadingView();
    BaseRequestConsumer<ApplyBean> requestConsumer = new BaseRequestConsumer<ApplyBean>(mvpView) {
      @Override
      protected void onRequestSuccess(ApplyBean data) {
        mvpView.loadApplyInfo(isLoadMore, data);
      }
    };
    BaseErrorConsumer baseErrorConsumer = new BaseErrorConsumer(mvpView);
    Observable<BaseRequestBean<ApplyBean>> observable;
    if (mvpView.isDealer()) {
      observable = jobModel.findAllApply(userModel.getCurUserId(), 20, mvpView.getCurPage());
    } else {
      observable = userModel.findApplyByUserId(userModel.getCurUserId(), 20, mvpView.getCurPage());
    }
    addDisposable(observable.subscribe(requestConsumer, baseErrorConsumer));
  }

  @Override
  public void cancelApply(int id, int position) {
    mvpView.showLoadingView();
    addDisposable(jobModel.cancelApply(id).subscribe(new BaseRequestConsumer<Object>(mvpView) {
      @Override
      protected void onRequestSuccess(Object data) {
        mvpView.onSuccessCancelApply(position);
      }
    }, new BaseErrorConsumer(mvpView)));
  }

  @Override
  public void dealApply(int id,boolean isOk) {
    mvpView.showLoadingView();
    addDisposable(userModel.handleApply(id,isOk ? 2 : 3).subscribe(
        new BaseRequestConsumer<Object>(mvpView) {
          @Override
          protected void onRequestSuccess(Object data) {
            mvpView.onDealSuccess();
          }
        },new BaseErrorConsumer(mvpView)));
  }
}

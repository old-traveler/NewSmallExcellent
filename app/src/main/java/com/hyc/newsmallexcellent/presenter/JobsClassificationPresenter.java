package com.hyc.newsmallexcellent.presenter;

import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.interfaces.JobsClassificationContract;
import com.hyc.newsmallexcellent.model.JobsClassificationModel;

public class JobsClassificationPresenter extends BasePresenter<JobsClassificationContract.View>
        implements JobsClassificationContract.JobsClassification{

    private JobsClassificationModel jobsClassificationModel = new JobsClassificationModel();

    @Override
    public void jobsClassification() {
        if (mvpView.verificationInput()) {
            mvpView.showLoadingView();
            addDisposable(jobsClassificationModel.jobsClassification(mvpView.getCategory())
                    .subscribe(new BaseRequestConsumer<Object>(mvpView) {
                @Override
                protected void onRequestSuccess(Object data) {
                    mvpView.addClassificationSuccess();
                }
            }, new BaseErrorConsumer(mvpView)));
        }
    }

    @Override
    public void deleteClassification() {
        if (mvpView.verificationInput()) {
            mvpView.showLoadingView();
            addDisposable(jobsClassificationModel.deleteClassification(mvpView.getCategory())
                    .subscribe(new BaseRequestConsumer<Object>(mvpView) {
                        @Override
                        protected void onRequestSuccess(Object data) {
                            mvpView.addClassificationSuccess();
                        }
                    }, new BaseErrorConsumer(mvpView)));
        }
    }
}

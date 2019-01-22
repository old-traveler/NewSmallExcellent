package com.hyc.newsmallexcellent.presenter;

import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.interfaces.JobDetailContact;
import com.hyc.newsmallexcellent.model.JobModel;
import com.hyc.newsmallexcellent.model.UserModel;

public class JobDetailPresenter extends BasePresenter<JobDetailContact.IView>
    implements JobDetailContact.IPresenter {

  JobModel jobModel = new JobModel();

  UserModel userModel = new UserModel();

  @Override
  public void uploadFootprint(int id, String jobTitle) {
    addDisposable(jobModel.uploadFootprint(userModel.getCurUserId(), id, jobTitle).subscribe());
  }
}

package com.hyc.newsmallexcellent.interfaces;

import com.hyc.newsmallexcellent.base.interfaces.ILoading;

public interface ApplyJobContact  {

  public interface IPresenter{

    void applyJob();

  }

  public interface IView extends ILoading {

    String getApplyInfo();

    int getJobId();

    void onSuccessSendApply();

  }

}

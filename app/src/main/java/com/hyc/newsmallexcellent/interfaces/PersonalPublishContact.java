package com.hyc.newsmallexcellent.interfaces;

import com.hyc.newsmallexcellent.base.interfaces.ILoading;
import com.hyc.newsmallexcellent.bean.JobBean;

public interface PersonalPublishContact {

  public interface IPresenter{

    void fetchPublishJob(boolean isLoadMore);

    void revokeJob(int id,int position);

  }

  public interface IView extends ILoading {

    int getCurPage();

    void loadJobInfo(JobBean jobBean,boolean isLoadMore);

    void onSuccessDeleteJob(int position);

    int getUserId();

  }


}

package com.hyc.newsmallexcellent.interfaces;

import com.hyc.newsmallexcellent.base.interfaces.ILoading;
import com.hyc.newsmallexcellent.bean.JobBean;

public interface JobDetailContact {

  public interface IPresenter{

    void uploadFootprint(int id,String jobTitle);

    void fetchJobInfoById(int jobId);

  }

  public interface IView extends ILoading {

    void initViewWithBundle(JobBean.ListBean listBean);

  }

}

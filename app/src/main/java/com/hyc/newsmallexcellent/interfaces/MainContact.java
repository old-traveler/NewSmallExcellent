package com.hyc.newsmallexcellent.interfaces;

import com.hyc.newsmallexcellent.base.interfaces.ILoading;
import com.hyc.newsmallexcellent.bean.JobBean;

public interface MainContact {

  public interface IPresenter{

    void fetchRecommendJob(double latitude,double longitude);

    void startLocation();

  }

  interface IView extends ILoading {

    int getCurPager();

    void loadJobInfo(JobBean jobBean);

    void initMapView();

  }

}

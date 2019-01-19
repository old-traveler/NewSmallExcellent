package com.hyc.newsmallexcellent.interfaces;

import android.graphics.Bitmap;
import android.util.Pair;
import com.amap.api.maps.model.LatLng;
import com.hyc.newsmallexcellent.base.interfaces.ILoading;
import com.hyc.newsmallexcellent.bean.JobBean;

public interface MainContact {

  public interface IPresenter{

    void fetchRecommendJob();

    void startLocation();

  }

  interface IView extends ILoading {

    int getCurPager();

    LatLng getCurLocation();

    void loadJobInfo(JobBean jobBean);

    void initMapView();

    void loadJobFail();

    void loadJobMapPoint(Pair<JobBean.ListBean, Bitmap> pair);

  }

}

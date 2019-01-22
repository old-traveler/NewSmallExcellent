package com.hyc.newsmallexcellent.interfaces;

import com.hyc.newsmallexcellent.base.interfaces.ILoading;
import com.hyc.newsmallexcellent.bean.AddressBean;
import com.hyc.newsmallexcellent.bean.JobBean;
import java.util.List;
import java.util.Map;

public interface QueryJobContact {

  public interface IPresenter{
    void queryJobByCondition(boolean isLoadMore);

    void getCityList();

    void fetchCategoryList();
  }

  public interface IView extends ILoading {

    Map<String,Object> getQueryCondition();

    void onSuccessQuery(JobBean jobBean);

    void appendJobInfo(JobBean jobBean);

    void loadCityList(AddressBean addressBean);

    void loadCategoryList(List<String> data);

  }

}

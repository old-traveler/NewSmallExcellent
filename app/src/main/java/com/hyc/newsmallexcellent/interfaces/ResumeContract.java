package com.hyc.newsmallexcellent.interfaces;

import com.hyc.newsmallexcellent.base.interfaces.ILoading;
import com.hyc.newsmallexcellent.bean.ResumeInfoBean;
import java.util.Map;

public interface ResumeContract {

  public interface IPresenter{

    void fetchResumeData();

    void changeResumeData();

  }

  public interface IView extends ILoading {

    int getResumeUserId();

    void onSuccessGetResume(ResumeInfoBean infoBean);

    void onFailGetResume();

    Map<String,Object> getResumeInfo();

    void enableEditResume();

  }


}

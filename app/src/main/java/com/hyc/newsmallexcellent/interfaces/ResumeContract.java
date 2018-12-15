package com.hyc.newsmallexcellent.interfaces;

import android.support.v4.app.FragmentActivity;
import com.hyc.newsmallexcellent.base.interfaces.ILoading;
import com.hyc.newsmallexcellent.bean.ResumeInfoBean;
import java.util.Map;

public interface ResumeContract {

  public interface IPresenter{

    void fetchResumeData();

    void changeResumeData();

    void requestPermission(FragmentActivity fragmentActivity);

  }

  public interface IView extends ILoading {

    int getResumeUserId();

    void onSuccessGetResume(ResumeInfoBean infoBean);

    void onFailGetResume();

    Map<String,Object> getResumeInfo();

    void enableEditResume();

    void requestPermissionSuccess();

    void requestPermissionFail();

    void onSuccessChangeResume();

  }
}

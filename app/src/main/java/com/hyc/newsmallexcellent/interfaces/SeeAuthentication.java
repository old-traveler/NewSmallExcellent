package com.hyc.newsmallexcellent.interfaces;

import com.hyc.newsmallexcellent.base.interfaces.ILoading;
import com.hyc.newsmallexcellent.bean.AuthenticationBean;

public interface SeeAuthentication {

  public interface IPresenter{

    void fetchAllAuthentication();
  }

  public interface IView extends ILoading {

    void loadAuthentication(AuthenticationBean authenticationBean);

    int getCurPage();


  }


}

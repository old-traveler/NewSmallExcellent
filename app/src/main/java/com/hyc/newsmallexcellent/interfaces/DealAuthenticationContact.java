package com.hyc.newsmallexcellent.interfaces;

import com.hyc.newsmallexcellent.base.interfaces.ILoading;
import com.hyc.newsmallexcellent.bean.AuthenticationBean;

public interface DealAuthenticationContact {

  interface IPresenter{
    void dealAuthentication(boolean isPass);
  }

  interface IView extends ILoading {
    AuthenticationBean.ListBean getListBean();

    String getResult();

    void onDealSuccess();

  }


}

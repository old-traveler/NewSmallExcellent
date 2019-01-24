package com.hyc.newsmallexcellent.interfaces;

import com.hyc.newsmallexcellent.base.interfaces.ILoading;
import com.hyc.newsmallexcellent.bean.ApplyBean;

public interface MyApplyContact {

  public interface IPresenter{

    void fetchApplyInfo(boolean isLoadMore);

    void cancelApply(int id,int position);

  }

  public interface IView extends ILoading {

    void loadApplyInfo(boolean isLoadMore,ApplyBean applyBean);

    void onSuccessCancelApply(int position);

    int getCurPage();

  }

}

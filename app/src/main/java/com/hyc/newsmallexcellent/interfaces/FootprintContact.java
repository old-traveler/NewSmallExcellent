package com.hyc.newsmallexcellent.interfaces;

import com.hyc.newsmallexcellent.base.interfaces.ILoading;
import com.hyc.newsmallexcellent.bean.FootPrintBean;

public interface FootprintContact {

  interface IPresenter{

    void fetchFootPrint();

    void deleteFootprint(int id,int position);
  }

  interface IView extends ILoading {

    void loadFootPrint(FootPrintBean footPrintBean);

    int getCurPage();

    void onSuccessDeleteFootPrint(int position);

  }

}

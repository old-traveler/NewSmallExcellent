package com.hyc.newsmallexcellent.interfaces;

import com.hyc.newsmallexcellent.base.interfaces.ILoading;
import com.hyc.newsmallexcellent.bean.FootPrintBean;

public interface FootprintContact {

  public interface IPresenter{

    void fetchFootPrint();
  }

  public interface IView extends ILoading {

    void loadFootPrint(FootPrintBean footPrintBean);

    int getCurPage();

  }

}

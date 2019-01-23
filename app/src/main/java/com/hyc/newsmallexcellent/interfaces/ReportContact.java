package com.hyc.newsmallexcellent.interfaces;

import com.hyc.newsmallexcellent.base.interfaces.ILoading;

public interface ReportContact {

  public interface IPresenter{

    void report();

  }

  public interface IView extends ILoading {

    String getReportContent();

    int getBereportUserId();

    void onSuccessSendReport();

  }


}

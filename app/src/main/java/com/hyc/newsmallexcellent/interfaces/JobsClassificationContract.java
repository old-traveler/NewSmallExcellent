package com.hyc.newsmallexcellent.interfaces;

import com.hyc.newsmallexcellent.base.interfaces.ILoading;
import java.util.List;

public interface JobsClassificationContract {

  interface JobsClassification {
    void jobsClassification();

    void deleteClassification(String category);

    void queryCategory();
  }

  interface View extends ILoading {
    String getCategory();

    boolean verificationInput();

    void addClassificationSuccess();

    void onQuerySuccess(List<String> data);

    void deleteCategory(String category);
  }
}

package com.hyc.newsmallexcellent.interfaces;

import com.hyc.newsmallexcellent.base.interfaces.ILoading;

public interface JobsClassificationContract {

    interface JobsClassification{
        void jobsClassification();
        void deleteClassification();

    }
    interface View extends ILoading {
        String getCategory();

        boolean verificationInput();
        void addClassificationSuccess();
    }
}

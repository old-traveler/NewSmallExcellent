package com.hyc.newsmallexcellent.interfaces;

import android.support.v4.app.FragmentActivity;

import com.hyc.newsmallexcellent.base.interfaces.ILoading;

public interface AuthenticationContact {

    interface Authentication{
        void authentication();
        void requestPermission(FragmentActivity fragmentActivity,boolean isFirst);
    }

    interface View extends ILoading {

        String userName();
        int authenticationType();
        String photoOne();
        String photoTwo();

        boolean verificationInput();
        void onChangeSuccess();

        void requestPermissionSuccess(boolean isFirst);

        void requestPermissionFail();
    }
}

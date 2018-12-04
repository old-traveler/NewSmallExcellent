package com.hyc.newsmallexcellent.interfaces;

import com.hyc.newsmallexcellent.base.interfaces.ILoading;

public interface ChangePasswordContract {

    interface ChangePresenter{
        void change();
    }

    interface View extends ILoading {
        String getOldPassword();
        String getNewPassword();

        boolean verificationInput();
        void onChangeSuccess();
    }
}

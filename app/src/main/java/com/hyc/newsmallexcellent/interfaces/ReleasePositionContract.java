package com.hyc.newsmallexcellent.interfaces;
import android.support.v4.app.FragmentActivity;
import com.hyc.newsmallexcellent.base.interfaces.ILoading;
public interface ReleasePositionContract {
    interface ReleasePositionPresenter{
        void releasePosition();
        void accessRequest(FragmentActivity activity);
    }

    interface View extends ILoading{
        String getJobTitle();
        String getJobDescribe();
        String getJobCategory();
        String getJobSalary();
        String getJobSalaryUnit();
        int getJobCount();
        String getWorkingHours();
        String getWorkingDays();
        String getContact();
        String getTelephone();
        String getCDate();
        String getIssuePlace();

        boolean verificationInput();
        void onReleaseSuccess();
    }
}

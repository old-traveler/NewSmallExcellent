package com.hyc.newsmallexcellent.interfaces;

import android.support.v4.app.FragmentActivity;
import com.hyc.newsmallexcellent.base.interfaces.ILoading;
import java.util.List;
import java.util.Map;

public interface ReleasePositionContract {
  interface ReleasePositionPresenter {
    void releasePosition();

    void accessRequest(FragmentActivity activity);

    void fetchJobCategory();
  }

  interface View extends ILoading {

    Map<String,Object> getJobInfo();

    boolean verificationInput();

    void onReleaseSuccess();

    void loadJobCategory(List<String> data);

  }
}

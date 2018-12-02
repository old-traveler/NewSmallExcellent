package com.hyc.newsmallexcellent.base.rx;

import android.util.Log;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.base.interfaces.ILoading;
import io.reactivex.functions.Consumer;

public class BaseErrorConsumer implements Consumer<Throwable> {

  ILoading iLoading;

  public BaseErrorConsumer(ILoading iLoading){
    this.iLoading = iLoading;
  }

  @Override
  public void accept(Throwable throwable) throws Exception {
    if (iLoading != null){
      iLoading.closeLoadingView();
    }
    Log.d("yangjing",throwable.getMessage());
    ToastHelper.toast(throwable);
  }
}

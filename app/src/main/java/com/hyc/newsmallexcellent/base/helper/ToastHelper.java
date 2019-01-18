package com.hyc.newsmallexcellent.base.helper;

import android.util.Log;
import android.widget.Toast;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.SmallExcellentApplication;

public class ToastHelper {

  public static void toast(String msg){
    Log.d("toastTAG",msg);
    Toast.makeText(SmallExcellentApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
  }

  public static void toast(int resId){
    toast(UiHelper.getString(resId));
  }

  public static void toast(Throwable throwable){
    if (throwable != null && throwable.getMessage()!= null){
      toast(throwable.getMessage());
    }
  }

  public static void longTimeToast(String string){
    Toast.makeText(SmallExcellentApplication.getContext(),string, Toast.LENGTH_LONG).show();
  }

}

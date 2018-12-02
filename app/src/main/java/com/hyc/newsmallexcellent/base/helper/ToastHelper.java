package com.hyc.newsmallexcellent.base.helper;

import android.widget.Toast;
import com.hyc.newsmallexcellent.SmallExcellentApplication;

public class ToastHelper {

  public static void toast(String msg){
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

}

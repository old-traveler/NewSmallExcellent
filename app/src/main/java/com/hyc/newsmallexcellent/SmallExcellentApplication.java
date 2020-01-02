package com.hyc.newsmallexcellent;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

public class SmallExcellentApplication extends Application {

  private static Context context;

  @Override
  public void onCreate() {
    super.onCreate();
    context = getApplicationContext();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
  }

  @Override
  public void onTrimMemory(int level) {
    super.onTrimMemory(level);
  }

  @Override
  public void registerOnProvideAssistDataListener(OnProvideAssistDataListener callback) {
    super.registerOnProvideAssistDataListener(callback);
  }

  public static Context getContext(){
    return context;
  }
}

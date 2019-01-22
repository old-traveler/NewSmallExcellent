package com.hyc.newsmallexcellent.helper;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.hyc.newsmallexcellent.SmallExcellentApplication;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class SpCacheHelper {

  public static EditorBuilder withBuilder(){ return new EditorBuilder(); }

  public static class EditorBuilder{

    private SharedPreferences.Editor editor;

    public EditorBuilder(){
      editor = getEditor();
    }

    public EditorBuilder withString(String key,String data){
      editor.putString(key,data);
      return this;
    }

    public EditorBuilder withInt(String key,int data){
      editor.putInt(key,data);
      return this;
    }



    public EditorBuilder withBoolean(String key,boolean data){
      editor.putBoolean(key,data);
      return this;
    }

    public EditorBuilder withLong(String key,long data){
      editor.putLong(key,data);
      return this;
    }

    public void apply(){
      editor.apply();
    }

    public void commit(){
      editor.commit();
    }
  }

  public static SharedPreferences.Editor getEditor() {
    return getSharedPreferences().edit();
  }

  private static SharedPreferences getSharedPreferences() {
    return SmallExcellentApplication.getContext().getSharedPreferences("helper", Context.MODE_PRIVATE);
  }

  public static void putClassIntoSp(final String key, final Object object) {
    Observable.create(new ObservableOnSubscribe<Object>() {
      @Override
      public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
        String json = new Gson().toJson(object);
        getEditor().putString(key, json).commit();
      }
    }).subscribeOn(Schedulers.io());
    Observable.create(new ObservableOnSubscribe<Object>() {
      @Override
      public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
        String json = new Gson().toJson(object);
        getEditor().putString(key, json).commit();
      }
    }).subscribe();
  }

  public static void deleteClassFromSp(String key){
    getEditor().clear().commit();
  }

  public static <T> T getClassFromSp(String key, Class<T> cls) {
    try {
      String json = getSharedPreferences().getString(key, "");
      return new Gson().fromJson(json, cls);
    } catch (Exception e) {
      return null;
    }
  }

  public static void putBoolean(String key, boolean data) {
    getEditor().putBoolean(key, data);
  }

  public static void putString(String key, String data) {
    getEditor().putString(key, data).commit();
  }

  public static void putLong(String key, long data){
    getEditor().putLong(key,data).commit();
  }

  public static long getLong(String key){
    return getSharedPreferences().getLong(key,0);
  }

  public static void putInt(String key, int data){
    getEditor().putInt(key,data).commit();
  }

  public static int getInt(String key,int defaultValue){
    return getSharedPreferences().getInt(key,defaultValue);
  }

  public static String getString(String key){
    return getSharedPreferences().getString(key,null);
  }

  public static boolean getBoolean(String key) {
    return getSharedPreferences().getBoolean(key, false);
  }
}

package com.hyc.newsmallexcellent.model;
import com.hyc.newsmallexcellent.base.bean.BaseRequestBean;
import com.hyc.newsmallexcellent.bean.LoginActionBean;
import com.hyc.newsmallexcellent.bean.ResumeInfoBean;
import com.hyc.newsmallexcellent.bean.UploadImageBean;
import com.hyc.newsmallexcellent.helper.RequestHelper;
import com.hyc.newsmallexcellent.helper.SpCacheHelper;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserModel {

  public Observable<BaseRequestBean<Object>> verificationUserPhone(String account) {
    return RequestHelper.getRequestApi().sendVerificationCode(account)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<BaseRequestBean<Object>> register(String account, String password, String code) {
    return RequestHelper.getRequestApi().register(account, password, code)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<BaseRequestBean<LoginActionBean>> login(String account,String password){
    return RequestHelper.getRequestApi().login(account,password)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public void cacheUserInfo(LoginActionBean bean){
    SpCacheHelper.withBuilder()
        .withInt("user_id",bean.getId())
        .withString("accountname",bean.getAccountname())
        .withString("nickname",bean.getNickname())
        .withInt("isAuthentication",bean.getIsAuthentication())
        .withInt("status",bean.getStatus())
        .withString("headUrl",bean.getHeadUrl())
        .commit();
  }

  public void cacheUserLocation(String city,String address,double latitude,double longitude){
    SpCacheHelper.withBuilder()
        .withString("city",city)
        .withString("address",address)
        .withString("lat", String.valueOf(latitude))
        .withString("lon", String.valueOf(longitude))
        .commit();
  }


  public Observable<BaseRequestBean<Object>> changePassword(int id, String oldPwd, String newPwd){
    return RequestHelper.getRequestApi().change(id,oldPwd,newPwd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<BaseRequestBean<ResumeInfoBean>> getUserResumeInfo(int id){
    return RequestHelper.getRequestApi().getUserResumeInfo(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }



  public boolean isCurUser(int id){
    return getCurUserId() == id;
  }

  public int getCurUserId(){
    return SpCacheHelper.getInt("user_id");
  }

  public String getCurHeadUrl(){
    return SpCacheHelper.getString("headUrl");
  }

  public Observable<BaseRequestBean<UploadImageBean>> uploadImage(Map<String,Object> map){
    File file = new File((String) map.get("headPhoto"));
    RequestBody requestFile =
        RequestBody.create(MediaType.parse("multipart/form-data"), file);
    MultipartBody.Part body =
        MultipartBody.Part.createFormData("uploadFile", file.getName(), requestFile);
    return RequestHelper.getRequestApi().uploadImage(body)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<BaseRequestBean<Object>> updateUserResume(Map<String,Object> map,int id,int userId){
    return RequestHelper.getRequestApi().updateResume(id,userId,map)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }


}

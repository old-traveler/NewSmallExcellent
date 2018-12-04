package com.hyc.newsmallexcellent.model;

import com.hyc.newsmallexcellent.base.bean.BaseRequestBean;
import com.hyc.newsmallexcellent.bean.LoginActionBean;
import com.hyc.newsmallexcellent.bean.ResumeInfoBean;
import com.hyc.newsmallexcellent.helper.RequestHelper;
import com.hyc.newsmallexcellent.helper.SpCacheHelper;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

  public Observable<BaseRequestBean<LoginActionBean>> login(String account, String password) {
    return RequestHelper.getRequestApi().login(account, password)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public void cacheUserInfo(LoginActionBean bean) {
    SpCacheHelper.withBuilder()
        .withInt("user_id", bean.getId())
        .withString("accountname", bean.getAccountname())
        .withString("nickname", bean.getNickname())
        .withInt("isAuthentication", bean.getIsAuthentication())
        .withInt("status", bean.getStatus())
        .withString("headUrl", bean.getHeadUrl())
        .commit();
  }

  public int getCurUserId() {
    return SpCacheHelper.getInt("user_id");
  }

  public String getCurHeadUrl() {
    return SpCacheHelper.getString("headUrl");
  }

  public Observable<BaseRequestBean<ResumeInfoBean>> getUserResumeInfo(int id){
    return RequestHelper.getRequestApi().getUserResume(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public boolean isCurUser(int id){
    return getCurUserId() == id;
  }

}

package com.hyc.newsmallexcellent.net;

import com.hyc.newsmallexcellent.base.bean.BaseRequestBean;
import com.hyc.newsmallexcellent.bean.LoginActionBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetApiService {

  String baseUrl = "http://120.78.144.241:8080/";

  @GET("user/findUserByAccountname.do")
  Observable<BaseRequestBean> sendVerificationCode(@Query("accountname") String accountname);

  @GET("user/register.do")
  Observable<BaseRequestBean> register(@Query("accountname") String accountname,
                                       @Query("password") String password,
                                       @Query("verificationCode") String verificationCode);

  @GET("user/login.do")
  Observable<BaseRequestBean<LoginActionBean>> login(@Query("accountname") String accountname,
                                                     @Query("password") String password);

  @GET("user/updatePassword.do")
  Observable<BaseRequestBean> change(@Query("id") int id,
                                     @Query("oldPwd") String oldPwd,
                                     @Query("newPwd") String newPwd);
}

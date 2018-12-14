package com.hyc.newsmallexcellent.net;

import com.hyc.newsmallexcellent.base.bean.BaseRequestBean;
import com.hyc.newsmallexcellent.bean.LoginActionBean;
import com.hyc.newsmallexcellent.bean.ResumeInfoBean;
import io.reactivex.Observable;
import java.util.Map;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface NetApiService {

  String baseUrl = "http://120.78.144.241:8080/";

  @GET("user/findUserByAccountname.do")
  Observable<BaseRequestBean<Object>> sendVerificationCode(
      @Query("accountname") String accountname);

  @GET("user/register.do")
  Observable<BaseRequestBean<Object>> register(@Query("accountname") String accountname,
      @Query("password") String password,
      @Query("verificationCode") String verificationCode);

  @GET("user/login.do")
  Observable<BaseRequestBean<LoginActionBean>> login(@Query("accountname") String accountname,
      @Query("password") String password);

  @GET("user/updatePassword.do")
  Observable<BaseRequestBean<Object>> change(@Query("id") int id,
      @Query("oldPwd") String oldPwd,
      @Query("newPwd") String newPwd);

  @GET("resume/findResumeByUserId.do")
  Observable<BaseRequestBean<ResumeInfoBean>> getUserResumeInfo(@Query("id") int id);

  @Multipart
  @POST("pic/uploadPhoto.do")
  Observable<BaseRequestBean<String>> uploadImage(@Part MultipartBody.Part uploadFile);

  @FormUrlEncoded
  @POST("resume/updateResume.do")
  Observable<BaseRequestBean<Object>> updateResume(@Query("id") int id,
      @Query("userId") int userId, @FieldMap
      Map<String, Object> params);
}

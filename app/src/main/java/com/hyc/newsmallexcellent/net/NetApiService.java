package com.hyc.newsmallexcellent.net;

import com.hyc.newsmallexcellent.base.bean.BaseRequestBean;
import com.hyc.newsmallexcellent.bean.ApplyBean;
import com.hyc.newsmallexcellent.bean.CategoryBean;
import com.hyc.newsmallexcellent.bean.FootPrintBean;
import com.hyc.newsmallexcellent.bean.JobBean;
import com.hyc.newsmallexcellent.bean.LoginActionBean;
import com.hyc.newsmallexcellent.bean.ResumeInfoBean;
import com.hyc.newsmallexcellent.bean.UploadImageBean;
import io.reactivex.Observable;
import java.util.List;
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
  Observable<BaseRequestBean<UploadImageBean>> uploadImage(@Part MultipartBody.Part uploadFile);

  @FormUrlEncoded
  @POST("resume/updateResume.do")
  Observable<BaseRequestBean<Object>> updateResume(@Query("id") int id,
      @Query("userId") int userId, @FieldMap
      Map<String, Object> params);

  @FormUrlEncoded
  @POST("job/addJob.do")
  Observable<BaseRequestBean<Object>> publishJob(@FieldMap Map<String, Object> params);

  @GET("category/addCategory.do")
  Observable<BaseRequestBean<Object>> jobsClassification(@Query("category") String category);

  @GET("category/delCategory.do")
  Observable<BaseRequestBean<Object>> deleteClassification(@Query("category") String category);

  @GET("category/findAllCategory.do")
  Observable<BaseRequestBean<List<CategoryBean>>> queryCategory();

  @GET("job/findAllJobByPosition.do")
  Observable<BaseRequestBean<JobBean>> queryNearbyJob(@Query("longitude") double lon,
      @Query("latitude") double lat, @Query("pageNum") int pageNum,
      @Query("pageSize") int pageSize);

  @FormUrlEncoded
  @POST("job/findAllJobByCondition.do")
  Observable<BaseRequestBean<JobBean>> queryJobByCondition(@FieldMap Map<String, Object> params);

  @GET("footprint/addFootprint.do")
  Observable<BaseRequestBean<Object>> uploadFootprint(@Query("userId") int userId,
      @Query("jobId") int id, @Query("jobTitle") String jobTitle);

  @GET("apply/addApply.do")
  Observable<BaseRequestBean<Object>> applyJob(@Query("jobId") int jobId, @Query("applyUserId")
      int userId, @Query("applyInformation") String applyInformation);

  @GET("report/addReport.do")
  Observable<BaseRequestBean<Object>> reportUser(@Query("reportUserId") int reportUserId,
      @Query("bereportUserId") int bereportUserId, @Query("reportContent") String reportContent);

  @GET("footprint/findAllFootprint.do")
  Observable<BaseRequestBean<FootPrintBean>> findAllFootprint(@Query("userId") int userId,
      @Query("pageSize") int pageSize, @Query("pageNum") int pageNum);

  @GET("job/findJobById.do")
  Observable<BaseRequestBean<JobBean.ListBean>> findJobById(@Query("id") int id);

  /**
   * 认证模块接口
   */
  @GET
  Observable<BaseRequestBean<Object>> authentication(
      @Query("userId") int userId, @Query("userName") String userName,
      @Query("authenticationType") int authenticationType, @Query("photoOne") String photoOne,
      @Query("photoTwo") String photoTwo);

  @GET("footprint/delFootprint.do")
  Observable<BaseRequestBean<Object>> deleteFootprint(@Query("id") int id);

  @GET("apply/findAllApply.do")
  Observable<BaseRequestBean<ApplyBean>> findAllApply(@Query("applyUserId") int id,
      @Query("pageSize") int pageSize, @Query("pageNum") int pageNum);

  @GET("apply/delApply.do")
  Observable<BaseRequestBean<Object>> cancelApply(@Query("id") int id);

  @GET("job/findAllJobById.do")
  Observable<BaseRequestBean<JobBean>> findUserPublishJob(@Query("userId") int userId,
      @Query("pageSize") int pageSize, @Query("pageNum") int pageNum);

  @GET("job/delJob.do")
  Observable<BaseRequestBean<Object>> revokeJob(@Query("id") int jobId);

  @GET("apply/findAllApplyByAddId.do")
  Observable<BaseRequestBean<ApplyBean>> findApplyByAddId(@Query("addId") int userId,
      @Query("pageSize") int pageSize, @Query("pageNum") int pageNum);

  @GET("apply/handleApply.do")
  Observable<BaseRequestBean<Object>> handleApply(@Query("id")int id,@Query("resultStatu")int resultStatu);
}

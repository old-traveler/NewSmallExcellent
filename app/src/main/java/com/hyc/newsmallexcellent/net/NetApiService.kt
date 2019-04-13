package com.hyc.newsmallexcellent.net

import com.hyc.newsmallexcellent.base.bean.BaseRequestBean
import com.hyc.newsmallexcellent.bean.ApplyBean
import com.hyc.newsmallexcellent.bean.AuthenticationBean
import com.hyc.newsmallexcellent.bean.CategoryBean
import com.hyc.newsmallexcellent.bean.FootPrintBean
import com.hyc.newsmallexcellent.bean.JobBean
import com.hyc.newsmallexcellent.bean.JobBean.ListBean
import com.hyc.newsmallexcellent.bean.LoginActionBean
import com.hyc.newsmallexcellent.bean.ReportBean
import com.hyc.newsmallexcellent.bean.ResumeInfoBean
import com.hyc.newsmallexcellent.bean.UploadImageBean
import com.hyc.newsmallexcellent.bean.WorkRecordBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

/**
 * 作者: 贺宇成
 * 时间: 2019/4/13
 * 描述:
 */

interface NetApiService {

  companion object {
    const val baseUrl = "http://120.78.144.241:8080/"
  }

  @GET("user/findUserByAccountname.do")
  fun sendVerificationCode(
    @Query("accountname") accountname: String
  ): Observable<BaseRequestBean<Any>>

  @GET("user/register.do")
  fun register(
    @Query("accountname") accountname: String,
    @Query("password") password: String,
    @Query("verificationCode") verificationCode: String
  ): Observable<BaseRequestBean<Any>>

  @GET("user/login.do")
  fun login(
    @Query("accountname") accountname: String,
    @Query("password") password: String
  ): Observable<BaseRequestBean<LoginActionBean>>

  @GET("user/updatePassword.do")
  fun change(
    @Query("id") id: Int,
    @Query("oldPwd") oldPwd: String,
    @Query("newPwd") newPwd: String
  ): Observable<BaseRequestBean<Any>>

  @GET("resume/findResumeByUserId.do")
  fun getUserResumeInfo(@Query("id") id: Int): Observable<BaseRequestBean<ResumeInfoBean>>

  @Multipart
  @POST("pic/uploadPhoto.do")
  fun uploadImage(@Part uploadFile: MultipartBody.Part): Observable<BaseRequestBean<UploadImageBean>>

  @FormUrlEncoded
  @POST("resume/updateResume.do")
  fun updateResume(
    @Query("id") id: Int,
    @Query("userId") userId: Int, @FieldMap
    params: Map<String, Any>
  ): Observable<BaseRequestBean<Any>>

  @FormUrlEncoded
  @POST("job/addJob.do")
  fun publishJob(@FieldMap params: Map<String, Any>): Observable<BaseRequestBean<Any>>

  @GET("category/addCategory.do")
  fun jobsClassification(@Query("category") category: String): Observable<BaseRequestBean<Any>>

  @GET("category/delCategory.do")
  fun deleteClassification(@Query("category") category: String): Observable<BaseRequestBean<Any>>

  @GET("category/findAllCategory.do")
  fun queryCategory(): Observable<BaseRequestBean<List<CategoryBean>>>

  @GET("job/findAllJobByPosition.do")
  fun queryNearbyJob(
    @Query("longitude") lon: Double,
    @Query("latitude") lat: Double, @Query("pageNum") pageNum: Int,
    @Query("pageSize") pageSize: Int
  ): Observable<BaseRequestBean<JobBean>>

  @FormUrlEncoded
  @POST("job/findAllJobByCondition.do")
  fun queryJobByCondition(@FieldMap params:@JvmSuppressWildcards  Map<String, Any>): Observable<BaseRequestBean<JobBean>>

  @GET("footprint/addFootprint.do")
  fun uploadFootprint(
    @Query("userId") userId: Int,
    @Query("jobId") id: Int, @Query("jobTitle") jobTitle: String
  ): Observable<BaseRequestBean<Any>>

  @GET("apply/addApply.do")
  fun applyJob(
    @Query("jobId") jobId: Int, @Query("applyUserId")
    userId: Int, @Query("applyInformation") applyInformation: String
  ): Observable<BaseRequestBean<Any>>

  @GET("report/addReport.do")
  fun reportUser(
    @Query("reportUserId") reportUserId: Int,
    @Query("bereportUserId") bereportUserId: Int, @Query("reportContent") reportContent: String
  ): Observable<BaseRequestBean<Any>>

  @GET("footprint/findAllFootprint.do")
  fun findAllFootprint(
    @Query("userId") userId: Int,
    @Query("pageSize") pageSize: Int, @Query("pageNum") pageNum: Int
  ): Observable<BaseRequestBean<FootPrintBean>>

  @GET("job/findJobById.do")
  fun findJobById(@Query("id") id: Int): Observable<BaseRequestBean<ListBean>>

  /**
   * 认证模块接口
   */
  @GET("authentication/addAuthentication.do")
  fun authentication(
    @Query("userId") userId: Int, @Query("userName") userName: String,
    @Query("authenticationType") authenticationType: Int, @Query("photoOne") photoOne: String,
    @Query("photoTwo") photoTwo: String
  ): Observable<BaseRequestBean<Any>>

  @GET("footprint/delFootprint.do")
  fun deleteFootprint(@Query("id") id: Int): Observable<BaseRequestBean<Any>>

  @GET("apply/findAllApply.do")
  fun findAllApply(
    @Query("applyUserId") id: Int,
    @Query("pageSize") pageSize: Int, @Query("pageNum") pageNum: Int
  ): Observable<BaseRequestBean<ApplyBean>>

  @GET("apply/delApply.do")
  fun cancelApply(@Query("id") id: Int): Observable<BaseRequestBean<Any>>

  @GET("job/findAllJobById.do")
  fun findUserPublishJob(
    @Query("userId") userId: Int,
    @Query("pageSize") pageSize: Int, @Query("pageNum") pageNum: Int
  ): Observable<BaseRequestBean<JobBean>>

  @GET("job/delJob.do")
  fun revokeJob(@Query("id") jobId: Int): Observable<BaseRequestBean<Any>>

  @GET("apply/findAllApplyByAddId.do")
  fun findApplyByAddId(
    @Query("addId") userId: Int,
    @Query("pageSize") pageSize: Int, @Query("pageNum") pageNum: Int
  ): Observable<BaseRequestBean<ApplyBean>>

  @GET("apply/handleApply.do")
  fun handleApply(
    @Query("id") id: Int,
    @Query("resultStatu") resultStatu: Int
  ): Observable<BaseRequestBean<Any>>

  @GET("authentication/findAllAuthentication.do")
  fun findAllAuthentication(
    @Query("pageSize") pageSize: Int, @Query("pageNum") pageNum: Int
  ): Observable<BaseRequestBean<AuthenticationBean>>

  @GET("report/findAllReportByUserId.do")
  fun findAllReportByUserId(
    @Query("reportUserId") id: Int,
    @Query("pageSize") pageSize: Int, @Query("pageNum") pageNum: Int
  ): Observable<BaseRequestBean<ReportBean>>

  @GET("report/findAllReport.do")
  fun findAllReport(
    @Query("pageSize") pageSize: Int,
    @Query("pageNum") pageNum: Int
  ): Observable<BaseRequestBean<ReportBean>>

  @GET("authentication/handleAuthentication.do")
  fun dealAuthentication(
    @Query("id") id: Int,
    @Query("userId") userId: Int, @Query("authenticationType") type: Int,
    @Query("status") status: Int, @Query("result") result: String
  ): Observable<BaseRequestBean<Any>>

  @GET("report/delReport.do")
  fun deleteReport(@Query("id") id: Int): Observable<BaseRequestBean<Any>>

  @GET("report/handleReport.do")
  fun dealReport(
    @Query("id") id: Int,
    @Query("resultNum") resultNum: Int, @Query("resultText") resultText: String
  ): Observable<BaseRequestBean<Any>>

  @GET("apply/findAllPayroll.do")
  fun findWordRecord(
    @Query("userId") userId: Int,
    @Query("pageSize") pageSize: Int,
    @Query("pageNum") pageNum: Int
  ): Observable<BaseRequestBean<WorkRecordBean>>

}

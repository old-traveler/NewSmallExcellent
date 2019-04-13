package com.hyc.newsmallexcellent.model

import android.text.TextUtils
import com.amap.api.maps.model.LatLng
import com.hyc.newsmallexcellent.base.bean.BaseRequestBean
import com.hyc.newsmallexcellent.bean.ApplyBean
import com.hyc.newsmallexcellent.bean.AuthenticationBean
import com.hyc.newsmallexcellent.bean.FootPrintBean
import com.hyc.newsmallexcellent.bean.JobBean
import com.hyc.newsmallexcellent.bean.LoginActionBean
import com.hyc.newsmallexcellent.bean.ReportBean
import com.hyc.newsmallexcellent.bean.ResumeInfoBean
import com.hyc.newsmallexcellent.bean.UploadImageBean
import com.hyc.newsmallexcellent.bean.WorkRecordBean
import com.hyc.newsmallexcellent.helper.RequestHelper
import com.hyc.newsmallexcellent.helper.SpCacheHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * 作者: 贺宇成
 * 时间: 2019/4/13
 * 描述:
 */
class UserModel {

  val isLogin: Boolean
    get() = !TextUtils.isEmpty(SpCacheHelper.getString("accountName".toLowerCase()))

  val userLatLng: LatLng
    get() = LatLng(
      java.lang.Double.parseDouble(SpCacheHelper.getString("lat")),
      java.lang.Double.parseDouble(SpCacheHelper.getString("lon"))
    )

  val curUserId: Int
    get() = SpCacheHelper.getInt("user_id", 2)

  val curHeadUrl: String
    get() = SpCacheHelper.getString("headUrl")

  fun verificationUserPhone(account: String): Observable<BaseRequestBean<Any>> {
    return RequestHelper.getRequestApi().sendVerificationCode(account)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun register(
    account: String, password: String,
    code: String
  ): Observable<BaseRequestBean<Any>> {
    return RequestHelper.getRequestApi().register(account, password, code)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun login(account: String, password: String): Observable<BaseRequestBean<LoginActionBean>> {
    return RequestHelper.getRequestApi().login(account, password)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun cacheUserInfo(bean: LoginActionBean) {
    SpCacheHelper.withBuilder()
      .withInt("user_id", bean.id)
      .withString("accountName".toLowerCase(), bean.accountname)
      .withString("nickname", bean.nickname)
      .withInt("isAuthentication", bean.isAuthentication)
      .withInt("status", bean.status)
      .withString("headUrl", bean.headUrl)
      .commit()
  }

  fun cacheUserLocation(city: String, address: String, latitude: Double, longitude: Double) {
    SpCacheHelper.withBuilder()
      .withString("city", city)
      .withString("address", address)
      .withString("lat", latitude.toString())
      .withString("lon", longitude.toString())
      .commit()
  }

  fun changePassword(id: Int, oldPwd: String, newPwd: String): Observable<BaseRequestBean<Any>> {
    return RequestHelper.getRequestApi().change(id, oldPwd, newPwd)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun getUserResumeInfo(id: Int): Observable<BaseRequestBean<ResumeInfoBean>> {
    return RequestHelper.getRequestApi().getUserResumeInfo(id)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun isCurUser(id: Int): Boolean {
    return curUserId == id
  }

  fun uploadImage(map: Map<String, Any>): Observable<BaseRequestBean<UploadImageBean>> {
    return uploadImage(map["headPhoto"] as String)
  }

  fun uploadImage(imagePath: String): Observable<BaseRequestBean<UploadImageBean>> {
    val file = File(imagePath)
    val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
    val body = MultipartBody.Part.createFormData("uploadFile", file.name, requestFile)
    return RequestHelper.getRequestApi().uploadImage(body)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun updateUserResume(
    map: Map<String, Any>, id: Int,
    userId: Int
  ): Observable<BaseRequestBean<Any>> {
    return RequestHelper.getRequestApi().updateResume(id, userId, map)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun findAllFootprint(
    userId: Int, pageSize: Int,
    pageNum: Int
  ): Observable<BaseRequestBean<FootPrintBean>> {
    return RequestHelper.getRequestApi().findAllFootprint(userId, pageSize, pageNum)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun deleteFootprint(id: Int): Observable<BaseRequestBean<Any>> {
    return RequestHelper.getRequestApi().deleteFootprint(id)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun findUserJob(userId: Int, pageSize: Int, pageNum: Int): Observable<BaseRequestBean<JobBean>> {
    return RequestHelper.getRequestApi().findUserPublishJob(userId, pageSize, pageNum)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun revokeJob(jobId: Int): Observable<BaseRequestBean<Any>> {
    return RequestHelper.getRequestApi().revokeJob(jobId)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun userAuthentication(
    userId: Int, userName: String,
    authenticationType: Int,
    photoOne: String, photoTwo: String
  ): Observable<BaseRequestBean<Any>> {
    return RequestHelper.getRequestApi()
      .authentication(userId, userName, authenticationType, photoOne, photoTwo)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun findApplyByUserId(
    userId: Int, pageSize: Int,
    pageNum: Int
  ): Observable<BaseRequestBean<ApplyBean>> {
    return RequestHelper.getRequestApi().findApplyByAddId(userId, pageSize, pageNum)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun handleApply(id: Int, resultStatus: Int): Observable<BaseRequestBean<Any>> {
    return RequestHelper.getRequestApi().handleApply(id, resultStatus)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun findAllAuthentication(
    pageSize: Int,
    pageNum: Int
  ): Observable<BaseRequestBean<AuthenticationBean>> {
    return RequestHelper.getRequestApi().findAllAuthentication(pageSize, pageNum)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun dealAuthentication(
    id: Int, userId: Int, type: Int,
    state: Int, result: String
  ): Observable<BaseRequestBean<Any>> {
    return RequestHelper.getRequestApi().dealAuthentication(id, userId, type, state, result)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun findAllReport(pageSize: Int, pageNum: Int): Observable<BaseRequestBean<ReportBean>> {
    return RequestHelper.getRequestApi().findAllReport(pageSize, pageNum)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun findAllReportByUserId(
    id: Int, pageSize: Int,
    pageNum: Int
  ): Observable<BaseRequestBean<ReportBean>> {
    return RequestHelper.getRequestApi().findAllReportByUserId(id, pageSize, pageNum)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun deleteReport(id: Int): Observable<BaseRequestBean<Any>> {
    return RequestHelper.getRequestApi().deleteReport(id)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun dealReport(id: Int, result: Int, resultText: String): Observable<BaseRequestBean<Any>> {
    return RequestHelper.getRequestApi().dealReport(id, result, resultText)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun findWorkRecord(
    userId: Int, pageSize: Int,
    pageNum: Int
  ): Observable<BaseRequestBean<WorkRecordBean>> {
    return RequestHelper.getRequestApi().findWordRecord(userId, pageSize, pageNum)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }
}

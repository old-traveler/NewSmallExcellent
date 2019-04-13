package com.hyc.newsmallexcellent.model

import com.hyc.newsmallexcellent.base.bean.BaseRequestBean
import com.hyc.newsmallexcellent.bean.ApplyBean
import com.hyc.newsmallexcellent.bean.CategoryBean
import com.hyc.newsmallexcellent.bean.JobBean
import com.hyc.newsmallexcellent.bean.JobBean.ListBean
import com.hyc.newsmallexcellent.helper.RequestHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 作者: 贺宇成
 * 时间: 2019/4/13
 * 描述:
 */
class JobModel {

  /**
   * 请求添加分类
   */
  fun jobsClassification(category: String): Observable<BaseRequestBean<Any>> {
    return RequestHelper.getRequestApi().jobsClassification(category)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  /**
   * 请求删除分类
   */
  fun deleteClassification(category: String): Observable<BaseRequestBean<Any>> {
    return RequestHelper.getRequestApi().deleteClassification(category)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun queryAllCategory(): Observable<BaseRequestBean<List<CategoryBean>>> {
    return RequestHelper.getRequestApi().queryCategory()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun publishJob(userId: Int, map: MutableMap<String, Any>): Observable<BaseRequestBean<Any>> {
    map["userId"] = userId
    return RequestHelper.getRequestApi().publishJob(map)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun queryNearbyJob(
    latitude: Double,
    longitude: Double,
    pageNum: Int
  ): Observable<BaseRequestBean<JobBean>> {
    return RequestHelper.getRequestApi().queryNearbyJob(longitude, latitude, pageNum, 20)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun queryJobByCondition(map: Map<String, Any>): Observable<BaseRequestBean<JobBean>> {
    return RequestHelper.getRequestApi().queryJobByCondition(map)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun uploadFootprint(userId: Int, jobId: Int, jobTitle: String): Observable<BaseRequestBean<Any>> {
    return RequestHelper.getRequestApi().uploadFootprint(userId, jobId, jobTitle)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun applyJob(userId: Int, jobId: Int, applyInfo: String): Observable<BaseRequestBean<Any>> {
    return RequestHelper.getRequestApi().applyJob(jobId, userId, applyInfo)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun reportUser(
    reportUserId: Int,
    beReportUserId: Int,
    reportContent: String
  ): Observable<BaseRequestBean<Any>> {
    return RequestHelper.getRequestApi().reportUser(reportUserId, beReportUserId, reportContent)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun findJobById(id: Int): Observable<BaseRequestBean<ListBean>> {
    return RequestHelper.getRequestApi().findJobById(id)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun findAllApply(
    userId: Int,
    pageSize: Int,
    pageNum: Int
  ): Observable<BaseRequestBean<ApplyBean>> {
    return RequestHelper.getRequestApi().findAllApply(userId, pageSize, pageNum)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun cancelApply(id: Int): Observable<BaseRequestBean<Any>> {
    return RequestHelper.getRequestApi().cancelApply(id)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

}

package com.hyc.newsmallexcellent.model;

import com.hyc.newsmallexcellent.base.bean.BaseRequestBean;
import com.hyc.newsmallexcellent.bean.CategoryBean;
import com.hyc.newsmallexcellent.bean.JobBean;
import com.hyc.newsmallexcellent.helper.RequestHelper;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.Map;

public class JobModel {

  /**
   * 请求添加分类
   * @param category
   * @return
   */
  public Observable<BaseRequestBean<Object>> jobsClassification(String category) {
    return RequestHelper.getRequestApi().jobsClassification(category)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  /**
   * 请求删除分类
   * @param category
   * @return
   */
  public Observable<BaseRequestBean<Object>> deleteClassification(String category) {
    return RequestHelper.getRequestApi().deleteClassification(category)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<BaseRequestBean<List<CategoryBean>>> queryAllCategory(){
    return RequestHelper.getRequestApi().queryCategory()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<BaseRequestBean<Object>> publishJob(int userId,Map<String,Object> map){
    map.put("userId",userId);
    return RequestHelper.getRequestApi().publishJob(map)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<BaseRequestBean<JobBean>> queryNearbyJob(double latitude,double longitude,int pageNum){
    return RequestHelper.getRequestApi().queryNearbyJob(longitude,latitude,pageNum,20)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<BaseRequestBean<JobBean>> queryJobByCondition(Map<String,Object> map){
    return RequestHelper.getRequestApi().queryJobByCondition(map)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<BaseRequestBean<Object>> uploadFootprint(int userId,int jobId,String jobTitle){
    return RequestHelper.getRequestApi().uploadFootprint(userId,jobId,jobTitle)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<BaseRequestBean<Object>> applyJob(int userId,int jobId,String applyInfo){
    return RequestHelper.getRequestApi().applyJob(jobId,userId,applyInfo)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<BaseRequestBean<Object>> reportUser(int reportUserId,int bereportUserId,String reportContent){
    return RequestHelper.getRequestApi().reportUser(reportUserId,bereportUserId,reportContent)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }



}

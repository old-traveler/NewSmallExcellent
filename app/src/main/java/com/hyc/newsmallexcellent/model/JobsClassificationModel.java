package com.hyc.newsmallexcellent.model;

import com.hyc.newsmallexcellent.base.bean.BaseRequestBean;
import com.hyc.newsmallexcellent.helper.RequestHelper;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class JobsClassificationModel {

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

}

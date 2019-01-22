package com.hyc.newsmallexcellent.presenter;

import android.content.res.AssetManager;
import com.google.gson.Gson;
import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.bean.AddressBean;
import com.hyc.newsmallexcellent.bean.CategoryBean;
import com.hyc.newsmallexcellent.bean.JobBean;
import com.hyc.newsmallexcellent.interfaces.QueryJobContact;
import com.hyc.newsmallexcellent.model.JobModel;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QueryJobPresenter extends BasePresenter<QueryJobContact.IView> implements QueryJobContact.IPresenter {

  private JobModel jobModel = new JobModel();

  private AddressBean addressBean;
  private List<String> categoryTitles;

  @Override
  public void queryJobByCondition(boolean isLoadMore) {
    mvpView.showLoadingView();
    addDisposable(jobModel.queryJobByCondition(mvpView.getQueryCondition())
    .subscribe(new BaseRequestConsumer<JobBean>(mvpView) {
      @Override
      protected void onRequestSuccess(JobBean data) {
        if (isLoadMore){
          mvpView.appendJobInfo(data);
        }else {
          mvpView.onSuccessQuery(data);
        }
      }
    },new BaseErrorConsumer(mvpView)));

  }

  @Override
  public void getCityList() {
    if (addressBean != null){
      mvpView.loadCityList(addressBean);
      return;
    }
    addDisposable(Observable.create((ObservableOnSubscribe<String>) emitter -> {
      StringBuilder stringBuilder = new StringBuilder();
      AssetManager assetManager = getContext().getAssets();
      InputStreamReader reader = new InputStreamReader(assetManager.open("citys.json"));
      BufferedReader bufferedReader = new BufferedReader(reader);
      String line;
      while ((line = bufferedReader.readLine()) != null){
        stringBuilder.append(line);
      }
      bufferedReader.close();
      reader.close();
      emitter.onNext(stringBuilder.toString());
    }).subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
          Gson gson = new Gson();
          addressBean = gson.fromJson(s, AddressBean.class);
          mvpView.loadCityList(addressBean);
        }, throwable -> ToastHelper.toast(throwable.getMessage())));

  }

  @Override
  public void fetchCategoryList() {
    if (categoryTitles != null) {
      mvpView.loadCategoryList(categoryTitles);
      return;
    }
    mvpView.showLoadingView();
    addDisposable(
        jobModel.queryAllCategory().subscribe(new BaseRequestConsumer<List<CategoryBean>>(mvpView) {

          @Override
          protected void onRequestSuccess(List<CategoryBean> data) {
            if (data.size() > 0) {
              List<String> strings = new ArrayList<>();
              for (CategoryBean dataBean : data) {
                strings.add(dataBean.getCategory());
              }
              categoryTitles = strings;
              mvpView.loadCategoryList(strings);
            }
          }
        }, new BaseErrorConsumer(mvpView)));
  }
}

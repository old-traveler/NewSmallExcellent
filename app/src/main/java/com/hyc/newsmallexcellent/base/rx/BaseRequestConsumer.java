package com.hyc.newsmallexcellent.base.rx;

import com.hyc.newsmallexcellent.base.bean.BaseRequestBean;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.base.interfaces.ILoading;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public abstract class BaseRequestConsumer<D> implements Consumer<BaseRequestBean<D>> {

  private ILoading iLoading;

  private List list;

  private ArrayList arrayList = (ArrayList)list;

  public BaseRequestConsumer(ILoading iLoading){
    this.iLoading = iLoading;
  }

  public static final int REQUEST_OK = 0;

  @Override
  public void accept(BaseRequestBean<D> baseRequestBean) throws Exception {
    if (baseRequestBean.getStatus() == REQUEST_OK){
      onRequestSuccess(baseRequestBean.getData());
    }else {
      onRequestError(baseRequestBean);
    }
    if (iLoading != null){
      iLoading.closeLoadingView();
    }
  }

  protected abstract void onRequestSuccess(D data);

  public void onRequestError(BaseRequestBean baseRequestBean){
    ToastHelper.toast(baseRequestBean.getMsg());
  }

}

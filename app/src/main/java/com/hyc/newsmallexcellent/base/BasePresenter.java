package com.hyc.newsmallexcellent.base;

import com.hyc.newsmallexcellent.base.rx.DisposableManager;
import io.reactivex.disposables.Disposable;

public class BasePresenter<V> {

  private DisposableManager disposableManager;

  public V mvpView;

  void attachView(V mvpView){
    this.mvpView = mvpView;
  }


  void detachView() {
    this.mvpView = null;
    onUnsubscribe();
  }

  private void onUnsubscribe(){
    if (disposableManager != null){
      disposableManager.cancelAllDisposable();
    }
  }


  public void addDisposable(Disposable disposable){
    if (disposableManager == null){
      disposableManager = new DisposableManager();
    }
    disposableManager.addDisposable(disposable);
  }

}

package com.hyc.newsmallexcellent.presenter

import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer
import com.hyc.newsmallexcellent.bean.ReportBean
import com.hyc.newsmallexcellent.interfaces.ReportListContract
import com.hyc.newsmallexcellent.model.UserModel

/**
 * 作者: 贺宇成
 * 时间: 2019/4/8
 * 描述:
 */
class ReportListPresenter : ReportListContract.Presenter() {

  val userModel = UserModel()

  override fun deleteReport(id: Int, position: Int) {
    mvpView.showLoadingView()
    addDisposable(userModel.deleteReport(id).subscribe(object : BaseRequestConsumer<Any>(mvpView) {
      override fun onRequestSuccess(data: Any?) {
        mvpView.deleteSuccess(position)
      }

    }, BaseErrorConsumer(mvpView)))
  }

  override fun fetchData(pageIndex: Int) {
    val observable = if (mvpView.isMaster()) {
      userModel.findAllReport(20, pageIndex)
    } else {
      userModel.findAllReportByUserId(userModel.curUserId, 20, pageIndex)
    }

    addDisposable(observable.subscribe(object : BaseRequestConsumer<ReportBean>() {
      override fun onRequestSuccess(data: ReportBean?) {
        mvpView.smartAppendData(data?.list, data?.isHasNextPage!!)
      }
    }, mvpView))
  }
}
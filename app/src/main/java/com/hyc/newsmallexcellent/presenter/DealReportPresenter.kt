package com.hyc.newsmallexcellent.presenter

import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer
import com.hyc.newsmallexcellent.interfaces.DealReportContract
import com.hyc.newsmallexcellent.model.UserModel

/**
 * 作者: 贺宇成
 * 时间: 2019/4/9
 * 描述:
 */
class DealReportPresenter : DealReportContract.Presenter() {

  val userModel = UserModel()

  override fun dealReport(id: Int, resultNum: Int, resultText: String) {
    addDisposable(userModel.dealReport(id, resultNum, resultText).subscribe(object :
      BaseRequestConsumer<Any>(mvpView) {
      override fun onRequestSuccess(data: Any?) {
        mvpView.showDealResult(resultText, resultNum)
      }

    }, BaseErrorConsumer(mvpView)));
  }

}
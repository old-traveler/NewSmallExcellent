package com.hyc.newsmallexcellent.presenter

import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer
import com.hyc.newsmallexcellent.interfaces.DealApplyContract
import com.hyc.newsmallexcellent.model.UserModel

/**
 * 作者: 贺宇成
 * 时间: 2019/4/8
 * 描述:
 */
class DealApplyPresenter : DealApplyContract.Presenter() {

  val userModel: UserModel = UserModel()

  override fun dealApply(id: Int, isPassed: Boolean) {
    mvpView.showLoadingView()
    addDisposable(
      userModel.handleApply(id, if (isPassed) 2 else 3).subscribe(
        object : BaseRequestConsumer<Any>(mvpView) {
          override fun onRequestSuccess(data: Any?) {
            mvpView.showDealResult(isPassed)
          }
        }, BaseErrorConsumer(mvpView)
      )
    )
  }
}
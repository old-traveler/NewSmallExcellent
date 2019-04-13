package com.hyc.newsmallexcellent.presenter

import com.hyc.newsmallexcellent.base.rx.BaseErrorConsumer
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer
import com.hyc.newsmallexcellent.bean.WorkRecordBean
import com.hyc.newsmallexcellent.interfaces.WorkRecordContract
import com.hyc.newsmallexcellent.model.UserModel

/**
 * 作者: 贺宇成
 * 时间: 2019/4/13
 * 描述:
 */
class WorkRecordPresenter : WorkRecordContract.Presenter() {
  val userModel = UserModel()

  override fun fetchData(pageIndex: Int) {
    addDisposable(userModel.findWorkRecord(userModel.curUserId, 20, pageIndex).subscribe(object :
      BaseRequestConsumer<WorkRecordBean>() {
      override fun onRequestSuccess(data: WorkRecordBean?) {
        data?.let { mvpView.smartAppendData(it.list, it.isHasNextPage) }
      }

    }, mvpView))
  }
}
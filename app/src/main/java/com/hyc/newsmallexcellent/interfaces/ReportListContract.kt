package com.hyc.newsmallexcellent.interfaces

import com.hyc.newsmallexcellent.base.interfaces.IListActivity
import com.hyc.newsmallexcellent.base.presenter.BaseListPresenter
import com.hyc.newsmallexcellent.bean.ReportBean

/**
 * 作者: 贺宇成
 * 时间: 2019/4/8
 * 描述:
 */
interface ReportListContract {

  abstract class Presenter : BaseListPresenter<View>() {

    abstract fun deleteReport(id: Int, position: Int)
  }

  interface View : IListActivity<ReportBean.ListBean> {

    fun deleteSuccess(position: Int)

    fun isMaster(): Boolean

  }

}
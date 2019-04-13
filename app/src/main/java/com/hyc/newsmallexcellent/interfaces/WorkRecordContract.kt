package com.hyc.newsmallexcellent.interfaces

import com.hyc.newsmallexcellent.base.interfaces.IListActivity
import com.hyc.newsmallexcellent.base.presenter.BaseListPresenter
import com.hyc.newsmallexcellent.bean.WorkRecordBean

/**
 * 作者: 贺宇成
 * 时间: 2019/4/13
 * 描述:
 */
interface WorkRecordContract {

  abstract class Presenter : BaseListPresenter<View>() {
    //
  }

  interface View : IListActivity<WorkRecordBean.ListBean> {
    //
  }

}
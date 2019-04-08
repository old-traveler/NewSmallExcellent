package com.hyc.newsmallexcellent.base.presenter

import com.hyc.newsmallexcellent.base.BasePresenter
import com.hyc.newsmallexcellent.base.interfaces.IListActivity

/**
 * 作者: 贺宇成
 * 时间: 2019/4/8
 * 描述:
 */
abstract class BaseListPresenter<V : IListActivity<*>> : BasePresenter<V>(){

  abstract fun fetchData(pageIndex : Int)

}
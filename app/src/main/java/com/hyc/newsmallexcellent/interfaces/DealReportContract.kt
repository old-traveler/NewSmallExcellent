package com.hyc.newsmallexcellent.interfaces

import com.hyc.newsmallexcellent.base.BasePresenter
import com.hyc.newsmallexcellent.base.interfaces.ILoading

/**
 * 作者: 贺宇成
 * 时间: 2019/4/8
 * 描述:
 */
interface DealReportContract {

  abstract class Presenter : BasePresenter<View>() {

    abstract fun dealReport(id: Int, resultNum: Int, resultText: String)

  }

  interface View : ILoading {

    fun showDealResult(resultText: String, resultNum: Int)

  }

}
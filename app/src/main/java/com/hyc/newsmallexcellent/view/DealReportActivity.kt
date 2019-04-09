package com.hyc.newsmallexcellent.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.hyc.newsmallexcellent.R
import com.hyc.newsmallexcellent.base.BaseMvpActivity
import com.hyc.newsmallexcellent.bean.ReportBean.ListBean
import com.hyc.newsmallexcellent.interfaces.DealReportContract
import com.hyc.newsmallexcellent.interfaces.DealReportContract.Presenter
import com.hyc.newsmallexcellent.presenter.DealReportPresenter
import kotlinx.android.synthetic.main.activity_deal_report.btn_pass
import kotlinx.android.synthetic.main.activity_deal_report.btn_un_pass
import kotlinx.android.synthetic.main.activity_deal_report.et_report_deal
import kotlinx.android.synthetic.main.activity_deal_report.fl_deal
import kotlinx.android.synthetic.main.activity_deal_report.ll_report_result
import kotlinx.android.synthetic.main.activity_deal_report.tv_report_info
import kotlinx.android.synthetic.main.activity_deal_report.tv_report_result
import kotlinx.android.synthetic.main.activity_deal_report.tv_report_statue
import kotlinx.android.synthetic.main.activity_deal_report.tv_see_resume

/**
 * 作者: 贺宇成
 * 时间: 2019/4/9
 * 描述:
 */
class DealReportActivity : BaseMvpActivity<DealReportContract.Presenter>(),
  DealReportContract.View, OnClickListener {

  private lateinit var listBean: ListBean
  private var isMaster: Boolean = false

  companion object {
    fun start(context: Context, listBean: ListBean?, isMaster: Boolean) {
      listBean?.let {
        val bundle = Bundle()
        bundle.putParcelable("listBean", listBean)
        bundle.putBoolean("isMaster", isMaster)
        context.startActivity(Intent(context, DealReportActivity::class.java).putExtras(bundle))
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_deal_report)
    super.onCreate(savedInstanceState)
    initIntentData()
    setToolBarTitle("举报处理")
    initView()
  }

  private fun initView() {
    tv_report_info.text = listBean.reportContent
    when (listBean.status) {
      2, 3 -> {
        showDealResult(listBean.result, listBean.status)
      }
      else -> {
        if (isMaster) {
          tv_report_statue.text = "待处理"
          btn_pass.setOnClickListener(this)
          btn_un_pass.setOnClickListener(this)
        } else {
          showDealResult("", listBean.status)
        }
      }
    }
    tv_see_resume.setOnClickListener(this)
  }

  override fun onClick(v: View?) {
    when (v?.id) {
      R.id.btn_pass, R.id.btn_un_pass -> {
        if (et_report_deal.text.toString().isNotEmpty()) {
          presenter.dealReport(
            listBean.id,
            if (v.id == R.id.btn_pass) 2 else 3,
            et_report_deal.text.toString()
          )
        }
      }
      R.id.tv_see_resume -> {
        val bundle = Bundle()
        bundle.putInt("user_id", listBean.bereportUserId)
        startActivity(Intent(this@DealReportActivity, ResumeActivity::class.java).putExtras(bundle))
      }
    }
  }

  private fun initIntentData() {
    listBean = dataBundle.getParcelable("listBean")
    isMaster = dataBundle.getBoolean("isMaster")
  }

  override fun createPresenter(): Presenter {
    return DealReportPresenter()
  }

  override fun showDealResult(resultText: String, resultNum: Int) {
    fl_deal.visibility = View.GONE
    et_report_deal.visibility = View.GONE
    ll_report_result.visibility = View.VISIBLE
    tv_report_result.text = resultText
    tv_report_statue.text = when (resultNum) {
      2 -> "举报生效"
      3 -> "举报无效"
      else -> "待处理"
    }
  }

}
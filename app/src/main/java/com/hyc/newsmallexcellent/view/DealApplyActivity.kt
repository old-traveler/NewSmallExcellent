package com.hyc.newsmallexcellent.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.hyc.newsmallexcellent.R
import com.hyc.newsmallexcellent.base.BaseMvpActivity
import com.hyc.newsmallexcellent.bean.ApplyBean
import com.hyc.newsmallexcellent.bean.ApplyBean.ListBean
import com.hyc.newsmallexcellent.interfaces.DealApplyContract
import com.hyc.newsmallexcellent.interfaces.DealApplyContract.Presenter
import com.hyc.newsmallexcellent.presenter.DealApplyPresenter
import kotlinx.android.synthetic.main.activity_deal_apply.btn_pass
import kotlinx.android.synthetic.main.activity_deal_apply.btn_un_pass
import kotlinx.android.synthetic.main.activity_deal_apply.fl_deal
import kotlinx.android.synthetic.main.activity_deal_apply.iv_result
import kotlinx.android.synthetic.main.activity_deal_apply.ll_result
import kotlinx.android.synthetic.main.activity_deal_apply.tv_job_name
import kotlinx.android.synthetic.main.activity_deal_apply.tv_result
import kotlinx.android.synthetic.main.activity_deal_apply.tv_see_resume

/**
 * 作者: 贺宇成
 * 时间: 2019/4/8
 * 描述:
 */
class DealApplyActivity : BaseMvpActivity<DealApplyContract.Presenter>(), DealApplyContract.View,
  OnClickListener {

  private var isDealer: Boolean? = false
  private var listBean: ApplyBean.ListBean? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_deal_apply)
    super.onCreate(savedInstanceState)
    initIntentData()
    initView()
    setToolBarTitle("处理申请")
  }

  companion object {

    fun start(context: Context, listBean: ListBean?, isDealer: Boolean) {
      val bundle = Bundle()
      bundle.putParcelable("listBean", listBean)
      bundle.putBoolean("isDealer", isDealer)
      context.startActivity(Intent(context, DealApplyActivity::class.java).putExtras(bundle))
    }

  }

  private fun initIntentData() {
    listBean = intent?.extras?.getParcelable("listBean")
    isDealer = intent?.extras?.getBoolean("isDealer", false)
  }

  private fun initView() {
    listBean?.let {
      tv_job_name.text = listBean!!.remark
      if (!isDealer!!) {
        showDealResult(listBean!!.handleStatus)
      } else if (listBean!!.handleStatus != 0) {
        showDealResult(listBean!!.handleStatus)
      } else {
        btn_pass.setOnClickListener(this)
        btn_un_pass.setOnClickListener(this)
      }
      tv_job_name.setOnClickListener(this)
      tv_see_resume.setOnClickListener(this)
    }
  }

  override fun showDealResult(isPassed: Boolean) {
    showDealResult(if (isPassed) 2 else 3)
  }

  override fun createPresenter(): Presenter {
    return DealApplyPresenter()
  }

  override fun onClick(v: View?) {
    if (listBean == null) {
      return
    }
    when (v?.id) {
      R.id.tv_job_name -> {
        JobDetailActivity.start(this@DealApplyActivity, listBean!!.jobId)
      }
      R.id.tv_see_resume -> {
        val bundle = Bundle()
        bundle.putInt("user_id", listBean!!.applyUserId)
        startActivity(Intent(this@DealApplyActivity, ResumeActivity::class.java).putExtras(bundle))
      }
      R.id.btn_pass, R.id.btn_un_pass -> {
        presenter.dealApply(listBean!!.id, v.id == R.id.btn_pass)
      }
    }
  }

  private fun showDealResult(result: Int) {
    fl_deal.visibility = View.GONE
    ll_result.visibility = View.VISIBLE
    when (result) {
      0 -> {
        iv_result.setImageResource(R.drawable.ic_undeal)
        tv_result.text = "待处理"
      }
      2 -> {
        iv_result.setImageResource(R.drawable.ic_passed)
        tv_result.text = "已同意"
      }
      3 -> {
        iv_result.setImageResource(R.drawable.ic_unpass)
        tv_result.text = "未同意"
      }
    }
  }
}
package com.hyc.newsmallexcellent.view

import android.os.Bundle
import android.view.View
import com.hyc.newsmallexcellent.R
import com.hyc.newsmallexcellent.adapter.viewholder.ReportViewHolder
import com.hyc.newsmallexcellent.base.activity.BaseMvpListActivity
import com.hyc.newsmallexcellent.base.adapter.BaseRecycleAdapter
import com.hyc.newsmallexcellent.base.interfaces.OnItemClickListener
import com.hyc.newsmallexcellent.base.interfaces.OnItemLongClickListener
import com.hyc.newsmallexcellent.bean.ReportBean
import com.hyc.newsmallexcellent.bean.ReportBean.ListBean
import com.hyc.newsmallexcellent.interfaces.ReportListContract
import com.hyc.newsmallexcellent.interfaces.ReportListContract.Presenter
import com.hyc.newsmallexcellent.presenter.ReportListPresenter
import kotlinx.android.synthetic.main.lib_activity_list.smartRefreshLayout

/**
 * 作者: 贺宇成
 * 时间: 2019/4/8
 * 描述:
 */
class ReportListActivity :
  BaseMvpListActivity<ReportListContract.Presenter, ReportBean.ListBean, ReportViewHolder>(),
  ReportListContract.View, OnItemLongClickListener<ListBean>, OnItemClickListener<ListBean> {

  override fun onItemLongClick(itemData: ListBean?, view: View?, position: Int): Boolean {
    presenter.deleteReport(itemData!!.id, position)
    return true
  }

  override fun onItemClick(itemData: ListBean?, view: View?, position: Int) {
    DealReportActivity.start(this@ReportListActivity, itemData, isMaster())
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setToolBarTitle(if (isMaster()) "举报管理" else "我的举报")
    smartRefreshLayout.autoRefresh()
    if (!isMaster()) {
      adapter.setOnItemLongClickListener(this)
    }
    adapter.setOnItemClickListener(this)
  }

  override fun deleteSuccess(position: Int) {
    adapter.removeItemFormList(position)
  }

  override fun isMaster(): Boolean {
    return intent.getBooleanExtra("isMaster",false)
  }

  override fun getAdapterClass(): BaseRecycleAdapter<ReportBean.ListBean, ReportViewHolder> {
    return BaseRecycleAdapter(R.layout.item_report, ReportViewHolder::class.java)
  }

  override fun createPresenter(): Presenter {
    return ReportListPresenter()
  }
}
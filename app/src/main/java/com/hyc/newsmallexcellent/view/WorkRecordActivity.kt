package com.hyc.newsmallexcellent.view

import android.os.Bundle
import android.view.View
import com.hyc.newsmallexcellent.R
import com.hyc.newsmallexcellent.adapter.viewholder.WorkRecordViewHolder
import com.hyc.newsmallexcellent.base.activity.BaseMvpListActivity
import com.hyc.newsmallexcellent.base.adapter.BaseRecycleAdapter
import com.hyc.newsmallexcellent.base.interfaces.OnItemClickListener
import com.hyc.newsmallexcellent.bean.WorkRecordBean
import com.hyc.newsmallexcellent.bean.WorkRecordBean.ListBean
import com.hyc.newsmallexcellent.interfaces.WorkRecordContract
import com.hyc.newsmallexcellent.interfaces.WorkRecordContract.Presenter
import com.hyc.newsmallexcellent.presenter.WorkRecordPresenter
import kotlinx.android.synthetic.main.lib_activity_list.smartRefreshLayout

/**
 * 作者: 贺宇成
 * 时间: 2019/4/13
 * 描述:
 */
class WorkRecordActivity :
  BaseMvpListActivity<WorkRecordContract.Presenter, WorkRecordBean.ListBean, WorkRecordViewHolder>(),
  OnItemClickListener<ListBean> , WorkRecordContract.View {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setToolBarTitle("兼职记录")
    smartRefreshLayout.autoRefresh()
    adapter.setOnItemClickListener(this@WorkRecordActivity)
  }

  override fun getAdapterClass(): BaseRecycleAdapter<ListBean, WorkRecordViewHolder> {
    return BaseRecycleAdapter(R.layout.item_work_record, WorkRecordViewHolder::class.java)
  }

  override fun createPresenter(): Presenter {
    return WorkRecordPresenter()
  }

  override fun onItemClick(itemData: ListBean?, view: View?, position: Int) {
    itemData?.jobId?.let { JobDetailActivity.start(this@WorkRecordActivity, it) }
  }

}
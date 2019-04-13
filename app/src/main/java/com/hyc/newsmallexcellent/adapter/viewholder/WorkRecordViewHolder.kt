package com.hyc.newsmallexcellent.adapter.viewholder

import android.content.Context
import android.view.View
import android.widget.TextView
import com.hyc.newsmallexcellent.R
import com.hyc.newsmallexcellent.base.adapter.viewholder.BaseViewHolder
import com.hyc.newsmallexcellent.bean.WorkRecordBean
import com.hyc.newsmallexcellent.bean.WorkRecordBean.ListBean

/**
 * 作者: 贺宇成
 * 时间: 2019/4/13
 * 描述:
 */
class WorkRecordViewHolder(itemView: View) : BaseViewHolder<WorkRecordBean.ListBean>(itemView) {

  private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
  private val tvSalary: TextView = itemView.findViewById(R.id.tv_salary)
  private val tvWorkTime: TextView = itemView.findViewById(R.id.tv_work_time)

  override fun loadItemData(context: Context?, data: ListBean?, position: Int) {
    tvTitle.text = data?.remark
    tvSalary.text = String.format("%s %s", data?.jobSalary, data?.jobSalaryUnit)
    tvWorkTime.text = data?.handleData
  }

}
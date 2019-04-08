package com.hyc.newsmallexcellent.adapter.viewholder

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.TextView
import com.hyc.newsmallexcellent.R
import com.hyc.newsmallexcellent.base.adapter.viewholder.BaseViewHolder
import com.hyc.newsmallexcellent.base.helper.UiHelper
import com.hyc.newsmallexcellent.bean.ReportBean
import java.text.SimpleDateFormat

/**
 * 作者: 贺宇成
 * 时间: 2019/4/8
 * 描述:
 */
class ReportViewHolder(itemView: View) : BaseViewHolder<ReportBean.ListBean>(itemView) {

  private val tvReportName: TextView = itemView.findViewById(R.id.tv_report_name)
  private val tvState: TextView = itemView.findViewById(R.id.tv_state)
  private val tvTime: TextView = itemView.findViewById(R.id.tv_report_time)

  @SuppressLint("SimpleDateFormat")
  override fun loadItemData(context: Context?, data: ReportBean.ListBean?, position: Int) {
    tvReportName.text = data?.reportContent
    val template = SimpleDateFormat(UiHelper.getString(R.string.year))
    val timeStr = template.format(data?.reportData)
    tvTime.text = timeStr
    tvState.text = when (data?.status) {
      0 -> "未处理"
      2 -> "举报生效"
      3 -> "举报无效"
      else -> "未知"
    }
  }
}
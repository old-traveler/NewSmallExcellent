package com.hyc.newsmallexcellent.adapter.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.adapter.viewholder.BaseViewHolder;
import com.hyc.newsmallexcellent.bean.JobBean;

public class NearbyJobViewHolder extends BaseViewHolder<JobBean.ListBean> {

  @BindView(R.id.tv_title)
  TextView tvTitle;
  @BindView(R.id.tv_salary)
  TextView tvSalary;
  @BindView(R.id.tv_need_number)
  TextView tvNeedNumber;
  @BindView(R.id.tv_address)
  TextView tvAddress;
  @BindView(R.id.tv_city)
  TextView tvCity;

  public NearbyJobViewHolder(@NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override
  public void loadItemData(Context context, JobBean.ListBean data, int position) {
    tvTitle.setText(data.getJobTitle());
    tvSalary.setText(String.format("%s %s", data.getJobSalary(), data.getJobSalaryUnit()));
    tvNeedNumber.setText(String.format("%d个职位",data.getJobCount()));
    tvAddress.setText(data.getIssuePlace());
    tvCity.setText(data.getCity());
  }
}

package com.hyc.newsmallexcellent.adapter.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.adapter.viewholder.BaseViewHolder;
import com.hyc.newsmallexcellent.bean.ApplyBean;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyApplyViewHolder extends BaseViewHolder<ApplyBean.ListBean> {

  @BindView(R.id.tv_job_name)
  TextView tvJobName;
  @BindView(R.id.tv_state)
  TextView tvState;
  @BindView(R.id.tv_apply_time)
  TextView tvApplyTime;

  public MyApplyViewHolder(@NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override
  public void loadItemData(Context context, ApplyBean.ListBean data, int position) {
    tvJobName.setText(data.getRemark());
    tvState.setText(data.getHandleStatus() == 0 ? "申请中":(data.getHandleStatus() == 2 ? "已同意":"未同意"));
    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    tvApplyTime.setText(format.format(new Date(data.getApplyData())));
  }
}

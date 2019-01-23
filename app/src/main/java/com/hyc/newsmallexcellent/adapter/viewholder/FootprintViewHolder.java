package com.hyc.newsmallexcellent.adapter.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.adapter.viewholder.BaseViewHolder;
import com.hyc.newsmallexcellent.bean.FootPrintBean;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FootprintViewHolder extends BaseViewHolder<FootPrintBean.ListBean> {
  @BindView(R.id.tv_job_name)
  TextView tvJobName;
  @BindView(R.id.tv_view_time)
  TextView tvViewTime;

  public FootprintViewHolder(@NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this,itemView);
  }

  @Override
  public void loadItemData(Context context, FootPrintBean.ListBean data, int position) {
    tvJobName.setText(data.getJobTitle());
    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    tvViewTime.setText(format.format(new Date(data.getViewingData())));
  }
}

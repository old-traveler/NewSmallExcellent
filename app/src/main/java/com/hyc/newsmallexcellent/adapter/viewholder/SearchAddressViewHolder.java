package com.hyc.newsmallexcellent.adapter.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.adapter.viewholder.BaseViewHolder;
import com.hyc.newsmallexcellent.base.bean.PoiAddressBean;

public class SearchAddressViewHolder extends BaseViewHolder<PoiAddressBean> {


  @BindView(R.id.tv_title)
  TextView tvTitle;
  @BindView(R.id.tv_address)
  TextView tvAddress;

  public SearchAddressViewHolder(@NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this,itemView);
  }

  @Override
  public void loadItemData(Context context, PoiAddressBean data, int position) {
    tvTitle.setText(data.getDetailAddress());
    tvAddress.setText(data.getDistrict());
  }
}

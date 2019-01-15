package com.hyc.newsmallexcellent.adapter.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.adapter.viewholder.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NearbyAddressViewHolder extends BaseViewHolder<PoiItem> {

    
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    public NearbyAddressViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void loadItemData(Context context, PoiItem data, int position) {
        tvTitle.setText(data.getTitle());
        tvAddress .setText(data.getSnippet());
    }

}

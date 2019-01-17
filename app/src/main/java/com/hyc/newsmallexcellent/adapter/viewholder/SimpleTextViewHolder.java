package com.hyc.newsmallexcellent.adapter.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.adapter.viewholder.BaseViewHolder;

public class SimpleTextViewHolder extends BaseViewHolder<String> {

  @BindView(R.id.tv_text)
  TextView textView;

  public SimpleTextViewHolder(@NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this,itemView);
  }

  @Override
  public void loadItemData(Context context, String data, int position) {
    textView.setText(data);
  }
}

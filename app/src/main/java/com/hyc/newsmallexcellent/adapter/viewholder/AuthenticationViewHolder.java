package com.hyc.newsmallexcellent.adapter.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.adapter.viewholder.BaseViewHolder;
import com.hyc.newsmallexcellent.bean.AuthenticationBean;
import com.hyc.newsmallexcellent.helper.ImageRequestHelper;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuthenticationViewHolder extends BaseViewHolder<AuthenticationBean.ListBean> {

  @BindView(R.id.iv_authentication)
  ImageView ivAuthentication;
  @BindView(R.id.tv_name)
  TextView tvName;
  @BindView(R.id.tv_type)
  TextView tvType;
  @BindView(R.id.tv_time)
  TextView tvTime;

  private SimpleDateFormat simpleDateFormat;

  public AuthenticationViewHolder(@NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this,itemView);
    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
  }

  @Override
  public void loadItemData(Context context, AuthenticationBean.ListBean data, int position) {
    ImageRequestHelper.loadImage(context,data.getPhotoOne(),ivAuthentication);
    tvName.setText(data.getUserName());
    tvType.setText(data.getAuthenticationType() == 0 ? "学生认证" : "实名认证");
    tvTime.setText(simpleDateFormat.format(new Date(data.getAuthenticationData())));
  }
}

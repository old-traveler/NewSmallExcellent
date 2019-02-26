package com.hyc.newsmallexcellent.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.bean.AuthenticationBean;
import com.hyc.newsmallexcellent.helper.ImageRequestHelper;
import com.hyc.newsmallexcellent.interfaces.DealAuthenticationContact;
import com.hyc.newsmallexcellent.presenter.DealAuthenticationPresenter;

public class DealAuthenticationActivity extends BaseMvpActivity<DealAuthenticationPresenter>
    implements
    DealAuthenticationContact.IView {

  @BindView(R.id.et_result)
  EditText etResult;
  @BindView(R.id.iv_one)
  ImageView ivOne;
  @BindView(R.id.iv_two)
  ImageView ivTwo;
  private AuthenticationBean.ListBean listBean;

  public static void start(Context context, AuthenticationBean.ListBean listBean) {
    Bundle bundle = new Bundle();
    bundle.putSerializable("info", listBean);
    context.startActivity(new Intent(context, DealAuthenticationActivity.class).putExtras(bundle));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_deal_authentication);
    super.onCreate(savedInstanceState);
    setToolBarTitle("处理认证");
    listBean = (AuthenticationBean.ListBean) getIntent().getExtras().getSerializable("info");
    ImageRequestHelper.loadImage(this, listBean.getPhotoOne(), ivOne);
    ImageRequestHelper.loadImage(this, listBean.getPhotoTwo(), ivTwo);
  }

  @Override
  protected DealAuthenticationPresenter createPresenter() {
    return new DealAuthenticationPresenter();
  }

  @Override
  public AuthenticationBean.ListBean getListBean() {
    return listBean;
  }

  @Override
  public String getResult() {
    return etResult.getText().toString();
  }

  @Override
  public void onDealSuccess() {
    ToastHelper.toast("处理成功");
    finish();
  }

  @OnClick({ R.id.btn_pass, R.id.btn_un_pass })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.btn_pass:
        presenter.dealAuthentication(true);
        break;
      case R.id.btn_un_pass:
        presenter.dealAuthentication(false);
        break;
    }
  }
}

package com.hyc.newsmallexcellent.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.interfaces.ApplyJobContact;
import com.hyc.newsmallexcellent.presenter.ApplyJobPresenter;

public class ApplyJobActivity extends BaseMvpActivity<ApplyJobPresenter> implements
    ApplyJobContact.IView {

  @BindView(R.id.et_apply_info)
  EditText etApplyInfo;

  private boolean hasApply = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_apply_job);
    super.onCreate(savedInstanceState);
    setToolBarTitle("申请兼职");
  }

  public static void start(Context context, int jobId) {
    context.startActivity(new Intent(context, ApplyJobActivity.class).putExtra("job_id", jobId));
  }

  @Override
  protected ApplyJobPresenter createPresenter() {
    return new ApplyJobPresenter();
  }

  @Override
  public String getApplyInfo() {
    return etApplyInfo.getText().toString();
  }

  @Override
  public int getJobId() {
    return getIntent().getIntExtra("job_id", 0);
  }

  @Override
  public void onSuccessSendApply() {
    hasApply = true;
    ToastHelper.toast("申请信息已送达");
  }

  @OnClick(R.id.btn_apply)
  public void onViewClicked() {
    if (hasApply) {
      ToastHelper.toast("已发送申请");
    } else {
      presenter.applyJob();
    }
  }
}

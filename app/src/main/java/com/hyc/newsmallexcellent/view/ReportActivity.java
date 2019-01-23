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
import com.hyc.newsmallexcellent.interfaces.ReportContact;
import com.hyc.newsmallexcellent.presenter.ReportPresenter;

public class ReportActivity extends BaseMvpActivity<ReportPresenter>
    implements ReportContact.IView {

  @BindView(R.id.et_report_info)
  EditText etReportInfo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_report);
    super.onCreate(savedInstanceState);
    setToolBarTitle("举报用户");
  }

  public static void start(Context context, int reportUserId) {
    context.startActivity(
        new Intent(context, ReportActivity.class).putExtra("report_id", reportUserId));
  }

  @Override
  protected ReportPresenter createPresenter() {
    return new ReportPresenter();
  }

  @Override
  public String getReportContent() {
    return etReportInfo.getText().toString();
  }

  @Override
  public int getBereportUserId() {
    return getIntent().getIntExtra("report_id", 0);
  }

  @Override
  public void onSuccessSendReport() {
    ToastHelper.toast("举报信息已发送");
  }

  @OnClick(R.id.btn_report)
  public void onViewClicked() {
    presenter.report();
  }
}

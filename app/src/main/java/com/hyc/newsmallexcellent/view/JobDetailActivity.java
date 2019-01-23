package com.hyc.newsmallexcellent.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.bean.JobBean;
import com.hyc.newsmallexcellent.interfaces.JobDetailContact;
import com.hyc.newsmallexcellent.presenter.JobDetailPresenter;
import java.util.Objects;

public class JobDetailActivity extends BaseMvpActivity<JobDetailPresenter> implements
    JobDetailContact.IView {

  @BindView(R.id.tv_job_name)
  TextView tvJobName;
  @BindView(R.id.tv_job_salary)
  TextView tvJobSalary;
  @BindView(R.id.tv_job_count)
  TextView tvJobCount;
  @BindView(R.id.tv_job_area)
  TextView tvJobArea;
  @BindView(R.id.tv_deadline)
  TextView tvDeadline;
  @BindView(R.id.tv_work_hour)
  TextView tvWorkHour;
  @BindView(R.id.tv_work_day)
  TextView tvWorkDay;
  @BindView(R.id.tv_publisher)
  TextView tvPublisher;
  @BindView(R.id.tv_contact_phone)
  TextView tvContactPhone;
  @BindView(R.id.tv_address)
  TextView tvAddress;
  @BindView(R.id.tv_job_describe)
  TextView tvJobDescribe;
  @BindView(R.id.btn_apply_job)
  Button btnApplyJob;

  private int jobId;
  private int publisherId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_job_detail);
    super.onCreate(savedInstanceState);
    setToolBarTitle("工作详情");
    Bundle bundle = getIntent().getExtras();
    assert bundle != null;
    JobBean.ListBean listBean = bundle.getParcelable("job_info");
    if (listBean != null) {
      initViewWithBundle(listBean);
    } else {
      presenter.fetchJobInfoById(bundle.getInt("jobId"));
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_job_detail, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.item_report) {
      ReportActivity.start(this, publisherId);
      return true;
    } else if (item.getItemId() == R.id.item_footprint) {
      startActivity(new Intent(this, FootprintActivity.class));
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void initViewWithBundle(JobBean.ListBean listBean) {
    tvJobName.setText(listBean.getJobTitle());
    tvJobSalary.setText(
        String.format("%s %s", listBean.getJobSalary(), listBean.getJobSalaryUnit()));
    tvJobCount.setText(String.format("%d人", listBean.getJobCount()));
    tvJobArea.setText(listBean.getCity());
    tvDeadline.setText(listBean.getClosingDate());
    tvWorkHour.setText(listBean.getWorkingHours());
    tvWorkDay.setText(String.format("%s天", listBean.getWorkingDays()));
    tvPublisher.setText(listBean.getContact());
    tvContactPhone.setText(listBean.getTelephone());
    tvAddress.setText(listBean.getIssuePlace());
    tvJobDescribe.setText(listBean.getJobDescribe());
    presenter.uploadFootprint(listBean.getId(), listBean.getJobTitle());
    jobId = listBean.getId();
    publisherId = listBean.getUserId();
  }

  public static void start(Context context, JobBean.ListBean listBean) {
    Bundle bundle = new Bundle();
    bundle.putParcelable("job_info", listBean);
    context.startActivity(new Intent(context, JobDetailActivity.class).putExtras(bundle));
  }

  public static void start(Context context, int jobId) {
    Bundle bundle = new Bundle();
    bundle.putInt("jobId", jobId);
    context.startActivity(new Intent(context, JobDetailActivity.class).putExtras(bundle));
  }

  @Override
  protected JobDetailPresenter createPresenter() {
    return new JobDetailPresenter();
  }

  @OnClick({ R.id.tv_path_plan, R.id.btn_apply_job })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.tv_path_plan:
        break;
      case R.id.btn_apply_job:
        ApplyJobActivity.start(this, jobId);
        break;
    }
  }
}

package com.hyc.newsmallexcellent.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.adapter.JobsClassificationAdapter;
import com.hyc.newsmallexcellent.adapter.viewholder.SimpleTextViewHolder;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.adapter.BaseRecycleAdapter;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.base.interfaces.OnItemLongClickListener;
import com.hyc.newsmallexcellent.interfaces.JobsClassificationContract;
import com.hyc.newsmallexcellent.presenter.JobsClassificationPresenter;
import java.util.ArrayList;
import java.util.List;

public class JobsClassificationActivity extends BaseMvpActivity<JobsClassificationPresenter>
    implements JobsClassificationContract.View{

  @BindView(R.id.et_input)
  EditText etInput;
  @BindView(R.id.rv_category)
  RecyclerView rvCategory;
  private BaseRecycleAdapter<String,SimpleTextViewHolder> categoryAdapter;
  private List<String> list = new ArrayList<String>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_jobs_classification);
    super.onCreate(savedInstanceState);
    setToolBarTitle("工作分类管理");
    initView();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_categoty,menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.item_add){
      presenter.jobsClassification();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void initView() {
    rvCategory.setLayoutManager(new LinearLayoutManager(this));
    categoryAdapter = new BaseRecycleAdapter<>(R.layout.item_simple_text,SimpleTextViewHolder.class);
    rvCategory.setAdapter(categoryAdapter);
    categoryAdapter.setOnItemLongClickListener((itemData, view, position) -> {
      presenter.deleteClassification(itemData);
      return true;
    });
    presenter.queryCategory();
  }

  @Override
  protected JobsClassificationPresenter createPresenter() {
    return new JobsClassificationPresenter();
  }

  @Override
  public String getCategory() {
    return etInput.getText().toString();
  }

  @Override
  public boolean verificationInput() {
    if (TextUtils.isEmpty(getCategory())) {
      ToastHelper.toast("请输入所需要的添加的分类");
    } else {
      return true;
    }
    return false;
  }

  @Override
  public void addClassificationSuccess() {
    categoryAdapter.appendDataToList(etInput.getText().toString());
    etInput.setText("");
  }

  @Override
  public void onQuerySuccess(List<String> data) {
    categoryAdapter.setDataList(data);
  }

  @Override
  public void deleteCategory(String category) {
    categoryAdapter.removeItemFormList(category);
  }
}

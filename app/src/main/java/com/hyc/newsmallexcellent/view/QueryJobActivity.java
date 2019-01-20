package com.hyc.newsmallexcellent.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.adapter.viewholder.NearbyJobViewHolder;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.adapter.BaseRecycleAdapter;
import com.hyc.newsmallexcellent.bean.AddressBean;
import com.hyc.newsmallexcellent.bean.JobBean;
import com.hyc.newsmallexcellent.helper.SpCacheHelper;
import com.hyc.newsmallexcellent.interfaces.QueryJobContact;
import com.hyc.newsmallexcellent.presenter.QueryJobPresenter;
import com.hyc.newsmallexcellent.util.BottomSelectDialogUtil;
import java.util.HashMap;
import java.util.Map;

public class QueryJobActivity extends BaseMvpActivity<QueryJobPresenter> implements
    QueryJobContact.IView {

  @BindView(R.id.tv_city)
  TextView tvCity;
  @BindView(R.id.tv_salary)
  TextView tvSalary;
  @BindView(R.id.tv_category)
  TextView tvCategory;
  @BindView(R.id.tv_work_day)
  TextView tvWorkDay;
  @BindView(R.id.rv_job)
  RecyclerView rvJob;
  private int pageNum = 1;
  private boolean enableLoadMore = false;

  private BaseRecycleAdapter<JobBean.ListBean, NearbyJobViewHolder> adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_query_job);
    super.onCreate(savedInstanceState);
    setToolBarTitle("查询兼职");
    adapter = new BaseRecycleAdapter<>(R.layout.item_job_info, NearbyJobViewHolder.class);
    rvJob.setLayoutManager(new LinearLayoutManager(this));
    rvJob.setAdapter(adapter);
    tvCity.setText(SpCacheHelper.getString("city"));
    presenter.queryJobByCondition(false);
    rvJob.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (enableLoadMore && !recyclerView.canScrollVertically(1)) {
          enableLoadMore = false;
          presenter.queryJobByCondition(true);
        }
      }
    });
  }

  @Override
  protected QueryJobPresenter createPresenter() {
    return new QueryJobPresenter();
  }

  @Override
  public Map<String, Object> getQueryCondition() {
    Map<String, Object> map = new HashMap<>();
    map.put("pageNum", pageNum);
    map.put("pageSize", 20);
    map.put("city", tvCity.getText().toString().replace("市", ""));
    return map;
  }

  @Override
  public void onSuccessQuery(JobBean jobBean) {
    adapter.setDataList(jobBean.getList());
    pageNum++;
    enableLoadMore = !jobBean.isIsLastPage();
  }

  @Override
  public void appendJobInfo(JobBean jobBean) {
    adapter.appendDataToList(jobBean.getList());
    pageNum++;
    enableLoadMore = !jobBean.isIsLastPage();
  }

  @Override
  public void loadCityList(AddressBean addressBean) {
    BottomSheetDialog dialog = new BottomSheetDialog(QueryJobActivity.this);
    BottomSelectDialogUtil.showCityListDialog(dialog, QueryJobActivity.this,addressBean,
        (itemData, view, position) -> {
          tvCity.setText(itemData);
          dialog.dismiss();
        });
  }

  @OnClick({ R.id.tv_city, R.id.tv_salary, R.id.tv_category, R.id.tv_work_day })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.tv_city:
        presenter.getCityList();
        break;
      case R.id.tv_salary:
        break;
      case R.id.tv_category:
        break;
      case R.id.tv_work_day:
        break;
    }
  }

}

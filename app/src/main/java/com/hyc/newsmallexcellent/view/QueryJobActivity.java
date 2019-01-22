package com.hyc.newsmallexcellent.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.adapter.viewholder.NearbyJobViewHolder;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.adapter.BaseRecycleAdapter;
import com.hyc.newsmallexcellent.base.interfaces.OnItemClickListener;
import com.hyc.newsmallexcellent.bean.AddressBean;
import com.hyc.newsmallexcellent.bean.CategoryBean;
import com.hyc.newsmallexcellent.bean.JobBean;
import com.hyc.newsmallexcellent.helper.SpCacheHelper;
import com.hyc.newsmallexcellent.interfaces.QueryJobContact;
import com.hyc.newsmallexcellent.presenter.QueryJobPresenter;
import com.hyc.newsmallexcellent.util.BottomSelectDialogUtil;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_map, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.item_search) {
      pageNum = 1;
      presenter.queryJobByCondition(false);
      return true;
    }
    return super.onOptionsItemSelected(item);
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
    if (tvCategory.getTag() != null) {
      map.put("category", tvCategory.getTag());
    }
    if (tvWorkDay.getTag() != null) {
      int position = (int) tvWorkDay.getTag();
      map.put("minWorkingDays", position == 0 ? 0 : (position == 1 ? 10 : 30));
      if (position != 2) {
        map.put("maxWorkingDays", position == 0 ? 10 : 30);
      }
    }
    if (tvSalary.getTag() != null) {
      String data = tvSalary.getText().toString();
      int length = data.length();
      int position = (int) tvSalary.getTag();
      Pair<Integer,Integer> salary = null;
      if (data.charAt(length - 2) == '时') {
        map.put("jobSalaryUnit","元/小时");
        salary = new Pair<>(20,40);
      } else if (data.charAt(length - 2) == '日') {
        map.put("jobSalaryUnit","元/日");
        salary = new Pair<>(60,100);
      } else {
        salary = new Pair<>(1000,3000);
        map.put("jobSalaryUnit","元/月");
      }
      map.put("minSalary", position == 0 ? 0 : (position == 1 ? salary.first : salary.second));
      if (position != 2) {
        map.put("maxSalary", position == 0 ? salary.first : salary.second);
      }
    }
    return map;
  }

  @Override
  public void onSuccessQuery(JobBean jobBean) {
    adapter.setDataList(jobBean.getList());
    pageNum++;
    enableLoadMore = jobBean.isHasNextPage();
  }

  @Override
  public void appendJobInfo(JobBean jobBean) {
    adapter.appendDataToList(jobBean.getList());
    pageNum++;
    enableLoadMore = jobBean.isHasNextPage();
  }

  @Override
  public void loadCityList(AddressBean addressBean) {
    BottomSheetDialog dialog = new BottomSheetDialog(QueryJobActivity.this);
    BottomSelectDialogUtil.showCityListDialog(dialog, QueryJobActivity.this, addressBean,
        (itemData, view, position) -> {
          tvCity.setText(itemData);
          dialog.dismiss();
        });
  }

  @Override
  public void loadCategoryList(List<String> data) {
    BottomSheetDialog dialog = new BottomSheetDialog(this);
    BottomSelectDialogUtil.showSimpleListTextDialog(dialog, data,
        (itemData, view, position) -> {
          tvCategory.setText(itemData);
          dialog.dismiss();
          tvCategory.setTag(itemData);
        });
  }

  @OnClick({ R.id.tv_city, R.id.tv_salary, R.id.tv_category, R.id.tv_work_day })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.tv_city:
        presenter.getCityList();
        break;
      case R.id.tv_salary:
        showSalarySelect();
        break;
      case R.id.tv_category:
        presenter.fetchCategoryList();
        break;
      case R.id.tv_work_day:
        showWorkDaySelect();
        break;
    }
  }

  private void showWorkDaySelect() {
    BottomSheetDialog dialog = new BottomSheetDialog(this);
    BottomSelectDialogUtil.showSimpleListTextDialog(dialog,
        Arrays.asList(new String[] { "0 - 10天", "10 - 30天", "30天以上" }),
        (itemData, view, position) -> {
          tvWorkDay.setText(itemData);
          dialog.dismiss();
          tvWorkDay.setTag(position);
        });
  }

  private void showSalarySelect() {
    BottomSheetDialog dialog = new BottomSheetDialog(QueryJobActivity.this);
    BottomSelectDialogUtil.showSalarySelectDialog(dialog, new OnItemClickListener<String>() {
      @Override
      public void onItemClick(String itemData, View view, int position) {
        tvSalary.setText(itemData);
        dialog.dismiss();
        tvSalary.setTag(position);
      }
    });
  }
}

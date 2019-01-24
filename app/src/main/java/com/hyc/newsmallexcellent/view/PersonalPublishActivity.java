package com.hyc.newsmallexcellent.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.adapter.viewholder.NearbyJobViewHolder;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.adapter.BaseRecycleAdapter;
import com.hyc.newsmallexcellent.base.interfaces.OnItemClickListener;
import com.hyc.newsmallexcellent.base.interfaces.OnItemLongClickListener;
import com.hyc.newsmallexcellent.bean.JobBean;
import com.hyc.newsmallexcellent.interfaces.PersonalPublishContact;
import com.hyc.newsmallexcellent.presenter.PersonalPublishPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class PersonalPublishActivity extends BaseMvpActivity<PersonalPublishPresenter> implements
    PersonalPublishContact.IView, OnRefreshListener, OnLoadMoreListener {

  @BindView(R.id.rv_publish)
  RecyclerView rvPublish;
  @BindView(R.id.srl_publish)
  SmartRefreshLayout srlPublish;
  private int page = 1;

  private BaseRecycleAdapter<JobBean.ListBean, NearbyJobViewHolder> adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_personal_publish);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);
    setToolBarTitle("个人发布");
    rvPublish.setLayoutManager(new LinearLayoutManager(this));
    rvPublish.setAdapter(adapter = new BaseRecycleAdapter<>(R.layout.item_job_info,NearbyJobViewHolder.class));
    srlPublish.setOnLoadMoreListener(this);
    srlPublish.setOnRefreshListener(this);
    srlPublish.autoRefresh();
    adapter.setOnItemClickListener((itemData, view, position) -> {
      JobDetailActivity.start(this,itemData.getId());
    });
    adapter.setOnItemLongClickListener((itemData, view, position) -> {
      presenter.revokeJob(itemData.getId(),position);
      return true;
    });
  }

  @Override
  protected PersonalPublishPresenter createPresenter() {
    return new PersonalPublishPresenter();
  }

  public static void start(Context context,int userId){
    context.startActivity(new Intent(context,PersonalPublishActivity.class).putExtra("userId",userId));
  }

  @Override
  public int getCurPage() {
    return page;
  }

  @Override
  public void loadJobInfo(JobBean jobBean,boolean isLoadMore) {
    if (isLoadMore){
      adapter.appendDataToList(jobBean.getList());
    }else {
      adapter.setDataList(jobBean.getList());
    }
    page++;
    srlPublish.setEnableLoadMore(jobBean.isHasNextPage());
  }

  @Override
  public void onSuccessDeleteJob(int position) {
    adapter.removeItemFormList(position);
  }

  @Override
  public int getUserId() {
    return getIntent().getIntExtra("userId",0);
  }

  @Override
  public void closeLoadingView() {
    super.closeLoadingView();
    if (srlPublish.getState() == RefreshState.Refreshing) {
      srlPublish.finishRefresh();
    } else if (srlPublish.getState() == RefreshState.Loading) {
      srlPublish.finishLoadMore();
    }
  }

  @Override
  public void onRefresh(@NonNull RefreshLayout refreshLayout) {
    page = 1;
    presenter.fetchPublishJob(false);

  }

  @Override
  public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
    presenter.fetchPublishJob(true);
  }
}

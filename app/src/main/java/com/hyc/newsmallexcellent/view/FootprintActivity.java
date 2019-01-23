package com.hyc.newsmallexcellent.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.adapter.viewholder.FootprintViewHolder;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.adapter.BaseRecycleAdapter;
import com.hyc.newsmallexcellent.base.interfaces.OnItemClickListener;
import com.hyc.newsmallexcellent.bean.FootPrintBean;
import com.hyc.newsmallexcellent.interfaces.FootprintContact;
import com.hyc.newsmallexcellent.presenter.FootprintPresenter;
import com.hyc.newsmallexcellent.widget.RecycleViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class FootprintActivity extends BaseMvpActivity<FootprintPresenter> implements
    FootprintContact.IView, OnRefreshListener, OnLoadMoreListener {

  @BindView(R.id.rv_footprint)
  RecyclerView rvFootprint;
  @BindView(R.id.srl_footprint)
  SmartRefreshLayout srlFootprint;

  private BaseRecycleAdapter<FootPrintBean.ListBean, FootprintViewHolder> adapter;

  private int page = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_footprint);
    super.onCreate(savedInstanceState);
    setToolBarTitle("我的足迹");
    adapter = new BaseRecycleAdapter<>(R.layout.item_footprint,FootprintViewHolder.class);
    rvFootprint.setLayoutManager(new LinearLayoutManager(this));
    rvFootprint.setAdapter(adapter);
    srlFootprint.setOnRefreshListener(this);
    srlFootprint.setOnLoadMoreListener(this);
    srlFootprint.autoRefresh();
    rvFootprint.addItemDecoration(
        new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 1, Color.GRAY));
    adapter.setOnItemClickListener((itemData, view, position) -> {
      JobDetailActivity.start(this,itemData.getJobId());
    });
  }

  @Override
  protected FootprintPresenter createPresenter() {
    return new FootprintPresenter();
  }

  @Override
  public void loadFootPrint(FootPrintBean footPrintBean) {
    if (srlFootprint.getState() == RefreshState.Refreshing){
      adapter.setDataList(footPrintBean.getList());
      srlFootprint.finishRefresh();
    }else {
      adapter.appendDataToList(footPrintBean.getList());
      srlFootprint.finishLoadMore();
    }
    page++;
    srlFootprint.setEnableLoadMore(footPrintBean.isHasNextPage());

  }

  @Override
  public int getCurPage() {
    return page;
  }

  @Override
  public void onRefresh(@NonNull RefreshLayout refreshLayout) {
    page = 1;
    presenter.fetchFootPrint();
  }

  @Override
  public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
    presenter.fetchFootPrint();
  }


}

package com.hyc.newsmallexcellent.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import butterknife.BindView;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.adapter.viewholder.AuthenticationViewHolder;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.adapter.BaseRecycleAdapter;
import com.hyc.newsmallexcellent.bean.AuthenticationBean;
import com.hyc.newsmallexcellent.interfaces.SeeAuthentication;
import com.hyc.newsmallexcellent.presenter.SeeAuthenticationPresenter;
import com.hyc.newsmallexcellent.widget.RecycleViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public class SeeAuthenticationActivity extends BaseMvpActivity<SeeAuthenticationPresenter>
    implements
    SeeAuthentication.IView {

  @BindView(R.id.rv_authentication)
  RecyclerView rvAuthentication;
  @BindView(R.id.srf_authentication)
  SmartRefreshLayout srfAuthentication;
  private int page = 1;
  private BaseRecycleAdapter<AuthenticationBean.ListBean, AuthenticationViewHolder> adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_see_authentication);
    super.onCreate(savedInstanceState);
    setToolBarTitle("查看认证");
    srfAuthentication.setOnRefreshListener(refreshLayout -> {
      page = 1;
      srfAuthentication.setEnableLoadMore(true);
      presenter.fetchAllAuthentication();
    });

    srfAuthentication.setOnLoadMoreListener(refreshLayout -> presenter.fetchAllAuthentication());
    rvAuthentication.setLayoutManager(new LinearLayoutManager(this));
    rvAuthentication.addItemDecoration(
        new RecycleViewDivider(this, LinearLayout.VERTICAL, 1, Color.GRAY));
    rvAuthentication.setAdapter(adapter =
        new BaseRecycleAdapter<>(R.layout.item_authentication, AuthenticationViewHolder.class));
    srfAuthentication.autoRefresh();
    adapter.setOnItemClickListener((itemData, view, position) -> DealAuthenticationActivity.start(
        SeeAuthenticationActivity.this, itemData));
  }

  @Override
  protected SeeAuthenticationPresenter createPresenter() {
    return new SeeAuthenticationPresenter();
  }

  @Override
  public void loadAuthentication(AuthenticationBean authenticationBean) {
    if (page == 1) {
      adapter.setDataList(authenticationBean.getList());
    } else {
      adapter.appendDataToList(authenticationBean.getList());
    }
    srfAuthentication.setEnableLoadMore(authenticationBean.isHasNextPage());
    page++;
  }

  @Override
  public void closeLoadingView() {
    super.closeLoadingView();
    srfAuthentication.finishLoadMore();
    srfAuthentication.finishRefresh();
  }

  @Override
  public int getCurPage() {
    return page;
  }
}

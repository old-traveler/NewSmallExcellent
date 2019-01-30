package com.hyc.newsmallexcellent.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.adapter.viewholder.MyApplyViewHolder;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.adapter.BaseRecycleAdapter;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.base.interfaces.OnDialogClickListener;
import com.hyc.newsmallexcellent.base.interfaces.OnItemClickListener;
import com.hyc.newsmallexcellent.base.interfaces.OnItemLongClickListener;
import com.hyc.newsmallexcellent.base.widger.CommonDialog;
import com.hyc.newsmallexcellent.bean.ApplyBean;
import com.hyc.newsmallexcellent.interfaces.MyApplyContact;
import com.hyc.newsmallexcellent.presenter.MyApplyPresenter;
import com.hyc.newsmallexcellent.widget.RecycleViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class MyApplyActivity extends BaseMvpActivity<MyApplyPresenter>
    implements MyApplyContact.IView, OnRefreshListener, OnLoadMoreListener {

  @BindView(R.id.rv_my_apply)
  RecyclerView rvMyApply;
  @BindView(R.id.srl_my_apply)
  SmartRefreshLayout srlMyApply;

  private boolean isDeal = true;

  private int page = 1;
  private BaseRecycleAdapter<ApplyBean.ListBean, MyApplyViewHolder> adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_my_apply);
    super.onCreate(savedInstanceState);
    rvMyApply.setLayoutManager(new LinearLayoutManager(this));
    rvMyApply.addItemDecoration(new RecycleViewDivider(this, LinearLayout.VERTICAL, 1, Color.GRAY));
    rvMyApply.setAdapter(
        adapter = new BaseRecycleAdapter<>(R.layout.item_my_apply, MyApplyViewHolder.class));
    srlMyApply.setOnRefreshListener(this);
    srlMyApply.setOnLoadMoreListener(this);
    if (isDeal) {
      adapter.setOnItemClickListener((itemData, view, position) -> {
        CommonDialog.showDialog(MyApplyActivity.this, "是否同意申请",
            "不同意", "同意", itemData.getApplyInformation(), isPosition -> {
              presenter.dealApply(itemData.getId(), isPosition);
            });
      });
    } else {
      adapter.setOnItemClickListener((itemData, view, position) -> {
        if (itemData.getHandleStatus() == 0) {
          CommonDialog.showDialog(MyApplyActivity.this, "是否撤销此条申请？", isPosition -> {
            if (isPosition) {
              presenter.cancelApply(itemData.getId(), position);
            }
          });
        }
      });
    }

    setToolBarTitle("我的申请");
    srlMyApply.autoRefresh();
  }

  @Override
  protected MyApplyPresenter createPresenter() {
    return new MyApplyPresenter();
  }

  @Override
  public void loadApplyInfo(boolean isLoadMore, ApplyBean applyBean) {
    page++;
    srlMyApply.setEnableLoadMore(applyBean.isHasNextPage());
    if (isLoadMore) {
      adapter.appendDataToList(applyBean.getList());
      srlMyApply.finishLoadMore();
    } else {
      adapter.setDataList(applyBean.getList());
      srlMyApply.finishRefresh();
    }
  }

  @Override
  public void closeLoadingView() {
    super.closeLoadingView();
    if (srlMyApply.getState() == RefreshState.Refreshing) {
      srlMyApply.finishRefresh();
    } else if (srlMyApply.getState() == RefreshState.Loading) {
      srlMyApply.finishLoadMore();
    }
  }

  @Override
  public void onSuccessCancelApply(int position) {
    adapter.removeItemFormList(position);
  }

  @Override
  public int getCurPage() {
    return page;
  }

  @Override
  public boolean isDealer() {
    return isDeal;
  }

  @Override
  public void onDealSuccess() {
    ToastHelper.toast("处理成功");
  }

  @Override
  public void onRefresh(@NonNull RefreshLayout refreshLayout) {
    page = 1;
    presenter.fetchApplyInfo(false);
  }

  @Override
  public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
    presenter.fetchApplyInfo(true);
  }
}

package com.hyc.newsmallexcellent.base.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.hyc.newsmallexcellent.R
import com.hyc.newsmallexcellent.base.BaseMvpActivity
import com.hyc.newsmallexcellent.base.adapter.BaseRecycleAdapter
import com.hyc.newsmallexcellent.base.adapter.viewholder.BaseViewHolder
import com.hyc.newsmallexcellent.base.helper.ToastHelper
import com.hyc.newsmallexcellent.base.interfaces.IListActivity
import com.hyc.newsmallexcellent.base.presenter.BaseListPresenter
import com.hyc.newsmallexcellent.widget.RecycleViewDivider
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState.Loading
import com.scwang.smartrefresh.layout.constant.RefreshState.Refreshing
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.lib_activity_list.recyclerView
import kotlinx.android.synthetic.main.lib_activity_list.smartRefreshLayout

/**
 * 作者: 贺宇成
 * 时间: 2019/4/8
 * 描述:
 */
abstract class BaseMvpListActivity<P : BaseListPresenter<*>, D, V : BaseViewHolder<D>> :
  BaseMvpActivity<P>(), IListActivity<D>,
  OnRefreshListener, OnLoadMoreListener {

  lateinit var adapter: BaseRecycleAdapter<D, V>

  private var pageIndex: Int = getStartPageIndex()

  override fun appendData(list: List<D>?, hasNextPage: Boolean) {
    adapter.appendDataToList(list)
    pageIndex++
    this.setLoadMoreEnable(hasNextPage)
    smartRefreshLayout.finishLoadMore()
  }

  override fun setData(list: List<D>?, hasNextPage: Boolean) {
    adapter.setDataList(list)
    pageIndex++
    this.setLoadMoreEnable(hasNextPage)
    smartRefreshLayout.finishRefresh()
  }

  override fun smartAppendData(list: List<D>?, hasNextPage: Boolean) {
    if (pageIndex == getStartPageIndex()) {
      setData(list, hasNextPage)
    } else {
      appendData(list, hasNextPage)
    }
  }

  override fun fetchDataFail(throwable: Throwable?) {
    when (smartRefreshLayout.state) {
      Loading -> {
        smartRefreshLayout.finishLoadMore()
      }
      Refreshing -> {
        smartRefreshLayout.finishRefresh()
      }
      else -> {
        closeLoadingView()
      }
    }
    throwable?.message?.let {
      ToastHelper.toast(throwable.message)
    }
    throwable?.printStackTrace()
  }

  fun getStartPageIndex(): Int {
    return 1
  }

  fun getLayoutManager(): RecyclerView.LayoutManager {
    return LinearLayoutManager(this)
  }

  abstract fun getAdapterClass(): BaseRecycleAdapter<D, V>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.lib_activity_list)
    adapter = getAdapterClass()
    smartRefreshLayout.setOnRefreshListener(this)
    smartRefreshLayout.setOnLoadMoreListener(this)
    recyclerView.layoutManager = getLayoutManager()
    recyclerView.adapter = adapter
    recyclerView.addItemDecoration(RecycleViewDivider(this, LinearLayout.VERTICAL, 1, Color.GRAY))
  }

  override fun setRefreshEnable(enable: Boolean) {
    smartRefreshLayout.setEnableRefresh(enable)
  }

  override fun setLoadMoreEnable(enable: Boolean) {
    smartRefreshLayout.setEnableLoadMore(enable)
  }

  override fun onLoadMore(refreshLayout: RefreshLayout) {
    presenter.fetchData(pageIndex)
  }

  override fun onRefresh(refreshLayout: RefreshLayout) {
    pageIndex = getStartPageIndex()
    smartRefreshLayout.setEnableLoadMore(true)
    presenter.fetchData(pageIndex)
  }

  override fun accept(t: Throwable?) {
    fetchDataFail(t)
  }

}
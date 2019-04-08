package com.hyc.newsmallexcellent.base.interfaces

/**
 * 作者: 贺宇成
 * 时间: 2019/4/8
 * 描述:
 */
interface IListActivity<D> : ILoading {

  fun appendData(list: List<D>?, hasNextPage: Boolean)

  fun setData(list: List<D>?, hasNextPage: Boolean)

  fun fetchDataFail(throwable: Throwable?)

  fun setRefreshEnable(enable: Boolean)

  fun setLoadMoreEnable(enable: Boolean)

  fun smartAppendData(list: List<D>?, hasNextPage: Boolean)

}
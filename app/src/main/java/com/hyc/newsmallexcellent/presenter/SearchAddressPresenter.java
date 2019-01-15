package com.hyc.newsmallexcellent.presenter;

import android.text.TextUtils;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.bean.PoiAddressBean;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.interfaces.SearchAddressContract;
import java.util.ArrayList;
import java.util.List;

public class SearchAddressPresenter extends BasePresenter<SearchAddressContract.IView>
    implements SearchAddressContract.IPresenter, PoiSearch.OnPoiSearchListener {

  private PoiSearch.Query query;

  @Override
  public void fetchAddressSuggest() {
    if (TextUtils.isEmpty(mvpView.getSearchKey())){
      mvpView.loadSuggest(null);
      return;
    }
    query = new PoiSearch.Query(mvpView.getSearchKey(), "", "");
    query.setPageSize(50);
    query.setPageNum(0);
    PoiSearch poiSearch = new PoiSearch(getContext(), query);
    poiSearch.setOnPoiSearchListener(this);
    poiSearch.searchPOIAsyn();
  }

  @Override
  public void onPoiSearched(PoiResult result, int rCode) {
    if (rCode == AMapException.CODE_AMAP_SUCCESS) {
      if (result != null && result.getQuery() != null) {  // 搜索poi的结果
        if (result.getQuery().equals(query)) {  // 是否是同一条
          ArrayList<PoiAddressBean> data = new ArrayList<>();//自己创建的数据集合
          List<PoiItem> poiItems = result.getPois();
          for(com.amap.api.services.core.PoiItem item : poiItems){
            LatLonPoint llp = item.getLatLonPoint();
            double lon = llp.getLongitude();
            double lat = llp.getLatitude();
            String title = item.getTitle();
            String text = item.getSnippet();
            String provinceName = item.getProvinceName();
            String cityName = item.getCityName();
            String adName = item.getAdName();
            data.add(new PoiAddressBean(lon, lat, title, text,provinceName,
                cityName,adName));
          }
          mvpView.loadSuggest(data);
        }
      } else {
        mvpView.loadSuggest(null);
      }
    } else {
      ToastHelper.toast(String.valueOf(rCode));
    }
  }

  @Override
  public void onPoiItemSearched(PoiItem poiItem, int i) {

  }
}

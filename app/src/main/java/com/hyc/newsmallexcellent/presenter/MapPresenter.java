package com.hyc.newsmallexcellent.presenter;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.interfaces.MapLocationContract;

public class MapPresenter extends BasePresenter<MapLocationContract.IView> implements MapLocationContract.IPresenter {


  @Override
  public void fetchNearPosition(Double latitude, Double longitude) {
    LatLonPoint point = new LatLonPoint(latitude,longitude);
    GeocodeSearch geocodeSearch = new GeocodeSearch(getContext());
    RegeocodeQuery regeocodeQuery = new RegeocodeQuery(point,3000, GeocodeSearch.AMAP);
    geocodeSearch.getFromLocationAsyn(regeocodeQuery);
    geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
      @Override
      public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
        if (AMapException.CODE_AMAP_SUCCESS == rCode) {
          final RegeocodeAddress address = regeocodeResult.getRegeocodeAddress();
          mvpView.loadNearPosition(address.getPois());
        }else {
          ToastHelper.toast(String.valueOf(rCode));
        }
      }
      @Override
      public void onGeocodeSearched(GeocodeResult geocodeResult, int i) { }
    });
  }
}

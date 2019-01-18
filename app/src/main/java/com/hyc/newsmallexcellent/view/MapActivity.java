package com.hyc.newsmallexcellent.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.PoiItem;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.adapter.viewholder.NearbyAddressViewHolder;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.adapter.BaseRecycleAdapter;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.interfaces.MapLocationContract;
import com.hyc.newsmallexcellent.presenter.MapPresenter;
import java.util.List;
import butterknife.BindView;

public class MapActivity extends BaseMvpActivity<MapPresenter> implements LocationSource,
    AMapLocationListener, MapLocationContract.IView {

  @BindView(R.id.map_mv)
  MapView mapMv;
  @BindView(R.id.mark)
  ImageView mark;
  @BindView(R.id.defalt_recycle)
  RecyclerView rvShowPoi; // 展示附近搜索热点
  private BaseRecycleAdapter<PoiItem, NearbyAddressViewHolder> adapter;
  //定位
  private LocationSource.OnLocationChangedListener mListener;
  private AMapLocationClient mLocationClient;
  private AMapLocationClientOption mLocationOption;
  private LatLng point;
  private Marker marker;
  private AMap navigationAMap;

  private String cityName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_map);
    super.onCreate(savedInstanceState);
    mapMv.onCreate(savedInstanceState);
    setToolBarTitle("位置选择");
    initialization();
  }

  @Override
  protected MapPresenter createPresenter() {
    return new MapPresenter();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_map, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
    } else if (item.getItemId() == R.id.item_search) {
      startActivityForResult(new Intent(this, SearchAddressActivity.class), 2010);
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * 初始化地图
   */
  private void initialization() {
    navigationAMap = mapMv.getMap();
    navigationAMap.getUiSettings().setMyLocationButtonEnabled(true);
    MyLocationStyle locationStyle = new MyLocationStyle();
    locationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
    locationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
    navigationAMap.setMyLocationStyle(locationStyle);
    navigationAMap.setLocationSource(this);
    navigationAMap.moveCamera(CameraUpdateFactory.zoomTo(18));
    navigationAMap.setMyLocationEnabled(true);
    adapter = new BaseRecycleAdapter<>(R.layout.item_showaddress, NearbyAddressViewHolder.class);
    rvShowPoi.setLayoutManager(new LinearLayoutManager(rvShowPoi.getContext()));
    rvShowPoi.setAdapter(adapter);
    rvShowPoi.setItemAnimator(new DefaultItemAnimator());
    adapter.setOnItemClickListener((itemData, view, position) -> {
      Intent intent = new Intent(this,ReleasePositionActivity.class)
          .putExtra("lat",itemData.getLatLonPoint().getLatitude())
          .putExtra("lon",itemData.getLatLonPoint().getLongitude())
          .putExtra("address",itemData.getTitle())
          .putExtra("city",MapActivity.this.cityName);
      setResult(RESULT_OK,intent);
      finish();
    });
    //监听地图发生变化之后
    navigationAMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
      @Override
      public void onCameraChange(CameraPosition cameraPosition) {

      }

      @Override
      public void onCameraChangeFinish(CameraPosition cameraPosition) {
        //发生变化时获取到经纬度传递逆地理编码获取周边数据
        presenter.fetchNearPosition(cameraPosition.target.latitude,
            cameraPosition.target.longitude);
        point = new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
        marker.remove();//将上一次描绘的mark清除
        //地图发生变化之后描绘mark
        marker = navigationAMap.addMarker(new com.amap.api.maps.model.MarkerOptions()
            .position(point)
            .title("")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.em_unread_count_bg)));
      }
    });
  }

  @Override
  public void onLocationChanged(AMapLocation aMapLocation) {
    navigationAMap = mapMv.getMap();
    if (aMapLocation != null && mListener != null) {
      if (aMapLocation.getErrorCode() == 0) {
        cityName = aMapLocation.getCity();
        mListener.onLocationChanged(aMapLocation);
      } else {
        ToastHelper.toast(aMapLocation.getErrorCode());
      }
    }
  }

  @Override
  public void activate(OnLocationChangedListener onLocationChangedListener) {
    mListener = onLocationChangedListener;
    if (mLocationClient == null) {
      mLocationClient = new AMapLocationClient(this.getApplicationContext());
      mLocationOption = new AMapLocationClientOption();
      mLocationClient.setLocationListener(this);
      mLocationOption.setOnceLocation(true); //只定位一次
      mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
      mLocationClient.setLocationOption(mLocationOption);
      mLocationClient.startLocation();
    }
  }

  @Override
  public void deactivate() {
    mListener = null;
    if (mLocationClient != null) {
      mLocationClient.stopLocation();
      mLocationClient.onDestroy();
    }
    mLocationClient = null;
    mLocationOption = null;
  }

  /**
   * 加载地图的四个个必要方法
   */
  @Override
  protected void onResume() {
    super.onResume();
    mapMv.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    deactivate();
    mapMv.onPause();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapMv.onSaveInstanceState(outState);
  }

  @Override
  protected void onDestroy() {
    mapMv.onDestroy();
    super.onDestroy();
  }

  @Override
  public void loadNearPosition(List<PoiItem> poiItems) {
    adapter.setDataList(poiItems);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 2010 && resultCode == RESULT_OK && data != null) {
      movePosition(data.getDoubleExtra("lat",0),data.getDoubleExtra("lon",0));
    }
  }

  private void movePosition(double lat,double lon){
    CameraPosition cp = navigationAMap.getCameraPosition();
    CameraPosition cpNew = CameraPosition.fromLatLngZoom(new LatLng(lat,lon),cp.zoom);
    CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cpNew);
    navigationAMap.moveCamera(cu);
  }
}

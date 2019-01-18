package com.hyc.newsmallexcellent.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.PoiItem;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.adapter.viewholder.NearbyJobViewHolder;
import com.hyc.newsmallexcellent.adapter.viewholder.SimpleTextViewHolder;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.adapter.BaseRecycleAdapter;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.bean.JobBean;
import com.hyc.newsmallexcellent.helper.ImageRequestHelper;
import com.hyc.newsmallexcellent.interfaces.MainContact;
import com.hyc.newsmallexcellent.interfaces.MapLocationContract;
import com.hyc.newsmallexcellent.model.UserModel;
import com.hyc.newsmallexcellent.presenter.MainPresenter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContact.IView ,
    LocationSource,
    AMapLocationListener, MapLocationContract.IView{

  @BindView(R.id.nav_view)
  NavigationView navView;
  @BindView(R.id.mv_map)
  MapView mvMap;
  @BindView(R.id.rv_bottom)
  RecyclerView rvBottom;
  @BindView(R.id.dl_main)
  DrawerLayout dlMain;

  //定位
  private LocationSource.OnLocationChangedListener mListener;
  private AMapLocationClient mLocationClient;
  private AMapLocationClientOption mLocationOption;

  private AMap aMap;

  private BaseRecycleAdapter<JobBean.ListBean,NearbyJobViewHolder> adapter;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_map,menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.item_resume) {
      Bundle bundle = new Bundle();
      bundle.putInt("user_id", new UserModel().getCurUserId());
      Intent intent = new Intent(this, ResumeActivity.class);
      startActivity(intent.putExtras(bundle));
    }else if (item.getItemId() == android.R.id.home){
      dlMain.openDrawer(GravityCompat.START);
    }
    return true;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_main);
    super.onCreate(savedInstanceState);
    ImageView headImageView = navView.getHeaderView(0).findViewById(R.id.iv_main_head);
    ImageRequestHelper.loadHeadImage(this, new UserModel().getCurHeadUrl()
        , headImageView);
    navView.setNavigationItemSelectedListener(this::onOptionsItemSelected);
    setNoBackToolBar();
    setHomeResId(R.drawable.ic_more);
    setToolBarTitle("小优兼职");
    mvMap.onCreate(savedInstanceState);
    adapter =  new BaseRecycleAdapter<>(R.layout.item_job_info,NearbyJobViewHolder.class);
    rvBottom.setLayoutManager(new LinearLayoutManager(this));
    rvBottom.setAdapter(adapter);
    presenter.startLocation();
  }

  @Override
  public void initMapView() {
    aMap = mvMap.getMap();
    aMap.getUiSettings().setMyLocationButtonEnabled(true);
    MyLocationStyle locationStyle = new MyLocationStyle();
    locationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
    locationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
    aMap.setMyLocationStyle(locationStyle);
    aMap.setLocationSource(this);
    aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
    aMap.setMyLocationEnabled(true);
  }

  @Override
  protected MainPresenter createPresenter() {
    return new MainPresenter();
  }

  @Override
  public int getCurPager() {
    return 0;
  }

  @Override
  public void loadJobInfo(JobBean jobBean) {
    adapter.setDataList(jobBean.getList());
  }



  /**
   * 加载地图的四个个必要方法
   */
  @Override
  protected void onResume() {
    super.onResume();
    mvMap.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    mvMap.onPause();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mvMap.onSaveInstanceState(outState);
  }

  @Override
  protected void onDestroy() {
    mvMap.onDestroy();
    super.onDestroy();
  }

  @Override
  public void onLocationChanged(AMapLocation aMapLocation) {
    if (aMapLocation != null && mListener != null) {
      if (aMapLocation.getErrorCode() == 0) {
        mListener.onLocationChanged(aMapLocation);
        presenter.fetchRecommendJob(aMapLocation.getLatitude(),aMapLocation.getLongitude());
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

  @Override
  public void loadNearPosition(List<PoiItem> poiItems) {

  }
}

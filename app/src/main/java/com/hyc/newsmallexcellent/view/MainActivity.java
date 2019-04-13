package com.hyc.newsmallexcellent.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
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
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.adapter.viewholder.NearbyJobViewHolder;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.adapter.BaseRecycleAdapter;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.bean.JobBean;
import com.hyc.newsmallexcellent.helper.ImageRequestHelper;
import com.hyc.newsmallexcellent.interfaces.MainContact;
import com.hyc.newsmallexcellent.model.UserModel;
import com.hyc.newsmallexcellent.other.route.RouteActivity;
import com.hyc.newsmallexcellent.presenter.MainPresenter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseMvpActivity<MainPresenter>
    implements MainContact.IView, LocationSource, AMapLocationListener, AMap.OnMarkerClickListener {

  @BindView(R.id.nav_view)
  NavigationView navView;
  @BindView(R.id.mv_map)
  MapView mvMap;
  @BindView(R.id.rv_bottom)
  RecyclerView rvBottom;
  @BindView(R.id.dl_main)
  DrawerLayout dlMain;
  @BindView(R.id.ll_bottom)
  LinearLayout llBottom;
  @BindView(R.id.tv_title)
  TextView tvTitle;
  @BindView(R.id.tv_publisher)
  TextView tvPublisher;
  @BindView(R.id.tv_salary)
  TextView tvSalary;
  @BindView(R.id.tv_need_number)
  TextView tvNeedNumber;
  @BindView(R.id.tv_address)
  TextView tvAddress;
  @BindView(R.id.tv_publish_time)
  TextView tvPublishTime;
  @BindView(R.id.tv_deadline)
  TextView tvDeadline;
  @BindView(R.id.ll_job_info)
  LinearLayout llJobInfo;
  private LatLng latLng;
  private int page = 1;
  private boolean enableLoadMore = false;
  private JobBean.ListBean listBean;

  //定位
  private OnLocationChangedListener mListener;
  private AMapLocationClient mLocationClient;
  private AMapLocationClientOption mLocationOption;
  private BottomSheetBehavior behavior;

  private AMap aMap;

  private List<Marker> markers = new ArrayList<>();

  private BaseRecycleAdapter<JobBean.ListBean, NearbyJobViewHolder> adapter;
  private boolean isTouchMap;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.item_resume) {
      Bundle bundle = new Bundle();
      bundle.putInt("user_id", new UserModel().getCurUserId());
      Intent intent = new Intent(this, AuthenticationActivity.class);
      startActivity(intent.putExtras(bundle));
    } else if (item.getItemId() == android.R.id.home) {
      dlMain.openDrawer(GravityCompat.START);
    } else if (item.getItemId() == R.id.item_search) {
      Intent intent = new Intent(this, QueryJobActivity.class);
      startActivity(intent);
    } else if (item.getItemId() == R.id.item_setting) {
      startActivity(new Intent(this, SeeAuthenticationActivity.class));
    } else if (item.getItemId() == R.id.item_footprint) {
      startActivity(new Intent(this, MyApplyActivity.class));
    } else if (item.getItemId() == R.id.item_wages) {
      startActivity(new Intent(this, ReleasePositionActivity.class));
    } else if (item.getItemId() == R.id.item_message) {
      startActivity(new Intent(this, ReportListActivity.class));
    } else if (item.getItemId() == R.id.item_word_record) {
      startActivity(new Intent(this, WorkRecordActivity.class));
    }
    return true;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);
    ImageView headImageView = navView.getHeaderView(0).findViewById(R.id.iv_main_head);
    ImageRequestHelper.loadHeadImage(this, new UserModel().getCurHeadUrl()
        , headImageView);
    navView.setNavigationItemSelectedListener(this::onOptionsItemSelected);
    setNoBackToolBar();
    setHomeResId(R.drawable.ic_more);
    setToolBarTitle("小优兼职");
    mvMap.onCreate(savedInstanceState);
    adapter = new BaseRecycleAdapter<>(R.layout.item_job_info, NearbyJobViewHolder.class);
    rvBottom.setLayoutManager(new LinearLayoutManager(this));
    rvBottom.setAdapter(adapter);
    presenter.startLocation();
    rvBottom.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (enableLoadMore && latLng != null && !recyclerView.canScrollVertically(1)) {
          enableLoadMore = false;
          presenter.fetchRecommendJob();
        }
      }
    });
    behavior = BottomSheetBehavior.from(llBottom);
    behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override
      public void onStateChanged(@NonNull View view, int i) {
        if (i == BottomSheetBehavior.STATE_EXPANDED) {
          llJobInfo.setVisibility(View.GONE);
        }
      }

      @Override
      public void onSlide(@NonNull View view, float v) {
      }
    });
    adapter.setOnItemClickListener((itemData, view, position) -> {
      behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
      showJobDetail(itemData);
    });
    aMap.setOnMarkerClickListener(this);
    aMap.setOnMapTouchListener(motionEvent -> {
      if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        if (motionEvent.getY() < llJobInfo.getY()) {
          isTouchMap = false;
        } else {
          isTouchMap = true;
        }
      }
      if (motionEvent.getAction() == MotionEvent.ACTION_MOVE && !isTouchMap) {
        if (llJobInfo.getVisibility() == View.VISIBLE) {
          llJobInfo.setVisibility(View.GONE);
        }
      }
    });
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
    aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
    aMap.setMyLocationEnabled(true);
  }

  @Override
  public void loadJobFail() {
    enableLoadMore = true;
  }

  @Override
  public void loadJobMapPoint(Pair<JobBean.ListBean, Bitmap> pair) {
    Marker marker = aMap.addMarker(new MarkerOptions().position(
        new LatLng(Double.parseDouble(pair.first.getLatitude()),
            Double.parseDouble(pair.first.getLongitude())))
        .icon(BitmapDescriptorFactory.fromBitmap(pair.second)));
    marker.setObject(pair.first);
    markers.add(marker);
  }

  @Override
  protected MainPresenter createPresenter() {
    return new MainPresenter();
  }

  @Override
  public int getCurPager() {
    return page;
  }

  @Override
  public LatLng getCurLocation() {
    return latLng;
  }

  @Override
  public void loadJobInfo(JobBean jobBean) {
    page++;
    adapter.appendDataToList(jobBean.getList());
    enableLoadMore = jobBean.isHasNextPage();
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
    markers.clear();
  }

  @Override
  public void onLocationChanged(AMapLocation aMapLocation) {
    if (aMapLocation != null && mListener != null) {
      if (aMapLocation.getErrorCode() == 0) {
        presenter.cacheLocationInfo(aMapLocation);
        mListener.onLocationChanged(aMapLocation);
        latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
        presenter.fetchRecommendJob();
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

  private void showJobDetail(JobBean.ListBean bean) {
    this.listBean = bean;
    tvTitle.setText(bean.getJobTitle());
    tvPublisher.setText("联系人：" + bean.getContact());
    tvAddress.setText("工作地址：" + bean.getIssuePlace());
    tvNeedNumber.setText("招聘人数" + bean.getJobCount());
    tvSalary.setText(bean.getJobSalary() + " " + bean.getJobSalaryUnit());
    tvDeadline.setText("截止时间：" + bean.getClosingDate());
    tvPublishTime.setText("发布时间：" + bean.getReleaseDate());
    movePosition(Double.parseDouble(bean.getLatitude()), Double.parseDouble(bean.getLongitude()));
    llJobInfo.setVisibility(View.VISIBLE);
    llJobInfo.setOnClickListener(view -> JobDetailActivity.start(MainActivity.this, listBean));
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

  private void movePosition(double lat, double lon) {
    CameraPosition cp = aMap.getCameraPosition();
    CameraPosition cpNew = CameraPosition.fromLatLngZoom(new LatLng(lat, lon), cp.zoom);
    CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cpNew);
    aMap.moveCamera(cu);
  }

  @Override
  public boolean onMarkerClick(Marker marker) {
    if (marker.getObject() instanceof JobBean.ListBean) {
      showJobDetail((JobBean.ListBean) marker.getObject());
    }
    return true;
  }
}

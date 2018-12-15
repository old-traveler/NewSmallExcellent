package com.hyc.newsmallexcellent.view;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
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
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.geocoder.RegeocodeRoad;
import com.amap.api.services.geocoder.StreetNumber;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.adapter.PoiKeywordSearchAdapter;
import com.hyc.newsmallexcellent.adapter.viewholder.NearbyAddressViewHolder;
import com.hyc.newsmallexcellent.base.adapter.BaseRecycleAdapter;
import com.hyc.newsmallexcellent.base.bean.PoiAddressBean;
import com.hyc.newsmallexcellent.base.interfaces.OnItemClickListener;
import com.hyc.newsmallexcellent.base.utils.KeyBoardUtils;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends AppCompatActivity implements LocationSource,
        AMapLocationListener, PoiSearch.OnPoiSearchListener{

    @BindView(R.id.map_mv) MapView mapMv;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.map_et_search) EditText navigationEtSearch;
    @BindView(R.id.img_focus_search) ImageView imgFocusSearch;
    @BindView(R.id.poirecycler) RecyclerView poirecycler; // 展示关键字搜索列表
    @BindView(R.id.mark) ImageView mark;
    @BindView(R.id.defalt_recycle) RecyclerView defaltRecycle; // 展示附近搜索热点

    private BaseRecycleAdapter<PoiItem,NearbyAddressViewHolder> adapter;
    private List<PoiItem> poiItemList;
    private final static String CONTENT = "content";

    //定位
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private LatLng point;
    private Marker marker ;
    private AMap navigationAMap;
    //poi搜索
    private String keyWord = "";// 要输入的poi搜索关键字
    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        mapMv.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        initialization();
        eventListener();
    }

    @OnClick({R.id.map_et_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.map_et_search:
                break;
        }
    }

    /**
     * RecyclerView的布局以及监听
     */
    private void eventListener() {
        defaltRecycle.setLayoutManager(new LinearLayoutManager(defaltRecycle.getContext()));
        poirecycler.setLayoutManager(new LinearLayoutManager(poirecycler.getContext()));
        // 当RecyclerView滑动时关闭软键盘
        poirecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                KeyBoardUtils.closeKeyBoard(navigationEtSearch, MapActivity.this);
            }
        });
        initPoiSearch();
    }

    /**
     * 搜索键盘的监听:关键字搜索
     */
    private void initPoiSearch() {
        navigationEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                keyWord = String.valueOf(charSequence);
                if ("".equals(keyWord)) {
                    Toast.makeText(MapActivity.this,"请输入搜索关键字",Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    poirecycler.setVisibility(View.VISIBLE);
                    doSearchQuery();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /***
     * POI搜索变化
     * @param
     */
    private void doSearchQuery() {
        currentPage = 0;
        //不输入城市名称有些地方搜索不到
        query = new PoiSearch.Query(keyWord, "", "北京");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        //这里没有做分页加载了,默认给50条数据
        query.setPageSize(50);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        poiSearch = new PoiSearch(MapActivity.this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    /**
     * 初始化地图
     */
    private void initialization() {
        if (navigationAMap == null) {
            navigationAMap = mapMv.getMap();
            MyLocationStyle locationStyle = new MyLocationStyle();
            locationStyle.strokeColor(Color.BLACK);
            locationStyle.radiusFillColor(Color.argb(100,0,0,180));
            locationStyle.strokeWidth(1.0f);
            navigationAMap.setMyLocationStyle(locationStyle);
            navigationAMap.setLocationSource(this);
            navigationAMap.getUiSettings().setMyLocationButtonEnabled(true);
            navigationAMap.setMyLocationEnabled(true);
            adapter = new BaseRecycleAdapter<>(R.layout.item_showaddress,NearbyAddressViewHolder.class);
            defaltRecycle.setAdapter(adapter);
            defaltRecycle.setItemAnimator(new DefaultItemAnimator());
            adapter.setOnItemClickListener(new OnItemClickListener<PoiItem>() {
                @Override
                public void onItemClick(PoiItem itemData, View view, int position) {
                    if (view.getId() == R.id.tv_title){
                        Toast.makeText(MapActivity.this, "点击标题", Toast.LENGTH_SHORT).show();
                    }else if (view.getId() == R.id.tv_address){
                        Toast.makeText(MapActivity.this, "点击地址", Toast.LENGTH_SHORT).show();
                    }else {
                        navigationEtSearch.setText(itemData.getTitle());
                    }
                }
            });
            //监听地图发生变化之后
            navigationAMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    //发生变化时获取到经纬度传递逆地理编码获取周边数据
                    nearbySearch(cameraPosition.target.latitude,cameraPosition.target.longitude,3000);
                    point = new LatLng(cameraPosition.target.latitude,cameraPosition.target.longitude);
                    marker.remove();//将上一次描绘的mark清除
                    Log.d("WSS", String.valueOf(cameraPosition.target.latitude));
                    Log.d("WSS", String.valueOf(cameraPosition.target.longitude));
                }

                @Override
                public void onCameraChangeFinish(CameraPosition cameraPosition) {
                    //地图发生变化之后描绘mark
                    marker = navigationAMap.addMarker(new com.amap.api.maps.model.MarkerOptions()
                            .position(point)
                            .title("")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.em_unread_count_bg)));
                }
            });
        }
    }

    /***
     * 逆地理编码获取定位后的附近地址
     * @param latitude
     * @param longitude
     * @param distances 设置查找范围
     */
    private void nearbySearch(Double latitude, Double longitude, int distances ) {
        LatLonPoint point = new LatLonPoint(latitude,longitude);
        GeocodeSearch geocodeSearch = new GeocodeSearch(MapActivity.this);
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(point,distances,geocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(regeocodeQuery);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
                String preAdd = "";//地址前缀
                if (1000 == rCode) {
                    final RegeocodeAddress address = regeocodeResult.getRegeocodeAddress();
                    StringBuffer stringBuffer = new StringBuffer();
                    String area = address.getProvince();//省或直辖市
                    String loc = address.getCity();//地级市或直辖市
                    String subLoc = address.getDistrict();//区或县或县级市
                    String ts = address.getTownship();//乡镇
                    String thf = null;//道路
                    List<RegeocodeRoad> regeocodeRoads = address.getRoads();//道路列表
                    if (regeocodeRoads != null && regeocodeRoads.size() > 0) {
                        RegeocodeRoad regeocodeRoad = regeocodeRoads.get(0);
                        if (regeocodeRoad != null) {
                            thf = regeocodeRoad.getName(); }
                    }
                    String subthf = null;//门牌号
                    StreetNumber streetNumber = address.getStreetNumber();
                    if (streetNumber != null) {
                        subthf = streetNumber.getNumber();
                    }
                    String fn = address.getBuilding();//标志性建筑,当道路为null时显示
                    if (area != null) {
                        stringBuffer.append(area);
                        preAdd += area;
                    }
                    if (loc != null && !area.equals(loc)) {
                        stringBuffer.append(loc);
                        preAdd += loc;
                    }
                    if (subLoc != null) {
                        stringBuffer.append(subLoc);
                        preAdd += subLoc;
                    }
                    if (ts != null)
                        stringBuffer.append(ts);
                    if (thf != null)
                        stringBuffer.append(thf);
                    if (subthf != null)
                        stringBuffer.append(subthf);
                    if ((thf == null && subthf == null) && fn != null && !subLoc.equals(fn))
                        stringBuffer.append(fn + "附近");

                    poiItemList = address.getPois();//获取周围兴趣点
                    adapter.setDataList(poiItemList);
                }
            }
            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) { }
        });
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (navigationAMap == null){
            navigationAMap = mapMv.getMap();
        }
        navigationAMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        if (aMapLocation != null && mListener != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);
                Double latitude = aMapLocation.getLatitude();
                Double longitude = aMapLocation.getLongitude();
                nearbySearch(latitude, longitude, 1000);
            } else {
                String errText = "定位失败！！" + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("报TM错", errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this.getApplicationContext());
            mLocationOption = new AMapLocationClientOption();
            mlocationClient.setLocationListener(this);
            mLocationOption.setOnceLocation(true); //只定位一次
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
        mLocationOption = null;
    }

    /**
     * poi搜索监听
     * @param result
     * @param rCode
     */
    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {  // 搜索poi的结果
                if (result.getQuery().equals(query)) {  // 是否是同一条
                    poiResult = result;
                    final ArrayList<PoiAddressBean> data = new ArrayList<PoiAddressBean>();//自己创建的数据集合
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    for(com.amap.api.services.core.PoiItem item : poiItems){
                        //获取经纬度对象
                        LatLonPoint llp = item.getLatLonPoint();
                        double lon = llp.getLongitude();
                        double lat = llp.getLatitude();
                        String title = item.getTitle();
                        String text = item.getSnippet();
                        String provinceName = item.getProvinceName();
                        String cityName = item.getCityName();
                        String adName = item.getAdName();
                        data.add(new PoiAddressBean(String.valueOf(lon), String.valueOf(lat), title, text,provinceName,
                                cityName,adName));
                    }

                    PoiKeywordSearchAdapter poiadapter = new PoiKeywordSearchAdapter(this,data);
                    poirecycler.setAdapter(poiadapter);
                    poirecycler.setItemAnimator(new DefaultItemAnimator());
                    poiadapter.setItemClickListener(new PoiKeywordSearchAdapter.PoiItemClickListener() {
                        @Override
                        public void onItemClick(View view, int pos) {
                            Double poiLatitude = Double.valueOf(data.get(pos).getLatitude()).doubleValue();
                            Double poiLongitude = Double.valueOf(data.get(pos).getLongitude()).doubleValue();
                            //通过经纬度重新再地图获取位置
                            CameraPosition cp = navigationAMap.getCameraPosition();
                            CameraPosition cpNew = CameraPosition.fromLatLngZoom(new LatLng(poiLatitude,poiLongitude),cp.zoom);
                            CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cpNew);
                            navigationAMap.moveCamera(cu);
                            poirecycler.setVisibility(View.GONE);
                            Log.d("ceshi","postion"+pos+"lat"+data.get(pos).getLatitude()+"long:"+data.get(pos).getLongitude());
//                            Log.d("LJJ",poiLatitude.toString());
//                            Log.d("LJJ",poiLongitude.toString());
//                            Log.d("LJJ", data.get(pos).detailAddress);
                            Intent intent = new Intent(MapActivity.this,ReleasePositionActivity.class);
                            String Place = data.get(pos).detailAddress;
                            intent.putExtra("Place",Place);
                            intent.putExtra("Latitude",poiLatitude);
                            intent.putExtra("Longitude",poiLongitude);
                            setResult(RESULT_OK,intent);
                            finish();
                        }
                    });
                }
            } else {
                Toast.makeText(this,"no_result",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,""+rCode,Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * poi搜索监听
     * @param poiItem
     * @param i
     */
    @Override
    public void onPoiItemSearched(com.amap.api.services.core.PoiItem poiItem, int i) {
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
}

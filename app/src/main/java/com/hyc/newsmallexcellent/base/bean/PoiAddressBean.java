package com.hyc.newsmallexcellent.base.bean;

import java.io.Serializable;

/**
 * Created by Alienware on 2017/12/7.
 */

public class PoiAddressBean implements Serializable {

    private double longitude;//经度
    private double latitude;//纬度
    private String text;//信息内容
    private String detailAddress;//详细地址 (搜索的关键字)
    private String province;//省
    private String city;//城市
    private String district;//区域(宝安区)

    public PoiAddressBean(double lon, double lat, String detailAddress, String text, String province, String city, String district){
        this.longitude = lon;
        this.latitude = lat;
        this.text = text;
        this.detailAddress = detailAddress;
        this.province = province;
        this.city = city;
        this.district = district;


    }


    public String getText() {
        return text;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}


package com.hyc.newsmallexcellent.helper;

import com.hyc.newsmallexcellent.net.NetApiService;
import com.hyc.newsmallexcellent.net.RetrofitClient;

public class RequestHelper {

  public static NetApiService getRequestApi() {
    RetrofitClient client = RetrofitClient.getInstance();
    return client.getRetrofit(NetApiService.baseUrl).create(NetApiService.class);
  }
}

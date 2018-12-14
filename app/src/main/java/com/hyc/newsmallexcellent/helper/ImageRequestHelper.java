package com.hyc.newsmallexcellent.helper;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hyc.newsmallexcellent.base.helper.UiHelper;

public class ImageRequestHelper {

  public static void loadImage(Context context, String uri, ImageView imageView) {
    if (TextUtils.isEmpty(uri)){
      return;
    }
    Glide.with(context)
        .load(uri)
        .apply(new RequestOptions().placeholder(UiHelper.getDefaultPlaceholder()))
        .into(imageView);
  }


  public static void loadImage(Context context, Uri uri, ImageView imageView) {
    Glide.with(context)
        .load(uri)
        .apply(new RequestOptions().placeholder(UiHelper.getDefaultPlaceholder()))
        .into(imageView);
    Log.d("YJ",uri.toString());
  }

  public static void loadImage(Context context, int resId, ImageView imageView) {
    Glide.with(context)
        .load(resId)
        .apply(new RequestOptions().placeholder(UiHelper.getDefaultPlaceholder()))
        .into(imageView);
  }

  public static void loadHeadImage(Context context, String url, ImageView imageView) {
    if (TextUtils.isEmpty(url)) {
      return;
    }
    Glide.with(context)
        .load( url)
        .apply(new RequestOptions().circleCrop().placeholder(UiHelper.getDefaultPlaceholder()))
        .into(imageView);
  }


}

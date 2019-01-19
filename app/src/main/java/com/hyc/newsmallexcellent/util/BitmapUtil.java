package com.hyc.newsmallexcellent.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Pair;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.SmallExcellentApplication;
import com.hyc.newsmallexcellent.base.helper.UiHelper;
import com.hyc.newsmallexcellent.bean.JobBean;
import io.reactivex.Observable;
import java.util.List;

public class BitmapUtil {

  public static Observable<Pair<JobBean.ListBean, Bitmap>> loadMapMarker(
      List<JobBean.ListBean> listBeans) {
    return Observable.create(emitter -> {
      Paint paint = new Paint();
      paint.setTextSize(80);
      paint.setAntiAlias(true);
      for (JobBean.ListBean listBean : listBeans) {
        paint.setColor(UiHelper.getColor(R.color.circleColor));
        Bitmap background = BitmapFactory.decodeResource(SmallExcellentApplication.getContext()
            .getResources(), R.drawable.white_map_head).copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(background);
        canvas.drawCircle(background.getWidth() >> 1, 78, 58f, paint);
        paint.setColor(Color.WHITE);
        String title = listBean.getJobCategory().substring(0, 1);
        canvas.drawText(title, (background.getWidth()-paint.measureText(title))/2, 105, paint);
        emitter.onNext(new Pair<>(listBean, background));
      }
      emitter.onComplete();
    });
  }
}

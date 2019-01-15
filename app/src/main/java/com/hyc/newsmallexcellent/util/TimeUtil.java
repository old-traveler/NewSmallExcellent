package com.hyc.newsmallexcellent.util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import java.util.Calendar;

public class TimeUtil {

  public static void showDatePickerDialog(Context context,
      DatePickerDialog.OnDateSetListener listener) {
    Calendar calendar = Calendar.getInstance();
    new DatePickerDialog(context
        , listener
        , calendar.get(Calendar.YEAR)
        , calendar.get(Calendar.MONTH)
        , calendar.get(Calendar.DAY_OF_MONTH)).show();
  }

  public static void showTimePickerDialog(Context context,
      TimePickerDialog.OnTimeSetListener listener) {
    Calendar calendar = Calendar.getInstance();
    new TimePickerDialog(context,
        listener
        , calendar.get(Calendar.HOUR_OF_DAY)
        , calendar.get(Calendar.MINUTE)
        , true).show();
  }
}

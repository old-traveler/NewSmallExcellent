package com.hyc.newsmallexcellent.base.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.SmallExcellentApplication;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class UiHelper {

  public static View inflater(int resId, ViewGroup parent) {
    return LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
  }

  public static int[] getIntegerArrays(int resId) {
    return SmallExcellentApplication.getContext().getResources().getIntArray(resId);
  }

  public static float getTextSize(int resId) {
    return SmallExcellentApplication.getContext().getResources().getDimension(resId);
  }

  public static View inflater(Context context, int resId, ViewGroup parent) {
    return LayoutInflater.from(context).inflate(resId, parent);
  }

  public static String getString(int resId) {
    return SmallExcellentApplication.getContext().getResources().getString(resId);
  }

  public static String getString(int resId, int value) {
    String str = getString(resId);
    return String.format(str, value);
  }

  public static int getColor(int resId) {
    return SmallExcellentApplication.getContext().getResources().getColor(resId);
  }

  public static LinearLayout getLinearLayout(Context context) {
    LinearLayout linearLayout = new LinearLayout(context);
    linearLayout.setOrientation(LinearLayout.VERTICAL);
    return linearLayout;
  }

  public static DisplayMetrics getDisplayMetrics() {
    return SmallExcellentApplication.getContext().getResources().getDisplayMetrics();
  }

  public static String[] getStringArrays(int resId) {
    return SmallExcellentApplication.getContext().getResources().getStringArray(resId);
  }

  public void hideInputWindow(Activity activity) {
    View focus = activity.getCurrentFocus();
    if (focus != null) {
      IBinder focusBinder = focus.getWindowToken();
      InputMethodManager manager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
      if (focusBinder != null && manager != null) {
        manager.hideSoftInputFromWindow(focusBinder, InputMethodManager.HIDE_NOT_ALWAYS);
      }
    }
  }

  public void showInputWindow(Activity activity,EditText mEditText) {
    mEditText.requestFocus();
    InputMethodManager manager = ((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE));
    if (manager != null) {
      manager.showSoftInput(mEditText, 0);
    }
  }

  public static void loadNotEmptyString(EditText editText,String data){
    if (!TextUtils.isEmpty(data)){
      editText.setText(data);
    }
  }



  public static Drawable getDefaultPlaceholder() {
    return SmallExcellentApplication.getContext().getResources().getDrawable(R.drawable.bg_placeholder);
  }




  //public static boolean isLongImage(ImageSizeBean bean) {
  //  float screenScale = DensityUtil.getScreenHeight() * 1.0f / DensityUtil.getScreenWidth();
  //  float imageScale = bean.getHeight() * 1.0f / bean.getWidth();
  //  return imageScale > screenScale;
  //}

  //public static SpannableStringBuilder getWebLinkStyle(CharSequence text, Context context) {
  //  if (text instanceof Spannable) {
  //    int end = text.length();
  //    Spannable sp = (Spannable) text;
  //    URLSpan urls[] = sp.getSpans(0, end, URLSpan.class);
  //    SpannableStringBuilder style = new SpannableStringBuilder(text);
  //    style.clearSpans();
  //    for (URLSpan urlSpan : urls) {
  //      ClickableSpan myURLSpan = new ClickableSpan() {
  //        @Override
  //        public void onClick(@NonNull View view) {
  //          if (urlSpan.getURL().startsWith("http")) {
  //            WebActivity.startWebBrowsing(context, urlSpan.getURL(), "");
  //          } else {
  //            String number = urlSpan.getURL();
  //            if (number.contains(":")) {
  //              number = number.split(":")[1];
  //            }
  //            showBottomSheetDialog(context, number);
  //          }
  //        }
  //      };
  //      style.setSpan(myURLSpan, sp.getSpanStart(urlSpan),
  //          sp.getSpanEnd(urlSpan),
  //          Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
  //    }
  //    return style;
  //  }
  //  return null;
  //}

  //public static void showBottomSheetDialog(Context context, final String number) {
  //  BottomSheetDialog dialog = new BottomSheetDialog(context);
  //  View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_bottom, null);
  //  TextView tvTitle = dialogView.findViewById(R.id.tv_title);
  //  tvTitle.setText(String.format("%s\n可能是一个电话号码或者其他联系方式，你可以", number));
  //  TextView tvCall = dialogView.findViewById(R.id.tv_call);
  //  tvCall.setOnClickListener(view -> {
  //    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
  //    context.startActivity(dialIntent);
  //    dialog.dismiss();
  //  });
  //  TextView tvCopty = dialogView.findViewById(R.id.tv_copy);
  //  tvCopty.setOnClickListener(view -> {
  //    ClipboardManager copy =
  //        (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
  //    copy.setText(number);
  //    dialog.dismiss();
  //    ToastHelper.toast("已复制到剪切板");
  //  });
  //  TextView tvCancel = dialogView.findViewById(R.id.tv_cancel);
  //  tvCancel.setOnClickListener(view -> dialog.dismiss());
  //  dialog.setContentView(dialogView);
  //  dialog.show();
  //}
  //
  //public static void initLinkTextView(TextView textView,Context context){
  //  SpannableStringBuilder spannableStringBuilder = getWebLinkStyle(textView.getText(),context);
  //  if (spannableStringBuilder != null){
  //    textView.setText(spannableStringBuilder);
  //  }
  //}
}

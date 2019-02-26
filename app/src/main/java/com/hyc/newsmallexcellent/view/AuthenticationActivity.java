package com.hyc.newsmallexcellent.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.base.helper.UiHelper;
import com.hyc.newsmallexcellent.helper.ImageRequestHelper;
import com.hyc.newsmallexcellent.interfaces.AuthenticationContact;
import com.hyc.newsmallexcellent.presenter.AuthenticationPresenter;
import com.hyc.newsmallexcellent.util.Constans;
import com.hyc.newsmallexcellent.util.FileUtil;
import com.hyc.newsmallexcellent.util.Glide4Engine;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthenticationActivity extends BaseMvpActivity<AuthenticationPresenter>
    implements AuthenticationContact.View {

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.et_username)
  EditText etUsername;
  @BindView(R.id.authentication_radio_student)
  RadioButton rbStudent;
  @BindView(R.id.authentication_radio_realName)
  RadioButton authenticationRadioRealName;
  @BindView(R.id.radiogroup)
  RadioGroup radiogroup;
  @BindView(R.id.authentication_img_photoOne)
  ImageView ivOne;
  @BindView(R.id.authentication_img_photoTwo)
  ImageView ivTwo;
  @BindView(R.id.authentication_but)
  Button authenticationBut;

  private String imageUrlOne;
  private String imageUrlTwo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_authentication);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);
    setToolBarTitle("认证");
  }

  @Override
  protected AuthenticationPresenter createPresenter() {
    return new AuthenticationPresenter();
  }

  @Override
  public String userName() {
    return etUsername.getText().toString();
  }

  @Override
  public int authenticationType() {
    return rbStudent.isChecked() ? 0 : 1;
  }

  @Override
  public String photoOne() {
    return imageUrlOne;
  }

  @Override
  public String photoTwo() {
    return imageUrlTwo;
  }

  @Override
  public boolean verificationInput() {
    if (TextUtils.isEmpty(userName())){
      ToastHelper.toast("请填写姓名");
    }else if (TextUtils.isEmpty(photoOne())){
      ToastHelper.toast("请选择第一个图片");
    }else if (TextUtils.isEmpty(photoTwo())){
      ToastHelper.toast("请选择第二张图片");
    }else {
      return true;
    }
    return false;
  }

  @Override
  public void onChangeSuccess() {
    ToastHelper.toast("发送成功");
  }

  @Override
  public void requestPermissionSuccess(boolean isFirst) {
    Matisse.from(this)
        .choose(MimeType.ofAll(), false)
        .countable(true)
        .maxSelectable(1)
        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
        .thumbnailScale(0.85f)
        .imageEngine(new Glide4Engine())
        .forResult(isFirst ? Constans.CHOICE_JUDGMENT_ONE : Constans.CHOICE_JUDGMENT_TWO);
  }

  @Override
  public void requestPermissionFail() {

  }

  @OnClick({
      R.id.authentication_img_photoOne, R.id.authentication_img_photoTwo,
      R.id.authentication_but
  })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.authentication_img_photoOne:
        presenter.requestPermission(this, true);
        break;
      case R.id.authentication_img_photoTwo:
        presenter.requestPermission(this, false);
        break;
      case R.id.authentication_but:
        presenter.authentication();
        break;
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (data == null) {
      return;
    }

    if (requestCode == Constans.CHOICE_JUDGMENT_ONE && resultCode == RESULT_OK) {
      crop(true, Matisse.obtainResult(data).get(0));
    } else if (requestCode == Constans.CHOICE_JUDGMENT_TWO && resultCode == RESULT_OK) {
      crop(false, Matisse.obtainResult(data).get(0));
    } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
      ImageRequestHelper.loadImage(this, imageUrlOne, ivOne);
      ImageRequestHelper.loadImage(this, imageUrlTwo, ivTwo);
    }
  }

  public void crop(boolean isFirst, Uri sourceUri) {
    String headImageFilePath = String.format("image%d.jpg", System.currentTimeMillis());
    File file = new File(getCacheDir(), headImageFilePath);
    if (isFirst) {
      imageUrlOne = file.getAbsolutePath();
    } else {
      imageUrlTwo = file.getAbsolutePath();
    }
    Uri destinationUri = Uri.fromFile(file);
    UCrop.Options options = new UCrop.Options();
    options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
    options.setToolbarColor(UiHelper.getColor(R.color.colorPrimary));
    options.setStatusBarColor(UiHelper.getColor(R.color.colorPrimary));
    options.withAspectRatio(85.6f, 54f);
    options.withMaxResultSize(1080, 1080);
    UCrop.of(sourceUri, destinationUri)
        .withOptions(options)
        .start(this);
  }
}

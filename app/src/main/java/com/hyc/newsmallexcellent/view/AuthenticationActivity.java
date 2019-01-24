package com.hyc.newsmallexcellent.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
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
  RadioButton authenticationRadioStudent;
  @BindView(R.id.authentication_radio_realName)
  RadioButton authenticationRadioRealName;
  @BindView(R.id.radiogroup)
  RadioGroup radiogroup;
  @BindView(R.id.authentication_img_photoOne)
  ImageView authenticationImgPhotoOne;
  @BindView(R.id.authentication_img_photoTwo)
  ImageView authenticationImgPhotoTwo;
  @BindView(R.id.authentication_but)
  Button authenticationBut;


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
    return null;
  }

  @Override
  public int authenticationType() {
    return 0;
  }

  @Override
  public String photoOne() {
    return null;
  }

  @Override
  public String photoTwo() {
    return null;
  }

  @Override
  public boolean verificationInput() {
    return false;
  }

  @Override
  public void onChangeSuccess() {

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
        .forResult(isFirst ? Constans.CHOICE_JUDGMENT_ONE:Constans.CHOICE_JUDGMENT_TWO);
  }


  @Override
  public void requestPermissionFail() {

  }

  @OnClick({
      R.id.radiogroup, R.id.authentication_img_photoOne, R.id.authentication_img_photoTwo,
      R.id.authentication_but
  })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.radiogroup:
        break;
      case R.id.authentication_img_photoOne:
        presenter.requestPermission(this,true);
        break;
      case R.id.authentication_img_photoTwo:
        presenter.requestPermission(this,false);
        break;
      case R.id.authentication_but:
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
      //List<Uri> result = Matisse.obtainResult(data);
      //if (result != null && result.size() > 0) {
      //  if (requestCode == Constans.CHOICE_JUDGMENT_TWO) {
      //    FileUtil.getFilePath(this, result.get(0));
      //    ImageRequestHelper.loadImage(this,result.get(0), authenticationImgPhotoOne);
      //  } else if (requestCode == Constans.CHOICE_JUDGMENT_TWO) {
      //    FileUtil.getFilePath(this, result.get(0));
      //    ImageRequestHelper.loadImage(this, UCrop.getOutput(data), authenticationImgPhotoTwo);
      //  }
      //}
    }else if (requestCode == Constans.CHOICE_JUDGMENT_TWO && resultCode == RESULT_OK){

    }
  }
}

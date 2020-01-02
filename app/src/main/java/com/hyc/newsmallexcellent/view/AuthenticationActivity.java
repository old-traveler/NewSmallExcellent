package com.hyc.newsmallexcellent.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.TextView;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.base.helper.UiHelper;
import com.hyc.newsmallexcellent.base.interfaces.OnItemClickListener;
import com.hyc.newsmallexcellent.helper.ImageRequestHelper;
import com.hyc.newsmallexcellent.helper.SpCacheHelper;
import com.hyc.newsmallexcellent.interfaces.AuthenticationContact;
import com.hyc.newsmallexcellent.interfaces.JobsClassificationContract;
import com.hyc.newsmallexcellent.model.UserModel;
import com.hyc.newsmallexcellent.presenter.AuthenticationPresenter;
import com.hyc.newsmallexcellent.util.BottomSelectDialogUtil;
import com.hyc.newsmallexcellent.util.Constans;
import com.hyc.newsmallexcellent.util.FileUtil;
import com.hyc.newsmallexcellent.util.Glide4Engine;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;
import java.util.Arrays;
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
  @BindView(R.id.fl_show)
  FrameLayout flShow;
  @BindView(R.id.tv_user_type)
  TextView tvUserType;
  @BindView(R.id.fl_image)
  LinearLayout flImage;
  private int userType = 0;

  private String imageUrlOne;
  private String imageUrlTwo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_authentication);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);
    setToolBarTitle("认证");
    initView();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    if (userType >= 1 && userType <= 3) {
      getMenuInflater().inflate(R.menu.menu_authentication, menu);
      return true;
    }
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.item_more) {
      showMoreSelect();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void showMoreSelect() {
    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
    List<String> strings;
    if (userType == 2||userType==1) {
      strings = Arrays.asList("发布职位", "查看申请", "我的发布");
    } else {
      strings = Arrays.asList("发布职位", "查看申请", "我的发布", "查看工作分类", "查看认证信息", "查看举报信息");
    }
    BottomSelectDialogUtil.showSimpleListTextDialog(bottomSheetDialog, strings,
        (itemData, view, position) -> {
          bottomSheetDialog.dismiss();
          if (position == 0) {
            startActivity(new Intent(AuthenticationActivity.this, ReleasePositionActivity.class));
          } else if (position == 1) {
            MyApplyActivity.start(AuthenticationActivity.this, true);
          } else if (position == 2){
            PersonalPublishActivity.start(AuthenticationActivity.this , new UserModel().getCurUserId());
          } else if (position == 3) {
            startActivity(new Intent(AuthenticationActivity.this,
                JobsClassificationActivity.class));
          } else if (position == 4) {
            startActivity(
                new Intent(AuthenticationActivity.this, SeeAuthenticationActivity.class));
          } else if (position == 5) {
            startActivity(
                new Intent(AuthenticationActivity.this, ReportListActivity.class).putExtra(
                    "isMaster", true));
          }
          if (position < 5) {
            finish();
          }
        });
  }

  private void initView() {
    userType = SpCacheHelper.getInt("isAuthentication", 0);
    if (new UserModel().getCurUserId() == 2) {
      userType = 3;
    }
    if (userType > 0 && userType <= 3) {
      showUserType(userType);
    } else {
      ivOne.setImageResource(R.drawable.xueshengzheng1);
      ivTwo.setImageResource(R.drawable.xueshengzheng2);
      flShow.setVisibility(View.GONE);
    }
  }

  private void showUserType(int userType) {
    etUsername.setVisibility(View.GONE);
    radiogroup.setVisibility(View.GONE);
    flImage.setVisibility(View.GONE);
    authenticationBut.setVisibility(View.GONE);
    if (userType == 1) {
      tvUserType.setText("学生认证");
    } else if (userType == 2) {
      tvUserType.setText("实名认证");
    } else if (userType == 3) {
      tvUserType.setText("管理员认证");
    }
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
    if (TextUtils.isEmpty(userName())) {
      ToastHelper.toast("请填写姓名");
    } else if (TextUtils.isEmpty(photoOne())) {
      ToastHelper.toast("请选择第一个图片");
    } else if (TextUtils.isEmpty(photoTwo())) {
      ToastHelper.toast("请选择第二张图片");
    } else {
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
      R.id.authentication_but,R.id.authentication_radio_realName,R.id.authentication_radio_student
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
      case R.id.authentication_radio_student:
        ivOne.setImageResource(R.drawable.xueshengzheng1);
        ivTwo.setImageResource(R.drawable.xueshengzheng2);
        break;
      case R.id.authentication_radio_realName:
        ivOne.setImageResource(R.drawable.shenfenzheng1);
        ivTwo.setImageResource(R.drawable.shenfenzheng2);
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

package com.hyc.newsmallexcellent.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.base.helper.UiHelper;
import com.hyc.newsmallexcellent.bean.ResumeInfoBean;
import com.hyc.newsmallexcellent.helper.ImageRequestHelper;
import com.hyc.newsmallexcellent.interfaces.ResumeContract;
import com.hyc.newsmallexcellent.presenter.ResumePresenter;
import com.hyc.newsmallexcellent.util.Constans;
import com.hyc.newsmallexcellent.util.Glide4Engine;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumeActivity extends BaseMvpActivity<ResumePresenter>
    implements ResumeContract.IView {

  @BindView(R.id.tv_error)
  TextView tvError;
  @BindView(R.id.iv_resume_head)
  ImageView ivResumeHead;
  @BindView(R.id.et_resume_name)
  EditText etResumeName;
  @BindView(R.id.et_resume_phone)
  EditText etResumePhone;
  @BindView(R.id.et_sex)
  EditText etSex;
  @BindView(R.id.et_height)
  EditText etHeight;
  @BindView(R.id.et_age)
  EditText etAge;
  @BindView(R.id.et_school)
  EditText etSchool;
  @BindView(R.id.et_address)
  EditText etAddress;
  @BindView(R.id.et_work_express)
  EditText etWorkExpress;
  @BindView(R.id.et_intent)
  EditText etIntent;
  @BindView(R.id.et_introduce)
  EditText etIntroduce;
  @BindView(R.id.sv_content)
  ScrollView svContent;

  private MenuItem menuItem;
  private String imageUrl;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_resume);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);
    presenter.fetchResumeData();
    setToolBarTitle("简历");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    if (presenter.isEnableChangeResume()) {
      getMenuInflater().inflate(R.menu.menu_resume, menu);
    }
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.item_save) {
      if (item.getTitle().equals("保存")) {
        presenter.changeResumeData();
      } else {
        item.setTitle("保存");
        enableEditResume();
      }
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected ResumePresenter createPresenter() {
    return new ResumePresenter();
  }

  @Override
  public int getResumeUserId() {
    return getDataBundle().getInt("user_id");
  }

  @Override
  public void onSuccessGetResume(ResumeInfoBean infoBean) {
    tvError.setVisibility(View.GONE);
    svContent.setVisibility(View.VISIBLE);
    UiHelper.loadNotEmptyString(etAddress, infoBean.getAddress());
    UiHelper.loadNotEmptyString(etAge, String.valueOf(infoBean.getAge()));
    UiHelper.loadNotEmptyString(etHeight, String.valueOf(infoBean.getHeight()));
    UiHelper.loadNotEmptyString(etIntent, infoBean.getIntention());
    UiHelper.loadNotEmptyString(etSchool, infoBean.getSchool());
    UiHelper.loadNotEmptyString(etSex, infoBean.getGender() == 1 ? "女" : "男");
    UiHelper.loadNotEmptyString(etWorkExpress, infoBean.getExperience());
    UiHelper.loadNotEmptyString(etIntroduce, infoBean.getIntroduction());
    UiHelper.loadNotEmptyString(etResumeName, infoBean.getUserName());
    UiHelper.loadNotEmptyString(etResumePhone, infoBean.getTelephone());
    ImageRequestHelper.loadImage(this, infoBean.getHeadPhoto(), ivResumeHead);
  }

  @Override
  public void onFailGetResume() {
    tvError.setVisibility(View.VISIBLE);
    tvError.setOnClickListener(view -> {
      presenter.fetchResumeData();
    });
  }

  @Override
  public Map<String, Object> getResumeInfo() {
    Map<String, Object> map = new HashMap<>();
    map.put("userName", etResumeName.getText());
    map.put("headPhoto",imageUrl);
    if (etSex.getText().toString().equals("女")){
      map.put("gender",1);
    }else {
      map.put("gender",0);
    }
    map.put("age",Integer.parseInt(etAge.getText().toString()));
    map.put("height",Integer.parseInt(etHeight.getText().toString()));
    map.put("address",etAddress.getText().toString());
    map.put("intention",etIntent.getText().toString());
    map.put("introduction",etIntroduce.getText().toString());
    map.put("experience",etWorkExpress.getText().toString());
    map.put("telephone",etResumePhone.getText().toString());
    map.put("school",etSchool.getText().toString());
    return map;
  }

  @Override
  public void enableEditResume() {
    etAddress.setEnabled(true);
    etAge.setEnabled(true);
    etHeight.setEnabled(true);
    etIntent.setEnabled(true);
    etIntroduce.setEnabled(true);
    etSchool.setEnabled(true);
    etSex.setEnabled(true);
    etWorkExpress.setEnabled(true);
    etResumePhone.setEnabled(true);
    etResumeName.setEnabled(true);
  }

  @Override
  public void requestPermissionSuccess() {
    Matisse.from(this)
        .choose(MimeType.ofAll(), false)
        .countable(true)
        .maxSelectable(1)
        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
        .thumbnailScale(0.85f)
        .imageEngine(new Glide4Engine())
        .forResult(Constans.REQUEST_CODE_CHOOSE);
  }

  @Override
  public void requestPermissionFail() {

  }

  @Override
  public void onSuccessChangeResume() {
    ToastHelper.toast("保存成功");
  }

  @OnClick(R.id.iv_resume_head)
  public void onViewClicked() {
    if (etResumeName.isEnabled()){
      presenter.requestPermission(this);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (data == null){
      return;
    }
    if (requestCode == Constans.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
      List<Uri> result = Matisse.obtainResult(data);
      if (result != null && result.size() > 0) {
        startCropImage(result.get(0));
      }
    }else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP ) {
      ImageRequestHelper.loadImage(this, UCrop.getOutput(data), ivResumeHead);
    }
  }

  public void startCropImage(Uri sourceUri) {
    String headImageFilePath = String.format("head_image%d.jpg",System.currentTimeMillis());
    File file = new File(getCacheDir(), headImageFilePath);
    imageUrl = file.getAbsolutePath();
    Uri destinationUri = Uri.fromFile(file);
    UCrop.Options options = new UCrop.Options();
    options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
    options.setToolbarColor(UiHelper.getColor(R.color.colorPrimary));
    options.setStatusBarColor(UiHelper.getColor(R.color.colorPrimary));
    options.withAspectRatio(9, 9);
    options.withMaxResultSize(1080, 1080);
    UCrop.of(sourceUri, destinationUri)
        .withOptions(options)
        .start(this);
  }
}

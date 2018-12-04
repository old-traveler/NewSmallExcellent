package com.hyc.newsmallexcellent.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.helper.UiHelper;
import com.hyc.newsmallexcellent.bean.ResumeInfoBean;
import com.hyc.newsmallexcellent.helper.ImageRequestHelper;
import com.hyc.newsmallexcellent.interfaces.ResumeContract;
import com.hyc.newsmallexcellent.presenter.ResumePresenter;
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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_resume);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);
    presenter.fetchResumeData();
    setToolBarTitle("简历");
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
    UiHelper.loadNotEmptyString(etAddress,infoBean.getAddress());
    UiHelper.loadNotEmptyString(etAge, String.valueOf(infoBean.getAge()));
    UiHelper.loadNotEmptyString(etHeight, String.valueOf(infoBean.getHeight()));
    UiHelper.loadNotEmptyString(etIntent,infoBean.getIntention());
    UiHelper.loadNotEmptyString(etSchool,infoBean.getSchool());
    UiHelper.loadNotEmptyString(etSex, String.valueOf(infoBean.getGender()));
    UiHelper.loadNotEmptyString(etWorkExpress,infoBean.getExperience());
    UiHelper.loadNotEmptyString(etIntroduce,infoBean.getIntroduction());
    UiHelper.loadNotEmptyString(etResumeName,infoBean.getUserName());
    UiHelper.loadNotEmptyString(etResumePhone,infoBean.getTelephone());
    ImageRequestHelper.loadImage(this,infoBean.getHeadPhoto(),ivResumeHead);
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
    return null;
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

}

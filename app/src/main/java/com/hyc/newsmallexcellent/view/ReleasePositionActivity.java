package com.hyc.newsmallexcellent.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.interfaces.ReleasePositionContract;
import com.hyc.newsmallexcellent.presenter.ReleasePositionPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReleasePositionActivity extends BaseMvpActivity<ReleasePositionPresenter> implements ReleasePositionContract.View {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.et_name) EditText etName;
    @BindView(R.id.et_work_name) EditText etWorkName;
    @BindView(R.id.et_work_content) EditText etWorkContent;
    @BindView(R.id.et_work_category) EditText etWorkCategory;
    @BindView(R.id.et_work_salary) EditText etWorkSalary;
    @BindView(R.id.et_salary_company) EditText etSalaryCompany;
    @BindView(R.id.et_position_number) EditText etPositionNumber;
    @BindView(R.id.et_work_time_slot) EditText etWorkTimeSlot;
    @BindView(R.id.et_work_heaven) EditText etWorkHeaven;
    @BindView(R.id.et_contact) EditText etContact;
    @BindView(R.id.et_deadline) EditText etDeadline;
    @BindView(R.id.et_contact_phone) EditText etContactPhone;
    @BindView(R.id.et_work_place) EditText etWorkPlace;
    @BindView(R.id.btn_release) Button btnRelease;

    private final static int REQUESTCODE = 1; // 返回的结果码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_release_position);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setToolBarTitle("职位发布");
    }

    @Override
    protected ReleasePositionPresenter createPresenter() {
        return new ReleasePositionPresenter();
    }

    @Override
    public String getJobTitle() {
        return etWorkName.getText().toString();
    }

    @Override
    public String getJobDescribe() {
        return etWorkContent.getText().toString();
    }

    @Override
    public String getJobCategory() {
        return etWorkCategory.getText().toString();
    }

    @Override
    public String getJobSalary() {
        return etWorkSalary.getText().toString();
    }

    @Override
    public String getJobSalaryUnit() {
        return etSalaryCompany.getText().toString();
    }

    @Override
    public int getJobCount() {
        return etPositionNumber.getText().length();
    }

    @Override
    public String getWorkingHours() {
        return etWorkTimeSlot.getText().toString();
    }

    @Override
    public String getWorkingDays() {
        return etWorkHeaven.getText().toString();
    }

    @Override
    public String getContact() {
        return etContact.getText().toString();
    }

    @Override
    public String getTelephone() {
        return etDeadline.getText().toString();
    }

    @Override
    public String getCDate() {
        return etContactPhone.getText().toString();
    }

    @Override
    public String getIssuePlace() {
        return etWorkPlace.getText().toString();
    }

    @Override
    public boolean verificationInput() {
        if (TextUtils.isEmpty(getJobTitle()) || TextUtils.isEmpty(getJobDescribe()) || TextUtils.isEmpty(getJobCategory())
                || TextUtils.isEmpty(getJobSalary()) || TextUtils.isEmpty(getJobSalaryUnit()) || !etPositionNumber.equals("")
                || TextUtils.isEmpty(getWorkingHours()) || TextUtils.isEmpty(getWorkingDays()) || TextUtils.isEmpty(getContact())
                || TextUtils.isEmpty(getTelephone()) || TextUtils.isEmpty(getCDate()) || TextUtils.isEmpty(getIssuePlace())) {
            ToastHelper.toast("以上填写不能有任何一项为空");
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void onReleaseSuccess() {

    }

    @OnClick({R.id.btn_release,R.id.et_work_place})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_release:
                presenter.releasePosition();
                break;
            case R.id.et_work_place:
                presenter.accessRequest(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1 && resultCode == RESULT_OK) {
                String a = data.getStringExtra("Place");
                etWorkPlace.setText(a);
        }
    }
}

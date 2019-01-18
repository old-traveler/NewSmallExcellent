package com.hyc.newsmallexcellent.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import android.widget.TextView;
import com.amap.api.maps.model.LatLng;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.base.helper.UiHelper;
import com.hyc.newsmallexcellent.interfaces.ReleasePositionContract;
import com.hyc.newsmallexcellent.presenter.ReleasePositionPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.hyc.newsmallexcellent.util.BottomSelectDialogUtil;
import com.hyc.newsmallexcellent.util.TimeUtil;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReleasePositionActivity extends BaseMvpActivity<ReleasePositionPresenter>
    implements ReleasePositionContract.View {

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.et_work_name)
  EditText etWorkName;
  @BindView(R.id.et_work_content)
  EditText etWorkContent;
  @BindView(R.id.et_work_category)
  TextView etWorkCategory;
  @BindView(R.id.et_work_salary)
  EditText etWorkSalary;
  @BindView(R.id.et_position_number)
  EditText etPositionNumber;
  @BindView(R.id.et_work_time_slot)
  EditText etWorkTimeSlot;
  @BindView(R.id.et_work_heaven)
  EditText etWorkHeaven;
  @BindView(R.id.et_contact)
  EditText etContact;
  @BindView(R.id.tv_deadline)
  TextView tvDeadline;
  @BindView(R.id.et_contact_phone)
  EditText etContactPhone;
  @BindView(R.id.et_work_place)
  TextView etWorkPlace;
  @BindView(R.id.tv_format)
  TextView tvFormat;

  private final static int REQUEST_CODE = 1001; // 返回的结果码
  private String cityCode;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_release_position);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);
    setToolBarTitle("职位发布");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_publish_job,menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.item_publish){
      presenter.releasePosition();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected ReleasePositionPresenter createPresenter() {
    return new ReleasePositionPresenter();
  }

  @Override
  public Map<String, Object> getJobInfo() {
    Map<String,Object> map = new HashMap<>();
    map.put("jobTitle",etWorkName.getText().toString());
    map.put("jobDescribe",etWorkContent.getText().toString());
    map.put("jobCategory",etWorkCategory.getText().toString());
    map.put("jobSalary",Integer.parseInt(etWorkSalary.getText().toString()));
    map.put("jobSalaryUnit",tvFormat.getText().toString());
    map.put("jobCount",Integer.parseInt(etPositionNumber.getText().toString()));
    map.put("workingHours",etWorkTimeSlot.getText().toString());
    map.put("workingDays",Integer.parseInt(etWorkHeaven.getText().toString()));
    map.put("city",cityCode);
    map.put("contact",etContact.getText().toString());
    map.put("telephone",etContactPhone.getText().toString());
    map.put("cDate",tvDeadline.getText().toString()+":00");
    LatLng latLng = (LatLng) etWorkPlace.getTag();
    map.put("issuePlace",etWorkPlace.getText().toString());
    map.put("latitude",String.valueOf(latLng.latitude));
    map.put("longitude",String.valueOf(latLng.longitude));
    map.put("isAuthentication",0);
    return map;
  }


  @Override
  public boolean verificationInput() {
    if (TextUtils.isEmpty(etWorkName.getText().toString())){
      ToastHelper.toast("工作名称为空");
    }else if (TextUtils.isEmpty(etWorkContent.getText().toString())){
      ToastHelper.toast("工作内容不能为空");
    }else if (etWorkCategory.getText().toString().equals("点击选择")){
      ToastHelper.toast("请选择工作类别");
    }else if (TextUtils.isEmpty(etWorkSalary.getText().toString())){
      ToastHelper.toast("工作薪水不能为空");
    }else if (tvFormat.getText().toString().equals("选择薪资格式")){
      ToastHelper.toast("请选择薪资格式");
    }else if (TextUtils.isEmpty(etPositionNumber.getText().toString())){
      ToastHelper.toast("职位数量不能为空");
    }else if (TextUtils.isEmpty(etWorkTimeSlot.getText().toString())){
      ToastHelper.toast("工作时段不能为空");
    }else if (TextUtils.isEmpty((etWorkHeaven.getText().toString()))){
      ToastHelper.toast("工作天数不能为空");
    }else if (TextUtils.isEmpty(etContact.getText().toString())){
      ToastHelper.toast("联系人员不能为空");
    }else if (tvDeadline.getText().toString().equals("点击选择")){
      ToastHelper.toast("请选择截止时间");
    }else if (TextUtils.isEmpty(etContactPhone.getText().toString())){
      ToastHelper.toast("请填写联系电话");
    }else if (etWorkPlace.getText().toString().equals("点击选择地址")){
      ToastHelper.toast("请选择工作地址");
    }else {
      return true;
    }
    return false;
  }

  @Override
  public void onReleaseSuccess() {
    ToastHelper.toast("发布成功");
  }

  @Override
  public void loadJobCategory(List<String> data) {
    BottomSheetDialog dialog = new BottomSheetDialog(this);
    BottomSelectDialogUtil.showSimpleListTextDialog(dialog,data, (itemData, view, position) -> {
      dialog.dismiss();
      etWorkCategory.setText(itemData);
    });
  }

  @OnClick({ R.id.et_work_place, R.id.tv_deadline ,R.id.et_work_category ,R.id.tv_format })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.et_work_place:
        presenter.accessRequest(this);
        break;
      case R.id.tv_deadline:
        selectDate();
        break;
      case R.id.et_work_category:
        presenter.fetchJobCategory();
        break;
      case R.id.tv_format:
        showSalaryFormat();
        break;
    }
  }

  private void showSalaryFormat() {
    BottomSheetDialog dialog = new BottomSheetDialog(this);
    BottomSelectDialogUtil.showSimpleListTextDialog(dialog,Arrays.asList(new String[]{"元/小时","元/日","元/月"}), (itemData, view, position) -> {
      dialog.dismiss();
      tvFormat.setText(itemData);
    });
  }

  private void selectDate() {
    TimeUtil.showDatePickerDialog(this,
        (datePicker, year, month, day) -> TimeUtil.showTimePickerDialog(
            ReleasePositionActivity.this,
            (timePicker, i, i1) -> tvDeadline.setText(
                String.format(UiHelper.getString(R.string.date_select_format), year, month+1, day, i, i1))));
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
      String address = data.getStringExtra("address");
      etWorkPlace.setText(address);
      cityCode = data.getStringExtra("city");
      etWorkPlace.setTag(new LatLng(data.getDoubleExtra("lat",0),data.getDoubleExtra("lon",0)));
    }
  }
}

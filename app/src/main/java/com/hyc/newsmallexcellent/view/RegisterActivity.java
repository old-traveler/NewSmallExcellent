package com.hyc.newsmallexcellent.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.interfaces.RegisterContract;
import com.hyc.newsmallexcellent.presenter.RegisterPresenter;
import java.lang.ref.WeakReference;

public class RegisterActivity extends BaseMvpActivity<RegisterPresenter> implements
    RegisterContract.IRegisterView {

  @BindView(R.id.et_username)
  EditText etUsername;
  @BindView(R.id.et_password)
  EditText etPassword;
  @BindView(R.id.et_config_password)
  EditText etConfigPassword;
  @BindView(R.id.et_verification)
  EditText etVerification;
  @BindView(R.id.btn_send_code)
  Button btnSendCode;

  private Handler handler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_register);
    super.onCreate(savedInstanceState);
    setToolBarTitle("注册");
  }

  @Override
  protected RegisterPresenter createPresenter() {
    return new RegisterPresenter();
  }

  @Override
  public String getUsername() {
    return etUsername.getText().toString();
  }

  @Override
  public String getPassword() {
    return etPassword.getText().toString();
  }

  @Override
  public String getVerificationCode() {
    return etVerification.getText().toString();
  }

  @Override
  public void onSendVerificationCodeSuccess() {
    btnSendCode.setClickable(false);
    resendTiming();
  }

  @Override
  public void onRegisterSuccess() {
    ToastHelper.toast("注册成功");
    finish();
  }

  @Override
  public boolean verificationInput() {
    if (TextUtils.isEmpty(etUsername.getText().toString())) {
      ToastHelper.toast("账号不能为空");
    } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
      ToastHelper.toast("密码不能为空");
    } else if (!etPassword.getText().toString().equals(etConfigPassword.getText().toString())) {
      ToastHelper.toast("两次密码输入不一致");
    } else if (TextUtils.isEmpty(etVerification.getText().toString())) {
      ToastHelper.toast("请输入验证码");
    } else {
      return true;
    }
    return false;
  }

  @OnClick({ R.id.btn_send_code, R.id.btn_register })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.btn_send_code:
        presenter.sendVerificationCode();
        break;
      case R.id.btn_register:
        presenter.registerNewUser();
        break;
    }
  }

  private void resendTiming(){
    if (handler == null){
      handler = new TimingHandler(btnSendCode);
    }
    for (int i = 0; i < 60; i++) {
      Message message = Message.obtain();
      message.what = 2;
      message.obj = i;
      handler.sendMessageDelayed(message,i * 1000);
    }
  }

  static class TimingHandler extends Handler{

    private WeakReference<Button> buttonWeakReference;
    TimingHandler(Button button){ buttonWeakReference = new WeakReference<>(button); }

    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (buttonWeakReference.get() == null){
        return;
      }
      Button button = buttonWeakReference.get();
      int time = 59 - (int)msg.obj;
      if (time > 0){
        button.setText(String.format("%d秒后重试",time));
      }else {
        button.setClickable(true);
        button.setText("发送验证码");
      }
    }
  }
}

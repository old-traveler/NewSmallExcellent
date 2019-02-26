package com.hyc.newsmallexcellent.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.interfaces.LoginContract;
import com.hyc.newsmallexcellent.presenter.LoginPresenter;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {

  @BindView(R.id.et_username)
  EditText etUsername;
  @BindView(R.id.et_password)
  EditText etPassword;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_login);
    super.onCreate(savedInstanceState);
    setToolBarTitle("登录");
    if (presenter.isLogin()){
      startActivity(new Intent(this,MainActivity.class));
      finish();
    }
  }

  @Override
  protected LoginPresenter createPresenter() {
    return new LoginPresenter();
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
  public boolean verificationInput() {
    if (TextUtils.isEmpty(getUsername())){
      ToastHelper.toast("请输入手机号");
    }else if (TextUtils.isEmpty(getPassword())){
      ToastHelper.toast("请输入密码");
    }else {
      return true;
    }
    return false;
  }

  @Override
  public void onLoginSuccess() {
    startActivity(new Intent(this,MainActivity.class));
    finish();
  }

  @OnClick({ R.id.tv_register, R.id.btn_login })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.tv_register:
        startActivity(new Intent(this,RegisterActivity.class));
        break;
      case R.id.btn_login:
        presenter.login();
        break;
    }
  }
}

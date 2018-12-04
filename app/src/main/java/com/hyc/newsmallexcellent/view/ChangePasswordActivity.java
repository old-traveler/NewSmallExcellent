package com.hyc.newsmallexcellent.view;
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
import com.hyc.newsmallexcellent.interfaces.ChangePasswordContract;
import com.hyc.newsmallexcellent.presenter.ChangePasswordPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class ChangePasswordActivity extends BaseMvpActivity<ChangePasswordPresenter> implements ChangePasswordContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_Old_password_change_password)
    EditText etOldPasswordChangePassword;
    @BindView(R.id.et_new_password_change_password)
    EditText etNewPasswordChangePassword;
    @BindView(R.id.et_confirm_password_config_password)
    EditText etConfirmPasswordConfigPassword;
    @BindView(R.id.btn_change_password)
    Button btnChangePassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setToolBarTitle("修改密码");
        Log.d("TAG","123123");
    }

    @Override
    protected ChangePasswordPresenter createPresenter() {
        return new ChangePasswordPresenter();
    }

    @Override
    public String getOldPassword() {
        return etOldPasswordChangePassword.getText().toString();
    }

    @Override
    public String getNewPassword() {
        return etNewPasswordChangePassword.getText().toString();
    }

    @Override
    public boolean verificationInput() {
        if (TextUtils.isEmpty(getOldPassword())){
            ToastHelper.toast("旧密码不能为空");
        }else if (TextUtils.isEmpty(getNewPassword())){
            ToastHelper.toast("新密码不能为空");
        }else if (!etConfirmPasswordConfigPassword.getText().toString().equals(getNewPassword())){
            ToastHelper.toast("确认密码不一致,请重新输入");
        }else {
            return true;
        }
        return false;
    }

    @Override
    public void onChangeSuccess() {

    }

    @OnClick(R.id.btn_change_password)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_change_password:
                presenter.change();
                break;
        }
    }
}

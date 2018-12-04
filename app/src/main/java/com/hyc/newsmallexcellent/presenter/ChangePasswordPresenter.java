package com.hyc.newsmallexcellent.presenter;
import com.hyc.newsmallexcellent.base.BasePresenter;
import com.hyc.newsmallexcellent.base.rx.BaseRequestConsumer;
import com.hyc.newsmallexcellent.interfaces.ChangePasswordContract;
import com.hyc.newsmallexcellent.model.UserModel;

public class ChangePasswordPresenter extends BasePresenter<ChangePasswordContract.View>
        implements ChangePasswordContract.ChangePresenter{

    private UserModel userModel = new UserModel();

    @Override
    public void change() {
        if (mvpView.verificationInput()){
            addDisposable(userModel.changePassword(userModel.getCurUserId(), mvpView.getOldPassword(), mvpView.getNewPassword(),
                    new BaseRequestConsumer<Object>(mvpView) {
                        @Override
                        protected void onRequestSuccess(Object data) {
                            mvpView.onChangeSuccess();
                        }
                    },mvpView));
        }
    }
}

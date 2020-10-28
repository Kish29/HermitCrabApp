package com.kish2.hermitcrabapp.presenter.activities;

import com.kish2.hermitcrabapp.model.IUserModel;
import com.kish2.hermitcrabapp.model.impl.UserModelImpl;
import com.kish2.hermitcrabapp.presenter.ABasePresenter;
import com.kish2.hermitcrabapp.presenter.UserPresenter;
import com.kish2.hermitcrabapp.utils.App;
import com.kish2.hermitcrabapp.utils.AppAndJSONUtil;
import com.kish2.hermitcrabapp.utils.view.KZDialogUtil;
import com.kish2.hermitcrabapp.utils.view.ToastUtil;
import com.kish2.hermitcrabapp.view.activities.UserProfileActivity;

import org.jetbrains.annotations.NotNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfilePresenter extends ABasePresenter<UserProfileActivity> implements UserPresenter {

    private UserProfileActivity mView;
    private IUserModel mModel;

    public UserProfilePresenter(UserProfileActivity mView) {
        this.mView = mView;
        this.mModel = new UserModelImpl();
    }

    @Override
    public void onActivityPause() {

    }

    @Override
    public void onActivityCreate() {

    }

    @Override
    public void onActivityResume() {

    }

    @Override
    public void onActivityDestroy() {

    }

    @Override
    public void onActivityStart() {

    }

    @Override
    public void onActivityStop() {

    }

    @Override
    public void loadDataFromServer() {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void onServerError(Object objects) {
        mWaitDialog.doDismiss();
    }

    @Override
    public void onServerSuccess(Object objects) {
        mWaitDialog.doDismiss();
        if (mView != null) {
            mView.refreshData();
        }
    }

    @Override
    public void changeAvatar() {

    }

    @Override
    public void updateUsername(String username) {
        mWaitDialog = KZDialogUtil.IOS_LIGHT_WAIT_NO_STOP_DIALOG(mView);
        mWaitDialog.show();
        Call<ResponseBody> call = mModel.usernameUpdate(App.USER.getUid(), username);
        dataUpdate(call);
    }

    @Override
    public void updatePassword(String password) {
        mWaitDialog = KZDialogUtil.IOS_LIGHT_WAIT_NO_STOP_DIALOG(mView);
        mWaitDialog.show();
        Call<ResponseBody> call = mModel.passwordUpdate(App.USER.getUid(), password);
        dataUpdate(call);
    }

    @Override
    public void dataUpdate(Call<ResponseBody> call) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.code() == 200) {
                    handleResponse(mView, AppAndJSONUtil.DO_JSON_TYPE.SET_USER, response);
                } else onFailure(call, new Throwable());
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                onServerError(null);
            }
        });
    }

    @Override
    public void updateEmail() {

    }

    @Override
    public void updateMobile() {

    }

    @Override
    public void updateBindInfo() {

    }

    @Override
    public void bindStudentInfo() {

    }
}

package com.kish2.hermitcrabapp.presenter.activities;

import android.content.Intent;
import android.os.Handler;

import com.kish2.hermitcrabapp.bean.HermitCrabBitMaps;
import com.kish2.hermitcrabapp.model.IUserModel;
import com.kish2.hermitcrabapp.model.impl.UserModelImpl;
import com.kish2.hermitcrabapp.presenter.ABasePresenter;
import com.kish2.hermitcrabapp.utils.AppAndJSONUtil;
import com.kish2.hermitcrabapp.utils.security.LicenseEncryption;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;
import com.kish2.hermitcrabapp.utils.view.ToastUtil;
import com.kish2.hermitcrabapp.view.activities.LoginActivity;
import com.kish2.hermitcrabapp.presenter.ILoginPresenter;
import com.kish2.hermitcrabapp.view.activities.MainActivity;
import com.kish2.hermitcrabapp.view.activities.RegisterActivity;

import org.jetbrains.annotations.NotNull;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter extends ABasePresenter<LoginActivity> implements ILoginPresenter {

    private LoginActivity mView;
    private IUserModel mModel;

    public LoginPresenter(LoginActivity loginActivity) {
        this.mView = loginActivity;
        this.mModel = new UserModelImpl();
    }

    @Override
    public void loadDataFromServer() {

    }

    @Override
    public void detachView() {
        /* 让CG回收内存 */
        this.mView = null;
    }

    @Override
    public void onServerDoOperationFailed(Object object) {
        if (mView != null) {
            mView.mLoginSubmit.revertAnimation();
        }
    }

    @Override
    public void onServerSuccess(Object object) {
        if (mView != null) {
            mView.mLoginSubmit.doneLoadingAnimation(ThemeUtil.Theme.afterGetResourcesColorId, HermitCrabBitMaps.mChecked);
            new Handler().postDelayed(() -> {
                intent = new Intent(mView, MainActivity.class);
                mView.startActivity(intent);
                mView.finish();
            }, 500);
        }
    }

    @Override
    public void onSysNetworkError(Throwable t) {
        if (mView != null) {
            mView.mLoginSubmit.revertAnimation();
            if (t instanceof SocketTimeoutException)
                ToastUtil.showSysNetworkErrorToast(mView, ToastUtil.NET_ERROR_TYPE.TIMEOUT);
            else
                ToastUtil.showSysNetworkErrorToast(mView, ToastUtil.NET_ERROR_TYPE.CONNECT_EXCEPTION);
        }
    }

    @Override
    public void dataUpdate(Call<ResponseBody> call) {
    }

    @Override
    public void afterHandleServerError() {
        if (mView != null)
            mView.mLoginSubmit.revertAnimation();
    }

    @Override
    public void loginByUsername(String username, String password) {
        if (mView != null) {
            mView.mLoginSubmit.startAnimation();
            Call<ResponseBody> bodyCall = mModel.authByUsername(username, LicenseEncryption.passwordEncryption(password));
            bodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                    handleResponse(mView, AppAndJSONUtil.DO_JSON_TYPE.SET_USER, response);
                }

                @Override
                public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                    onSysNetworkError(t);
                }
            });
        }
    }

    @Override
    public void loginByMobile(String mobile, String checkCode) {
        if (mView != null) {
            mView.mLoginSubmit.startAnimation();
            Call<ResponseBody> bodyCall = mModel.authByMobile(mobile, checkCode);
            bodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                    handleResponse(mView, AppAndJSONUtil.DO_JSON_TYPE.SET_USER, response);
                }

                @Override
                public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                    onSysNetworkError(t);
                }
            });
        }
    }

    @Override
    public void register(String mobile, String checkCode) {
        mView.startActivity(new Intent(mView, RegisterActivity.class));
    }

    @Override
    public void rememberUser(boolean isRemember) {
    }

    @Override
    public void forgetPassword() {
    }

    @Override
    public void loginByWeChat() {
    }

    @Override
    public void loginByQQ() {
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

}

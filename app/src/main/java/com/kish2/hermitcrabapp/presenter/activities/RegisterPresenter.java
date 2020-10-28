package com.kish2.hermitcrabapp.presenter.activities;

import android.content.Intent;
import android.os.Handler;

import com.kish2.hermitcrabapp.bean.HermitCrabBitMaps;
import com.kish2.hermitcrabapp.model.IUserModel;
import com.kish2.hermitcrabapp.model.impl.UserModelImpl;
import com.kish2.hermitcrabapp.presenter.ABasePresenter;
import com.kish2.hermitcrabapp.presenter.ILoginPresenter;
import com.kish2.hermitcrabapp.utils.AppAndJSONUtil;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;
import com.kish2.hermitcrabapp.view.activities.MainActivity;
import com.kish2.hermitcrabapp.view.activities.RegisterActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter extends ABasePresenter<RegisterActivity> implements ILoginPresenter {

    private RegisterActivity mView;
    private IUserModel mModel;

    public RegisterPresenter(RegisterActivity activity) {
        this.mView = activity;
        this.mModel = new UserModelImpl();
    }

    @Override
    public void loginByUsername() {

    }

    @Override
    public void register() {
        Call<ResponseBody> bodyCall = mModel.userRegister(mView.getMobile());
        bodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    handleResponse(mView, AppAndJSONUtil.DO_JSON_TYPE.SET_USER, response);
                } else onFailure(call, new Throwable());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onServerError(null);
            }
        });
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
    public void loadDataFromServer() {

    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    @Override
    public void onServerError(Object object) {
        if (mView != null) {
            mView.mRegisterSubmit.revertAnimation();
        }
    }

    @Override
    public void onServerSuccess(Object object) {
        if (mView != null) {
            mView.mRegisterSubmit.doneLoadingAnimation(ThemeUtil.Theme.afterGetResourcesColorId, HermitCrabBitMaps.mChecked);
            new Handler().postDelayed(() -> {
                intent = new Intent(mView, MainActivity.class);
                mView.startActivity(intent);
                mView.finish();
            }, 500);
        }
    }

    @Override
    public void dataUpdate(Call<ResponseBody> call) {

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

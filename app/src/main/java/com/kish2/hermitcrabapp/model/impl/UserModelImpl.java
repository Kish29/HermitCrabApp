package com.kish2.hermitcrabapp.model.impl;

import com.kish2.hermitcrabapp.model.IUserModel;
import com.kish2.hermitcrabapp.utils.network.RetrofitUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class UserModelImpl implements IUserModel {

    private IUserModel retrofitUserModel;

    public UserModelImpl() {
        Retrofit baseRequest = RetrofitUtil.getBaseRequest();
        retrofitUserModel = baseRequest.create(IUserModel.class);
    }

    @Override
    public Call<ResponseBody> userRegister(String mobile, String code) {
        return retrofitUserModel.userRegister(mobile, code);
    }

    @Override
    public Call<ResponseBody> usernameUpdate(long uid, String username) {
        return retrofitUserModel.usernameUpdate(uid, username);
    }

    @Override
    public Call<ResponseBody> passwordUpdate(long uid, String password) {
        return retrofitUserModel.passwordUpdate(uid, password);
    }

    @Override
    public Call<ResponseBody> authByUsername(String username, String password) {
        return retrofitUserModel.authByUsername(username, password);
    }

    @Override
    public Call<ResponseBody> authByMobile(String mobile, String code) {
        return retrofitUserModel.authByMobile(mobile, code);
    }

    @Override
    public Call<ResponseBody> getDozenOfUsers(int num) {
        return retrofitUserModel.getDozenOfUsers(num);
    }
}

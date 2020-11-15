package com.kish2.hermitcrabapp.model.impl;

import com.kish2.hermitcrabapp.model.BaseModel;
import com.kish2.hermitcrabapp.model.IUserModel;
import com.kish2.hermitcrabapp.model.api.UserApi;
import com.kish2.hermitcrabapp.presenter.impl.UserPresenterImpl;
import com.kish2.hermitcrabapp.utils.network.RetrofitUtil;
import com.kish2.hermitcrabapp.utils.view.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class UserModelImpl extends BaseModel implements IUserModel {

    private UserApi userApi;

    /* 为了区分model层调用的函数，用该函数表来进行区分 */
    public enum method_index {
        register,
        login,
        update_username,
        update_password,
        user_list
    }

    public method_index index = method_index.login;

    public UserModelImpl(UserPresenterImpl presenter, OnRequestModelCallBack callBack) {
        super(callBack);
        Retrofit baseRequest = RetrofitUtil.getBaseRequest();
        userApi = baseRequest.create(UserApi.class);
    }

    @Override
    public void userRegister(String mobile, String code) {
        Call<ResponseBody> bodyCall = userApi.userRegister(mobile, code);
        index = method_index.register;
        bodyCall.enqueue(this);
    }

    @Override
    public void usernameUpdate(long uid, String username) {
        Call<ResponseBody> bodyCall = userApi.usernameUpdate(uid, username);
        index = method_index.update_username;
        bodyCall.enqueue(this);
    }

    @Override
    public void passwordUpdate(long uid, String password) {
        Call<ResponseBody> bodyCall = userApi.passwordUpdate(uid, password);
        index = method_index.update_password;
        bodyCall.enqueue(this);
    }

    @Override
    public void authByUsername(String username, String password) {
        Call<ResponseBody> bodyCall = userApi.authByUsername(username, password);
        index = method_index.login;
        bodyCall.enqueue(this);
    }

    @Override
    public void authByMobile(String mobile, String code) {
        Call<ResponseBody> bodyCall = userApi.authByMobile(mobile, code);
        index = method_index.login;
        bodyCall.enqueue(this);
    }

    @Override
    public void getDozenOfUsers(int num) {
        Call<ResponseBody> bodyCall = userApi.getDozenOfUsers(num);
        index = method_index.user_list;
        bodyCall.enqueue(this);
    }

    @Override
    public Object onServerSuccess(JSONObject jsonObject) throws JSONException {
        switch (index) {
            case user_list:
                JSONObject userListObj = jsonObject.getJSONObject("user_list");
                JSONObject bindListObj = jsonObject.getJSONObject("user_bind_info_list");
                return JsonUtil.toUserBeanList(userListObj, bindListObj);
            case login:
            case register:
            case update_password:
            case update_username:
            default:
                JSONObject userObj = jsonObject.getJSONObject("user");
                JSONObject bindObj = jsonObject.getJSONObject("user_bind_info");
                return JsonUtil.toUserBean(userObj, bindObj);
        }
    }

    @Override
    protected int getModelRequestCode() {
        switch (index) {
            case register:
                return 0;
            case login:
                return 1;
            case user_list:
                return 2;
            case update_password:
                return 3;
            case update_username:
                return 4;
            default:
                return -1;
        }
    }
}

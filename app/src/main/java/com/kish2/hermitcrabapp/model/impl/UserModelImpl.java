package com.kish2.hermitcrabapp.model.impl;

import com.kish2.hermitcrabapp.bean.User;
import com.kish2.hermitcrabapp.model.BaseModel;
import com.kish2.hermitcrabapp.model.IUserModel;
import com.kish2.hermitcrabapp.model.api.UserApi;
import com.kish2.hermitcrabapp.presenter.impl.UserPresenterImpl;
import com.kish2.hermitcrabapp.utils.network.RetrofitUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserModelImpl extends BaseModel<UserPresenterImpl> implements IUserModel {

    private UserApi userApi;

    public UserModelImpl(UserPresenterImpl presenter) {
        Retrofit baseRequest = RetrofitUtil.getBaseRequest();
        userApi = baseRequest.create(UserApi.class);
        bindPresenter(presenter);
    }

    @Override
    public void userRegister(String mobile, String code) {
        Call<ResponseBody> bodyCall = userApi.userRegister(mobile, code);
        bodyCall.enqueue(this);
    }

    @Override
    public void usernameUpdate(long uid, String username) {
        Call<ResponseBody> bodyCall = userApi.usernameUpdate(uid, username);
        bodyCall.enqueue(this);
    }

    @Override
    public void passwordUpdate(long uid, String password) {
        Call<ResponseBody> bodyCall = userApi.passwordUpdate(uid, password);
        bodyCall.enqueue(this);

    }

    @Override
    public void authByUsername(String username, String password) {
        Call<ResponseBody> bodyCall = userApi.authByUsername(username, password);
        bodyCall.enqueue(this);
    }

    @Override
    public void authByMobile(String mobile, String code) {
        Call<ResponseBody> bodyCall = userApi.authByMobile(mobile, code);
        bodyCall.enqueue(this);
    }

    @Override
    public void getDozenOfUsers(int num) {
        Call<ResponseBody> bodyCall = userApi.getDozenOfUsers(num);
        bodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void onServerSuccess(String rawData) throws JSONException {
        Map<MODEL_RET, Object> retMap = new HashMap<>();
        JSONObject data1 = new JSONObject(rawData);
        int status = data1.getInt(KEY_SERVER_STATUS);
        String msg = data1.getString(KEY_SERVER_MSG);
        /* 服务端返回msg可以直接压入 */
        retMap.put(MODEL_RET.ret_msg, msg);
        /* 服务端访问成功，但是操作失败 */
        if (status == SERVER_OPERATED_FAILURE) {
            retMap.put(MODEL_RET.ret_status, MODEL_STATUS.model_failure);
            presenter.onModelFailure(retMap);
            return;
        } else {
            retMap.put(MODEL_RET.ret_status, MODEL_STATUS.model_success);
            JSONObject user = data1.getJSONObject("user");
            JSONObject user_bind_info = data1.getJSONObject("user_bind_info");
            User userBean = new User();
            userBean.setUid(user.getLong("uid"));
            userBean.setUsername(user.getString("username"));
            userBean.setGender(user.getString("gender"));
            userBean.setEmail(user_bind_info.getString("email"));
            userBean.setDepartment(user_bind_info.getString("department"));
            userBean.setMobile(user_bind_info.getString("mobile"));
            userBean.setGrade(user_bind_info.getString("grade"));
            userBean.setStudentId(user_bind_info.getString("studentId"));
            retMap.put(MODEL_RET.ret_obj, userBean);
        }
        presenter.onModelSuccess(retMap);
    }

}

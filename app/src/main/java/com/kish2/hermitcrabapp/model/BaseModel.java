package com.kish2.hermitcrabapp.model;

import com.kish2.hermitcrabapp.utils.dev.ExceptionHandler;
import com.kish2.hermitcrabapp.utils.network.RetrofitUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public abstract class BaseModel implements IBaseModel, Callback<ResponseBody> {

    protected static final int SERVER_OPERATED_FAILURE = 222;
    protected static final String KEY_SERVER_STATUS = "server_code";
    protected static final String KEY_SERVER_MSG = "server_msg";

    public enum MODEL_STATUS {
        model_success,
        model_failure
    }

    public enum MODEL_RET {
        ret_status,
        ret_msg,
        ret_obj,
    }


    protected OnRequestModelCallBack mCallBack;
    protected Retrofit mBaseRequest;

    public BaseModel(OnRequestModelCallBack callBack) {
        this.mCallBack = callBack;
        this.mBaseRequest = RetrofitUtil.getBaseRequest();
    }

    /* 让子类实现该方法，该方法与presenter对接 */
    protected abstract int getModelRequestCode();

    @Override
    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
        int code = response.code();
        /* 访问正常 */
        if (code == 200) {
            if (response.body() != null) {
                try {
                    String rawData = response.body().string();
                    onServerAccessSuccess(rawData);
                } catch (IOException | JSONException e) {
                    ExceptionHandler.BaseHandle();
                }
            } else onFailure(call, new Throwable());
        } else onServerFailure(code);
    }

    @Override
    public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
        onLocalNetworkFailure(t);
    }

    @Override
    public void onServerFailure(int serverExceptionCode) {
        Map<MODEL_RET, Object> retMap = new HashMap<>();
        retMap.put(MODEL_RET.ret_status, MODEL_STATUS.model_failure);
        switch (serverExceptionCode) {
            case 404:
                retMap.put(MODEL_RET.ret_msg, "您访问的页面不存在哦~");
                break;
            case 500:
            default:
                retMap.put(MODEL_RET.ret_msg, "服务器异常，请稍后试试吧~");
                break;
        }
        mCallBack.onModelFailure(retMap, getModelRequestCode());
    }

    @Override
    public void onLocalNetworkFailure(Throwable throwable) {
        Map<MODEL_RET, Object> retMap = new HashMap<>();
        retMap.put(MODEL_RET.ret_status, MODEL_STATUS.model_failure);
        if (throwable instanceof SocketTimeoutException)
            retMap.put(MODEL_RET.ret_msg, "网络连接超时，请检查您的网络状况~");
        else
            retMap.put(MODEL_RET.ret_msg, "网络异常或无法连接到服务器~");
        mCallBack.onModelFailure(retMap, getModelRequestCode());
    }

    private void onServerAccessSuccess(String json) throws JSONException {
        Map<MODEL_RET, Object> retMap = new HashMap<>();
        JSONObject jsonObject = new JSONObject(json);
        int status = jsonObject.getInt(KEY_SERVER_STATUS);
        String msg = jsonObject.getString(KEY_SERVER_MSG);
        /* 服务端返回msg可以直接压入 */
        retMap.put(MODEL_RET.ret_msg, msg);
        /* 服务端访问成功，但是操作失败 */
        if (status == SERVER_OPERATED_FAILURE) {
            retMap.put(MODEL_RET.ret_status, MODEL_STATUS.model_failure);
            mCallBack.onModelFailure(retMap, getModelRequestCode());
        } else {
            retMap.put(MODEL_RET.ret_status, MODEL_STATUS.model_success);
            retMap.put(MODEL_RET.ret_obj, onServerSuccess(jsonObject));
            mCallBack.onModelSuccess(retMap, getModelRequestCode());
        }
    }

}

package com.kish2.hermitcrabapp.model;

import com.kish2.hermitcrabapp.presenter.BasePresenter;
import com.kish2.hermitcrabapp.utils.dev.ExceptionHandler;
import com.kish2.hermitcrabapp.view.BaseActivity;
import com.kish2.hermitcrabapp.view.BaseFragment;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseModel<P extends BasePresenter<BaseActivity, BaseFragment>> implements IBaseModel, Callback<ResponseBody> {

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

    protected P presenter;

    public void bindPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
        int code = response.code();
        /* 访问正常 */
        if (code == 200) {
            if (response.body() != null) {
                try {
                    String rawData = response.body().string();
                    onServerSuccess(rawData);
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
        presenter.onModelFailure(retMap);
    }

    @Override
    public void onLocalNetworkFailure(Throwable throwable) {
        Map<MODEL_RET, Object> retMap = new HashMap<>();
        retMap.put(MODEL_RET.ret_status, MODEL_STATUS.model_failure);
        if (throwable instanceof SocketTimeoutException)
            retMap.put(MODEL_RET.ret_msg, "网络连接超时，请检查您的网络状况~");
        else
            retMap.put(MODEL_RET.ret_msg, "网络异常，请检查您的网络状况~");
        presenter.onModelFailure(retMap);
    }
}

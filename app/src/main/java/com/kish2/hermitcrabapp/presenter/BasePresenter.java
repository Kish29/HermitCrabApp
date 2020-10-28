package com.kish2.hermitcrabapp.presenter;

import android.content.Context;

import androidx.lifecycle.LifecycleObserver;

import com.kish2.hermitcrabapp.utils.dev.ExceptionHandler;
import com.kish2.hermitcrabapp.custom.view.CustomTipDialog;
import com.kish2.hermitcrabapp.utils.App;
import com.kish2.hermitcrabapp.utils.AppAndJSONUtil;
import com.kish2.hermitcrabapp.utils.view.ToastUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public abstract class BasePresenter implements IBasePresenter, LifecycleObserver {

    protected CustomTipDialog mWaitDialog;

    abstract public void loadDataFromServer();

    abstract public void detachView();

    abstract public void dataUpdate(Call<ResponseBody> call);

    protected void handleResponse(Context context, AppAndJSONUtil.DO_JSON_TYPE type, @NotNull Response<ResponseBody> response) {
        try {
            assert response.body() != null;
            String rowData = response.body().string();
            Map<AppAndJSONUtil.DO_JSON_RET, Object> res = AppAndJSONUtil.doJsonObject(rowData, type);
            assert res != null;
            String msg = (String) res.get(AppAndJSONUtil.DO_JSON_RET.RET_MSG);
            ToastUtil.showToast(context == null ? App.getAppContext() : context, msg, ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
            if ((boolean) res.get(AppAndJSONUtil.DO_JSON_RET.RET_STATUS)) {
                onServerSuccess(null);
            } else onServerError(null);
        } catch (IOException | JSONException e) {
            ExceptionHandler.BaseHandle();
        }
    }
}

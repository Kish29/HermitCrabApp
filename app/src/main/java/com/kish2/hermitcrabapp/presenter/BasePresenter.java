package com.kish2.hermitcrabapp.presenter;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LifecycleObserver;

import com.kish2.hermitcrabapp.utils.dev.ExceptionHandler;
import com.kish2.hermitcrabapp.custom.view.CustomTipDialog;
import com.kish2.hermitcrabapp.utils.App;
import com.kish2.hermitcrabapp.utils.AppAndJSONUtil;
import com.kish2.hermitcrabapp.utils.view.KZDialogUtil;
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
        int code = response.code();
        if (code == 200) {
            try {
                assert response.body() != null;
                String rowData = response.body().string();
                Map<AppAndJSONUtil.DO_JSON_RET, Object> res = AppAndJSONUtil.doJsonObject(rowData, type);
                assert res != null;
                String msg = (String) res.get(AppAndJSONUtil.DO_JSON_RET.RET_MSG);
                ToastUtil.showToast(context == null ? App.getAppContext() : context, msg, ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
                if ((boolean) res.get(AppAndJSONUtil.DO_JSON_RET.RET_STATUS)) {
                    onServerSuccess(null);
                } else onServerDoOperationFailed(null);
            } catch (IOException | JSONException e) {
                ExceptionHandler.BaseHandle();
            }
        } else onServerErrorOrException(context, code);
    }

    protected void onServerErrorOrException(Context context, int serverExceptionCode) {
        if (context == null)
            context = App.getAppContext();
        switch (serverExceptionCode) {
            case 404:
                KZDialogUtil.IOS_LIGHT_ERROR_DIALOG(context, "您访问的页面不存在哦~").show();
                break;
            case 500:
            default:
                KZDialogUtil.IOS_LIGHT_ERROR_DIALOG(context, "服务器异常，请稍后试试吧~").show();
                break;
        }
        /* 服务器异常处理后的UI变更 */
        afterHandleServerError();
    }

    abstract public void afterHandleServerError();
}

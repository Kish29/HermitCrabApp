package com.kish2.hermitcrabapp.utils.dev;

import com.kish2.hermitcrabapp.utils.App;
import com.kish2.hermitcrabapp.utils.view.ToastUtil;

public class ExceptionHandler {

    public static void BaseHandle() {
        ToastUtil.showToast(App.getAppContext(), "系统异常，请稍后再试", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
    }

}

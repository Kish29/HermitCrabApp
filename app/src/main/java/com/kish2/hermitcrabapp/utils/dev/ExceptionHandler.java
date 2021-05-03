package com.kish2.hermitcrabapp.utils.dev;

import com.kish2.hermitcrabapp.utils.view.ToastUtil;

public class ExceptionHandler {

    public static void BaseHandle() {
        ToastUtil.showToast(HermitCrabApp.getAppContext(), "服务异常，请稍后再试", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
    }

}

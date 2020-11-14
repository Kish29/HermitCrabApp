package com.kish2.hermitcrabapp.utils.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil {
    public enum TOAST_POSITION {
        TOAST_CENTER,
        TOAST_TOP,
        TOAST_BOTTOM,
        TOAST_LEFT,
        TOAST_RIGHT
    }

    public enum TOAST_DURATION {
        TOAST_SHORT,
        TOAST_LONG
    }

    // 显示toast
    @SuppressLint("ShowToast")
    public static void showToast(Context context, String msg, TOAST_DURATION duration, TOAST_POSITION position) {
        Toast toast;
        if (duration == TOAST_DURATION.TOAST_LONG) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
        switch (position) {
            case TOAST_CENTER:
                toast.setGravity(Gravity.CENTER, 0, 0);
                break;
            case TOAST_TOP:
                toast.setGravity(Gravity.TOP, 0, 0);
                break;
            case TOAST_LEFT:
                toast.setGravity(Gravity.START, 0, 0);
                break;
            case TOAST_RIGHT:
                toast.setGravity(Gravity.END, 0, 0);
                break;
            default:
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                break;
        }
        toast.show();
    }

    public enum NET_ERROR_TYPE {
        CONNECT_EXCEPTION,
        TIMEOUT,
    }

    public static void showSysNetworkErrorToast(Context context, NET_ERROR_TYPE type) {
        switch (type) {
            case TIMEOUT:
                showToast(context, "网络连接超时", TOAST_DURATION.TOAST_SHORT, TOAST_POSITION.TOAST_BOTTOM);
                break;
            default:
            case CONNECT_EXCEPTION:
                showToast(context, "网络异常", TOAST_DURATION.TOAST_SHORT, TOAST_POSITION.TOAST_BOTTOM);
        }
    }
}

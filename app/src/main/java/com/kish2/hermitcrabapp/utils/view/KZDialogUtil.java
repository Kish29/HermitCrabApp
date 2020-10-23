package com.kish2.hermitcrabapp.utils.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.kish2.hermitcrabapp.custom.view.CustomTipDialog;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.util.TextInfo;
import com.kongzue.dialog.v3.MessageDialog;

public class KZDialogUtil {

    private static TextInfo FONT_COLOR = new TextInfo().setFontColor(ThemeUtil.Theme.afterGetResourcesColorId);

    /* 竖直排列的messageDialog，要得到水平排列的话，在获取后设置orientation即可 */
    public static MessageDialog IOS_LIGHT_VER_ONE_BUTTON_MESSAGE(Context context, String title, String msg, String okBtnMsg) {
        MessageDialog msgDialog = MessageDialog.build((AppCompatActivity) context);
        msgDialog.setStyle(DialogSettings.STYLE.STYLE_IOS);
        msgDialog.setTheme(DialogSettings.THEME.LIGHT);
        if (title != null) {
            msgDialog.setTitle(title);
        }
        if (msg != null) {
            msgDialog.setMessage(msg);
        }
        if (okBtnMsg != null) {
            msgDialog.setOkButton(okBtnMsg);
        }
        msgDialog.setButtonTextInfo(FONT_COLOR);
        msgDialog.setButtonOrientation(LinearLayout.VERTICAL);
        return msgDialog;
    }

    public static MessageDialog IOS_LIGHT_VER_TWO_BUTTON_MESSAGE(Context context, String title, String msg, String okBtnMsg, String cancelBtnMsg) {
        MessageDialog msgDialog = IOS_LIGHT_VER_ONE_BUTTON_MESSAGE(context, title, msg, okBtnMsg);
        if (cancelBtnMsg != null) {
            msgDialog.setCancelButton(cancelBtnMsg);
        }
        return msgDialog;
    }

    public static MessageDialog IOS_LIGHT_VER_THREE_BUTTON_MESSAGE(Context context, String title, String msg, String okBtnMsg, String cancelBtnMsg, String otherBtnMsg) {
        MessageDialog msgDialog = IOS_LIGHT_VER_TWO_BUTTON_MESSAGE(context, title, msg, okBtnMsg, cancelBtnMsg);
        if (otherBtnMsg != null) {
            msgDialog.setOtherButton(otherBtnMsg);
        }
        return msgDialog;
    }

    /* 同理，黑色主题时，重新设置一下即可 */
    public static CustomTipDialog IOS_LIGHT_WAIT_DIALOG(Context context, Drawable tip, String msg, int tipTime, boolean cancelable) {
        CustomTipDialog tipDialog = CustomTipDialog.build((AppCompatActivity) context);
        tipDialog.setTheme(DialogSettings.THEME.LIGHT);
        if (tip != null) {
            tipDialog.setTipDrawable(tip);
        }
        if (msg != null) {
            tipDialog.setMessage(msg);
        }
        /* 值位负值时，需要程序员手动关闭或用户按返回关闭 */
        if (tipTime >= 0) {
            tipDialog.setTipTime(tipTime);
        } else {
            tipDialog.setNoAutoDismiss(true);
        }
        tipDialog.setCancelable(cancelable);
        return tipDialog;
    }

    public static CustomTipDialog IOS_LIGHT_ERROR_DIALOG(Context context, Drawable tip, String msg, int tipTime) {
        CustomTipDialog errorDialog = IOS_LIGHT_WAIT_DIALOG(context, tip, msg, tipTime, true);
        errorDialog.setTip(CustomTipDialog.TYPE.ERROR);
        return errorDialog;
    }

}

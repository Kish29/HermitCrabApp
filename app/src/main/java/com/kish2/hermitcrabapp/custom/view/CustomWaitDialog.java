package com.kish2.hermitcrabapp.custom.view;

import androidx.appcompat.app.AppCompatActivity;

public class CustomWaitDialog extends CustomTipDialog {
    private CustomWaitDialog() {
    }

    public static CustomTipDialog show(AppCompatActivity context, String message) {
        return CustomTipDialog.showWait(context, message);
    }

    public static CustomTipDialog show(AppCompatActivity context, int messageResId) {
        return CustomTipDialog.showWait(context, messageResId);
    }

    @Override
    public void show() {
        setDismissEvent();
        showDialog();
    }

    public CustomWaitDialog setCustomDialogStyleId(int customDialogStyleId) {
        if (isAlreadyShown) {
            error("必须使用 build(...) 方法创建时，才可以使用 setTheme(...) 来修改对话框主题或风格。");
            return this;
        }
        this.customDialogStyleId = customDialogStyleId;
        return this;
    }

    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
}

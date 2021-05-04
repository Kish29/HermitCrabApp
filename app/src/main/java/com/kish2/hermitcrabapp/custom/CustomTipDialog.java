package com.kish2.hermitcrabapp.custom;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.kongzue.dialog.R;
import com.kongzue.dialog.interfaces.OnBackClickListener;
import com.kongzue.dialog.interfaces.OnShowListener;
import com.kongzue.dialog.interfaces.OnDismissListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.util.TextInfo;
import com.kongzue.dialog.util.view.BlurView;
import com.kongzue.dialog.util.view.ProgressView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.kongzue.dialog.util.DialogSettings.blurAlpha;

/**
 * Author: @Kongzue
 * Github: https://github.com/kongzue/
 * Homepage: http://kongzue.com/
 * Mail: myzcxhh@live.cn
 * CreateTime: 2019/3/22 16:16
 * 修改于2020/10/23 kish2
 */
public class CustomTipDialog extends BaseDialog {

    private DialogSettings.THEME tipTheme;

    public enum TYPE {
        WARNING, SUCCESS, ERROR, OTHER
    }

    private OnDismissListener dismissListener;

    public static CustomTipDialog waitDialogTemp;
    protected CharSequence message;
    private TYPE type;
    private Drawable tipImage;

    private BlurView blurView;

    private RelativeLayout boxBody;
    private RelativeLayout boxBlur;
    private RelativeLayout boxProgress;
    private ProgressView progress;
    private RelativeLayout boxTip;
    private TextView txtInfo;
    private Drawable backgroundDrawable;

    private int tipTime = 1500;

    public void setNoAutoDismiss(boolean noAutoDismiss) {
        this.noAutoDismiss = noAutoDismiss;
    }

    private boolean noAutoDismiss = false;

    protected CustomTipDialog() {
    }

    public void setProgressColor(int colorId) {
        if (progress != null) {
            progress.setup(colorId);
        }
    }

    public static CustomTipDialog build(AppCompatActivity context) {
        synchronized (CustomTipDialog.class) {
            CustomTipDialog waitDialog = new CustomTipDialog();

            if (waitDialogTemp == null) {
                waitDialogTemp = waitDialog;
            } else {
                if (waitDialogTemp.context.get() != context) {
                    dismiss();
                    waitDialogTemp = waitDialog;
                } else {
                    waitDialog = waitDialogTemp;
                }
            }
            waitDialog.log("装载提示/等待框: " + waitDialog.toString());
            waitDialog.context = new WeakReference<>(context);
            waitDialog.build(waitDialog, R.layout.dialog_wait);
            return waitDialog;
        }
    }

    public static CustomTipDialog showWait(AppCompatActivity context, CharSequence message) {
        synchronized (CustomTipDialog.class) {
            CustomTipDialog waitDialog = build(context);

            waitDialogTemp.onDismissListener = new OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (waitDialogTemp != null && waitDialogTemp.dismissListener != null)
                        waitDialogTemp.dismissListener.onDismiss();
                    waitDialogTemp = null;
                }
            };

            if (waitDialog == null) {
                waitDialogTemp.setTip(null);
                waitDialogTemp.setMessage(message);
                if (waitDialogTemp.cancelTimer != null) waitDialogTemp.cancelTimer.cancel();
                return waitDialogTemp;
            } else {
                waitDialog.message = message;
                waitDialog.type = null;
                waitDialog.tipImage = null;
                if (waitDialog.cancelTimer != null) waitDialog.cancelTimer.cancel();
                waitDialog.showDialog();
                return waitDialog;
            }
        }
    }

    public static CustomTipDialog showWait(AppCompatActivity context, int messageResId) {
        synchronized (CustomTipDialog.class) {
            CustomTipDialog waitDialog = build(context);

            waitDialogTemp.onDismissListener = new OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (waitDialogTemp != null && waitDialogTemp.dismissListener != null)
                        waitDialogTemp.dismissListener.onDismiss();
                    waitDialogTemp = null;
                }
            };

            if (waitDialog == null) {
                waitDialogTemp.setTip(null);
                waitDialogTemp.setMessage(context.getString(messageResId));
                if (waitDialogTemp.cancelTimer != null) waitDialogTemp.cancelTimer.cancel();
                return waitDialogTemp;
            } else {
                waitDialog.message = context.getString(messageResId);
                waitDialog.type = null;
                waitDialog.tipImage = null;
                if (waitDialog.cancelTimer != null) waitDialog.cancelTimer.cancel();
                waitDialog.showDialog();
                return waitDialog;
            }
        }
    }

    public static CustomTipDialog show(AppCompatActivity context, CharSequence message, TYPE type) {
        synchronized (CustomTipDialog.class) {
            CustomTipDialog waitDialog = build(context);

            waitDialogTemp.onDismissListener = new OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (waitDialogTemp != null && waitDialogTemp.dismissListener != null)
                        waitDialogTemp.dismissListener.onDismiss();
                    waitDialogTemp = null;
                }
            };

            if (waitDialog == null) {
                waitDialogTemp.setTip(type);
                waitDialogTemp.setMessage(message);
                waitDialogTemp.autoDismiss();
                return waitDialogTemp;
            } else {
                waitDialog.message = message;
                waitDialog.setTip(type);
                waitDialog.showDialog();
                waitDialog.autoDismiss();
                return waitDialog;
            }
        }
    }

    public static CustomTipDialog show(AppCompatActivity context, int messageResId, TYPE type) {
        return show(context, context.getString(messageResId), type);
    }

    public static CustomTipDialog show(AppCompatActivity context, CharSequence message, int icoResId) {
        synchronized (CustomTipDialog.class) {
            CustomTipDialog waitDialog = build(context);

            waitDialogTemp.onDismissListener = new OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (waitDialogTemp != null && waitDialogTemp.dismissListener != null)
                        waitDialogTemp.dismissListener.onDismiss();
                    waitDialogTemp = null;
                }
            };

            if (waitDialog == null) {
                waitDialogTemp.setTip(icoResId);
                waitDialogTemp.setMessage(message);
                waitDialogTemp.autoDismiss();
                return waitDialogTemp;
            } else {
                waitDialog.message = message;
                waitDialog.setTip(icoResId);
                waitDialog.showDialog();
                waitDialog.autoDismiss();
                return waitDialog;
            }
        }
    }

    public static CustomTipDialog show(AppCompatActivity context, int messageResId, int icoResId) {
        return show(context, context.getString(messageResId), icoResId);
    }

    protected void showDialog() {
        log("启动提示/等待框 -> " + toString());
        super.showDialog();
        setDismissEvent();
    }

    private View rootView;

    @Override
    public void bindView(View rootView) {
        if (boxTip != null) {
            boxTip.removeAllViews();
        }
        if (boxBlur != null) {
            boxBlur.removeAllViews();
        }
        this.rootView = rootView;
        boxBody = rootView.findViewById(R.id.box_body);
        boxBlur = rootView.findViewById(R.id.box_blur);
        boxProgress = rootView.findViewById(R.id.box_progress);
        progress = rootView.findViewById(R.id.progress);
        boxTip = rootView.findViewById(R.id.box_tip);
        txtInfo = rootView.findViewById(R.id.txt_info);

        refreshView();
        if (onShowListener != null) onShowListener.onShow(this);
    }


    private Timer cancelTimer;

    private void autoDismiss() {
        if (cancelTimer != null) cancelTimer.cancel();
        if (!noAutoDismiss) {
            cancelTimer = new Timer();
            cancelTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    doDismiss();
                    dismiss();
                    cancelTimer.cancel();
                }
            }, tipTime);
        }
    }

    public void refreshView() {
        if (rootView != null) {
            final int bkgResId, blurFrontColor;
            if (tipTheme == null) tipTheme = DialogSettings.tipTheme;
            /* 如果同时设置了tip的background，又同时设置了container的background，让container的background不生效 */
            if (DialogSettings.tipBackgroundResId != 0 && backgroundResId == -1) {
                backgroundResId = DialogSettings.tipBackgroundResId;
            }

            switch (tipTheme) {
                case LIGHT:
                    bkgResId = R.drawable.rect_light;
                    int darkColor = Color.rgb(0, 0, 0);
                    blurFrontColor = Color.argb(blurAlpha, 255, 255, 255);
                    if (progress != null) {
                        progress.setup(R.color.black);
                    }
                    txtInfo.setTextColor(darkColor);
                    if (type != null) {
                        boxProgress.setVisibility(View.GONE);
                        boxTip.setVisibility(View.VISIBLE);
                        switch (type) {
                            case OTHER:
                                boxTip.setBackground(tipImage);
                                break;
                            case ERROR:
                                boxTip.setBackgroundResource(R.mipmap.img_error_dark);
                                break;
                            case WARNING:
                                boxTip.setBackgroundResource(R.mipmap.img_warning_dark);
                                break;
                            case SUCCESS:
                                boxTip.setBackgroundResource(R.mipmap.img_finish_dark);
                                break;
                        }
                    } else {
                        boxProgress.setVisibility(View.VISIBLE);
                        boxTip.setVisibility(View.GONE);
                    }
                    break;
                case DARK:
                    bkgResId = R.drawable.rect_dark;
                    int lightColor = Color.rgb(255, 255, 255);
                    blurFrontColor = Color.argb(blurAlpha, 0, 0, 0);
                    if (progress != null) {
                        progress.setup(R.color.white);
                    }
                    txtInfo.setTextColor(lightColor);
                    if (type != null) {
                        boxProgress.setVisibility(View.GONE);
                        boxTip.setVisibility(View.VISIBLE);
                        switch (type) {
                            case OTHER:
                                boxTip.setBackground(tipImage);
                                break;
                            case ERROR:
                                boxTip.setBackgroundResource(R.mipmap.img_error);
                                break;
                            case WARNING:
                                boxTip.setBackgroundResource(R.mipmap.img_warning);
                                break;
                            case SUCCESS:
                                boxTip.setBackgroundResource(R.mipmap.img_finish);
                                break;
                        }
                    } else {
                        boxProgress.setVisibility(View.VISIBLE);
                        boxTip.setVisibility(View.GONE);
                    }
                    break;
                default:
                    bkgResId = R.drawable.rect_dark;
                    blurFrontColor = Color.argb(blurAlpha, 0, 0, 0);
                    break;
            }
            /* 如果设置了background*/
            if (backgroundResId != -1) {
                boxBody.setBackgroundResource(backgroundResId);
            } else {

                if (DialogSettings.isUseBlur) {
                    boxBlur.post(new Runnable() {
                        @Override
                        public void run() {
                            blurView = new BlurView(context.get(), null);
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            blurView.setOverlayColor(blurFrontColor);
                            boxBlur.addView(blurView, 0, params);
                        }
                    });
                    boxBody.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
                        if (boxBlur != null && boxBody != null) {
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(boxBody.getWidth(), boxBody.getHeight());
                            boxBlur.setLayoutParams(params);
                        }
                    });
                } else {
                    if (backgroundDrawable != null)
                        boxBody.setBackground(backgroundDrawable);
                    else
                        boxBody.setBackgroundResource(bkgResId);
                }
            }

            if (isNull(message)) {
                txtInfo.setVisibility(View.GONE);
            } else {
                txtInfo.setVisibility(View.VISIBLE);
                txtInfo.setText(message);
                useTextInfo(txtInfo, tipTextInfo);
            }

            if (customView != null) {
                boxProgress.setVisibility(View.GONE);
                boxTip.setBackground(null);
                boxTip.setVisibility(View.VISIBLE);
                boxTip.addView(customView);
                if (onBindView != null) onBindView.onBind(this, customView);
            }
        }
    }

    protected void setDismissEvent() {
        onDismissListener = new OnDismissListener() {
            @Override
            public void onDismiss() {
                if (dismissListener != null)
                    dismissListener.onDismiss();
                waitDialogTemp = null;
            }
        };
    }

    @Override
    public void show() {
        showDialog();
        autoDismiss();
    }

    public void showNoAutoDismiss() {
        showDialog();
    }

    public OnDismissListener getOnDismissListener() {
        return dismissListener == null ? new OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        } : dismissListener;
    }

    public CustomTipDialog setOnDismissListener(OnDismissListener onDismissListener) {
        this.dismissListener = onDismissListener;
        setDismissEvent();
        return this;
    }

    public OnShowListener getOnShowListener() {
        return onShowListener == null ? new OnShowListener() {
            @Override
            public void onShow(BaseDialog dialog) {

            }
        } : onShowListener;
    }

    public CustomTipDialog setOnShowListener(OnShowListener onShowListener) {
        this.onShowListener = onShowListener;
        return this;
    }

    public static void dismiss() {
        if (waitDialogTemp != null) waitDialogTemp.doDismiss();
        waitDialogTemp = null;
        List<BaseDialog> temp = new ArrayList<>();
        temp.addAll(dialogList);
        for (BaseDialog dialog : temp) {
            if (dialog instanceof CustomTipDialog) {
                dialog.doDismiss();
            }
        }
    }

    public static void dismiss(int millisecond) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, millisecond);
    }

    public CharSequence getMessage() {
        return message;
    }

    public CustomTipDialog setMessage(CharSequence message) {
        this.message = message;
        log("启动提示/等待框 -> " + toString());
        if (txtInfo != null) txtInfo.setText(message);
        refreshView();
        return this;
    }

    public CustomTipDialog setMessage(int messageResId) {
        this.message = context.get().getString(messageResId);
        log("启动提示/等待框 -> " + toString());
        if (txtInfo != null) txtInfo.setText(message);
        refreshView();
        return this;
    }

    public CustomTipDialog setTip(TYPE type) {
        this.type = type;
        if (type != TYPE.OTHER) tipImage = null;
        refreshView();
        return this;
    }

    public CustomTipDialog setTip(@DrawableRes int resId) {
        this.type = TYPE.OTHER;
        tipImage = ContextCompat.getDrawable(context.get(), resId);
        refreshView();
        return this;
    }

    public CustomTipDialog setTipDrawable(Drawable drawable) {
        this.type = TYPE.OTHER;
        tipImage = drawable;
        refreshView();
        return this;
    }

    public TYPE getType() {
        return type;
    }

    public Drawable getTipImage() {
        return tipImage;
    }

    public TextView getTxtInfo() {
        return txtInfo;
    }

    public CustomTipDialog setTipTime(int tipTime) {
        this.tipTime = tipTime;
        if (type != null) autoDismiss();
        return this;
    }

    public CustomTipDialog setTheme(DialogSettings.THEME theme) {
        tipTheme = theme;
        refreshView();
        return this;
    }

    public DialogSettings.THEME getTheme() {
        return tipTheme;
    }

    public CustomTipDialog setCustomView(View customView) {
        this.customView = customView;
        refreshView();
        return this;
    }

    private OnBindView onBindView;

    public CustomTipDialog setCustomView(int customViewLayoutId, OnBindView onBindView) {
        customView = LayoutInflater.from(context.get()).inflate(customViewLayoutId, null);
        this.onBindView = onBindView;
        refreshView();
        return this;
    }

    public boolean getCancelable() {
        return cancelable == BOOLEAN.TRUE;
    }

    public CustomTipDialog setCancelable(boolean enable) {
        this.cancelable = enable ? BOOLEAN.TRUE : BOOLEAN.FALSE;
        if (dialog != null) dialog.get().setCancelable(cancelable == BOOLEAN.TRUE);
        return this;
    }

    public interface OnBindView {
        void onBind(CustomTipDialog dialog, View v);
    }

    @Deprecated
    public TextInfo getMessageTextInfo() {
        return messageTextInfo;
    }

    @Deprecated
    public CustomTipDialog setMessageTextInfo(TextInfo messageTextInfo) {
        this.messageTextInfo = messageTextInfo;
        refreshView();
        return this;
    }

    public TextInfo getTipTextInfo() {
        return tipTextInfo;
    }

    public CustomTipDialog setTipTextInfo(TextInfo tipTextInfo) {
        this.tipTextInfo = tipTextInfo;
        refreshView();
        return this;
    }

    public int getBackgroundResId() {
        return backgroundResId;
    }

    public CustomTipDialog setBackgroundResId(int backgroundResId) {
        this.backgroundResId = backgroundResId;
        refreshView();
        return this;
    }

    public CustomTipDialog setBackgroundDrawable(Drawable drawable) {
        this.backgroundDrawable = drawable;
        refreshView();
        return this;
    }

    public CustomTipDialog setCustomDialogStyleId(int customDialogStyleId) {
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

    public OnBackClickListener getOnBackClickListener() {
        return onBackClickListener;
    }

    public CustomTipDialog setOnBackClickListener(OnBackClickListener onBackClickListener) {
        this.onBackClickListener = onBackClickListener;
        return this;
    }
}

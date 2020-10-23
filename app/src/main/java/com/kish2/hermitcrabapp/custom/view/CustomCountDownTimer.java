package com.kish2.hermitcrabapp.custom.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.kish2.hermitcrabapp.R;

public class CustomCountDownTimer extends CountDownTimer {

    private View mAttachView;
    private Context mContext;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CustomCountDownTimer(long millisInFuture, long countDownInterval, View attachView, Context context) {
        super(millisInFuture, countDownInterval);
        this.mAttachView = attachView;
        this.mContext = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long millisUntilFinished) {
        mAttachView.setClickable(false);
        ((TextView) mAttachView).setText(millisUntilFinished / 1000 + "s");
    }

    @Override
    public void onFinish() {
        mAttachView.setClickable(true);
        ((TextView) mAttachView).setText(mContext.getResources().getText(R.string.resend_verify));
    }
}

package com.kish2.hermitcrabapp.view.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.bean.HermitCrabVectorIllustrations;
import com.kish2.hermitcrabapp.custom.view.CustomCountDownTimer;
import com.kish2.hermitcrabapp.utils.security.InputCheckUtil;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;
import com.kish2.hermitcrabapp.utils.view.ToastUtil;
import com.kish2.hermitcrabapp.view.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FLoginMobile extends BaseFragment {
    /* 三个图标*/
    @BindView(R.id.iv_user_icon)
    ImageView mUserIcon;
    @BindView(R.id.iv_verify_icon)
    ImageView mVerifyIcon;
    @BindView(R.id.ib_mobile_clear)
    ImageButton mMobileClear;

    /* 两个输入框*/
    @BindView(R.id.et_mobile_input)
    EditText mMobileInput;
    @BindView(R.id.et_verify_input)
    EditText mVerifyInput;

    @BindView(R.id.tv_verify_send)
    TextView mVerifySend;

    private CustomCountDownTimer mTimer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View loginMobile = inflater.inflate(R.layout.view_login_mobile, container, false);// 视图与父容器ViewGroup不需要连接
        ButterKnife.bind(this, loginMobile);
        getAndSetLayoutView();
        /* 注册事件 */
        registerViewComponentsAffairs();
        return loginMobile;
    }

    @Override
    public void getAndSetLayoutView() {
        mUserIcon.setImageDrawable(HermitCrabVectorIllustrations.VI_MOBILE);
        mVerifyIcon.setImageDrawable(HermitCrabVectorIllustrations.VI_VERIFY);
        mVerifySend.setTextColor(ThemeUtil.Theme.afterGetResourcesColorId);
        mMobileClear.setImageDrawable(HermitCrabVectorIllustrations.VI_CLEAR);
    }

    @Override
    public void getLayoutComponentsAttr() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void refreshData() {

    }


    @Override
    public void registerViewComponentsAffairs() {
        mMobileInput.setOnClickListener(v -> {
            mVerifyInput.clearFocus();
            mMobileInput.setFocusableInTouchMode(true);
            mMobileInput.requestFocus();
        });
        mVerifyInput.setOnClickListener(v -> {
            mMobileInput.clearFocus();
            mVerifyInput.setFocusableInTouchMode(true);
            mVerifyInput.requestFocus();
        });
        mMobileInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mMobileInput.setActivated(true);
                mVerifyInput.setActivated(false);
            } else {
                if (!InputCheckUtil.isValidMobile(getMobile())) {
                    ToastUtil.showToast(mContext, "您输入的是无效的手机号哦~", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_CENTER);
                }
            }
        });
        mVerifyInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mVerifyInput.setActivated(true);
                mMobileInput.setActivated(false);
            }
        });
        mMobileInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0)
                    mMobileClear.setVisibility(View.VISIBLE);
                else mMobileClear.setVisibility(View.INVISIBLE);
            }
        });
        mMobileClear.setOnClickListener(v -> {
            mMobileInput.setText(null);
        });

        mVerifySend.setOnClickListener(v -> {
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
            mTimer = new CustomCountDownTimer(60500, 1000, mVerifySend, mContext);
            mTimer.start();
        });
    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /* 防止计时器内存泄漏*/
        disposeTimer();
    }

    public String getVerifyCode() {
        return this.mVerifyInput.getText().toString().trim();
    }

    public String getMobile() {
        return this.mMobileInput.getText().toString().trim();
    }

    private void disposeTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    protected void themeChanged() {
        getAndSetLayoutView();
    }

    @Override
    public void initHandler() {

    }
}

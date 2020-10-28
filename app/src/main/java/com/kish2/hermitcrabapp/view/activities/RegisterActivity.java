package com.kish2.hermitcrabapp.view.activities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.bean.HermitCrabVectorIllustrations;
import com.kish2.hermitcrabapp.custom.view.CustomCountDownTimer;
import com.kish2.hermitcrabapp.custom.view.StatusFixedToolBar;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.activities.RegisterPresenter;
import com.kish2.hermitcrabapp.utils.view.BitMapAndDrawableUtil;
import com.kish2.hermitcrabapp.utils.security.InputCheckUtil;
import com.kish2.hermitcrabapp.utils.dev.StatusBarUtil;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;
import com.kish2.hermitcrabapp.utils.view.ToastUtil;
import com.kish2.hermitcrabapp.view.BaseActivity;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.sft_toolbar_top)
    StatusFixedToolBar mToolBar;

    /* 三个图标*/
    @BindView(R.id.iv_user_icon)
    ImageView mUserIcon;
    @BindView(R.id.iv_verify_icon)
    ImageView mVerifyIcon;

    /* 两个输入框*/
    @BindView(R.id.et_mobile_input)
    EditText mMobileInput;
    @BindView(R.id.et_verify_input)
    EditText mVerifyInput;

    @BindView(R.id.cpb_register_submit)
    public CircularProgressButton mRegisterSubmit;
    @BindView(R.id.tv_verify_send)
    TextView mVerifySend;
    @BindView(R.id.ib_mobile_clear)
    ImageButton mMobileClear;

    private GradientDrawable mBGDrawable;
    private CustomCountDownTimer mTimer;
    private RegisterPresenter mPresenter;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MessageForHandler.DATA_LOADED:
                        mRegisterSubmit.setBackground(mBGDrawable);
                        break;
                    default:
                        break;
                }
            }
        };

        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        attachPresenter();
        getAndSetLayoutView();
        new Thread() {
            @Override
            public void run() {
                registerViewComponentsAffairs();
                loadData();
            }
        }.start();
    }

    @Override
    public void getLayoutComponentsAttr() {

    }

    @Override
    public void getAndSetLayoutView() {
        StatusBarUtil.setTranslucentStatus(this);
        mToolBar.bindAndSetThisToolbar(this, false, null);
        mUserIcon.setImageDrawable(HermitCrabVectorIllustrations.VI_MOBILE);
        mVerifyIcon.setImageDrawable(HermitCrabVectorIllustrations.VI_VERIFY);
        mVerifySend.setTextColor(ThemeUtil.Theme.afterGetResourcesColorId);
        mMobileClear.setImageDrawable(HermitCrabVectorIllustrations.VI_CLEAR);
    }

    @Override
    public void loadData() {
        mBGDrawable = BitMapAndDrawableUtil.getGradientCircleDrawable(this);
        mHandler.sendEmptyMessage(MessageForHandler.DATA_LOADED);
    }

    @Override
    public void refreshData() {

    }

    @Override
    public void registerViewComponentsAffairs() {
        mToolBar.setNavigationOnClickListener(v -> finish());
        mRegisterSubmit.setOnClickListener(v -> {
            if (!InputCheckUtil.isValidIdentify(getMobile())) {
                ToastUtil.showToast(this, "您输入的是无效的手机号哦~", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_CENTER);
            } else {
                mRegisterSubmit.startAnimation();
                mPresenter.register();
            }
        });
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
                if (!InputCheckUtil.isValidIdentify(getMobile())) {
                    ToastUtil.showToast(this, "您输入的是无效的手机号哦~", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_CENTER);
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
            mTimer = new CustomCountDownTimer(60500, 1000, mVerifySend, this);
            mTimer.start();
        });

    }

    public String getMobile() {
        return this.mMobileInput.getText().toString();
    }

    @Override
    public void attachPresenter() {
        this.mPresenter = new RegisterPresenter(this);
    }

    @Override
    public void detachPresenter() {
        this.mPresenter.detachView();
    }

    private void disposeTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    protected void onDestroy() {
        detachPresenter();
        mRegisterSubmit.dispose();
        /* 防止计时器内存泄漏*/
        disposeTimer();
        super.onDestroy();
    }
}

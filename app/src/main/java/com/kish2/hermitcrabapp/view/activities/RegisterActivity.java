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
import com.kish2.hermitcrabapp.bean.VectorIllustrations;
import com.kish2.hermitcrabapp.custom.CustomCountDownTimer;
import com.kish2.hermitcrabapp.custom.StatusFixedToolBar;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.activities.RegisterPresenter;
import com.kish2.hermitcrabapp.utils.BitMapAndDrawableUtil;
import com.kish2.hermitcrabapp.utils.InputCheckUtil;
import com.kish2.hermitcrabapp.utils.StatusBarUtil;
import com.kish2.hermitcrabapp.utils.ThemeUtil;
import com.kish2.hermitcrabapp.utils.ToastUtil;
import com.kish2.hermitcrabapp.view.BaseActivity;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.sft_action_bar)
    StatusFixedToolBar mToolBar;

    /* 三个图标*/
    @BindView(R.id.iv_user_icon)
    ImageView mUserIcon;
    @BindView(R.id.iv_password_icon)
    ImageView mPasswordIcon;
    @BindView(R.id.iv_verify_icon)
    ImageView mVerifyIcon;

    /* 三个输入框*/
    @BindView(R.id.et_mobile_input)
    EditText mMobileInput;
    @BindView(R.id.et_password_input)
    EditText mPasswordInput;
    @BindView(R.id.et_verify_input)
    EditText mVerifyInput;

    @BindView(R.id.cpb_register_submit)
    CircularProgressButton mRegisterSubmit;
    @BindView(R.id.tv_verify_send)
    TextView mVerifySend;
    @BindView(R.id.ib_mobile_clear)
    ImageButton mMobileClear;
    @BindView(R.id.ib_password_clear)
    ImageButton mPasswordClear;

    private GradientDrawable mBGDrawable;
    private Bitmap bitmap;
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
                    case RegisterPresenter.REGISTER_SUCCESS:
                        mRegisterSubmit.doneLoadingAnimation(ThemeUtil.Theme.afterGetResourcesColorId, bitmap);
                        mPresenter.registerSuccess();
                        break;
                    case MessageForHandler.DATA_LOADED:
                        mRegisterSubmit.setBackground(mBGDrawable);
                        break;
                    case RegisterPresenter.REGISTER_FAILURE:
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
                loadData();
                registerViewComponentsAffairs();
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
        mUserIcon.setImageDrawable(VectorIllustrations.VI_MOBILE);
        mPasswordIcon.setImageDrawable(VectorIllustrations.VI_PASSWORD);
        mVerifyIcon.setImageDrawable(VectorIllustrations.VI_VERIFY);
        mVerifySend.setTextColor(ThemeUtil.Theme.afterGetResourcesColorId);
    }

    @Override
    public void loadData() {
        mBGDrawable = BitMapAndDrawableUtil.getGradientDrawable(this);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_check_white);
        mHandler.sendEmptyMessage(MessageForHandler.DATA_LOADED);
    }

    @Override
    public void registerViewComponentsAffairs() {
        mToolBar.setNavigationOnClickListener(v -> finish());
        mRegisterSubmit.setOnClickListener(v -> {
            mRegisterSubmit.startAnimation();
            mPresenter.register();
        });
        mMobileInput.setOnClickListener(v -> {
            mPasswordInput.clearFocus();
            mVerifyInput.clearFocus();
            mMobileInput.setFocusableInTouchMode(true);
            mMobileInput.requestFocus();
        });
        mPasswordInput.setOnClickListener(v -> {
            mMobileInput.clearFocus();
            mVerifyInput.clearFocus();
            mPasswordInput.setFocusableInTouchMode(true);
            mPasswordInput.requestFocus();
        });
        mVerifyInput.setOnClickListener(v -> {
            mMobileInput.clearFocus();
            mPasswordInput.clearFocus();
            mVerifyInput.setFocusableInTouchMode(true);
            mVerifyInput.requestFocus();
        });
        mMobileInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mMobileInput.setActivated(true);
                mPasswordInput.setActivated(false);
                mVerifyInput.setActivated(false);
            } else {
                if (!InputCheckUtil.isValidMobile(getMobile())) {
                    ToastUtil.showToast(this, "您输入的是无效的手机号哦~", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_CENTER);
                }
            }
        });
        mPasswordInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mPasswordInput.setActivated(true);
                mMobileInput.setActivated(false);
                mVerifyInput.setActivated(false);
            }
        });
        mVerifyInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mVerifyInput.setActivated(true);
                mMobileInput.setActivated(false);
                mPasswordInput.setActivated(false);
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
        mPasswordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0)
                    mPasswordClear.setVisibility(View.VISIBLE);
                else mPasswordClear.setVisibility(View.INVISIBLE);
            }
        });
        mMobileClear.setOnClickListener(v -> {
            mMobileInput.setText(null);
        });
        mPasswordClear.setOnClickListener(v -> {
            mPasswordInput.setText(null);
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

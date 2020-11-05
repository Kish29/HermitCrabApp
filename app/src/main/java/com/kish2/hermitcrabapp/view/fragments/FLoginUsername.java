package com.kish2.hermitcrabapp.view.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.bean.HermitCrabVectorIllustrations;
import com.kish2.hermitcrabapp.utils.view.ToastUtil;
import com.kish2.hermitcrabapp.view.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FLoginUsername extends BaseFragment
        implements
        View.OnClickListener,
        View.OnFocusChangeListener,
        TextWatcher {

    // 登录信息输入框
    @BindView(R.id.iv_user_icon)
    ImageView mUserIcon;
    @BindView(R.id.iv_password_icon)
    ImageView mPasswordIcon;

    @BindView(R.id.et_identify_input)
    EditText mUsernameInput;
    // 清空输入
    @BindView(R.id.ib_identify_clear)
    ImageButton mClearUsername;
    // 密码
    @BindView(R.id.et_password_input)
    EditText mPasswordInput;
    // 清空密码
    @BindView(R.id.ib_password_clear)
    ImageButton mClearPassword;

    @Override
    protected void themeChanged() {
        getAndSetLayoutView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View loginUsername = inflater.inflate(R.layout.ly_login_username, container, false);// 视图与父容器ViewGroup不需要连接
        ButterKnife.bind(this, loginUsername);
        getAndSetLayoutView();
        /* 注册事件 */
        registerViewComponentsAffairs();
        return loginUsername;
    }

    @Override
    public void getAndSetLayoutView() {
        mUserIcon.setImageDrawable(HermitCrabVectorIllustrations.VI_USER);
        mPasswordIcon.setImageDrawable(HermitCrabVectorIllustrations.VI_PASSWORD);
        mClearUsername.setBackground(HermitCrabVectorIllustrations.VI_CLEAR);
        mClearPassword.setBackground(HermitCrabVectorIllustrations.VI_CLEAR);
    }

    public String getUsername() {
        return this.mUsernameInput.getText().toString().trim();
    }

    public String getPassword() {
        return this.mPasswordInput.getText().toString().trim();
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
        /* 登录信息 */
        // 点击事件
        mUsernameInput.setOnClickListener(this);
        // 其它事件
        mUsernameInput.setOnFocusChangeListener(this);   // 焦点变化
        mUsernameInput.addTextChangedListener(this);     // 输入发生变化
        // 清空输入按钮
        mClearUsername.setOnClickListener(this);

        /* 密码 */
        mPasswordInput.setOnClickListener(this);
        mPasswordInput.setOnFocusChangeListener(this);
        mPasswordInput.addTextChangedListener(this);
        mClearPassword.setOnClickListener(this);
    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /* 焦点处于此处*/
            case R.id.et_identify_input:
                mPasswordInput.clearFocus();
                mUsernameInput.setFocusableInTouchMode(true);
                mUsernameInput.requestFocus();
                break;
            case R.id.et_password_input:
                mUsernameInput.clearFocus();
                mPasswordInput.setFocusableInTouchMode(true);
                mPasswordInput.requestFocus();
                break;
            case R.id.ib_identify_clear:
                mUsernameInput.setText(null);
                break;
            case R.id.ib_password_clear:
                mPasswordInput.setText(null);
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.et_identify_input) {
            if (hasFocus) {
                mUsernameInput.setActivated(true);
                mPasswordInput.setActivated(false);
            }
        } else {
            if (hasFocus) {
                mPasswordInput.setActivated(true);
                mUsernameInput.setActivated(false);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String inputIdentify = mUsernameInput.getText().toString();
        String inputPassword = mPasswordInput.getText().toString();

        /* 是否显示清除按钮 */
        if (inputIdentify.length() > 0) {
            mClearUsername.setVisibility(View.VISIBLE);
        } else {
            mClearUsername.setVisibility(View.INVISIBLE);
        }

        if (inputPassword.length() > 0) {
            mClearPassword.setVisibility(View.VISIBLE);
        } else {
            mClearPassword.setVisibility(View.INVISIBLE);
        }
    }
}

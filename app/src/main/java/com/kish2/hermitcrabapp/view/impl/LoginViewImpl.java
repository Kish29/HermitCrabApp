package com.kish2.hermitcrabapp.view.impl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kish2.hermitcrabapp.MainActivity;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.present.impl.LoginPresenterImpl;
import com.kish2.hermitcrabapp.view.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("Registered")
public class LoginViewImpl extends AppCompatActivity
        implements LoginView,
        View.OnClickListener,
        View.OnFocusChangeListener,
        ViewTreeObserver.OnGlobalFocusChangeListener,
        TextWatcher {

    /* 登录界面具有组件 */
    // 返回按钮
    @BindView(R.id.ib_navigate_back)
    ImageButton mTopBarNavigationBack;
    @BindView(R.id.ll_retrieve_bar)
    LinearLayout mLayBackBar;
    // 登录 logo
    /*@BindView(R.id.iv_login_logo)
    ImageView loginLogo;*/

    // 登录信息输入框
    @BindView(R.id.et_identify_input)
    EditText mIdentify;
    // 清空输入
    @BindView(R.id.ib_login_identify_input_del)
    ImageButton mClearIdentify;
    // 密码
    @BindView(R.id.et_password_input)
    EditText mPassword;
    // 清空密码
    @BindView(R.id.ib_login_password_input_del)
    ImageButton mClearPassword;
    // 登录/注册按钮
    @BindView(R.id.btn_login_submit)
    Button mLoginSubmit;
    @BindView(R.id.btn_register_submit)
    Button mRegisterSubmit;

    // 自动登录
    @BindView(R.id.cb_remember_account)
    CheckBox mRememberUser;
    // 忘记密码
    @BindView(R.id.tv_forget_pwd)
    TextView mForgetPassword;

    // 下拉层、其他登录方式层
    @BindView(R.id.ll_login_layer)
    View mLoginLayer;
    @BindView(R.id.ll_login_pull)
    LinearLayout mLoginPull;
    @BindView(R.id.ll_login_options)
    LinearLayout mLoginOptions;

    // 其它登录方式
    @BindView(R.id.ib_login_wx)
    ImageButton mLoginWeChat;
    @BindView(R.id.ib_login_qq)
    ImageButton mLoginQQ;

    /* identify 和密码 */
    private String identify;
    private String password;

    /*private int mLogoHeight;
    private int mLogoWidth;*/

    private final String mobileStandard = "^1[3|4|5|7|8][0-9]{9}";

    private LoginPresenterImpl loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        this.loginPresenter = new LoginPresenterImpl(this);
        ButterKnife.bind(this);
        initView();
    }

    /* 注册视图组件的监听事件 */
    private void initView() {
        // 返回按钮
        mTopBarNavigationBack.setOnClickListener(this);
        mLayBackBar.getViewTreeObserver().addOnGlobalFocusChangeListener(this);
        // 登录信息部分
        /* 登录信息 */
        // 点击事件
        mIdentify.setOnClickListener(this);
        // 其它事件
        mIdentify.setOnFocusChangeListener(this);   // 焦点变化
        mIdentify.addTextChangedListener(this);     // 输入发生变化
        // 清空输入按钮
        mClearIdentify.setOnClickListener(this);

        /* 密码 */
        mPassword.setOnClickListener(this);
        mPassword.setOnFocusChangeListener(this);
        mPassword.addTextChangedListener(this);
        mClearPassword.setOnClickListener(this);

        /* 登录按钮 */
        mLoginSubmit.setOnClickListener(this);
        /* 注册按钮 */
        mRegisterSubmit.setOnClickListener(this);

        // 其它登录方式下拉层
        mLoginPull.setOnClickListener(this);
    }


    @Override
    public String getIdentify() {
        if (isValidPassword(this.identify))
            return this.identify;
        else
            return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void login() {
        Intent intent = new Intent(this, MainActivity.class);
        // 清除栈顶activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void register() {
        Intent intent = new Intent(this, LoginViewImpl.class);
        startActivity(intent);
    }

    @Override
    public void forgetPassword() {

    }

    @Override
    public void rememberUser() {

    }

    @Override
    public void pullOtherLoginView() {
        /* 停止当前动画 */
        /* 并在监听出获取进度 */
        mLoginLayer.animate().cancel();
        mLoginPull.animate().cancel();

        /* 获取被隐藏的高度 */
        int height = mLoginOptions.getHeight();

        /* 获取此时已经滑动的进度 */
        float progress = (mLoginLayer.getTag() != null && mLoginLayer.getTag() instanceof Float) ?
                (float) mLoginLayer.getTag() : 1;

        /* 根据进度设定时间 */
        int time = (int) (360 * progress);

        /* 已经在向上滑动的过程或者已经向上滑完*/
        if (mLoginPull.getTag() != null) {
            mLoginPull.setTag(null);
            glide(height, progress, time);
        } else {
            /* 上滑标志true */
            mLoginPull.setTag(true);
            upGlide(height, progress, time);
        }

    }

    /* 向下收起 */
    private void glide(int height, float progress, int time) {
        Log.d("glide", "glide method running...");
        mLoginPull.animate()
                .translationYBy(height - height * progress)
                .translationY(height)
                .setDuration(time)
                .start();

        /* 下滑，layer从(alphaBy(1 * progress))变成完全透明(alpha(0)) */
        mLoginLayer.animate()
                .alphaBy(1 * progress)
                .alpha(0)
                .setDuration(time)
                .setListener(new AnimatorListenerAdapter() {
                    /* 动画取消时的获取此时进度 */
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            mLoginLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            mLoginLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                        /* 动画结束，不可见 */
                        mLoginLayer.setVisibility(View.INVISIBLE);
                    }
                })
                .start();
    }

    /* 向上滑出 */
    private void upGlide(int height, float progress, int time) {
        Log.d("glide", "upGlide method running...");
        mLoginPull.animate()
                .translationYBy(height * progress)      //从height * progress 到 0
                .translationY(0)
                .setDuration(time)
                .start();

        /* 上滑，layer从(alphaBy(progress))变成完全不透明(alpha(1)) */
        mLoginLayer.animate()
                .alphaBy(1 - progress)
                .alpha(1)
                .setDuration(time)
                .setListener(new AnimatorListenerAdapter() {
                    /* 变为可见 */
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mLoginLayer.setVisibility(View.VISIBLE);
                    }

                    /* 取消时获取进度 */
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            mLoginLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            mLoginLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                    }

                })
                .start();
    }

    @Override
    public boolean isValidIdentify(String identify) {
        return false;
    }

    @Override
    public boolean isValidPassword(String password) {
        return true;
    }


    @Override
    public void loginByWeChat() {

    }

    @Override
    public void loginByQQ() {

    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {
        this.loginPresenter.detachView();
    }

    @Override
    public void navigationBack() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    protected void onDestroy() {
        detachPresenter();
        super.onDestroy();
    }

    /* 重写点击事件 */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_navigate_back:
                /*返回*/
                finish();
                break;
            /*焦点处于此处*/
            case R.id.et_identify_input:
                mPassword.clearFocus();
                mIdentify.setFocusableInTouchMode(true);
                mIdentify.requestFocus();
                break;
            case R.id.et_password_input:
                mIdentify.clearFocus();
                mPassword.setFocusableInTouchMode(true);
                mPassword.requestFocus();
                break;
            case R.id.ib_login_identify_input_del:
                mIdentify.setText(null);
                break;
            case R.id.ib_login_password_input_del:
                mPassword.setText(null);
                break;
            case R.id.btn_login_submit:
                login();
                break;
            case R.id.btn_register_submit:
                register();
                break;
            case R.id.cb_remember_account:
                rememberUser();
                break;
            case R.id.tv_forget_pwd:
                forgetPassword();
                break;
            case R.id.ll_login_layer:
            case R.id.ll_login_pull:
                pullOtherLoginView();
                break;
            case R.id.ib_login_wx:
                loginByWeChat();
                break;
            case R.id.ib_login_qq:
                loginByQQ();
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /* 用户还没有输入字符串 */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /* 用户输入了字符串 */
    @Override
    public void afterTextChanged(Editable s) {
        /* 检测输入是否合法 */
        /*isValidIdentify();
        isValidPassword();*/

        String inputIdentify = mIdentify.getText().toString().trim();
        String inputPassword = mPassword.getText().toString().trim();
        Log.d("input", inputIdentify);
        Log.d("input", inputPassword);

        /* 是否显示清除按钮 */
        if (inputIdentify.length() > 0) {
            mClearIdentify.setVisibility(View.VISIBLE);
        } else {
            mClearIdentify.setVisibility(View.INVISIBLE);
        }

        if (inputPassword.length() > 0) {
            mClearPassword.setVisibility(View.VISIBLE);
        } else {
            mClearPassword.setVisibility(View.INVISIBLE);
        }

    }

    /* 用户名/密码焦点改变 */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.et_identify_input) {
            if (hasFocus) {
                mIdentify.setActivated(true);
                mPassword.setActivated(false);
            }
        } else {
            if (hasFocus) {
                mPassword.setActivated(true);
                mIdentify.setActivated(false);
            }
        }
    }

    @Override
    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
        /*final ImageView ivLogo = this.loginLogo;
        Rect KeypadRect = new Rect();

        mLayBackBar.getWindowVisibleDisplayFrame(KeypadRect);

        int screenHeight = mLayBackBar.getRootView().getHeight();
        int keypadHeight = screenHeight - KeypadRect.bottom;

        //隐藏logo
        if (keypadHeight > 300 && ivLogo.getTag() == null) {
            final int height = ivLogo.getHeight();
            final int width = ivLogo.getWidth();
            this.mLogoHeight = height;
            this.mLogoWidth = width;

            ivLogo.setTag(true);

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = ivLogo.getLayoutParams();
                    layoutParams.height = (int) (height * animatedValue);
                    layoutParams.width = (int) (width * animatedValue);
                    ivLogo.requestLayout();
                    ivLogo.setAlpha(animatedValue);
                }
            });

            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();
        }
        //显示logo
        else if (keypadHeight < 300 && ivLogo.getTag() != null) {
            final int height = mLogoHeight;
            final int width = mLogoWidth;

            ivLogo.setTag(null);

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = ivLogo.getLayoutParams();
                    layoutParams.height = (int) (height * animatedValue);
                    layoutParams.width = (int) (width * animatedValue);
                    ivLogo.requestLayout();
                    ivLogo.setAlpha(animatedValue);
                }
            });

            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();
        }*/
    }
}

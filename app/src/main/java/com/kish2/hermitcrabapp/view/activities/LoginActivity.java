package com.kish2.hermitcrabapp.view.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.custom.view.FixedVideoView;
import com.kish2.hermitcrabapp.custom.view.StatusFixedToolBar;
import com.kish2.hermitcrabapp.custom.listener.OnClickMayTriggerFastRepeatListener;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.activities.LoginPresenter;
import com.kish2.hermitcrabapp.utils.security.InputCheckUtil;
import com.kish2.hermitcrabapp.utils.view.BitMapAndDrawableUtil;
import com.kish2.hermitcrabapp.utils.dev.StatusBarUtil;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;
import com.kish2.hermitcrabapp.utils.view.ToastUtil;
import com.kish2.hermitcrabapp.view.BaseActivity;
import com.kish2.hermitcrabapp.view.fragments.FLoginMobile;
import com.kish2.hermitcrabapp.view.fragments.FLoginUsername;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("Registered")
public class LoginActivity extends BaseActivity
        implements
        View.OnFocusChangeListener,
        TextWatcher,
        View.OnTouchListener {

    /* 背景视频 */
    @BindView(R.id.login_reg_video)
    FixedVideoView mBGVideo;

    /* ToolBar*/
    @BindView(R.id.sft_toolbar_top)
    StatusFixedToolBar mToolBar;

    /* 登录界面容器 */
    @BindView(R.id.ll_login_container)
    LinearLayout mLoginContainer;
    /* 登录界面具有组件 */

    // 改变登录方式
    @BindView(R.id.tv_change_login_type)
    TextView mLoginTypeChange;

    // 登录/注册按钮
    @BindView(R.id.cpb_login_submit)
    public CircularProgressButton mLoginSubmit;
    @BindView(R.id.btn_register_jump)
    Button mRegister;

    // 自动登录
    @BindView(R.id.cb_remember_account)
    CheckBox mRememberUser;
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

    private GradientDrawable mBGDrawable;
    private String mVideoPath;
    private LoginPresenter mPresenter;

    /* 两种登录方式的fragment */
    private FLoginUsername mFLoginUsername;
    private FLoginMobile mFLoginMobile;

    /* 滑出时间 */
    private static final int glideTime = 200;

    @Override
    protected void themeChanged() {

    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MessageForHandler.DATA_LOADED:
                        mLoginSubmit.setBackground(mBGDrawable);
                        /*mBGVideo.setVideoPath(mVideoPath);
                        mBGVideo.start();*/
                        break;
                    default:
                        break;
                }
            }
        };

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        /* 绑定presenter */
        attachPresenter();
        getAndSetLayoutView();
        registerViewComponentsAffairs();
        new Thread() {
            @Override
            public void run() {
                loadData();
            }
        }.start();
    }

    public void getLayoutComponentsAttr() {

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void getAndSetLayoutView() {
        StatusBarUtil.setTranslucentStatus(this);
        mToolBar.bindAndSetThisToolbar(this, false, null);
        mRememberUser.setButtonDrawable(ThemeUtil.CHECK_BOX_SELECTOR);
    }

    @Override
    public void loadData() {
        this.mFLoginUsername = new FLoginUsername();
        this.mFLoginMobile = new FLoginMobile();
        changeFragment(R.id.fcv_login_type, this.mFLoginUsername);
        mBGDrawable = BitMapAndDrawableUtil.getGradientCircleDrawable(this);
        mVideoPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bg_video).toString();
        mHandler.sendEmptyMessage(MessageForHandler.DATA_LOADED);
    }


    @Override
    public void refreshData() {

    }

    /* 注册视图组件的监听事件 */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void registerViewComponentsAffairs() {
        mToolBar.setNavigationOnClickListener(v -> finish());
        /* 登录按钮 */
        mLoginSubmit.setOnClickListener(v -> {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fcv_login_type);
            if (fragment instanceof FLoginUsername) {
                String username = this.mFLoginUsername.getUsername();
                String password = this.mFLoginUsername.getPassword();
                if (username.equals("") || password.equals("")) {
                    ToastUtil.showToast(this, "未输入用户名或密码", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_CENTER);
                } else {
                    this.mPresenter.loginByUsername(username, password);
                }
            } else {
                String mobile = this.mFLoginMobile.getMobile();
                String checkCode = this.mFLoginMobile.getVerifyCode();
                if (!InputCheckUtil.isValidMobile(mobile)) {
                    ToastUtil.showToast(this, "您输入的是无效的手机号哦~", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_CENTER);
                } else {
                    mPresenter.loginByMobile(mobile, checkCode);
                }
            }
        });
        /* 注册按钮 */
        mRegister.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        /* 自动登录和忘记密码 */
//        mRememberUser.setOnClickListener(v -> mPresenter.rememberUser(mRememberUser.isChecked()));
        mLoginTypeChange.setOnClickListener(v -> {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fcv_login_type);
            if (fragment instanceof FLoginUsername) {
                changeFragment(R.id.fcv_login_type, mFLoginMobile);
                mLoginTypeChange.setText("用户名登录");
            } else {
                changeFragment(R.id.fcv_login_type, mFLoginUsername);
                mLoginTypeChange.setText("短信验证码登录");
            }
        });

        // 其它登录方式下拉层
        mLoginPull.setOnClickListener(v -> {
            pullOtherLoginView();
        });
        mLoginWeChat.setOnClickListener(v -> {
            mPresenter.loginByWeChat();
        });
        mLoginQQ.setOnClickListener(v -> {
            mPresenter.loginByQQ();
        });

        // 按钮的touch动效
        mLoginWeChat.setOnTouchListener(this);
        mLoginQQ.setOnTouchListener(this);

        /*mBGVideo.setOnPreparedListener(mp -> {
            mp.start();
            mp.setLooping(true);
        });
        mBGVideo.setOnCompletionListener(mp -> {
            mBGVideo.setVideoPath(mVideoPath);
            mBGVideo.start();
        });*/
    }

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
        int time = (int) (glideTime * progress);

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

        mLoginLayer.setOnClickListener(null);

    }

    /* 向上滑出 */
    private void upGlide(int height, float progress, int time) {
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

        mLoginLayer.setOnClickListener(v -> glide(mLoginOptions.getHeight(), 1, glideTime));
    }

    @Override
    public void attachPresenter() {
        this.mPresenter = new LoginPresenter(this);
        /* 添加观察者*/
        getLifecycle().addObserver(this.mPresenter);
    }

    @Override
    public void detachPresenter() {
        this.mPresenter.detachView();
    }

    @Override
    protected void onDestroy() {
        detachPresenter();
        mLoginSubmit.dispose();
        super.onDestroy();
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


    }

    /* 用户名/密码焦点改变 */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                scaleView(true, v);
                break;
            case MotionEvent.ACTION_UP:
                scaleView(false, v);
                break;
            default:
                break;
        }
        return false;
    }

    private void scaleView(boolean zoomOut, View... views) {
        for (View view : views) {
            if (zoomOut) {
                view.setScaleX(0.8f);
                view.setScaleY(0.8f);
            } else {
                view.setScaleX(1.0f);
                view.setScaleY(1.0f);
            }
        }
    }
}


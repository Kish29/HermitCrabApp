package com.kish2.hermitcrabapp.view.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.bean.VectorIllustrations;
import com.kish2.hermitcrabapp.custom.FixedVideoView;
import com.kish2.hermitcrabapp.custom.StatusFixedToolBar;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.activities.LoginPresenter;
import com.kish2.hermitcrabapp.utils.BitMapAndDrawableUtil;
import com.kish2.hermitcrabapp.utils.StatusBarUtil;
import com.kish2.hermitcrabapp.utils.ThemeUtil;
import com.kish2.hermitcrabapp.view.BaseActivity;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("Registered")
public class LoginActivity extends BaseActivity
        implements
        View.OnClickListener,
        View.OnFocusChangeListener,
        TextWatcher,
        View.OnTouchListener {

    /* 背景视频 */
    @BindView(R.id.login_reg_video)
    FixedVideoView mBGVideo;

    /* ToolBar*/
    @BindView(R.id.sft_action_bar)
    StatusFixedToolBar mToolBar;

    /* 登录界面容器 */
    @BindView(R.id.ll_login_container)
    LinearLayout mLoginContainer;
    /* 登录界面具有组件 */

    // 登录信息输入框
    @BindView(R.id.iv_user_icon)
    ImageView mICUserInput;
    @BindView(R.id.iv_password_icon)
    ImageView mICPasswordInput;

    @BindView(R.id.et_identify_input)
    EditText mIdentify;
    // 清空输入
    @BindView(R.id.ib_identify_clear)
    ImageButton mClearIdentify;
    // 密码
    @BindView(R.id.et_password_input)
    EditText mPassword;
    // 清空密码
    @BindView(R.id.ib_password_clear)
    ImageButton mClearPassword;
    // 登录/注册按钮
    @BindView(R.id.cpb_login_submit)
    CircularProgressButton mLoginSubmit;
    @BindView(R.id.btn_register_jump)
    Button mRegisterSubmit;

    // 自动登录
    @BindView(R.id.cb_remember_account)
    CheckBox mRememberUser;
    // 忘记密码
    @BindView(R.id.tv_login_by_verify_code)
    TextView mLoginByVerifyCode;

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
    private Bitmap mBitMap;
    private LoginPresenter mPresenter;

    /* 滑出时间 */
    private static final int glideTime = 200;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case LoginPresenter.LOGIN_SUCCESS:
                        mLoginSubmit.doneLoadingAnimation(ThemeUtil.Theme.afterGetResourcesColorId, mBitMap);
                        mPresenter.loginSuccess();
                        break;
                    case MessageForHandler.DATA_LOADED:
                        mLoginSubmit.setBackground(mBGDrawable);
                        /*mBGVideo.setVideoPath(mVideoPath);
                        mBGVideo.start();*/
                        break;
                    case LoginPresenter.LOGIN_FAILURE:
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
        new Thread() {
            @Override
            public void run() {
                loadData();
                registerViewComponentsAffairs();
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
        mICUserInput.setImageDrawable(VectorIllustrations.VI_USER);
        mICPasswordInput.setImageDrawable(VectorIllustrations.VI_PASSWORD);
        mClearIdentify.setBackground(VectorIllustrations.VI_CLEAR);
        mClearPassword.setBackground(VectorIllustrations.VI_CLEAR);
    }

    @Override
    public void loadData() {
        mBGDrawable = BitMapAndDrawableUtil.getGradientDrawable(this);
        mBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_check_white);
        mVideoPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bg_video).toString();
        mHandler.sendEmptyMessage(MessageForHandler.DATA_LOADED);
    }

    /* 注册视图组件的监听事件 */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void registerViewComponentsAffairs() {
        mToolBar.setNavigationOnClickListener(v -> finish());
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

        /* 自动登录和忘记密码 */
        mRememberUser.setOnClickListener(this);
        mLoginByVerifyCode.setOnClickListener(v -> {

        });

        // 其它登录方式下拉层
        mLoginPull.setOnClickListener(this);
        mLoginWeChat.setOnClickListener(this);
        mLoginQQ.setOnClickListener(this);

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

    public String getIdentify() {
        return this.mIdentify.getText().toString();
    }

    public String getPassword() {
        return this.mPassword.getText().toString().trim();
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

        mLoginLayer.setOnClickListener(this);
    }

    @Override
    public void attachPresenter() {
        this.mPresenter = new LoginPresenter(this);
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

    /* 重写点击事件 */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.ib_identify_clear:
                mIdentify.setText(null);
                break;
            case R.id.ib_password_clear:
                mPassword.setText(null);
                break;
            case R.id.cpb_login_submit:
                mLoginSubmit.startAnimation();
                this.mPresenter.login();
                break;
            case R.id.btn_register_jump:
                mLoginSubmit.revertAnimation();
                this.mPresenter.register();
                break;
            case R.id.cb_remember_account:
                this.mPresenter.rememberUser(mRememberUser.isChecked());
                break;
            case R.id.tv_login_by_verify_code:
                this.mPresenter.forgetPassword();
                break;
            case R.id.ll_login_layer:
                glide(mLoginOptions.getHeight(), 1, glideTime);
                break;
            case R.id.ll_login_pull:
                pullOtherLoginView();
                break;
            case R.id.ib_login_wx:
                this.mPresenter.loginByWeChat();
                break;
            case R.id.ib_login_qq:
                this.mPresenter.loginByQQ();
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
        String inputIdentify = mIdentify.getText().toString();
        String inputPassword = mPassword.getText().toString();

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


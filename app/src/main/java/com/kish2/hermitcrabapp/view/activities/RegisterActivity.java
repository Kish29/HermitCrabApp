package com.kish2.hermitcrabapp.view.activities;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.custom.view.StatusFixedToolBar;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.impl.UserPresenterImpl;
import com.kish2.hermitcrabapp.utils.dev.StatusBarUtil;
import com.kish2.hermitcrabapp.utils.security.InputCheckUtil;
import com.kish2.hermitcrabapp.utils.view.BitMapAndDrawableUtil;
import com.kish2.hermitcrabapp.utils.view.ToastUtil;
import com.kish2.hermitcrabapp.view.BaseActivity;
import com.kish2.hermitcrabapp.view.fragments.FLoginMobile;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.sft_toolbar_top)
    StatusFixedToolBar mToolBar;

    @BindView(R.id.cpb_register_submit)
    public CircularProgressButton mRegisterSubmit;

    private GradientDrawable mBGDrawable;
    private UserPresenterImpl mPresenter;
    private FLoginMobile mFragment;

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
        registerViewComponentsAffairs();
        new Thread() {
            @Override
            public void run() {
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
    }

    @Override
    public void loadData() {
        this.mFragment = new FLoginMobile();
        changeFragment(R.id.fcv_login_type, this.mFragment);
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
            String mobile = this.mFragment.getMobile();
            String checkCode = this.mFragment.getVerifyCode();
            if (!InputCheckUtil.isValidMobile(mobile)) {
                ToastUtil.showToast(this, "您输入的是无效的手机号哦~", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_CENTER);
            } else {
                mPresenter.register(mobile, checkCode);
            }
        });

    }

    @Override
    public void attachPresenter() {
        this.mPresenter = new UserPresenterImpl();
        this.mPresenter.bindView(this);
    }

    @Override
    public void detachPresenter() {
        this.mPresenter.detachView();
    }

    @Override
    protected void onDestroy() {
        mRegisterSubmit.dispose();
        super.onDestroy();
    }
}

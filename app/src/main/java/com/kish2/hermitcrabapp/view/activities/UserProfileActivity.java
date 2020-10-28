package com.kish2.hermitcrabapp.view.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.custom.view.StatusFixedToolBar;
import com.kish2.hermitcrabapp.presenter.activities.UserProfilePresenter;
import com.kish2.hermitcrabapp.utils.App;
import com.kish2.hermitcrabapp.utils.dev.StatusBarUtil;
import com.kish2.hermitcrabapp.utils.view.KZDialogUtil;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;
import com.kish2.hermitcrabapp.utils.view.ToastUtil;
import com.kish2.hermitcrabapp.view.BaseActivity;
import com.kongzue.dialog.v3.InputDialog;
import com.kongzue.dialog.v3.MessageDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProfileActivity extends BaseActivity {

    @BindView(R.id.sft_toolbar_top)
    StatusFixedToolBar mToolBar;

    @BindView(R.id.ll_user_avatar)
    ViewGroup mUserAvatar;
    @BindView(R.id.ll_username)
    ViewGroup mUsername;
    @BindView(R.id.ll_user_gender)
    ViewGroup mUserGender;
    @BindView(R.id.ll_bind_mobile)
    ViewGroup mBindMobile;
    @BindView(R.id.ll_bind_email)
    ViewGroup mBindEmail;
    @BindView(R.id.ll_change_password)
    ViewGroup mChangePwd;
    @BindView(R.id.ll_bind_student_id)
    ViewGroup mBindStudentId;
    @BindView(R.id.ll_bind_user_grade)
    ViewGroup mUserGrade;
    @BindView(R.id.ll_bind_student_department)
    ViewGroup mBindDepartment;

    public StatusFixedToolBar getmToolBar() {
        return mToolBar;
    }

    public ViewGroup getmUserAvatar() {
        return mUserAvatar;
    }

    public ViewGroup getmUsername() {
        return mUsername;
    }

    public ViewGroup getmUserGender() {
        return mUserGender;
    }

    public ViewGroup getmBindMobile() {
        return mBindMobile;
    }

    public ViewGroup getmBindEmail() {
        return mBindEmail;
    }

    public ViewGroup getmChangePwd() {
        return mChangePwd;
    }

    public ViewGroup getmBindStudentId() {
        return mBindStudentId;
    }

    public ViewGroup getmUserGrade() {
        return mUserGrade;
    }

    public ViewGroup getmBindDepartment() {
        return mBindDepartment;
    }

    private UserProfilePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ButterKnife.bind(this);
        attachPresenter();
        getAndSetLayoutView();
        new Thread() {
            @Override
            public void run() {
                registerViewComponentsAffairs();
            }
        }.start();
    }

    @Override
    public void getAndSetLayoutView() {
        StatusBarUtil.setSinkStatusBar(this, false, ThemeUtil.Theme.afterGetResourcesColorId);
        mToolBar.bindAndSetThisToolbar(this, true, "个人信息");
        new Handler().postDelayed((Runnable) () -> {
            /* title 部分 */
            ((TextView) mUserAvatar.findViewById(R.id.tv_profile_title)).setText("头像");
            ((TextView) mUserGender.findViewById(R.id.tv_profile_title)).setText("性别");
            ((TextView) mUsername.findViewById(R.id.tv_profile_title)).setText("用户名");
            ((TextView) mUserGrade.findViewById(R.id.tv_profile_title)).setText("年级");
            ((TextView) mBindStudentId.findViewById(R.id.tv_profile_title)).setText("学号");
            ((TextView) mBindMobile.findViewById(R.id.tv_profile_title)).setText("手机号");
            ((TextView) mBindEmail.findViewById(R.id.tv_profile_title)).setText("邮箱");
            ((TextView) mChangePwd.findViewById(R.id.tv_profile_title)).setText("更换密码");
            ((TextView) mBindDepartment.findViewById(R.id.tv_profile_title)).setText("学院");

        }, 0);
    }

    @Override
    public void getLayoutComponentsAttr() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void refreshData() {
        /* 小字部分 */
        if (App.LOAD_USER_SUCCESS && App.IS_USER_LOG_IN) {
            ((TextView) mUserGender.findViewById(R.id.tv_profile_preview)).setText(App.USER.getGender());
            ((TextView) mUsername.findViewById(R.id.tv_profile_preview)).setText(App.USER.getUsername());
            ((TextView) mUserGrade.findViewById(R.id.tv_profile_preview)).setText(App.USER.getGrade());
            ((TextView) mBindStudentId.findViewById(R.id.tv_profile_preview)).setText(App.USER.getStudentId());
            ((TextView) mBindMobile.findViewById(R.id.tv_profile_preview)).setText(App.USER.getMobile());
            ((TextView) mBindEmail.findViewById(R.id.tv_profile_preview)).setText(App.USER.getEmail());
            ((TextView) mBindDepartment.findViewById(R.id.tv_profile_preview)).setText(App.USER.getDepartment());
        }
    }

    @Override
    public void registerViewComponentsAffairs() {
        mToolBar.setNavigationOnClickListener(v -> {
            finish();
        });
        mUserAvatar.setOnClickListener(v -> {
            MessageDialog messageDialog = KZDialogUtil.IOS_LIGHT_VER_THREE_BUTTON_MESSAGE(UserProfileActivity.this,
                    "更换头像",
                    "选择一种方式更换头像",
                    "拍照",
                    "相册",
                    "查看头像");
            messageDialog.show();
            messageDialog.setOnCancelButtonClickListener((baseDialog, v1) -> {
                return false;
            });
        });
        mUsername.setOnClickListener(v -> {
            InputDialog inputDialog = KZDialogUtil.IOS_LIGHT_INPUT(UserProfileActivity.this, "请输入新的用户名");
            inputDialog.setOnOkButtonClickListener((baseDialog, v12, inputStr) -> {
                inputStr = inputStr.trim();
                if (inputStr.equals(App.USER.getUsername()))
                    ToastUtil.showToast(this, "已是当前用户名", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_CENTER);
                else {
                    mPresenter.updateUsername(inputStr);
                }
                return false;
            });
            inputDialog.show();
        });
        mChangePwd.setOnClickListener(v -> {
            InputDialog inputDialog = KZDialogUtil.IOS_LIGHT_INPUT(UserProfileActivity.this, "请输入新的密码");
            inputDialog.setOnOkButtonClickListener((baseDialog, v12, inputStr) -> {
                mPresenter.updatePassword(inputStr);
                return false;
            });
            inputDialog.show();
        });
    }

    @Override
    public void attachPresenter() {
        this.mPresenter = new UserProfilePresenter(this);
        getLifecycle().addObserver(this.mPresenter);
    }

    @Override
    public void detachPresenter() {
        this.mPresenter.detachView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachPresenter();
    }
}

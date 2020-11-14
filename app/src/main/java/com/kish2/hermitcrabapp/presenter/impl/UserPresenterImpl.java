package com.kish2.hermitcrabapp.presenter.impl;

import android.content.Intent;
import android.os.Handler;

import com.kish2.hermitcrabapp.bean.HermitCrabBitMaps;
import com.kish2.hermitcrabapp.bean.User;
import com.kish2.hermitcrabapp.model.BaseModel;
import com.kish2.hermitcrabapp.model.IUserModel;
import com.kish2.hermitcrabapp.model.impl.UserModelImpl;
import com.kish2.hermitcrabapp.presenter.BasePresenter;
import com.kish2.hermitcrabapp.presenter.IUserPresenter;
import com.kish2.hermitcrabapp.utils.App;
import com.kish2.hermitcrabapp.utils.view.KZDialogUtil;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;
import com.kish2.hermitcrabapp.utils.view.ToastUtil;
import com.kish2.hermitcrabapp.view.BaseActivity;
import com.kish2.hermitcrabapp.view.BaseFragment;
import com.kish2.hermitcrabapp.view.activities.LoginActivity;
import com.kish2.hermitcrabapp.view.activities.MainActivity;
import com.kish2.hermitcrabapp.view.activities.RegisterActivity;
import com.kish2.hermitcrabapp.view.activities.UserProfileActivity;

import java.util.Map;

public class UserPresenterImpl extends BasePresenter<BaseActivity, BaseFragment> implements IUserPresenter {

    private IUserModel model;

    public UserPresenterImpl() {
        this.model = new UserModelImpl(this);
    }

    @Override
    public void onModelSuccess(Map<BaseModel.MODEL_RET, Object> data) {
        String msg = (String) data.get(BaseModel.MODEL_RET.ret_msg);
        App.USER = (User) data.get(BaseModel.MODEL_RET.ret_obj);
        ToastUtil.showToast(getContext(), msg, ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
        if (getActivity() != null) {
            if (activity instanceof LoginActivity) {
                ((LoginActivity) activity).mLoginSubmit.doneLoadingAnimation(ThemeUtil.Theme.afterGetResourcesColorId, HermitCrabBitMaps.mChecked);
                new Handler().postDelayed(() -> {
                    intent = null;
                    intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }, 500);
            } else if (activity instanceof RegisterActivity) {
                ((RegisterActivity) activity).mRegisterSubmit.doneLoadingAnimation(ThemeUtil.Theme.afterGetResourcesColorId, HermitCrabBitMaps.mChecked);
                new Handler().postDelayed(() -> {
                    intent = null;
                    intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }, 500);
            } else if (activity instanceof UserProfileActivity) {
                mWaitDialog.doDismiss();
                activity.refreshData();
            }
        }
    }

    @Override
    public void onModelFailure(Map<BaseModel.MODEL_RET, Object> data) {
        String msg = (String) data.get(BaseModel.MODEL_RET.ret_msg);
        ToastUtil.showToast(getContext(), msg, ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
        if (getActivity() != null) {
            if (activity instanceof LoginActivity) {
                ((LoginActivity) activity).mLoginSubmit.revertAnimation();
            } else if (activity instanceof RegisterActivity) {
                ((RegisterActivity) activity).mRegisterSubmit.revertAnimation();
            } else if (activity instanceof UserProfileActivity) {
                mWaitDialog.doDismiss();
            }
        }
    }

    @Override
    public void changeAvatar() {

    }

    @Override
    public void updateUsername(String username) {
        mWaitDialog = KZDialogUtil.IOS_LIGHT_WAIT_NO_STOP_DIALOG(getContext());
        mWaitDialog.show();
        model.usernameUpdate(App.USER.getUid(), username);
    }

    @Override
    public void updatePassword(String password) {
        mWaitDialog = KZDialogUtil.IOS_LIGHT_WAIT_NO_STOP_DIALOG(getContext());
        mWaitDialog.show();
        model.usernameUpdate(App.USER.getUid(), password);
    }

    @Override
    public void updateEmail() {

    }

    @Override
    public void updateMobile() {

    }

    @Override
    public void updateBindInfo() {

    }

    @Override
    public void bindStudentInfo() {

    }

    @Override
    public void loginByUsername(String username, String password) {
        if (getActivity() != null) {
            ((LoginActivity) activity).mLoginSubmit.startAnimation();
            model.authByUsername(username, password);
        }
    }

    @Override
    public void loginByMobile(String mobile, String checkCode) {
        if (getActivity() != null) {
            ((LoginActivity) activity).mLoginSubmit.startAnimation();
            model.authByMobile(mobile, checkCode);
        }
    }

    @Override
    public void register(String mobile, String checkCode) {
        if (getActivity() != null) {
            ((RegisterActivity) activity).mRegisterSubmit.startAnimation();
            model.userRegister(mobile, checkCode);
        }
    }

    @Override
    public void rememberUser(boolean isRemember) {

    }

    @Override
    public void forgetPassword() {

    }

    @Override
    public void loginByWeChat() {

    }

    @Override
    public void loginByQQ() {

    }


    @Override
    public void onViewPause() {

    }

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewResume() {

    }

    @Override
    public void onViewDestroy() {

    }

    @Override
    public void onViewStart() {

    }

    @Override
    public void onViewStop() {

    }
}

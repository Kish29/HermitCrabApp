package com.kish2.hermitcrabapp.presenter.fragments;

import com.kish2.hermitcrabapp.presenter.FBasePresenter;
import com.kish2.hermitcrabapp.presenter.UserPresenter;
import com.kish2.hermitcrabapp.view.fragments.personal.PersonalFragment;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class PersonalPresenter extends FBasePresenter<PersonalPresenter> implements UserPresenter {

    private PersonalFragment mView;

    public PersonalPresenter(PersonalFragment fragment) {
        this.mView = fragment;
    }

    @Override
    public void onFragmentPause() {

    }

    @Override
    public void onFragmentCreate() {

    }

    @Override
    public void onFragmentResume() {

    }

    @Override
    public void onFragmentDestroy() {

    }

    @Override
    public void onFragmentStart() {

    }

    @Override
    public void onFragmentStop() {

    }

    @Override
    public void loadDataFromServer() {

    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    @Override
    public void onServerDoOperationFailed(Object object) {

    }

    @Override
    public void onServerSuccess(Object object) {

    }

    @Override
    public void onSysNetworkError(Throwable t) {

    }

    @Override
    public void dataUpdate(Call<ResponseBody> call) {

    }

    @Override
    public void afterHandleServerError() {

    }

    @Override
    public void changeAvatar() {

    }

    @Override
    public void updateUsername(String username) {

    }

    @Override
    public void updatePassword(String password) {

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
}

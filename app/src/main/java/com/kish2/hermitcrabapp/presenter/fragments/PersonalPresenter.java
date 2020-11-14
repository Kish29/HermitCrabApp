package com.kish2.hermitcrabapp.presenter.fragments;

import com.kish2.hermitcrabapp.model.BaseModel;
import com.kish2.hermitcrabapp.presenter.BasePresenter;
import com.kish2.hermitcrabapp.view.activities.MainActivity;
import com.kish2.hermitcrabapp.view.fragments.personal.PersonalFragment;

import java.util.Map;

public class PersonalPresenter extends BasePresenter<MainActivity, PersonalFragment> {

    public PersonalPresenter(PersonalFragment fragment) {
        bindView(fragment);
    }

    @Override
    public void onModelSuccess(Map<BaseModel.MODEL_RET, Object> data) {

    }

    @Override
    public void onModelFailure(Map<BaseModel.MODEL_RET, Object> data) {

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

package com.kish2.hermitcrabapp.presenter.fragments;

import com.kish2.hermitcrabapp.presenter.BasePresenter;
import com.kish2.hermitcrabapp.view.activities.MainActivity;
import com.kish2.hermitcrabapp.view.fragments.personal.PersonalFragment;

public class PersonalPresenter extends BasePresenter<MainActivity, PersonalFragment> {

    public PersonalPresenter(PersonalFragment fragment) {
        bindView(fragment);
    }

    @Override
    public void initDataAdapter() {

    }

    @Override
    public void registerItemEvent() {

    }

    @Override
    public void initHandler() {

    }
}

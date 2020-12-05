package com.kish2.hermitcrabapp.presenter.fragments;

import android.os.Message;

import com.kish2.hermitcrabapp.adapters.InformAdapter;
import com.kish2.hermitcrabapp.bean.Inform;
import com.kish2.hermitcrabapp.model.fragments.LatestModel;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.BasePresenter;
import com.kish2.hermitcrabapp.view.activities.MainActivity;
import com.kish2.hermitcrabapp.view.fragments.home.FLatest;

import java.util.ArrayList;

public class LatestPresenter extends BasePresenter<MainActivity, FLatest> {

    private LatestModel mLatestModel;
    private FLatest mLatest;
    private boolean firstLoad = true;
    private ArrayList<Inform> mInforms;

    InformAdapter adapter;

    public LatestPresenter(FLatest latest) {
        this.mLatest = latest;
        this.mLatestModel = new LatestModel();
        bindView(latest);
        mInforms = new ArrayList<>();
    }

    @Override
    public void initDataAdapter() {

    }

    @Override
    public void registerItemEvent() {

    }

    public void getDataFromModel() {
        mLatestModel.updateInforms(mInforms);
        Message message = new Message();
        if (mInforms.size() != 0) {
            if (firstLoad) {
                mLatest.setmInformsAdapter(new InformAdapter(mLatest.getContext(), mInforms));
                message.what = MessageForHandler.ADAPTER_INIT;
                firstLoad = false;
            } else message.what = MessageForHandler.DATA_UPDATE;
        } else {
            message.what = -1;
        }
        mLatest.mHandler.sendMessage(message);
    }

    @Override
    public void initHandler() {

    }

}

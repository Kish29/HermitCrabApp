package com.kish2.hermitcrabapp.presenter.fragments;

import android.os.Message;

import com.kish2.hermitcrabapp.adapters.InformAdapter;
import com.kish2.hermitcrabapp.bean.Inform;
import com.kish2.hermitcrabapp.model.BaseModel;
import com.kish2.hermitcrabapp.model.fragments.SecondHandModel;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.BasePresenter;
import com.kish2.hermitcrabapp.view.activities.MainActivity;
import com.kish2.hermitcrabapp.view.fragments.community.FSecondHand;

import java.util.ArrayList;
import java.util.Map;

public class SecondHandPresenter extends BasePresenter<MainActivity, FSecondHand> {

    private FSecondHand mSecondHand;
    private SecondHandModel mSecondHandModel;
    private ArrayList<Inform> mProducts;
    private boolean firstLoad = true;

    public SecondHandPresenter(FSecondHand secondHand) {
        this.mSecondHand = secondHand;
        this.mSecondHandModel = new SecondHandModel();
        this.mProducts = new ArrayList<>();
    }

    @Override
    public void getDataFromModel() {
        mSecondHandModel.updateInforms(mProducts);
        Message message = new Message();
        if (mProducts.size() != 0) {
            if (firstLoad) {
                mSecondHand.setmProductsAdapter(new InformAdapter(mProducts, mSecondHand.getContext()));
                message.what = MessageForHandler.ADAPTER_INIT;
                firstLoad = false;
            } else message.what = MessageForHandler.DATA_UPDATE;
        } else {
            message.what = -1;
        }
        mSecondHand.mHandler.sendMessage(message);
    }

    @Override
    public void detachView() {
        this.mSecondHandModel = null;
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

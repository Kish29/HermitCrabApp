package com.kish2.hermitcrabapp.presenter.fragments;

import android.os.Message;
import android.widget.ArrayAdapter;

import com.kish2.hermitcrabapp.adapters.RecyclerInformAdapter;
import com.kish2.hermitcrabapp.bean.Inform;
import com.kish2.hermitcrabapp.model.fragments.SecondHandModel;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.FBasePresenter;
import com.kish2.hermitcrabapp.view.fragments.community.FSecondHand;

import java.util.ArrayList;

public class SecondHandPresenter extends FBasePresenter {

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
    public void getData() {
        mSecondHandModel.updateInforms(mProducts);
        Message message = new Message();
        if (mProducts.size() != 0) {
            if (firstLoad) {
                mSecondHand.setmProductsAdapter(new RecyclerInformAdapter(mProducts, mSecondHand.getContext()));
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
}

package com.kish2.hermitcrabapp.presenter.fragments;

import android.os.Message;

import com.kish2.hermitcrabapp.adapters.RecyclerInformAdapter;
import com.kish2.hermitcrabapp.bean.Inform;
import com.kish2.hermitcrabapp.model.fragments.SecondHandModel;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.FBasePresenter;
import com.kish2.hermitcrabapp.view.fragments.community.FSecondHand;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class SecondHandPresenter extends FBasePresenter<FSecondHand> {

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
    public void loadDataFromServer() {
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
}

package com.kish2.hermitcrabapp.presenter.fragments;

import android.os.Message;

import com.kish2.hermitcrabapp.adapters.RecyclerInformAdapter;
import com.kish2.hermitcrabapp.bean.Inform;
import com.kish2.hermitcrabapp.model.fragments.LatestModel;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.FBasePresenter;
import com.kish2.hermitcrabapp.view.fragments.home.FLatest;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class LatestPresenter extends FBasePresenter<FLatest> {

    private LatestModel mLatestModel;
    private FLatest mLatest;
    private boolean firstLoad = true;
    private ArrayList<Inform> mInforms;

    public LatestPresenter(FLatest latest) {
        this.mLatest = latest;
        this.mLatestModel = new LatestModel();
        mInforms = new ArrayList<>();
    }

    public void loadDataFromServer() {
        mLatestModel.updateInforms(mInforms);
        Message message = new Message();
        if (mInforms.size() != 0) {
            if (firstLoad) {
                mLatest.setmInformsAdapter(new RecyclerInformAdapter(mInforms, mLatest.getContext()));
                message.what = MessageForHandler.ADAPTER_INIT;
                firstLoad = false;
            } else message.what = MessageForHandler.DATA_UPDATE;
        } else {
            message.what = -1;
        }
        mLatest.mHandler.sendMessage(message);
    }

    @Override
    public void detachView() {
        this.mLatest = null;
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

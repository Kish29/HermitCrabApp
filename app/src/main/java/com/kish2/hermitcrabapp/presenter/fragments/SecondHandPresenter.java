package com.kish2.hermitcrabapp.presenter.fragments;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kish2.hermitcrabapp.HermitCrabApp;
import com.kish2.hermitcrabapp.adapters.SecondHandListAdapter;
import com.kish2.hermitcrabapp.bean.SecondHandPreview;
import com.kish2.hermitcrabapp.custom.view.CustomSwipeRefreshLayout;
import com.kish2.hermitcrabapp.custom.view.LoadMoreView;
import com.kish2.hermitcrabapp.model.BaseModel;
import com.kish2.hermitcrabapp.model.IBaseModel;
import com.kish2.hermitcrabapp.model.fragments.SecondHandModel;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.BasePresenter;
import com.kish2.hermitcrabapp.view.activities.MainActivity;
import com.kish2.hermitcrabapp.view.fragments.community.FSecondHand;

import java.util.List;
import java.util.Map;

public class SecondHandPresenter extends BasePresenter<MainActivity, FSecondHand> implements IBaseModel.OnRequestModelCallBack {

    private final SecondHandModel mModel;
    private CustomSwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private final SecondHandListAdapter adapter;

    public SecondHandPresenter(FSecondHand secondHand) {
        bindView(secondHand);
        initHandler();
        this.mModel = new SecondHandModel(this);
        adapter = new SecondHandListAdapter(getContext());
    }

    @Override
    public void getDataFromModel() {
        List<SecondHandPreview> previews = mModel.getSecondHandPreview(20);
        adapter.addData(previews);
    }

    @Override
    public void initHandler() {
        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MessageForHandler.DATA_LOADING:
                        refreshLayout.setRefreshing(true);
                        break;
                    case MessageForHandler.ADAPTER_INIT:
                        recyclerView.setAdapter(adapter);
                    case MessageForHandler.DATA_LOADED:
                        adapter.getLoadMoreModule().loadMoreComplete();
                    case MessageForHandler.DATA_UPDATE:
                    default:
                        refreshLayout.setRefreshing(false);
                        break;
                }
            }
        };
    }

    @Override
    public void initDataAdapter() {
        refreshLayout = fragment.getRefreshLayout();
        recyclerView = fragment.getRecyclerView();
        refreshLayout.setRefreshing(true);
        HermitCrabApp.APP_THREAD_POOL.execute(() -> {
            getDataFromModel();
            handler.sendEmptyMessage(MessageForHandler.ADAPTER_INIT);
        });
    }

    @Override
    public void registerItemEvent() {
        adapter.addLoadMoreModule(adapter);
        adapter.getLoadMoreModule().setPreLoadNumber(10);
        adapter.getLoadMoreModule().setLoadMoreView(new LoadMoreView());
        adapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
            getDataFromModel();
            if (adapter.getItemCount() <= 1000)
                adapter.getLoadMoreModule().loadMoreComplete();
        });
    }

    @Override
    public void onModelSuccess(Map<BaseModel.MODEL_RET, Object> data, int requestModelMethod) {

    }

    @Override
    public void onModelFailure(Map<BaseModel.MODEL_RET, Object> data, int requestModelMethod) {

    }
}

package com.kish2.hermitcrabapp.presenter.fragments;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kish2.hermitcrabapp.adapters.ChatListAdapter;
import com.kish2.hermitcrabapp.bean.ChatItemPreview;
import com.kish2.hermitcrabapp.custom.view.CustomSwipeRefreshLayout;
import com.kish2.hermitcrabapp.custom.view.LoadMoreView;
import com.kish2.hermitcrabapp.model.BaseModel;
import com.kish2.hermitcrabapp.model.IBaseModel;
import com.kish2.hermitcrabapp.model.fragments.ChatModel;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.BasePresenter;
import com.kish2.hermitcrabapp.view.activities.MainActivity;
import com.kish2.hermitcrabapp.view.fragments.chat.ChatFragment;

import java.util.List;
import java.util.Map;

public class ChatPresenter extends BasePresenter<MainActivity, ChatFragment> implements IBaseModel.OnRequestModelCallBack {

    private ChatModel mChatModel;
    private CustomSwipeRefreshLayout refreshLayout;
    private ChatListAdapter adapter;
    private RecyclerView recyclerView;

    @SuppressLint("HandlerLeak")
    public ChatPresenter(ChatFragment chatFragment) {
        bindView(chatFragment);
        this.mChatModel = new ChatModel(this);
        adapter = new ChatListAdapter(getContext());
        handler = new Handler() {
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
    public void getDataFromModel() {
        List<ChatItemPreview> chatPreviewItems = mChatModel.getChatPreviewItems(20);
        adapter.addData(chatPreviewItems);
    }

    @Override
    public void initDataAdapter() {
        refreshLayout = fragment.getRefreshLayout();
        recyclerView = fragment.getRecyclerView();
        refreshLayout.setRefreshing(true);
        new Thread() {
            @Override
            public void run() {
                getDataFromModel();
                handler.sendEmptyMessage(MessageForHandler.ADAPTER_INIT);
            }
        }.start();
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

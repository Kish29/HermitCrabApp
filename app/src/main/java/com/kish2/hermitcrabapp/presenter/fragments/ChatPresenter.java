package com.kish2.hermitcrabapp.presenter.fragments;

import android.os.Message;

import com.kish2.hermitcrabapp.adapters.InformAdapter;
import com.kish2.hermitcrabapp.bean.Inform;
import com.kish2.hermitcrabapp.model.fragments.ChatModel;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.BasePresenter;
import com.kish2.hermitcrabapp.view.activities.MainActivity;
import com.kish2.hermitcrabapp.view.fragments.chat.ChatFragment;

import java.util.ArrayList;

public class ChatPresenter extends BasePresenter<MainActivity, ChatFragment> {

    private ChatFragment mChatFragment;
    private ChatModel mChatModel;
    private boolean firstLoad = true;
    private ArrayList<Inform> mChats;

    public ChatPresenter(ChatFragment messageFragment) {
        this.mChatFragment = messageFragment;
        this.mChatModel = new ChatModel();
        mChats = new ArrayList<>();
    }

    @Override
    public void getDataFromModel() {
        mChatModel.updateInforms(mChats);
        Message message = new Message();
        if (mChats.size() != 0) {
            if (firstLoad) {
                mChatFragment.setmChatsAdapter(new InformAdapter(mChats, mChatFragment.getContext()));
                message.what = MessageForHandler.ADAPTER_INIT;
                firstLoad = false;
            } else message.what = MessageForHandler.DATA_UPDATE;
        } else {
            message.what = -1;
        }
        mChatFragment.mHandler.sendMessage(message);
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

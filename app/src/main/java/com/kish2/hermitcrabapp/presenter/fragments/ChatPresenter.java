package com.kish2.hermitcrabapp.presenter.fragments;

import android.os.Message;

import com.kish2.hermitcrabapp.adapters.RecyclerInformAdapter;
import com.kish2.hermitcrabapp.bean.Inform;
import com.kish2.hermitcrabapp.model.fragments.ChatModel;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.FBasePresenter;
import com.kish2.hermitcrabapp.view.fragments.message.ChatFragment;

import java.util.ArrayList;

public class ChatPresenter extends FBasePresenter<ChatFragment> {

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
    public void getData() {
        mChatModel.updateInforms(mChats);
        Message message = new Message();
        if (mChats.size() != 0) {
            if (firstLoad) {
                mChatFragment.setmChatsAdapter(new RecyclerInformAdapter(mChats, mChatFragment.getContext()));
                message.what = MessageForHandler.ADAPTER_INIT;
                firstLoad = false;
            } else message.what = MessageForHandler.DATA_UPDATE;
        } else {
            message.what = -1;
        }
        mChatFragment.mHandler.sendMessage(message);
    }

    @Override
    public void detachView() {
        this.mChatFragment = null;
    }
}

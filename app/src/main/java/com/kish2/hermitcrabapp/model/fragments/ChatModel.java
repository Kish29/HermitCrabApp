package com.kish2.hermitcrabapp.model.fragments;

import com.kish2.hermitcrabapp.bean.ChatItemPreview;
import com.kish2.hermitcrabapp.model.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatModel extends BaseModel {
    public ChatModel(OnRequestModelCallBack callBack) {
        super(callBack);
    }

    public List<ChatItemPreview> getChatPreviewItems(int n) {
        ArrayList<ChatItemPreview> chatItemPreviews = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ChatItemPreview chatItemPreview = new ChatItemPreview();
            chatItemPreview.setTargetNickName("他是🐖" + i);
            chatItemPreview.setLastMsgTime("2020-11-11");
            chatItemPreview.setMsgLatest("我劝你们这些年轻人耗子尾汁！");
            chatItemPreviews.add(chatItemPreview);
        }
        return chatItemPreviews;
    }

    @Override
    protected int getModelRequestCode() {
        return 0;
    }

    @Override
    public Object onServerSuccess(JSONObject jsonObject) throws JSONException {
        return null;
    }
}

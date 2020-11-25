package com.kish2.hermitcrabapp.adapters;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.adapters.viewholder.VHChatItemPreview;
import com.kish2.hermitcrabapp.bean.ChatItemPreview;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChatListAdapter extends BaseQuickAdapter<ChatItemPreview, VHChatItemPreview> implements LoadMoreModule {

    private Context mContext;

    public ChatListAdapter(Context context, @Nullable List<ChatItemPreview> data) {
        super(R.layout.view_item_chat_preview, data);
        this.mContext = context;
    }

    public ChatListAdapter(Context context) {
        super(R.layout.view_item_chat_preview);
        this.mContext = context;
    }

    @Override
    protected void convert(@NotNull VHChatItemPreview vhChatItemPreview, ChatItemPreview chatItemPreview) {
        vhChatItemPreview.targetNickName.setText(chatItemPreview.getTargetNickName());
        vhChatItemPreview.msgLatest.setText(chatItemPreview.getMsgLatest());
        vhChatItemPreview.lastMsgTime.setText(chatItemPreview.getLastMsgTime());
        Glide.with(mContext)
                .load("http://cipp.ustb.edu.cn/attachment/0/20201103/78FD9D00644743C78D4D7BF0F51D873E_校园网登录界面海报_自定义px_2020-11-03-0 (1).jpeg")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(vhChatItemPreview.targetAvatar);
    }
}

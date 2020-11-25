package com.kish2.hermitcrabapp.adapters.viewholder;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.kish2.hermitcrabapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;

public class VHChatItemPreview extends BaseViewHolder {

    public RoundedImageView targetAvatar;

    public TextView targetNickName;

    public TextView msgLatest;

    public TextView lastMsgTime;

    public VHChatItemPreview(@NotNull View view) {
        super(view);
        targetAvatar = view.findViewById(R.id.riv_chat_target);
        targetNickName = view.findViewById(R.id.tv_chat_nickname);
        msgLatest = view.findViewById(R.id.tv_msg_latest);
        lastMsgTime = view.findViewById(R.id.tv_last_msg_time);
    }
}

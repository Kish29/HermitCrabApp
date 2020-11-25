package com.kish2.hermitcrabapp.bean;

public class ChatItemPreview {

    private String targetAvatarUrl;

    private String targetNickName;

    private String msgLatest;

    private String lastMsgTime;

    public String getTargetAvatarUrl() {
        return targetAvatarUrl;
    }

    public void setTargetAvatarUrl(String targetAvatarUrl) {
        this.targetAvatarUrl = targetAvatarUrl;
    }

    public String getTargetNickName() {
        return targetNickName;
    }

    public void setTargetNickName(String targetNickName) {
        this.targetNickName = targetNickName;
    }

    public String getMsgLatest() {
        return msgLatest;
    }

    public void setMsgLatest(String msgLatest) {
        this.msgLatest = msgLatest;
    }

    public String getLastMsgTime() {
        return lastMsgTime;
    }

    public void setLastMsgTime(String lastMsgTime) {
        this.lastMsgTime = lastMsgTime;
    }
}

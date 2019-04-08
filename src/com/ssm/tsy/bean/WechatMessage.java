package com.ssm.tsy.bean;

import java.util.Date;

public class WechatMessage {
    private Integer wechatMessageId;

    private String wechatMessageFromUser;

    private String wechatMessage;

    private Date wechatMessageTime;

    private Integer wechatMessageRes;

    private Date wechatMessageResTime;

    public Integer getWechatMessageId() {
        return wechatMessageId;
    }

    public void setWechatMessageId(Integer wechatMessageId) {
        this.wechatMessageId = wechatMessageId;
    }

    public String getWechatMessageFromUser() {
        return wechatMessageFromUser;
    }

    public void setWechatMessageFromUser(String wechatMessageFromUser) {
        this.wechatMessageFromUser = wechatMessageFromUser == null ? null : wechatMessageFromUser.trim();
    }

    public String getWechatMessage() {
        return wechatMessage;
    }

    public void setWechatMessage(String wechatMessage) {
        this.wechatMessage = wechatMessage == null ? null : wechatMessage.trim();
    }

    public Date getWechatMessageTime() {
        return wechatMessageTime;
    }

    public void setWechatMessageTime(Date wechatMessageTime) {
        this.wechatMessageTime = wechatMessageTime;
    }

    public Integer getWechatMessageRes() {
        return wechatMessageRes;
    }

    public void setWechatMessageRes(Integer wechatMessageRes) {
        this.wechatMessageRes = wechatMessageRes;
    }

    public Date getWechatMessageResTime() {
        return wechatMessageResTime;
    }

    public void setWechatMessageResTime(Date wechatMessageResTime) {
        this.wechatMessageResTime = wechatMessageResTime;
    }
}
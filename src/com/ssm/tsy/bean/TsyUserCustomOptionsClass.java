package com.ssm.tsy.bean;

import java.util.Date;

public class TsyUserCustomOptionsClass {
    private Integer id;

    private String customTitle;

    private Integer customState;

    private Integer customType;

    private Date customTime;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomTitle() {
        return customTitle;
    }

    public void setCustomTitle(String customTitle) {
        this.customTitle = customTitle == null ? null : customTitle.trim();
    }

    public Integer getCustomState() {
        return customState;
    }

    public void setCustomState(Integer customState) {
        this.customState = customState;
    }

    public Integer getCustomType() {
        return customType;
    }

    public void setCustomType(Integer customType) {
        this.customType = customType;
    }

    public Date getCustomTime() {
        return customTime;
    }

    public void setCustomTime(Date customTime) {
        this.customTime = customTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
package com.ssm.tsy.bean;

import java.util.Date;

public class TsyUserCustomOptionsLabel {
    private Integer id;

    private String labelTitle;

    private Integer labelState;

    private Date labelTime;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabelTitle() {
        return labelTitle;
    }

    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle == null ? null : labelTitle.trim();
    }

    public Integer getLabelState() {
        return labelState;
    }

    public void setLabelState(Integer labelState) {
        this.labelState = labelState;
    }

    public Date getLabelTime() {
        return labelTime;
    }

    public void setLabelTime(Date labelTime) {
        this.labelTime = labelTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
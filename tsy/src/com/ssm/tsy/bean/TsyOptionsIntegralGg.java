package com.ssm.tsy.bean;

import java.util.Date;

public class TsyOptionsIntegralGg {
    private Integer id;

    private Integer optionsIntegral;

    private String optionsIntegralType;

    private String optionsIntegralTypeCont;

    private Date optionsIntegralTime;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOptionsIntegral() {
        return optionsIntegral;
    }

    public void setOptionsIntegral(Integer optionsIntegral) {
        this.optionsIntegral = optionsIntegral;
    }

    public String getOptionsIntegralType() {
        return optionsIntegralType;
    }

    public void setOptionsIntegralType(String optionsIntegralType) {
        this.optionsIntegralType = optionsIntegralType == null ? null : optionsIntegralType.trim();
    }

    public String getOptionsIntegralTypeCont() {
        return optionsIntegralTypeCont;
    }

    public void setOptionsIntegralTypeCont(String optionsIntegralTypeCont) {
        this.optionsIntegralTypeCont = optionsIntegralTypeCont == null ? null : optionsIntegralTypeCont.trim();
    }

    public Date getOptionsIntegralTime() {
        return optionsIntegralTime;
    }

    public void setOptionsIntegralTime(Date optionsIntegralTime) {
        this.optionsIntegralTime = optionsIntegralTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
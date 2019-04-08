package com.ssm.tsy.bean;

public class WechatSetup {
    private Integer id;

    private String setupName;

    private Integer setupType;

    private Integer setupState;

    private Integer appid;

    private String setupSmart;

    private String firstval;

    private String secondval;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSetupName() {
        return setupName;
    }

    public void setSetupName(String setupName) {
        this.setupName = setupName == null ? null : setupName.trim();
    }

    public Integer getSetupType() {
        return setupType;
    }

    public void setSetupType(Integer setupType) {
        this.setupType = setupType;
    }

    public Integer getSetupState() {
        return setupState;
    }

    public void setSetupState(Integer setupState) {
        this.setupState = setupState;
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public String getSetupSmart() {
        return setupSmart;
    }

    public void setSetupSmart(String setupSmart) {
        this.setupSmart = setupSmart == null ? null : setupSmart.trim();
    }

    public String getFirstval() {
        return firstval;
    }

    public void setFirstval(String firstval) {
        this.firstval = firstval == null ? null : firstval.trim();
    }

    public String getSecondval() {
        return secondval;
    }

    public void setSecondval(String secondval) {
        this.secondval = secondval == null ? null : secondval.trim();
    }
}
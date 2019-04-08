package com.ssm.tsy.bean;

public class TsyAopLog {
    private Integer id;

    private String browseData;//请求日期

    private String browseIp;//请求IP

    private Integer browseNum;//请求类型：：：：：：1：微信请求，2：微博请求

    private Integer browseNumSuccess;//请求成功次数：默认为1

    private String browsePlace;//请求地址：比如：河南省郑州市

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrowseData() {
        return browseData;
    }

    public void setBrowseData(String browseData) {
        this.browseData = browseData == null ? null : browseData.trim();
    }

    public String getBrowseIp() {
        return browseIp;
    }

    public void setBrowseIp(String browseIp) {
        this.browseIp = browseIp == null ? null : browseIp.trim();
    }

    public Integer getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(Integer browseNum) {
        this.browseNum = browseNum;
    }

    public Integer getBrowseNumSuccess() {
        return browseNumSuccess;
    }

    public void setBrowseNumSuccess(Integer browseNumSuccess) {
        this.browseNumSuccess = browseNumSuccess;
    }

    public String getBrowsePlace() {
        return browsePlace;
    }

    public void setBrowsePlace(String browsePlace) {
        this.browsePlace = browsePlace == null ? null : browsePlace.trim();
    }
}
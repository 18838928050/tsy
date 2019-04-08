package com.ssm.tsy.bean;

public class TsyOptions {
    private Integer id;

    private String optionName;

    private String optionPath;

    private String optionType;

    private Long optionSize;

    private String optionSizeUnit;

    private String optionRemarks;

    private Integer optionState;

    private String optionPicPath;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName == null ? null : optionName.trim();
    }

    public String getOptionPath() {
        return optionPath;
    }

    public void setOptionPath(String optionPath) {
        this.optionPath = optionPath == null ? null : optionPath.trim();
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType == null ? null : optionType.trim();
    }

    public Long getOptionSize() {
        return optionSize;
    }

    public void setOptionSize(Long optionSize) {
        this.optionSize = optionSize;
    }

    public String getOptionSizeUnit() {
        return optionSizeUnit;
    }

    public void setOptionSizeUnit(String optionSizeUnit) {
        this.optionSizeUnit = optionSizeUnit == null ? null : optionSizeUnit.trim();
    }

    public String getOptionRemarks() {
        return optionRemarks;
    }

    public void setOptionRemarks(String optionRemarks) {
        this.optionRemarks = optionRemarks == null ? null : optionRemarks.trim();
    }

    public Integer getOptionState() {
        return optionState;
    }

    public void setOptionState(Integer optionState) {
        this.optionState = optionState;
    }

    public String getOptionPicPath() {
        return optionPicPath;
    }

    public void setOptionPicPath(String optionPicPath) {
        this.optionPicPath = optionPicPath == null ? null : optionPicPath.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
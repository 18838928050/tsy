package com.ssm.tsy.bean;

import java.util.Date;

public class TsyUserCustomOptions {
    private Integer id;

    private String optionTitle;

    private String optionVedio;

    private String optionId;

    private Integer userId;

    private Date optionTime;

    private String optionIntroduce;

    private Integer optionType;

    private Integer optionState;

    private int optionClass;
    
    private String optionLabel;

    private String optionContext;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOptionTitle() {
        return optionTitle;
    }

    public void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle == null ? null : optionTitle.trim();
    }

    public String getOptionVedio() {
        return optionVedio;
    }

    public void setOptionVedio(String optionVedio) {
        this.optionVedio = optionVedio == null ? null : optionVedio.trim();
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId == null ? null : optionId.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getOptionTime() {
        return optionTime;
    }

    public void setOptionTime(Date optionTime) {
        this.optionTime = optionTime;
    }

    public String getOptionIntroduce() {
        return optionIntroduce;
    }

    public void setOptionIntroduce(String optionIntroduce) {
        this.optionIntroduce = optionIntroduce == null ? null : optionIntroduce.trim();
    }

    public Integer getOptionType() {
        return optionType;
    }

    public void setOptionType(Integer optionType) {
        this.optionType = optionType;
    }

    public Integer getOptionState() {
        return optionState;
    }

    public void setOptionState(Integer optionState) {
        this.optionState = optionState;
    }

    public int getOptionClass() {
		return optionClass;
	}

	public void setOptionClass(int optionClass) {
		this.optionClass = optionClass;
	}

	public String getOptionLabel() {
		return optionLabel;
	}

	public void setOptionLabel(String optionLabel) {
		this.optionLabel = optionLabel;
	}

	public String getOptionContext() {
        return optionContext;
    }

    public void setOptionContext(String optionContext) {
        this.optionContext = optionContext == null ? null : optionContext.trim();
    }
}
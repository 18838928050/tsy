package com.ssm.tsy.bean;

import java.util.Date;

public class TsyOptionsEvaluate {
    private Integer id;

    private Integer optionsId;

    private Date evaluateTime;

    private Double evaluateStart;

    private Integer evaluateUserid;

    private Integer evaluateParentId;

    private String evaluateContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOptionsId() {
        return optionsId;
    }

    public void setOptionsId(Integer optionsId) {
        this.optionsId = optionsId;
    }

    public Date getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(Date evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public Double getEvaluateStart() {
        return evaluateStart;
    }

    public void setEvaluateStart(Double evaluateStart) {
        this.evaluateStart = evaluateStart;
    }

    public Integer getEvaluateUserid() {
        return evaluateUserid;
    }

    public void setEvaluateUserid(Integer evaluateUserid) {
        this.evaluateUserid = evaluateUserid;
    }

    public Integer getEvaluateParentId() {
        return evaluateParentId;
    }

    public void setEvaluateParentId(Integer evaluateParentId) {
        this.evaluateParentId = evaluateParentId;
    }

    public String getEvaluateContent() {
        return evaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent == null ? null : evaluateContent.trim();
    }
}
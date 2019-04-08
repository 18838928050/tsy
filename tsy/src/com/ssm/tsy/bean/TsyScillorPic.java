package com.ssm.tsy.bean;

public class TsyScillorPic {
    private Integer id;

    private String scollorPicName;//名称

    private String scollorPicPath;//图片路径

    private String scollorPicData;//添加日期

    private Integer scollorPicUserid;//用户id

    private Integer scollorPicDisplay;//是否上线。0：下线；1：上线。

    private String scollorPicIntroduce;//介绍

    private String scollorPicContent;//内容
    
    private Integer scollorPicFb;//是否发布

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScollorPicName() {
        return scollorPicName;
    }

    public void setScollorPicName(String scollorPicName) {
        this.scollorPicName = scollorPicName == null ? null : scollorPicName.trim();
    }

    public String getScollorPicPath() {
        return scollorPicPath;
    }

    public void setScollorPicPath(String scollorPicPath) {
        this.scollorPicPath = scollorPicPath == null ? null : scollorPicPath.trim();
    }

    public String getScollorPicData() {
        return scollorPicData;
    }

    public void setScollorPicData(String scollorPicData) {
        this.scollorPicData = scollorPicData == null ? null : scollorPicData.trim();
    }

    public Integer getScollorPicUserid() {
        return scollorPicUserid;
    }

    public void setScollorPicUserid(Integer scollorPicUserid) {
        this.scollorPicUserid = scollorPicUserid;
    }

    public Integer getScollorPicDisplay() {
        return scollorPicDisplay;
    }

    public void setScollorPicDisplay(Integer scollorPicDisplay) {
        this.scollorPicDisplay = scollorPicDisplay;
    }

    public String getScollorPicIntroduce() {
        return scollorPicIntroduce;
    }

    public void setScollorPicIntroduce(String scollorPicIntroduce) {
        this.scollorPicIntroduce = scollorPicIntroduce == null ? null : scollorPicIntroduce.trim();
    }

    public String getScollorPicContent() {
        return scollorPicContent;
    }
    public void setScollorPicContent(String scollorPicContent) {
        this.scollorPicContent = scollorPicContent == null ? null : scollorPicContent.trim();
    }
	public Integer getScollorPicFb() {
		return scollorPicFb;
	}
	public void setScollorPicFb(Integer scollorPicFb) {
		this.scollorPicFb = scollorPicFb;
	}
}
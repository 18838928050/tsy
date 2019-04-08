package com.ssm.tsy.bean;

public class TsyUserLoginLog {
    private Integer id;

    private String no;//登陆用户名

    private String loginPlace;//登陆地址:比如：河南省郑州市

    private String loginIp;//登陆ip

    private String loginData;//登陆时间

    private Integer loginNumSuccess;//登陆成功次数：默认为1
    
    private String newtable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public String getLoginPlace() {
        return loginPlace;
    }

    public void setLoginPlace(String loginPlace) {
        this.loginPlace = loginPlace == null ? null : loginPlace.trim();
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public String getLoginData() {
        return loginData;
    }

    public void setLoginData(String loginData) {
        this.loginData = loginData == null ? null : loginData.trim();
    }

    public Integer getLoginNumSuccess() {
        return loginNumSuccess;
    }

    public void setLoginNumSuccess(Integer loginNumSuccess) {
        this.loginNumSuccess = loginNumSuccess;
    }

	public String getNewtable() {
		return newtable;
	}

	public void setNewtable(String newtable) {
		this.newtable = newtable;
	}
    
}
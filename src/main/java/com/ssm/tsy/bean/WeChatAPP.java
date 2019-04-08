package com.ssm.tsy.bean;

public class WeChatAPP {
	
	private int id;
	private String appid;
	private String appSecret;
	private int appclass;
	
	
	@Override
	public String toString() {
		return "WeChatAPP [id=" + id + ", appid=" + appid + ", appSecret="
				+ appSecret + ", appclass=" + appclass + "]";
	}
	public WeChatAPP() {
		super();
	}
	public WeChatAPP(int id, String appid, String appSecret, int appclass) {
		super();
		this.id = id;
		this.appid = appid;
		this.appSecret = appSecret;
		this.appclass = appclass;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public int getAppclass() {
		return appclass;
	}
	public void setAppclass(int appclass) {
		this.appclass = appclass;
	}

}

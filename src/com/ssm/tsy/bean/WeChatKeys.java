package com.ssm.tsy.bean;

public class WeChatKeys {

	private int id;
	private String keyvalue;// 关键字
	private String context;// 回复内容
	private int judge;// 判断是否正在运作1.在运作0.不在运作
	private int keyclass;// 关键字类型 --->1.数字关键字 --->2.系统正文关键字--->3.符号关键字--->4.文字关键字

	@Override
	public String toString() {
		return "WeChatKeys [id=" + id + ", keyvalue=" + keyvalue + ", context="
				+ context + ", judge=" + judge + ", keyclass=" + keyclass + "]";
	}

	public WeChatKeys() {
		super();
	}

	public WeChatKeys(String keyvalue, String context, int judge, int keyclass) {
		super();
		this.keyvalue = keyvalue;
		this.context = context;
		this.judge = judge;
		this.keyclass = keyclass;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKeyvalue() {
		return keyvalue;
	}

	public void setKeyvalue(String keyvalue) {
		this.keyvalue = keyvalue;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public int getJudge() {
		return judge;
	}

	public void setJudge(int judge) {
		this.judge = judge;
	}

	public int getKeyclass() {
		return keyclass;
	}

	public void setKeyclass(int keyclass) {
		this.keyclass = keyclass;
	}
}

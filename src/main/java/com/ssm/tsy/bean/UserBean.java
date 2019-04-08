package com.ssm.tsy.bean;

/**
 * 用户表
 * 
 */
public class UserBean {

	private int id;// 主键，自增
	private String no;// 工号
	private String password;// 密码
	private String name;// 姓名
	private int quanxian;// 用户类别-->0,普通员工-->1,超级管理员-->2,普通管理员-->3,高级管理员
	private String redata;// 用户注册时间
	private int appid;//微信公众号id

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuanxian() {
		return quanxian;
	}

	public void setQuanxian(int quanxian) {
		this.quanxian = quanxian;
	}

	public String getRedata() {
		return redata;
	}

	public void setRedata(String redata) {
		this.redata = redata;
	}

}

package com.ssm.tsy.bean;

public class Liebiao {

	private int id;

	private String name;// 菜单名称
	private String nameurl;// 菜单连接
	private String nameicon;// 菜单图标
	private int belongto;// 如果是二级菜单，则属于那个一级菜单

	private int jibie;// 是几级菜单

	public Liebiao() {
		super();
	}

	public Liebiao(int id, String name, String nameurl, String nameicon,
			int belongto, int jibie) {
		super();
		this.id = id;
		this.name = name;
		this.nameurl = nameurl;
		this.nameicon = nameicon;
		this.belongto = belongto;
		this.jibie = jibie;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameurl() {
		return nameurl;
	}

	public void setNameurl(String nameurl) {
		this.nameurl = nameurl;
	}

	public String getNameicon() {
		return nameicon;
	}

	public void setNameicon(String nameicon) {
		this.nameicon = nameicon;
	}

	public int getBelongto() {
		return belongto;
	}

	public void setBelongto(int belongto) {
		this.belongto = belongto;
	}

	public int getJibie() {
		return jibie;
	}

	public void setJibie(int jibie) {
		this.jibie = jibie;
	}
}

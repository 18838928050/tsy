package com.ssm.tsy.bean;

public class WeChatKnowledge {

	private int id;// 唯一标识符

	private String name;// 标题名称

	private String jieshao;// 分类介绍 一级分类有介绍 二级分类没有介绍

	private int jibie;// 知识菜单级别 一级分类：1；二级分类：2。

	private String content;// 内容介绍 一级分类没有内容 二级分类有内容

	private int fatherid;// 二级分类所属一级分类的id，如果是一级分类，则为0
	
	private int upordown;//当前状态为上线状态或者下线状态。0：下线；1：上线

	@Override
	public String toString() {
		return "WeChatKnowledge [id=" + id + ", name=" + name + ", jieshao="
				+ jieshao + ", jibie=" + jibie + ", content=" + content
				+ ", fatherid=" + fatherid + ", upordown=" + upordown +"]";
	}

	public WeChatKnowledge() {
		super();
	}

	public WeChatKnowledge(int id, String name, String jieshao, int jibie,
			String content, int fatherid , int upordown) {
		super();
		this.id = id;
		this.name = name;
		this.jieshao = jieshao;
		this.jibie = jibie;
		this.content = content;
		this.fatherid = fatherid;
		this.upordown = upordown;
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

	public String getJieshao() {
		return jieshao;
	}

	public void setJieshao(String jieshao) {
		this.jieshao = jieshao;
	}

	public int getJibie() {
		return jibie;
	}

	public void setJibie(int jibie) {
		this.jibie = jibie;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getFatherid() {
		return fatherid;
	}

	public void setFatherid(int fatherid) {
		this.fatherid = fatherid;
	}

	public int getUpordown() {
		return upordown;
	}

	public void setUpordown(int upordown) {
		this.upordown = upordown;
	}

}

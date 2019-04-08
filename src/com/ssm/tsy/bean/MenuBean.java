package com.ssm.tsy.bean;

public class MenuBean {

	private int id;
	private String menuname;// 菜单列表名称
	private String menutype;// 菜单列表类型--->一般为click
	private String menukey;// 菜单点击事件的判断id
	private int menulevel;// 菜单级别-----是一级菜单还是二级菜单
	private int menubolong;// 如果是二级菜单，属于哪个一级菜单
	private int haschild;// 判断是否有子菜单
	private int rebackint;// 0为还未添加回复类别或者只修改名称1：图文消息2：图片3：语音4：视频5：文字6：网页
	private String rebackcontext;// 要回复的内容，根据类别不同，存的内容不同

	@Override
	public String toString() {
		return "MenuBean [id=" + id + ", menuname=" + menuname + ", menutype="
				+ menutype + ", menukey=" + menukey + ", menulevel="
				+ menulevel + ", menubolong=" + menubolong + ", haschild="
				+ haschild + ", rebackint=" + rebackint + ", rebackcontext="
				+ rebackcontext + "]";
	}

	public MenuBean() {
		super();
	}

	public MenuBean(int id, String menuname, String menutype, String menukey,
			int menulevel, int menubolong, int haschild, int rebackint,
			String rebackcontext) {
		super();
		this.id = id;
		this.menuname = menuname;
		this.menutype = menutype;
		this.menukey = menukey;
		this.menulevel = menulevel;
		this.menubolong = menubolong;
		this.haschild = haschild;
		this.rebackint = rebackint;
		this.rebackcontext = rebackcontext;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getMenutype() {
		return menutype;
	}

	public void setMenutype(String menutype) {
		this.menutype = menutype;
	}

	public String getMenukey() {
		return menukey;
	}

	public void setMenukey(String menukey) {
		this.menukey = menukey;
	}

	public int getMenulevel() {
		return menulevel;
	}

	public void setMenulevel(int menulevel) {
		this.menulevel = menulevel;
	}

	public int getMenubolong() {
		return menubolong;
	}

	public void setMenubolong(int menubolong) {
		this.menubolong = menubolong;
	}

	public int getHaschild() {
		return haschild;
	}

	public void setHaschild(int haschild) {
		this.haschild = haschild;
	}

	public int getRebackint() {
		return rebackint;
	}

	public void setRebackint(int rebackint) {
		this.rebackint = rebackint;
	}

	public String getRebackcontext() {
		return rebackcontext;
	}

	public void setRebackcontext(String rebackcontext) {
		this.rebackcontext = rebackcontext;
	}
}

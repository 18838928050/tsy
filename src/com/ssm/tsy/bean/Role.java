package com.ssm.tsy.bean;

public class Role  {
	private int id;
	private String username;//账号
	private String realname;//真实姓名
	private int sex;//性别1男0女
	private int age;//年龄
	private String idcard;//身份证号码
	private String csri;//出生日期
	private String education;//学历
	private String major;//专业
	private String email;//电子邮箱
	private int whether_graduate;//是否毕业1毕业0未毕业
	private String bysj;//毕业时间
	private String nation;//民族
	private String political_landscape;//政治面貌
	private String qq;//QQ
	private String phone;//联系方式
	private String home_address;//户籍所在地
	private String now_address;//现居住地址
	private String work_address;//工作地点
	private String work_time;//入职时间
	private String update_time;//最近修改时间
	private String message;//个人简介
	
	public Role() {
		super();
	}
	public Role(int id, String username, String realname, int sex, int age,
			String idcard, String csri, String education, String major,
			String email, int whether_graduate, String bysj, String nation,
			String political_landscape, String qq, String phone,
			String home_address, String now_address, String work_address,
			String work_time, String update_time, String message) {
		super();
		this.id = id;
		this.username = username;
		this.realname = realname;
		this.sex = sex;
		this.age = age;
		this.idcard = idcard;
		this.csri = csri;
		this.education = education;
		this.major = major;
		this.email = email;
		this.whether_graduate = whether_graduate;
		this.bysj = bysj;
		this.nation = nation;
		this.political_landscape = political_landscape;
		this.qq = qq;
		this.phone = phone;
		this.home_address = home_address;
		this.now_address = now_address;
		this.work_address = work_address;
		this.work_time = work_time;
		this.update_time = update_time;
		this.message = message;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", username=" + username + ", realname="
				+ realname + ", sex=" + sex + ", age=" + age + ", idcard="
				+ idcard + ", csri=" + csri + ", education=" + education
				+ ", major=" + major + ", email=" + email
				+ ", whether_graduate=" + whether_graduate + ", bysj=" + bysj
				+ ", nation=" + nation + ", political_landscape="
				+ political_landscape + ", qq=" + qq + ", phone=" + phone
				+ ", home_address=" + home_address + ", now_address="
				+ now_address + ", work_address=" + work_address
				+ ", work_time=" + work_time + ", update_time=" + update_time
				+ ", message=" + message + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getCsri() {
		return csri;
	}
	public void setCsri(String csri) {
		this.csri = csri;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getWhether_graduate() {
		return whether_graduate;
	}
	public void setWhether_graduate(int whether_graduate) {
		this.whether_graduate = whether_graduate;
	}
	public String getBysj() {
		return bysj;
	}
	public void setBysj(String bysj) {
		this.bysj = bysj;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getPolitical_landscape() {
		return political_landscape;
	}
	public void setPolitical_landscape(String political_landscape) {
		this.political_landscape = political_landscape;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getHome_address() {
		return home_address;
	}
	public void setHome_address(String home_address) {
		this.home_address = home_address;
	}
	public String getNow_address() {
		return now_address;
	}
	public void setNow_address(String now_address) {
		this.now_address = now_address;
	}
	public String getWork_address() {
		return work_address;
	}
	public void setWork_address(String work_address) {
		this.work_address = work_address;
	}
	public String getWork_time() {
		return work_time;
	}
	public void setWork_time(String work_time) {
		this.work_time = work_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}

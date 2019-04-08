package com.ssm.tsy.bean;

public class WeChatUser {

	private int id;
	private String openid;// 用户的标识，对当前公众号唯一
	private String nickname;// 用户的昵称
	private String city;// 用户所在城市
	private String country;// 用户所在国家
	private String subscribe_time;// 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	private int sex;// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	private String province;// 用户所在省份
	private int groupid;// 分组id，为0说明没有分组
	private String headimgurl;// 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
	private String subscribe;// 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	private String remark;// 是否被标识------>目前全部为0
	private int user_id;// 微信绑定用户id，为0说明没有绑定
	private int frozen;// 微信用户是否被冻结，0为被冻结，1为没有被冻结
	private int appId;// 所属微信公众号id；


	@Override
	public String toString() {
		return "WeChatUser [id=" + id + ", openid=" + openid + ", nickname="
				+ nickname + ", city=" + city + ", country=" + country
				+ ", subscribe_time=" + subscribe_time + ", sex=" + sex
				+ ", province=" + province + ", groupid=" + groupid
				+ ", headimgurl=" + headimgurl + ", subscribe=" + subscribe
				+ ", remark=" + remark + ", user_id=" + user_id + ", frozen="
				+ frozen + ", appId=" + appId + "]";
	}

	public WeChatUser() {
		super();
	}

	public WeChatUser(String openid, String nickname, String city,
			String country, String subscribe_time, int sex, String province,
			int groupid, String headimgurl, String subscribe, String remark,
			int user_id, int frozen, int appId) {
		super();
		this.openid = openid;
		this.nickname = nickname;
		this.city = city;
		this.country = country;
		this.subscribe_time = subscribe_time;
		this.sex = sex;
		this.province = province;
		this.groupid = groupid;
		this.headimgurl = headimgurl;
		this.subscribe = subscribe;
		this.remark = remark;
		this.user_id = user_id;
		this.frozen = frozen;
		this.appId = appId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSubscribe_time() {
		return subscribe_time;
	}

	public void setSubscribe_time(String subscribe_time) {
		this.subscribe_time = subscribe_time;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getFrozen() {
		return frozen;
	}

	public void setFrozen(int frozen) {
		this.frozen = frozen;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}
	

}

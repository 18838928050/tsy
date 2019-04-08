package com.ssm.tsy.bean;

public class NewsBean {

	private int id;//
	private String realtype;/* 类型二 */
	private String title;/* 标题 */
	private String thumbnail_pic_s03;/* 图片3 */
	private String uniquekey;/* 新闻编号 */
	private String thumbnail_pic_s02;/* 图片2 */
	private String author_name;/* 作者 */
	private String thumbnail_pic_s;/* 图片1 */
	private String type;/* 类型一 */
	private String date;/* 时间 */
	private String url;/* 新闻链接 */
	private int shifoufasong;/* 判断是否发送1：已经发送0：未发送 */
	private String fasongdata;/* 发送日期 */

	@Override
	public String toString() {
		return "NewsBean [id=" + id + ", realtype=" + realtype + ", title="
				+ title + ", thumbnail_pic_s03=" + thumbnail_pic_s03
				+ ", uniquekey=" + uniquekey + ", thumbnail_pic_s02="
				+ thumbnail_pic_s02 + ", author_name=" + author_name
				+ ", thumbnail_pic_s=" + thumbnail_pic_s + ", type=" + type
				+ ", date=" + date + ", url=" + url + ", shifoufasong="
				+ shifoufasong + ", fasongdata=" + fasongdata + "]";
	}

	public NewsBean() {
		super();
	}

	public NewsBean(int id, String realtype, String title,
			String thumbnail_pic_s03, String uniquekey,
			String thumbnail_pic_s02, String author_name,
			String thumbnail_pic_s, String type, String date, String url,
			int shifoufasong, String fasongdata) {
		super();
		this.id = id;
		this.realtype = realtype;
		this.title = title;
		this.thumbnail_pic_s03 = thumbnail_pic_s03;
		this.uniquekey = uniquekey;
		this.thumbnail_pic_s02 = thumbnail_pic_s02;
		this.author_name = author_name;
		this.thumbnail_pic_s = thumbnail_pic_s;
		this.type = type;
		this.date = date;
		this.url = url;
		this.shifoufasong = shifoufasong;
		this.fasongdata = fasongdata;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRealtype() {
		return realtype;
	}

	public void setRealtype(String realtype) {
		this.realtype = realtype;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumbnail_pic_s03() {
		return thumbnail_pic_s03;
	}

	public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
		this.thumbnail_pic_s03 = thumbnail_pic_s03;
	}

	public String getUniquekey() {
		return uniquekey;
	}

	public void setUniquekey(String uniquekey) {
		this.uniquekey = uniquekey;
	}

	public String getThumbnail_pic_s02() {
		return thumbnail_pic_s02;
	}

	public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
		this.thumbnail_pic_s02 = thumbnail_pic_s02;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getThumbnail_pic_s() {
		return thumbnail_pic_s;
	}

	public void setThumbnail_pic_s(String thumbnail_pic_s) {
		this.thumbnail_pic_s = thumbnail_pic_s;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getShifoufasong() {
		return shifoufasong;
	}

	public void setShifoufasong(int shifoufasong) {
		this.shifoufasong = shifoufasong;
	}

	public String getFasongdata() {
		return fasongdata;
	}

	public void setFasongdata(String fasongdata) {
		this.fasongdata = fasongdata;
	}

}

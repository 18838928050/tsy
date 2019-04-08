package com.ssm.tsy.bean;

public class TsyGraphicMessage {
	private Integer id;

	private String graphicMessageNoTitle;// 网上文章----标题

	private String graphicMessageNoPicS;// 网上文章----图片

	private String graphicMessageNoUrl;// 网上文章----链接

	private String graphicMessageTitle;// 手动输入文章----标题

	private String graphicMessageJieshaoPic;// 手动输入文章----图片

	private String graphicMessageContent;// 手动输入文章----内容

	private Integer graphicMessageType;// 文章类型：1.网上文章；2.手动输入文章

	private Integer graphicMessageFatherid;// 分组id

	private Integer graphicMessageJudgeSend;// 是否发送：0未发送；1已发送

	private String graphicMessageSendDate;// 发送日期

	private Integer graphicMessageSendUserid;// 发送用户id

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGraphicMessageNoTitle() {
		return graphicMessageNoTitle;
	}

	public void setGraphicMessageNoTitle(String graphicMessageNoTitle) {
		this.graphicMessageNoTitle = graphicMessageNoTitle == null ? null
				: graphicMessageNoTitle.trim();
	}

	public String getGraphicMessageNoPicS() {
		return graphicMessageNoPicS;
	}

	public void setGraphicMessageNoPicS(String graphicMessageNoPicS) {
		this.graphicMessageNoPicS = graphicMessageNoPicS == null ? null
				: graphicMessageNoPicS.trim();
	}

	public String getGraphicMessageNoUrl() {
		return graphicMessageNoUrl;
	}

	public void setGraphicMessageNoUrl(String graphicMessageNoUrl) {
		this.graphicMessageNoUrl = graphicMessageNoUrl == null ? null
				: graphicMessageNoUrl.trim();
	}

	public Integer getGraphicMessageType() {
		return graphicMessageType;
	}

	public void setGraphicMessageType(Integer graphicMessageType) {
		this.graphicMessageType = graphicMessageType;
	}

	public String getGraphicMessageTitle() {
		return graphicMessageTitle;
	}

	public void setGraphicMessageTitle(String graphicMessageTitle) {
		this.graphicMessageTitle = graphicMessageTitle == null ? null
				: graphicMessageTitle.trim();
	}

	public String getGraphicMessageJieshaoPic() {
		return graphicMessageJieshaoPic;
	}

	public void setGraphicMessageJieshaoPic(String graphicMessageJieshaoPic) {
		this.graphicMessageJieshaoPic = graphicMessageJieshaoPic == null ? null
				: graphicMessageJieshaoPic.trim();
	}

	public Integer getGraphicMessageFatherid() {
		return graphicMessageFatherid;
	}

	public void setGraphicMessageFatherid(Integer graphicMessageFatherid) {
		this.graphicMessageFatherid = graphicMessageFatherid;
	}

	public Integer getGraphicMessageJudgeSend() {
		return graphicMessageJudgeSend;
	}

	public void setGraphicMessageJudgeSend(Integer graphicMessageJudgeSend) {
		this.graphicMessageJudgeSend = graphicMessageJudgeSend;
	}

	public String getGraphicMessageSendDate() {
		return graphicMessageSendDate;
	}

	public void setGraphicMessageSendDate(String graphicMessageSendDate) {
		this.graphicMessageSendDate = graphicMessageSendDate == null ? null
				: graphicMessageSendDate.trim();
	}

	public Integer getGraphicMessageSendUserid() {
		return graphicMessageSendUserid;
	}

	public void setGraphicMessageSendUserid(Integer graphicMessageSendUserid) {
		this.graphicMessageSendUserid = graphicMessageSendUserid;
	}

	public String getGraphicMessageContent() {
		return graphicMessageContent;
	}

	public void setGraphicMessageContent(String graphicMessageContent) {
		this.graphicMessageContent = graphicMessageContent == null ? null
				: graphicMessageContent.trim();
	}
}
package com.ssm.tsy.bean.wechat;

/**
 * 文本消息
 * 
 */
public class ResponseTextMessage extends ResponseBaseMessage {
	// 回复的消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}

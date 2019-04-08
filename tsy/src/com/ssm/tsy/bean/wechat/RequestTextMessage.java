package com.ssm.tsy.bean.wechat;
/** 
 * 文本消息 
 *  
 */
public class RequestTextMessage extends RequestBaseMessage{
	// 消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}

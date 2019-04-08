package com.ssm.tsy.util;

import java.util.Date;
import java.util.List;

import com.ssm.tsy.bean.wechat.ResponseArticle;
import com.ssm.tsy.bean.wechat.ResponseMusic;
import com.ssm.tsy.bean.wechat.ResponseMusicMessage;
import com.ssm.tsy.bean.wechat.ResponseNewsMessage;
import com.ssm.tsy.bean.wechat.ResponseTextMessage;


public class WeChatPublicUtil {
	
	/**
	 * 文本消息
	 * @param fromUserName
	 * @param toUserName
	 * @param msgType
	 * @param content
	 * @return
	 */
	public static ResponseTextMessage getTextMessage(String fromUserName,String toUserName,String msgType,String content){
		ResponseTextMessage textMessage = new ResponseTextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(msgType);
		textMessage.setContent(content);
		textMessage.setFuncFlag(0);
		return textMessage;
	}
	
	/**
	 * 音乐消息
	 * @param fromUserName
	 * @param toUserName
	 * @param msgType
	 * @param music
	 * @return
	 */
	public static ResponseMusicMessage getMusicMessage(String fromUserName,String toUserName,String msgType,ResponseMusic music){
		ResponseMusicMessage musicMessage = new ResponseMusicMessage();
		musicMessage.setToUserName(fromUserName);
		musicMessage.setFromUserName(toUserName);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMsgType(msgType);
		musicMessage.setMusic(music);
		return musicMessage;
	}
	
	/**
	 * 图文消息
	 * @param fromUserName
	 * @param toUserName
	 * @param msgType
	 * @param articleList
	 * @return
	 */
	public static ResponseNewsMessage getNewsMessage(String fromUserName,String toUserName,String msgType,List<ResponseArticle> articleList){
		ResponseNewsMessage newsMessage = new ResponseNewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(msgType);
		newsMessage.setFuncFlag(0);
		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);
		return newsMessage;
	}
}

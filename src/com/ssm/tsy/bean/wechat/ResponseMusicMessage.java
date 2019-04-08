package com.ssm.tsy.bean.wechat;

/**
 * 音乐消息
 * 
 */
public class ResponseMusicMessage extends ResponseBaseMessage {
	// 音乐
	private ResponseMusic Music;

	public ResponseMusic getMusic() {
		return Music;
	}

	public void setMusic(ResponseMusic music) {
		Music = music;
	}
}

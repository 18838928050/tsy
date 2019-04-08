package com.ssm.tsy.service;

import java.util.Map;

import com.ssm.tsy.bean.WeChatAPP;

public interface WeChatAPPService {
	
	/**
	 * 添加
	 * 
	 * @param bean
	 * @return
	 */
	public Map<String,Object> add(WeChatAPP bean);

	/**
	 * 修改
	 * 
	 * @param bean
	 * @return
	 */
	public int update(WeChatAPP bean);
	
	/**
	 * 查询
	 * 
	 * @return
	 */
	public WeChatAPP Select(Map<String,Object> map);
}

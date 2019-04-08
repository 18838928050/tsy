package com.ssm.tsy.dao;


import java.util.Map;

import com.ssm.tsy.bean.WeChatAPP;

public interface WeChatAPPDao {

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
	public WeChatAPP Select(Map<String, Object> map);
	
	/**
	 * 查询
	 * 
	 * @return
	 */
	public WeChatAPP SelectAll();

}

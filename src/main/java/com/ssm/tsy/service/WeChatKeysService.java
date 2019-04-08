package com.ssm.tsy.service;

import java.util.List;

import com.ssm.tsy.bean.WeChatKeys;

public interface WeChatKeysService {

	/**
	 * 查询所有的关键字列表
	 * 
	 * @return
	 */
	public List<WeChatKeys> SelectAll();

	/**
	 * 根据关键字的键来查询
	 * 
	 * @param keyvalue
	 * @return
	 */
	public WeChatKeys SelectAllByKey(String keyvalue);

	/**
	 * 根据关键字类别来查询
	 * 
	 * @param keyclass
	 * @return
	 */
	public List<WeChatKeys> SelectAllByKeyClass(WeChatKeys bean);

	/**
	 * 添加新的关键字
	 * 
	 * @param bean
	 * @return
	 */
	public int AddWeChatKeys(WeChatKeys bean);

	/**
	 * 修改关键字
	 * 
	 * @param bean
	 * @return
	 */
	public int UpdateWeChatKeys(WeChatKeys bean);

	/**
	 * 删除关键字
	 * 
	 * @param id
	 * @return
	 */
	public int DeleteWeChatKeys(int id);

}

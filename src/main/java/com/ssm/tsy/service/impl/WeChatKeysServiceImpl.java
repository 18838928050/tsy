package com.ssm.tsy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.tsy.bean.WeChatKeys;
import com.ssm.tsy.dao.WeChatKeysDao;
import com.ssm.tsy.service.WeChatKeysService;

@Service
public class WeChatKeysServiceImpl implements WeChatKeysService {

	@Resource
	private WeChatKeysDao weChatKeysDao;

	/**
	 * 查询所有的关键字列表
	 * 
	 * @return
	 */
	@Override
	public List<WeChatKeys> SelectAll() {
		return weChatKeysDao.SelectAll();
	}

	/**
	 * 根据关键字的键来查询
	 * 
	 * @param keyvalue
	 * @return
	 */
	@Override
	public WeChatKeys SelectAllByKey(String keyvalue) {
		return weChatKeysDao.SelectAllByKey(keyvalue);
	}

	/**
	 * 根据关键字类别来查询
	 * 
	 * @param keyclass
	 * @return
	 */
	@Override
	public List<WeChatKeys> SelectAllByKeyClass(WeChatKeys bean) {
		return weChatKeysDao.SelectAllByKeyClass(bean);
	}

	/**
	 * 添加新的关键字
	 * 
	 * @param bean
	 * @return
	 */
	@Override
	public int AddWeChatKeys(WeChatKeys bean) {
		return weChatKeysDao.AddWeChatKeys(bean);
	}

	/**
	 * 修改关键字
	 * 
	 * @param bean
	 * @return
	 */
	@Override
	public int UpdateWeChatKeys(WeChatKeys bean) {
		return weChatKeysDao.UpdateWeChatKeys(bean);
	}

	/**
	 * 删除关键字
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public int DeleteWeChatKeys(int id) {
		return weChatKeysDao.DeleteWeChatKeys(id);
	}

}

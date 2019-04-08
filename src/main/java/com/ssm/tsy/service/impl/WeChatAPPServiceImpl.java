package com.ssm.tsy.service.impl;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.tsy.bean.WeChatAPP;
import com.ssm.tsy.dao.WeChatAPPDao;
import com.ssm.tsy.service.WeChatAPPService;

@Service
public class WeChatAPPServiceImpl implements WeChatAPPService {

	@Resource
	WeChatAPPDao weChatAPPDao;

	/**
	 * 添加
	 * 
	 * @param bean
	 * @return
	 */
	@Override
	public Map<String,Object> add(WeChatAPP bean) {
		return weChatAPPDao.add(bean);
	}

	/**
	 * 修改
	 * 
	 * @param bean
	 * @return
	 */
	@Override
	public int update(WeChatAPP bean) {
		return weChatAPPDao.update(bean);
	}

	/**
	 * 查询
	 */
	@Override
	public WeChatAPP Select(Map<String, Object> map) {
		return weChatAPPDao.Select(map);
	}

}

package com.ssm.tsy.service;

import com.ssm.tsy.bean.TsyUserLoginLog;

public interface TsyUserLoginLogService {
	
	/**
	 * 添加用户登陆记录
	 * @throws Exception 
	 */
	int insert(TsyUserLoginLog record) throws Exception;
}

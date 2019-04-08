package com.ssm.tsy.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.tsy.bean.TsyUserLoginLog;
import com.ssm.tsy.dao.TsyUserLoginLogDao;
import com.ssm.tsy.service.TsyUserLoginLogService;
@Service
public class TsyUserLoginLogServiceImpl implements TsyUserLoginLogService{
	
	@Resource
	private TsyUserLoginLogDao tsyUserLoginLogDao;


	/**
	 * 添加用户登陆记录
	 * @throws Exception 
	 */
	@Override
	public int insert(TsyUserLoginLog record) throws Exception {
		String login_table = "tsy_user_login_log";
		String[] Now_time = new SimpleDateFormat("yyyy-MM-dd").format(
				new Date()).split("-");
		int Year = Integer.parseInt(Now_time[0]);
		int Month = Integer.parseInt(Now_time[1]);
		String new_login_table = login_table + String.format("%04d", (Year))
				+ String.format("%02d", Month);
		record.setNewtable(new_login_table);
		return tsyUserLoginLogDao.insert(record);
	}

}

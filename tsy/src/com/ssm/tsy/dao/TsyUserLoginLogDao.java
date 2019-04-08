package com.ssm.tsy.dao;

import com.ssm.tsy.bean.TsyUserLoginLog;

public interface TsyUserLoginLogDao {
	int deleteByPrimaryKey(Integer id) throws Exception;

	int insert(TsyUserLoginLog record) throws Exception;

	int insertSelective(TsyUserLoginLog record) throws Exception;

	TsyUserLoginLog selectByPrimaryKey(Integer id) throws Exception;

	int updateByPrimaryKeySelective(TsyUserLoginLog record) throws Exception;

	int updateByPrimaryKey(TsyUserLoginLog record) throws Exception;
}
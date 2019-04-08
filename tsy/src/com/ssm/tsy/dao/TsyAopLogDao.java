package com.ssm.tsy.dao;

import com.ssm.tsy.bean.TsyAopLog;

public interface TsyAopLogDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TsyAopLog record);

    int insertSelective(TsyAopLog record);

    TsyAopLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TsyAopLog record);

    int updateByPrimaryKey(TsyAopLog record);
}
package com.ssm.tsy.dao;

import java.util.List;
import java.util.Map;


public interface TsyMhUserMapper {
    public int deleteByPrimaryKey(Map<String,Object> map) throws Exception;

    public int insert(Map<String,Object> map) throws Exception;

    public int insertSelective(Map<String,Object> map) throws Exception;

    public Map<String,Object> selectByPrimaryKey(Map<String,Object> map) throws Exception;

    public int updateByPrimaryKeySelective(Map<String,Object> map) throws Exception;

    public int updateByPrimaryKey(Map<String,Object> map) throws Exception;
    
    public Map<String,Object> selectByNumber(Map<String,Object> map) throws Exception;
    
    public Map<String,Object> selectByNumberAndPwd(Map<String,Object> map) throws Exception;
    
    public int insertRole(Map<String,Object> map) throws Exception;

	public Map<String, Object> queryUserStatistics(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> queryLatelyMonthLog(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> queryLatelyEightDayLog(Map<String, Object> map) throws Exception;
}
package com.ssm.tsy.dao;

import java.util.List;
import java.util.Map;


public interface TsyUserCustomOptionsClassMapper {
    public int deleteByPrimaryKey(Map<String,Object> map) throws Exception;

    public int insert(Map<String,Object> map) throws Exception;

    public int insertSelective(Map<String,Object> map) throws Exception;

    public Map<String,Object> selectByPrimaryKey(Map<String,Object> map) throws Exception;

    public int updateByPrimaryKeySelective(Map<String,Object> map) throws Exception;
    
    public List<Map<String,Object>> selectByUserId(Map<String,Object> map) throws Exception;

}
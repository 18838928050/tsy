package com.ssm.tsy.dao;

import java.util.List;
import java.util.Map;


public interface WechatSetupMapper {
    public int deleteByPrimaryKey(Map<String,Object> map) throws Exception;

    public Map<String,Object> insert(Map<String,Object> map) throws Exception;

    public Map<String,Object> insertSelective(Map<String,Object> map) throws Exception;

    public Map<String,Object> selectByPrimaryKey(Map<String,Object> map) throws Exception;

    public int updateByPrimaryKeySelective(Map<String,Object> map) throws Exception;

    public int updateByPrimaryKey(Map<String,Object> map) throws Exception;
    
    public List<Map<String,Object>> queryWechatSetupList(Map<String,Object> map) throws Exception;
    
    public int updateWechatSetupList(Map<String,Object> map) throws Exception;
    
    public Map<String,Object> queryWechatSetupListByAppId(Map<String,Object> map) throws Exception;
}
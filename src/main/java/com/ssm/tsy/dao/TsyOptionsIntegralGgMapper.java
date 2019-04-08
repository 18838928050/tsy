package com.ssm.tsy.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface TsyOptionsIntegralGgMapper {
    public int deleteByPrimaryKey(Map<String,Object> map) throws Exception;

    public int insert(Map<String,Object> map) throws Exception;

    public int insertSelective(Map<String,Object> map) throws Exception;

    public Map<String,Object> selectByPrimaryKey(Map<String,Object> map) throws Exception;

    public int updateByPrimaryKeySelective(Map<String,Object> map) throws Exception;

    public int updateByPrimaryKey(Map<String,Object> map) throws Exception;
    
    public List<Map<String,Object>> queryTsyOptionsIntegralGg(Map<String,Object> map,PageBounds pageBounds) throws Exception;
    
    public Map<String,Object> queryTsyOptionsIntegralGgByoptionsIntegralType(Map<String,Object> map) throws Exception;
}
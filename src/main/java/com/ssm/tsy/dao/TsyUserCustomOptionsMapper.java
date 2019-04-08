package com.ssm.tsy.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface TsyUserCustomOptionsMapper {
	public int deleteByPrimaryKey(Map<String,Object> map) throws Exception;

	public int insertSelective(Map<String,Object> map) throws Exception;

	public Map<String,Object> selectByPrimaryKey(Map<String,Object> map) throws Exception;

	public int updateByPrimaryKeySelective(Map<String,Object> map) throws Exception;

	public int updateByPrimaryKeyWithBLOBs(Map<String,Object> map) throws Exception;

	public int updateByPrimaryKey(Map<String,Object> map) throws Exception;

	public List<Map<String, Object>> queryVedioList(Map<String, Object> map, PageBounds pageBounds) throws Exception;

	public int updateVedioState(Map<String, Object> map) throws Exception;

	public Map<String, Object> queryVedioListById(Map<String, Object> map) throws Exception;
	
	public int updateVedioById(Map<String, Object> map) throws Exception;
}
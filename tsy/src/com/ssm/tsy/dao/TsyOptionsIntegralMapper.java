package com.ssm.tsy.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;


public interface TsyOptionsIntegralMapper {
    public int deleteByPrimaryKey(Map<String,Object> map) throws Exception;

    public int insert(Map<String,Object> map) throws Exception;

    public int insertSelective(Map<String,Object> map) throws Exception;

    public Map<String,Object> selectByPrimaryKey(Map<String,Object> map) throws Exception;

    public int updateByPrimaryKeySelective(Map<String,Object> map) throws Exception;

    public int updateByPrimaryKey(Map<String,Object> map) throws Exception;

	public List<Map<String, Object>> selectEvaluateuserIdOptionsId(Map<String, Object> map) throws Exception;

	public int selectIntegral(Object object) throws Exception;
	
	public List<Map<String, Object>> queryMaxStarTopTenPeople(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> queryMaxIntegralTopTen(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> queryVedioClassTopThree(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> queryVedioTopForuth(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> queryVedioTopFifteen(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> queryVedioByOptionClassAndUser(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> queryVedioByOptionClassAndNotequalUser(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> queryVedio(Map<String, Object> map, PageBounds pageBounds) throws Exception;

	public List<Map<String, Object>> queryVedioByRand(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> queryVedioByRandEleven(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> wechatSearch(Map<String, Object> map, PageBounds pageBounds) throws Exception;
	
	public Map<String, Object> wechatSearchFatherIdById(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> queryWechatKnowledge(Map<String, Object> map, PageBounds pageBounds) throws Exception;

	public Map<String, Object> queryWechatKnowledgeByRowId(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> queryWechatKnowledgeNewFifth(Map<String, Object> map);

	public List<Map<String, Object>> queryVedioFjByRowId(Map<String, Object> map) throws Exception;
    
}
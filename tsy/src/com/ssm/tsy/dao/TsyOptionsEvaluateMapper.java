package com.ssm.tsy.dao;

import java.util.List;
import java.util.Map;


public interface TsyOptionsEvaluateMapper {
	public int deleteByPrimaryKey(Map<String,Object> map) throws Exception;

    public int insert(Map<String,Object> map) throws Exception;

    public int insertSelective(Map<String,Object> map) throws Exception;

    public Map<String,Object> selectByPrimaryKey(Map<String,Object> map) throws Exception;

    public int updateByPrimaryKeySelective(Map<String,Object> map) throws Exception;

    public int updateByPrimaryKeyWithBLOBs(Map<String,Object> map) throws Exception;

    public int updateByPrimaryKey(Map<String,Object> map) throws Exception;

	public int addContentAndNumStar(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectStraNum(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectTsyOptionsIntegralGg(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectTsyOptionsIntegral(Map<String, Object> map) throws Exception;

	public void addTsyoptionsintegral(Map<String, Object> tsyOptionsIntegralGgmap) throws Exception;

	public void updateTsyoptionsintegral(Map<String, Object> tsyOptionsIntegralGgmap) throws Exception;
	
	public Map<String, Object> queryTsyoptionsStar(Map<String, Object> tsyOptionsIntegralGgmap) throws Exception;
	
	public List<Map<String, Object>> queryTsyoptionsEvaluate(Map<String, Object> tsyOptionsIntegralGgmap) throws Exception;
	
	public List<Map<String, Object>> queryTsyoptionsEvaluateReply(Map<String, Object> tsyOptionsIntegralGgmap) throws Exception;

	public int insertContentAndParentid(Map<String, Object> map) throws Exception;

}
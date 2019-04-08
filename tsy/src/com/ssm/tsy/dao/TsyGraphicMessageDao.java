package com.ssm.tsy.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;


public interface TsyGraphicMessageDao {
    public int deleteByPrimaryKey(Map<String,Object> map) throws Exception;

    public int insert(Map<String,Object> map) throws Exception;

    public int insertSelective(Map<String,Object> map) throws Exception;

    public Map<String,Object> selectByPrimaryKey(Map<String,Object> map) throws Exception;

    public int updateByPrimaryKeySelective(Map<String,Object> map) throws Exception;

    public int updateByPrimaryKeyWithBLOBs(Map<String,Object> map) throws Exception;

    public int updateByPrimaryKey(Map<String,Object> map) throws Exception;
    
    public int insertGraphicMessage(Map<String,Object> map) throws Exception;

	public void updateGraphicMessage(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> queryGraphicMessage(Map<String, Object> map, PageBounds pageBounds) throws Exception;

	public List<Map<String, Object>> queryGraphicMessageByGraphicMessageFatherid(Map<String, Object> map) throws Exception;
	
	public int deleteByMessageId(Map<String,Object> map) throws Exception;
	
	public int deleteByMessageFatherId(Map<String,Object> map) throws Exception;

	public List<Map<String, Object>> queryGraphicMessageByRowId(Map<String, Object> cdBean) throws Exception;
	
	public List<Map<String, Object>> queryGraphicMessageByFatherId(Map<String, Object> cdBean) throws Exception;

	public List<Map<String, Object>> insertMessageAndSelectById(Map<String, Object> map) throws Exception;

	public int updateSendStateByRowId(Map<String, Object> map) throws Exception;

}
package com.ssm.tsy.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface LeavingMessageDao {

	public int insertLeavingMessage(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> queryLeavingMessage(Map<String, Object> map, PageBounds pageBounds) throws Exception;

}

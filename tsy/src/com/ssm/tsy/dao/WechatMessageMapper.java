package com.ssm.tsy.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;


public interface WechatMessageMapper {
    public Map<String,Object> deleteByPrimaryKey(Map<String,Object> map);

    public void insert(Map<String,Object> map);

    /**
	 * 添加回复消息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
    public void insertSelective(Map<String,Object> map);
    
    /**
	 * 查询微信消息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
    public List<Map<String,Object>> selectWechatMessage(Map<String,Object> map,PageBounds pageBounds);

    public Map<String,Object> selectByPrimaryKey(Map<String,Object> map);

    public void updateByPrimaryKeySelective(Map<String,Object> map);

    public void updateByPrimaryKey(Map<String,Object> map);
}
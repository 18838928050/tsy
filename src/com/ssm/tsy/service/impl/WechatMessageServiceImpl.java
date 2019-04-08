package com.ssm.tsy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.tsy.dao.WechatMessageMapper;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.WechatMessageService;
import com.ssm.tsy.util.Constants;
import com.ssm.tsy.util.DateUtil;

@Service
public class WechatMessageServiceImpl implements WechatMessageService{
	
	@Resource
	private WechatMessageMapper wechatMessageMapper;
	
	/**
	 * 添加消息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public Map<String,Object> insertWechatMessage(Map<String,Object> map) throws Exception {
		map.put("wechatMessageTime", DateUtil.getTimeAndToString());
		wechatMessageMapper.insertSelective(map);
		return map;
	}
	
	/**
	 * 添加回复消息
	 */
	@Override
	public void updateWechatMessage(Map<String,Object> map) throws Exception {
		map.put("wechatMessageResTime", DateUtil.getTimeAndToString());
		map.put("wechatMessageRes", Constants.WECHAT_MESSAGE_STATE_ZC);//设置状态为已回复
		wechatMessageMapper.updateByPrimaryKeySelective(map);
	}

	/**
	 * 查询微信消息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectWechatMessage(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		int page = Integer.parseInt(map.get("offset").toString())/Integer.parseInt(map.get("limit").toString());
		page++;
		int limit = Integer.parseInt(map.get("limit").toString());
		List<Map<String,Object>> beans = wechatMessageMapper.selectWechatMessage(map,new PageBounds(page,limit,true));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>)beans;
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(beans);
		outputObject.settotal(total);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
	
}

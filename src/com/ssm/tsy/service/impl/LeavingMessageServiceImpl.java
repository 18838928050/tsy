package com.ssm.tsy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.tsy.dao.LeavingMessageDao;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.LeavingMessageService;
import com.ssm.tsy.util.Constants;
import com.ssm.tsy.util.DateUtil;
@Service
public class LeavingMessageServiceImpl implements LeavingMessageService {
	
	
	@Resource
	private LeavingMessageDao leavingMessageDao;
	
	/**
	 * 添加留言
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
 	@Override
	public void insertLeavingMessage(InputObject inputObject,OutputObject outputObject) throws Exception {
 		Map<String,Object> map =inputObject.getParams();
 		map.put("messageTime", DateUtil.getTimeAndToString());
		leavingMessageDao.insertLeavingMessage(map);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

 	/**
	 * 查看留言内容
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@Override
	public void queryLeavingMessage(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map =inputObject.getParams();
		int page = Integer.parseInt(map.get("offset").toString())/Integer.parseInt(map.get("limit").toString());
		page++;
		int limit = Integer.parseInt(map.get("limit").toString());
		List<Map<String,Object>> beans = leavingMessageDao.queryLeavingMessage(map,new PageBounds(page,limit));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>)beans;
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(beans);
		outputObject.settotal(total);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

}

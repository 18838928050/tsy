package com.ssm.tsy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.tsy.bean.UserBean;
import com.ssm.tsy.dao.TsyUserCustomOptionsLabelMapper;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyUserCustomOptionsLabelService;
import com.ssm.tsy.util.Constants;
import com.ssm.tsy.util.DateUtil;

@Service
public class TsyUserCustomOptionsLabelServiceImpl implements TsyUserCustomOptionsLabelService{
	
	@Resource
	private TsyUserCustomOptionsLabelMapper tsyUserCustomOptionsLabelMapper;
	
	@SuppressWarnings("static-access")
	@Override
	public void addTsyUserCustomOptionsLabel(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		UserBean user = (UserBean) inputObject.getRequest().getSession().getAttribute("user");
		map.put("userId", user.getId());
		map.put("labelTime", DateUtil.getTimeAndToString());
		map.put("labelState", Constants.TSY_USER_CUSTOM_OPTIONS_LABEL_STATE_ZC);//正常
		tsyUserCustomOptionsLabelMapper.insertSelective(map);
		outputObject.setBean(map);
		outputObject.setreturnCode(Constants.ADD_SUCCESS);
		outputObject.setreturnMessage("成功");
	}

	@SuppressWarnings("static-access")
	@Override
	public void queryTsyUserCustomOptionsLabel(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		UserBean user = (UserBean) inputObject.getRequest().getSession().getAttribute("user");
		map.put("userId", user.getId());
		map.put("labelState", Constants.TSY_USER_CUSTOM_OPTIONS_LABEL_STATE_ZC);//正常
		List<Map<String,Object>> mapList = tsyUserCustomOptionsLabelMapper.selectByUserId(map);
		outputObject.setBeans(mapList);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
	
	
}

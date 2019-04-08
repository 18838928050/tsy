package com.ssm.tsy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.tsy.bean.UserBean;
import com.ssm.tsy.dao.WechatSetupMapper;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.WechatSetupService;
import com.ssm.tsy.util.Constants;


@Service
public class WechatSetupServiceImpl implements WechatSetupService{

	
	@Resource
	private WechatSetupMapper wechatSetupMapper;

	/**
	 * 查询设置
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void queryWechatSetupList(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		UserBean user = (UserBean) inputObject.getRequest().getSession().getAttribute("user");
		map.put("userId", user.getId());
		List<Map<String,Object>> beans = wechatSetupMapper.queryWechatSetupList(map);
		outputObject.setBeans(beans);
		outputObject.settotal(beans.size());
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 修改智能回复设置
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateWechatSetupList(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(wechatSetupMapper.updateWechatSetupList(map)>=1){
			outputObject.setreturnCode(Constants.ZERO);
			outputObject.setreturnMessage("切换成功");
		}else{
			outputObject.setreturnCode(Constants.UPDATE_ERROR);
			outputObject.setreturnMessage("状态修改失败");
		}
	}
	
	
	
}

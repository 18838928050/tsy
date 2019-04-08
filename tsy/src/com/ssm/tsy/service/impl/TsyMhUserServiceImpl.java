package com.ssm.tsy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.tsy.bean.UserBean;
import com.ssm.tsy.dao.TsyMhUserMapper;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyMhUserService;
import com.ssm.tsy.util.Constants;

@Service
public class TsyMhUserServiceImpl implements TsyMhUserService{

	@Resource
	private TsyMhUserMapper tsyMhUserMapper;

	/**
	 * 后台管理粉丝关注统计（取关人数，冻结人数，总人数）
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void queryUserStatistics(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String ,Object> map = inputObject.getParams();
		Map<String ,Object> bean = tsyMhUserMapper.queryUserStatistics(map);
		outputObject.setBean(bean);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 查询最近一个月的登录地址
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@Override
	public void queryLatelyMonthLog(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String ,Object> map = inputObject.getParams();
		UserBean user = (UserBean) inputObject.getRequest().getSession().getAttribute("user");
		map.put("userId", user.getId());
		map.put("tableName", Constants.getLoginThisMonthTable());
		List<Map<String ,Object>> beans = tsyMhUserMapper.queryLatelyMonthLog(map);
		outputObject.setBeans(beans);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 查询最近八天的登录次数
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@Override
	public void queryLatelyEightDayLog(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String ,Object> map = inputObject.getParams();
		UserBean user = (UserBean) inputObject.getRequest().getSession().getAttribute("user");
		map.put("userId", user.getId());
		map.put("tableNameThis", Constants.getLoginThisMonthTable());
		map.put("tableNamePrev", Constants.getLoginPrevMonthTable());
		List<Map<String ,Object>> beans = tsyMhUserMapper.queryLatelyEightDayLog(map);
		outputObject.setBeans(beans);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
	
}

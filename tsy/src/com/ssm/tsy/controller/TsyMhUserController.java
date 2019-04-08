package com.ssm.tsy.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyMhUserService;


@Controller
public class TsyMhUserController {

	@Resource
	private TsyMhUserService tsyMhUserService;
	
	
	/**
	 * 后台管理粉丝关注统计（取关人数，冻结人数，总人数）
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/TsyMhUserController/queryUserStatistics")
	@ResponseBody
	public void queryUserStatistics(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyMhUserService.queryUserStatistics(inputObject, outputObject);
	}
	
	/**
	 * 查询最近一个月的登录地址
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/TsyMhUserController/queryLatelyMonthLog")
	@ResponseBody
	public void queryLatelyMonthLog(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyMhUserService.queryLatelyMonthLog(inputObject, outputObject);
	}
	
	/**
	 * 查询最近八天的登录次数
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/TsyMhUserController/queryLatelyEightDayLog")
	@ResponseBody
	public void queryLatelyEightDayLog(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyMhUserService.queryLatelyEightDayLog(inputObject, outputObject);
	}
	
}

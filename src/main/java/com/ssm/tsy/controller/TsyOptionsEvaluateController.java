package com.ssm.tsy.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyOptionsEvaluateService;

@Controller
public class TsyOptionsEvaluateController {
	
	@Resource
	private TsyOptionsEvaluateService tsyOptionsEvaluateService;
	
	/**
	 * 查询百度云搜索
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("post/TsyOptionsEvaluate/queryBaiDuSearch")
	@ResponseBody
	public void queryBaiDuSearch(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyOptionsEvaluateService.queryBaiDuSearch(inputObject,outputObject);
	}
	
}

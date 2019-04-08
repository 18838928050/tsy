package com.ssm.tsy.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyUserCustomOptionsClassService;

@Controller
public class TsyUserCustomOptionsClassController {
	
	@Resource
	private TsyUserCustomOptionsClassService tsyUserCustomOptionsClassService;
	
	/**
	 * 添加模块
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/Class/addTsyUserCustomOptionsClass")
	@ResponseBody
	public void addTsyUserCustomOptionsClass(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyUserCustomOptionsClassService.addTsyUserCustomOptionsClass(inputObject, outputObject);
	}
	
	/**
	 * 根据用户id查询模块
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/Class/queryTsyUserCustomOptionsClass")
	@ResponseBody
	public void queryTsyUserCustomOptionsClass(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyUserCustomOptionsClassService.queryTsyUserCustomOptionsClass(inputObject, outputObject);
	}
	
}

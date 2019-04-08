package com.ssm.tsy.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyUserCustomOptionsLabelService;

@Controller
public class TsyUserCustomOptionsLabelController {
	
	@Resource
	private TsyUserCustomOptionsLabelService tsyUserCustomOptionsLabelService;
	
	/**
	 * 添加标签
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/Class/addTsyUserCustomOptionsLabel")
	@ResponseBody
	public void addTsyUserCustomOptionsLabel(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyUserCustomOptionsLabelService.addTsyUserCustomOptionsLabel(inputObject, outputObject);
	}
	
	/**
	 * 根据用户id查询标签
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/Class/queryTsyUserCustomOptionsLabel")
	@ResponseBody
	public void queryTsyUserCustomOptionsLabel(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyUserCustomOptionsLabelService.queryTsyUserCustomOptionsLabel(inputObject, outputObject);
	}
	
}

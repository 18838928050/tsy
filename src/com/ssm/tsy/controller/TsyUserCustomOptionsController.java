package com.ssm.tsy.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyUserCustomOptionsService;


@Controller
public class TsyUserCustomOptionsController {
	
	@Resource
	private TsyUserCustomOptionsService tsyUserCustomOptionsService;
	
	/**
	 * 添加内容
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/add/insertTsyUserCustomOptions")
	@ResponseBody
	public void insertTsyUserCustomOptions(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyUserCustomOptionsService.insertTsyUserCustomOptions(inputObject, outputObject);
	}
	
	/**
	 * 查询内容列表
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("post/TsyUserCustomOptions/queryVedioList")
	@ResponseBody
	public void queryVedioList(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyUserCustomOptionsService.queryVedioList(inputObject, outputObject);
	}
	
	/**
	 * 查询内容
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("post/TsyUserCustomOptions/queryVedioListById")
	@ResponseBody
	public void queryVedioListById(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyUserCustomOptionsService.queryVedioListById(inputObject, outputObject);
	}
	
	/**
	 * 修改视频状态
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("post/TsyUserCustomOptions/updateVedioState")
	@ResponseBody
	public void updateVedioState(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyUserCustomOptionsService.updateVedioState(inputObject, outputObject);
	}
	
	/**
	 * 修改视频资料
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("post/TsyUserCustomOptions/updateVedioById")
	@ResponseBody
	public void updateVedioById(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyUserCustomOptionsService.updateVedioById(inputObject, outputObject);
	}
	
}

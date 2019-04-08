package com.ssm.tsy.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyOptionsIntegralGgService;

@Controller
public class TsyOptionsIntegralGgController {
	
	@Resource
	private TsyOptionsIntegralGgService tsyOptionsIntegralGgService;
	
	/**
	 * 添加积分标准
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/TsyOptionsIntegralGg/insertTsyOptionsIntegralGg")
	@ResponseBody
	public void insertTsyOptionsIntegralGg(InputObject inputObject,OutputObject outputObject) throws Exception{
		tsyOptionsIntegralGgService.insertTsyOptionsIntegralGg(inputObject, outputObject);
	}
	
	/**
	 * 修改积分标准
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/TsyOptionsIntegralGg/updateTsyOptionsIntegralGg")
	@ResponseBody
	public void updateTsyOptionsIntegralGg(InputObject inputObject,OutputObject outputObject) throws Exception{
		tsyOptionsIntegralGgService.updateTsyOptionsIntegralGg(inputObject, outputObject);
	}
	
	/**
	 * 删除积分标准
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/TsyOptionsIntegralGg/daleteTsyOptionsIntegralGg")
	@ResponseBody
	public void daleteTsyOptionsIntegralGg(InputObject inputObject,OutputObject outputObject) throws Exception{
		tsyOptionsIntegralGgService.daleteTsyOptionsIntegralGg(inputObject, outputObject);
	}
	
	/**
	 * 查看积分标准
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/TsyOptionsIntegralGg/queryTsyOptionsIntegralGg")
	@ResponseBody
	public void queryTsyOptionsIntegralGg(InputObject inputObject,OutputObject outputObject) throws Exception{
		tsyOptionsIntegralGgService.queryTsyOptionsIntegralGg(inputObject, outputObject);
	}
	
	/**
	 * 根据id查看积分标准
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/TsyOptionsIntegralGg/queryTsyOptionsIntegralGgById")
	@ResponseBody
	public void queryTsyOptionsIntegralGgById(InputObject inputObject,OutputObject outputObject) throws Exception{
		tsyOptionsIntegralGgService.queryTsyOptionsIntegralGgById(inputObject, outputObject);
	}
	
}

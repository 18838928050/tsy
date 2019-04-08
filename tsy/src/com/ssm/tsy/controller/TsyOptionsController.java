package com.ssm.tsy.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyOptionsService;


@Controller
public class TsyOptionsController {
	
	@Resource
	private TsyOptionsService tsyOptionsService;
	
	/**
	 * 上传附件
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("post/TsyOptions/upload")
	@ResponseBody
	public void upload(InputObject inputObject,OutputObject outputObject,@RequestParam("files") CommonsMultipartFile files) throws Exception {
		tsyOptionsService.insertOptions(inputObject, outputObject, files);
	}
	
	/**
	 * 上传图文消息封面
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("post/TsyOptions/uploadGraphicMessage")
	@ResponseBody
	public void uploadGraphicMessage(InputObject inputObject,OutputObject outputObject,@RequestParam("imgFm") CommonsMultipartFile files) throws Exception {
		tsyOptionsService.insertOptions(inputObject, outputObject, files);
	}
	
	/**
	 * 上传主文件
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("post/TsyOptions/uploadVedio")
	@ResponseBody
	public void uploadVedio(InputObject inputObject,OutputObject outputObject,@RequestParam("vedio") CommonsMultipartFile files) throws Exception {
		tsyOptionsService.insertOptions(inputObject, outputObject, files);
	}
	
	/**
	 * 删除附件
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("post/TsyOptions/dalete")
	@ResponseBody
	public void dalete(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyOptionsService.deleteOptions(inputObject, outputObject);
	}
	
	/**
	 * 根据用户ID查看对应已经上传的附件
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("post/TsyOptions/queryOptionsList")
	@ResponseBody
	public void queryOptionsList(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyOptionsService.queryOptionsList(inputObject, outputObject);
	}
	
}

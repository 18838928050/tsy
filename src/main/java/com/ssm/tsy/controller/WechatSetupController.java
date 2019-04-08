package com.ssm.tsy.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.WechatSetupService;


@Controller
public class WechatSetupController {

	@Resource
	private WechatSetupService wechatSetupService;
	
	
	/**
	 * 查询设置
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatSetup/queryWechatSetupList")
	@ResponseBody
	public void queryWechatSetupList(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatSetupService.queryWechatSetupList(inputObject,outputObject);
	}
	
	/**
	 * 修改智能回复设置
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatSetup/updateWechatSetupList")
	@ResponseBody
	public void updateWechatSetupList(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatSetupService.updateWechatSetupList(inputObject,outputObject);
	}
	
}

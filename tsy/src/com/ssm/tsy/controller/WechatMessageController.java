package com.ssm.tsy.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.WechatMessageService;


@Controller
public class WechatMessageController {

	@Resource
	private WechatMessageService wechatMessageService;
	
	/**
	 * 查询微信消息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatMessage/selectWechatMessage")
	@ResponseBody
	public void selectWechatMessage(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatMessageService.selectWechatMessage(inputObject, outputObject);
	}
	
}

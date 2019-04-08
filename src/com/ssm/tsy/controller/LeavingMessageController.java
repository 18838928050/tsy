package com.ssm.tsy.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.LeavingMessageService;

@Controller
public class LeavingMessageController {
	
	@Resource
	private LeavingMessageService leavingMessageService;
	
	/**
	 * 添加留言
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@RequestMapping("post/admTsy/insertLeavingMessage")
	@ResponseBody
	public void insertLeavingMessage(InputObject inputObject,OutputObject outputObject) throws Exception{
		leavingMessageService.insertLeavingMessage(inputObject,outputObject);
	}
	
	/**
	 * 查看留言内容
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@RequestMapping("post/leavingMessage/queryLeavingMessage")
	@ResponseBody
	public void queryLeavingMessage(InputObject inputObject,OutputObject outputObject) throws Exception{
		leavingMessageService.queryLeavingMessage(inputObject,outputObject);
	}
	
	
}

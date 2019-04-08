package com.ssm.tsy.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyGraphicMessageService;


@Controller
public class TsyGraphicMessageController {
	
	@Resource
	private TsyGraphicMessageService tsyGraphicMessageService;
	
	/**
	 * 查询所有的图文消息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/GraphicMessage/queryGraphicMessage")
	@ResponseBody
	public void queryGraphicMessage(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyGraphicMessageService.queryGraphicMessage(inputObject, outputObject);
	}
	
	/**
	 * 插入图文消息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/GraphicMessage/insertGraphicMessage")
	@ResponseBody
	public void insertGraphicMessage(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyGraphicMessageService.insertGraphicMessage(inputObject, outputObject);
	}
	
	/**
	 * 编辑图文消息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/GraphicMessage/updateGraphicMessage")
	@ResponseBody
	public void updateGraphicMessage(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyGraphicMessageService.updateGraphicMessage(inputObject, outputObject);
	}
	
	/**
	 * 根据ID查询图文消息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/GraphicMessage/queryGraphicMessageById")
	@ResponseBody
	public void queryGraphicMessageById(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyGraphicMessageService.queryGraphicMessageById(inputObject, outputObject);
	}
	
	/**
	 * 删除图文消息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/GraphicMessage/deleteGraphicMessageById")
	@ResponseBody
	public void deleteGraphicMessageById(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyGraphicMessageService.deleteGraphicMessageById(inputObject, outputObject);
	}
	
	/**
	 * 修改页面中查询图文消息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/GraphicMessage/updateGraphicMessageSelect")
	@ResponseBody
	public void updateGraphicMessageSelect(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyGraphicMessageService.updateGraphicMessageSelect(inputObject, outputObject);
	}

/***********************************************************************************************************************************************************************/
	/**
     * 发送图文消息
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
	@RequestMapping("post/GraphicMessage/sendMessage")
	@ResponseBody
	public void sendMessage(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyGraphicMessageService.insertMessage(inputObject, outputObject);
	}
	
}

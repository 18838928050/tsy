package com.ssm.tsy.service;

import java.util.Map;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;

public interface WechatMessageService {

	public Map<String,Object> insertWechatMessage(Map<String,Object> map) throws Exception;

	public void selectWechatMessage(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void updateWechatMessage(Map<String, Object> map) throws Exception;

}

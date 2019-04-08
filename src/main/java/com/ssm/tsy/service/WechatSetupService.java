package com.ssm.tsy.service;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;

public interface WechatSetupService {

	public void queryWechatSetupList(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void updateWechatSetupList(InputObject inputObject, OutputObject outputObject) throws Exception;

}

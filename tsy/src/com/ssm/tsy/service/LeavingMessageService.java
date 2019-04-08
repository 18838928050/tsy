package com.ssm.tsy.service;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;

public interface LeavingMessageService {

	public void insertLeavingMessage(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryLeavingMessage(InputObject inputObject, OutputObject outputObject) throws Exception;

}

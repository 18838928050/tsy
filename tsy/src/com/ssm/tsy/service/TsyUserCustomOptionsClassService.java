package com.ssm.tsy.service;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;


public interface TsyUserCustomOptionsClassService {
	
	public void addTsyUserCustomOptionsClass(InputObject inputObject,OutputObject outputObject) throws Exception;

	public void queryTsyUserCustomOptionsClass(InputObject inputObject, OutputObject outputObject) throws Exception;

}

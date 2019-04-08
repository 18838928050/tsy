package com.ssm.tsy.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;

public interface TsyOptionsService {
	
	public void insertOptions(InputObject inputObject,OutputObject outputObject,CommonsMultipartFile files) throws Exception;

	public void deleteOptions(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryOptionsList(InputObject inputObject, OutputObject outputObject) throws Exception;
	
}

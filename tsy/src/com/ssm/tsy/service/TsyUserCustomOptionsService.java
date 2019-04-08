package com.ssm.tsy.service;


import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;

public interface TsyUserCustomOptionsService {
	public void insertTsyUserCustomOptions(InputObject inputObject,OutputObject outputObject) throws Exception;

	public void queryVedioList(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void updateVedioState(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryVedioListById(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void updateVedioById(InputObject inputObject, OutputObject outputObject) throws Exception;
}

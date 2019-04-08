package com.ssm.tsy.service;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;

public interface TsyMhUserService {

	public void queryUserStatistics(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryLatelyMonthLog(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryLatelyEightDayLog(InputObject inputObject, OutputObject outputObject) throws Exception;

}

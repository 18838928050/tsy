package com.ssm.tsy.service;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;

public interface TsyOptionsIntegralGgService {
	
	/**
	 * 添加积分标准
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void insertTsyOptionsIntegralGg(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	/**
	 * 查看积分标准
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void queryTsyOptionsIntegralGg(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	/**
	 * 修改积分标准
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void updateTsyOptionsIntegralGg(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	/**
	 * 删除积分标准
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void daleteTsyOptionsIntegralGg(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	/**
	 * 根据id查看积分标准
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void queryTsyOptionsIntegralGgById(InputObject inputObject,OutputObject outputObject) throws Exception;

}

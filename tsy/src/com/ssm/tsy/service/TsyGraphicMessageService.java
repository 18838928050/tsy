package com.ssm.tsy.service;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;

public interface TsyGraphicMessageService {
    /**
     * 发送图文消息
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    public void insertMessage(InputObject inputObject,OutputObject outputObject) throws Exception;

    /**
     * 查询所有的图文消息
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    public void queryGraphicMessage(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 插入图文消息
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
	public void insertGraphicMessage(InputObject inputObject, OutputObject outputObject) throws Exception;

	 /**
     * 修改图文消息
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
	public void updateGraphicMessage(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 根据ID查询图文消息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void queryGraphicMessageById(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 删除图文消息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void deleteGraphicMessageById(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 修改页面中查询图文消息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void updateGraphicMessageSelect(InputObject inputObject, OutputObject outputObject) throws Exception;

}

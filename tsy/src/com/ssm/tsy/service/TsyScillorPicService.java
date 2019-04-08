package com.ssm.tsy.service;

import java.util.List;

import com.ssm.tsy.bean.TsyScillorPic;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;

public interface TsyScillorPicService {
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<TsyScillorPic> SelectAll();
	
	/**
	 * 添加
	 * @param bean
	 * @return
	 */
	public int add(TsyScillorPic bean);
	
	/**
	 * 修改
	 * @param bean
	 * @return
	 */
	public int update(TsyScillorPic bean);
	
	/**
	 * 删除
	 * @param bean
	 * @return
	 */
	public int delete(int id);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public TsyScillorPic selectByPrimaryKey(int id);
	
	/**
	 * 门户---滚动图片
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void queryTsyScillorlist(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	/**
	 * 表格根据类型查找
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void queryTsyScillorTablelist(InputObject inputObject,OutputObject outputObject) throws Exception;

	/**
	 * 发布
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void updateTsyScillorPicFb(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 门户---通知列表详情查看---从当前id查询十条已发布的数据展示出来
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void queryTsyScillorItemsByIdToTen(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 门户---通知列表详情查看---根据id查询详情
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void queryTsyScillorItemsContentById(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 门户---通知列表详情查看---查询所有
	 * @param inputObject
	 * @param outputObject
	 */
	public void queryTsyScillorItemsAll(InputObject inputObject, OutputObject outputObject) throws Exception;
}

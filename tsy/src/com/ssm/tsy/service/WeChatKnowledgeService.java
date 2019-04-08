package com.ssm.tsy.service;

import java.util.List;

import com.ssm.tsy.bean.WeChatKnowledge;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;

public interface WeChatKnowledgeService {

	/**
	 * 根据fatherid查询
	 * 
	 * @param fatherid
	 * @return
	 */
	public List<WeChatKnowledge> SelectByFatherid(int fatherid);

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	public List<WeChatKnowledge> SelectById(int id);

	/**
	 * 查询所有的一级知识列表
	 * 
	 * @return
	 */
	public List<WeChatKnowledge> SelectAllIsOne();
	
	/**
	 * 查询当前上线的栏目个数
	 * @return
	 */
	public int SelectUpSize();

	/**
	 * 查询所有的二级知识列表
	 * 
	 * @return
	 */
	public List<WeChatKnowledge> SelectAllIsTwo();

	/**
	 * 根据名称和所属id查询
	 * 
	 * @param name
	 * @param fatherid
	 * @return
	 */
	public List<WeChatKnowledge> SelectAllByNameAndFatherid(String name,
			int fatherid);

	/**
	 * 添加
	 * 
	 * @param bean
	 * @return
	 */
	public int add(WeChatKnowledge bean);

	/**
	 * 修改
	 * 
	 * @param bean
	 * @return
	 */
	public int update(WeChatKnowledge bean);

	/**
	 * 删除
	 * 
	 * @param bean
	 * @return
	 */
	public int delete(int id);

	public void queryKnowledge(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryKnowledgeById(InputObject inputObject, OutputObject outputObject) throws Exception;
}

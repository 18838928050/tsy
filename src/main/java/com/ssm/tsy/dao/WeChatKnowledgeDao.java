package com.ssm.tsy.dao;

import java.util.List;
import java.util.Map;

import com.ssm.tsy.bean.WeChatKnowledge;

public interface WeChatKnowledgeDao {

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
	 * 查询当前上线的栏目个数
	 * @return
	 */
	public String SelectUpSize();

	/**
	 * 查询所有的一级知识列表
	 * 
	 * @return
	 */
	public List<WeChatKnowledge> SelectAllIsOne();

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
	/**
	 * 门户---查询内容
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryKnowledge(Map<String,Object> map) throws Exception;
	/**
	 * 门户---根据id查看单个知识内容
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public Map<String,Object> queryKnowledgeById(Map<String,Object> map) throws Exception;
}

package com.ssm.tsy.service;

import java.util.List;

import com.ssm.tsy.bean.Liebiao;

public interface LiebiaoService {
	/**
	 * 查询所有列表菜单
	 * 
	 * @return
	 */
	public List<Liebiao> SelectAll();

	/**
	 * 添加新的列表
	 * 
	 * @param bean
	 * @return
	 */
	public int add(Liebiao bean, String belongtoid);

	/**
	 * 修改列表信息
	 * 
	 * @param bean
	 * @return
	 */
	public int update(Liebiao bean);

	/**
	 * 删除列表信息
	 * 
	 * @param id
	 * @return
	 */
	public int delete(int id);

	/**
	 * 根据列表id查询列表对象
	 * 
	 * @param id
	 * @return
	 */
	public Liebiao SelectLiebiaoById(int id);

	/**
	 * 根据列表级别查询菜单
	 * 
	 * @param jibie
	 * @return
	 */
	public List<Liebiao> SelectAllByjibie(int jibie);

	/**
	 * 根据列表名称查询菜单
	 * 
	 * @param name
	 * @return
	 */
	public List<Liebiao> SelectAllByName(String name);

	/**
	 * 根据列表父菜单id查询子菜单
	 * 
	 * @param bolongto
	 * @return
	 */
	public List<Liebiao> SelectAllBybolongto(int bolongto);

	/**
	 * 根据条件查询列表菜单
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	public List<Liebiao> SelectAllByTiaojian(Liebiao bean);
}

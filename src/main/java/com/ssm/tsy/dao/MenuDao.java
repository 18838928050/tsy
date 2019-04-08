package com.ssm.tsy.dao;

import java.util.List;
import java.util.Map;

import com.ssm.tsy.bean.MenuBean;

public interface MenuDao {

	/**
	 * 查询所有自定义菜单
	 * 
	 * @return
	 */
	public List<MenuBean> SelectAll();

	/**
	 * 查询单项自定义菜单
	 * 
	 * @param id
	 * @return
	 */
	public MenuBean SelectItemById(int id);

	/**
	 * 根据一级菜单查询子菜单
	 * 
	 * @return
	 */
	public List<MenuBean> SelectAllByBolong(int bolong);
	
	/**
	 * 查询所有的一级菜单或者二级菜单
	 * 
	 * @return
	 */
	List<MenuBean> SelectAllByLevel(int level);

	/**
	 * 添加自定义菜单
	 * 
	 * @param bean
	 * @return
	 */
	public int AddMenuBean(MenuBean bean);

	/**
	 * 修改自定义菜单
	 * 
	 * @param bean
	 * @return
	 */
	public int UpdateMenuBean(MenuBean bean);

	/**
	 * 删除自定义菜单
	 * 
	 * @param bean
	 * @return
	 */
	public int DeleteMenuBean(int id);
	
	/**
	 * 根据菜单名称查询菜单
	 * 
	 * @return
	 */
	List<MenuBean> SelectAllByName(String menuname);

	public Map<String, Object> SelectItemByMenuKey(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> queryGraphicMessageByFirst(Map<String, Object> map) throws Exception;

}

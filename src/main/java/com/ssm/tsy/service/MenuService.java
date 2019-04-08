package com.ssm.tsy.service;

import java.util.List;

import com.ssm.tsy.bean.MenuBean;
import com.ssm.tsy.bean.wechat.Menu;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;

public interface MenuService {
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
	 * 查询所有的一级菜单或者二级菜单
	 * 
	 * @return
	 */
	public List<MenuBean> SelectAllByLevel(int level);

	/**
	 * 根据菜单名称查询菜单
	 * 
	 * @return
	 */
	public List<MenuBean> SelectAllByName(String menuname);

	/**
	 * 发布菜单
	 * 
	 * @return
	 */
	public Menu getMenu();

	public void queryGraphicMessageByFirst(InputObject inputObject, OutputObject outputObject) throws Exception;

}

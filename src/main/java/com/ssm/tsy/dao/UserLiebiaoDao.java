package com.ssm.tsy.dao;

import java.util.List;

import com.ssm.tsy.bean.User_Liebiao;

public interface UserLiebiaoDao {

	/**
	 * 根据用户id查询该用户拥有的列表id
	 */
	public List<User_Liebiao> SelectAll(int userid);

	/**
	 * 查询该用户是否已经拥有此权限
	 * 
	 * @param bean
	 * @return
	 */
	public int SelectItem(User_Liebiao bean);

	/**
	 * 添加新权限
	 * 
	 * @param bean
	 * @return
	 */
	public int add(User_Liebiao bean);

	/**
	 * 移除权限
	 * 
	 * @param id
	 * @return
	 */
	public int delete(int id);

	/**
	 * 根据一级菜单查询所有的二级菜单id
	 * 
	 * @param bean
	 * @return
	 */
	public List<User_Liebiao> SelectItemForBean(User_Liebiao bean);
	
	/**
	 * 查询该菜单是否有用户使用
	 * @param id
	 * @return
	 */
	public int SelectItembyliebiaoid(int id);

}

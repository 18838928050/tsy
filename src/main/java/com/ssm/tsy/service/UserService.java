package com.ssm.tsy.service;

import java.util.List;

import com.ssm.tsy.bean.UserBean;

public interface UserService {

	/**
	 * 根据工号进行查询
	 * 
	 * @param no
	 * @return
	 */
	public UserBean getUserByNo(String no);

	/**
	 * 添加新用户
	 */
	public int setUserBean(UserBean bean);

	/**
	 * 查询所有用户
	 * 
	 * @return
	 */
	public List<UserBean> SelectAll();

	/**
	 * 根据身份和名字查询所有用户
	 * 
	 * @return
	 */
	public List<UserBean> SelectAllByNameAndQuanxian(UserBean bean);

	/**
	 * 修改用户信息 ->修改密码
	 * 
	 * @param bean
	 * @return
	 */
	public int Update(UserBean bean);
	
	/**
	 * 复制表
	 * @return
	 */
	public void CopyTable();
}

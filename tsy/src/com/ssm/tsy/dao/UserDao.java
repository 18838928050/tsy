package com.ssm.tsy.dao;

import java.util.List;
import java.util.Map;

import com.ssm.tsy.bean.UserBean;

public interface UserDao {

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
	public int CopyAopTable(String newtable);
	
	/**
	 * 复制表
	 * @return
	 */
	public int CopyLoginTable(String newtable);
	
	
    /**
     * 查询登陆人信息
     * */
	public Map<String, Object> selectLoginMessage(Map<String, Object> map) throws Exception;
	/**
     * 修改登陆人信息
     * */
	public int updateLoginMessage(Map<String, Object> map) throws Exception;
}

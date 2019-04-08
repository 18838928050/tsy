package com.ssm.tsy.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.tsy.bean.UserBean;
import com.ssm.tsy.dao.UserDao;
import com.ssm.tsy.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	UserDao userDao;

	/**
	 * 根据工号进行查询
	 */
	@Override
	public UserBean getUserByNo(String no) {
		return userDao.getUserByNo(no);
	}

	/**
	 * 添加新用户
	 * 
	 * -》如果注册的用户已存在，则不添加
	 */
	@Override
	public int setUserBean(UserBean bean) {
		if (userDao.getUserByNo(bean.getNo()) == null) {
			return userDao.setUserBean(bean);
		} else {
			return 0;
		}
	}

	/**
	 * 查询所有用户
	 */
	@Override
	public List<UserBean> SelectAll() {
		return userDao.SelectAll();
	}

	/**
	 * 修改用户信息 ->修改密码
	 */
	@Override
	public int Update(UserBean bean) {
		return userDao.Update(bean);
	}

	/**
	 * 根据身份和名字查询所有用户
	 * 
	 * @return
	 */
	@Override
	public List<UserBean> SelectAllByNameAndQuanxian(UserBean bean) {
		return userDao.SelectAllByNameAndQuanxian(bean);
	}

	/**
	 * 复制表
	 * 
	 * @return
	 */
	@Override
	public void CopyTable() {
		String aop_table = "tsy_aop_log";
		String login_table = "tsy_user_login_log";
		String[] Now_time = new SimpleDateFormat("yyyy-MM-dd").format(
				new Date()).split("-");
		int Year = Integer.parseInt(Now_time[0]);
		int Month = Integer.parseInt(Now_time[1]);
		String new_aop_table;
		String new_login_table;
		if (Month == 12) {
			// 拼接请求日志表
			new_aop_table = aop_table + String.format("%04d", (Year + 1))
					+ String.format("%02d", 1);
			// 拼接登陆日志表
			new_login_table = login_table + String.format("%04d", (Year + 1))
					+ String.format("%02d", 1);
		} else {
			// 拼接请求日志表
			new_aop_table = aop_table + String.format("%04d", Year)
					+ String.format("%02d", Month + 1);
			// 拼接登陆日志表
			new_login_table = login_table + String.format("%04d", Year)
					+ String.format("%02d", Month + 1);
		}
		userDao.CopyAopTable(new_aop_table);
		userDao.CopyLoginTable(new_login_table);
	}
}

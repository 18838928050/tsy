package com.ssm.tsy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.tsy.bean.User_Liebiao;
import com.ssm.tsy.dao.UserLiebiaoDao;
import com.ssm.tsy.service.User_LiebiaoService;
@Service
public class User_LiebiaoServiceImpl implements User_LiebiaoService{
	
	@Resource
	UserLiebiaoDao user_LiebiaoDao;
	
	/**
	 * 根据用户id查询该用户拥有的列表id
	 */
	@Override
	public List<User_Liebiao> SelectAll(int userid) {
		return user_LiebiaoDao.SelectAll(userid);
	}

	@Override
	public boolean SelectItem(User_Liebiao bean) {
		if(user_LiebiaoDao.SelectItem(bean)>0){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public int delete(int id) {
		return user_LiebiaoDao.delete(id);
	}

	@Override
	public int add(User_Liebiao bean) {
		return user_LiebiaoDao.add(bean);
	}

	@Override
	public List<User_Liebiao> SelectItemForBean(User_Liebiao bean) {
		return user_LiebiaoDao.SelectItemForBean(bean);
	}

	@Override
	public int SelectItembyliebiaoid(int id) {
		return user_LiebiaoDao.SelectItembyliebiaoid(id);
	}

}

package com.ssm.tsy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.tsy.bean.Liebiao;
import com.ssm.tsy.dao.LiebiaoDao;
import com.ssm.tsy.service.LiebiaoService;

@Service
public class LiebiaoServiceImpl implements LiebiaoService {

	@Resource
	LiebiaoDao liebiaoDao;

	@Override
	public List<Liebiao> SelectAll() {
		return liebiaoDao.SelectAll();
	}

	@Override
	public int add(Liebiao bean, String belongtoid) {

		// 如果是一级菜单，则不需要链接
		if (bean.getJibie() == 1) {
			bean.setBelongto(0);
			bean.setNameurl(null);
		} else if (bean.getJibie() == 2) {
			// 如果是二级菜单，则需要链接
			bean.setBelongto(Integer.parseInt(belongtoid));
		}
		return liebiaoDao.add(bean);
	}

	@Override
	public int update(Liebiao bean) {
		return liebiaoDao.update(bean);
	}

	@Override
	public int delete(int id) {
		Liebiao bean = liebiaoDao.SelectLiebiaoById(id);

		// 如果是一级菜单
		if (bean.getJibie() == 1) {

			List<Liebiao> beans = liebiaoDao.SelectAllBybolongto(bean.getId());
			// 如果该一级菜单下面有二级菜单，则不能删除
			if (beans == null || beans.size() == 0) {

				return liebiaoDao.delete(bean.getId());

			} else {

				return 0;

			}

		} else if (bean.getJibie() == 2) {

			// 如果是二级菜单，则直接删除
			return liebiaoDao.delete(id);

		}
		return 0;
	}

	@Override
	public Liebiao SelectLiebiaoById(int id) {
		return liebiaoDao.SelectLiebiaoById(id);
	}

	@Override
	public List<Liebiao> SelectAllByjibie(int jibie) {
		return liebiaoDao.SelectAllByjibie(jibie);
	}

	@Override
	public List<Liebiao> SelectAllByName(String name) {
		return liebiaoDao.SelectAllByName(name);
	}

	@Override
	public List<Liebiao> SelectAllBybolongto(int bolongto) {
		return liebiaoDao.SelectAllBybolongto(bolongto);
	}

	@Override
	public List<Liebiao> SelectAllByTiaojian(Liebiao bean) {
		return liebiaoDao.SelectAllByTiaojian(bean);
	}

}

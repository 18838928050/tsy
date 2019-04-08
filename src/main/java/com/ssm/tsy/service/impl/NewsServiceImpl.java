package com.ssm.tsy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.tsy.bean.NewsBean;
import com.ssm.tsy.dao.NewsDao;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService {

	@Resource
	private NewsDao newsDao;

	/**
	 * 查询数据库中的新闻
	 * 
	 * @return
	 */
	@Override
	public List<NewsBean> SelectAll() throws Exception{
		return newsDao.SelectAll();
	}

	/**
	 * 添加新的新闻
	 * 
	 * @param bean
	 * @return
	 */
	@Override
	public int Add(NewsBean bean) throws Exception{
		return newsDao.Add(bean);
	}
	
	
	@Override
	public void queryMyList(InputObject inputObject,OutputObject outputObject) throws Exception{
		Map<String,Object> map = inputObject.getParams();
		List<Map<String,Object>> beans = newsDao.queryMyList(map);
		outputObject.setBeans(beans);
		outputObject.settotal(beans.size());
	}

	@Override
	public void queryRealType(InputObject inputObject, OutputObject outputObject) throws Exception {
		List<Map<String,Object>> beans = newsDao.queryRealType();
		outputObject.setBeans(beans);
	}

}

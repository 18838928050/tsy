package com.ssm.tsy.dao;

import java.util.List;
import java.util.Map;

import com.ssm.tsy.bean.NewsBean;

public interface NewsDao {
	
	public List<NewsBean> SelectAll();
	
	public int Add(NewsBean bean);
	
	public List<Map<String,Object>> queryMyList(Map<String,Object> map);

	public List<Map<String, Object>> queryRealType();
}

package com.ssm.tsy.service;

import java.util.List;

import com.ssm.tsy.bean.NewsBean;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;

public interface NewsService {
	
	public List<NewsBean> SelectAll() throws Exception;
	
	public int Add(NewsBean bean) throws Exception;

	public void queryMyList(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryRealType(InputObject inputObject, OutputObject outputObject) throws Exception;

}

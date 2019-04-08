package com.ssm.tsy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.tsy.bean.WeChatKnowledge;
import com.ssm.tsy.dao.WeChatKnowledgeDao;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.WeChatKnowledgeService;
import com.ssm.tsy.util.Constants;

@Service
public class WeChatKnowledgeServiceImpl implements WeChatKnowledgeService {

	@Resource
	WeChatKnowledgeDao weChatKnowledgeDao;

	@Override
	public List<WeChatKnowledge> SelectAllIsOne() {
		return weChatKnowledgeDao.SelectAllIsOne();
	}

	@Override
	public List<WeChatKnowledge> SelectAllIsTwo() {
		return weChatKnowledgeDao.SelectAllIsTwo();
	}

	@Override
	public List<WeChatKnowledge> SelectAllByNameAndFatherid(String name,
			int fatherid) {
		return weChatKnowledgeDao.SelectAllByNameAndFatherid(name, fatherid);
	}

	@Override
	public int add(WeChatKnowledge bean) {
		return weChatKnowledgeDao.add(bean);
	}

	@Override
	public int update(WeChatKnowledge bean) {
		return weChatKnowledgeDao.update(bean);
	}

	@Override
	public int delete(int id) {
		return weChatKnowledgeDao.delete(id);
	}

	@Override
	public List<WeChatKnowledge> SelectByFatherid(int fatherid) {
		return weChatKnowledgeDao.SelectByFatherid(fatherid);
	}

	@Override
	public List<WeChatKnowledge> SelectById(int id) {
		return weChatKnowledgeDao.SelectById(id);
	}

	@Override
	public int SelectUpSize() {
		String up_size = weChatKnowledgeDao.SelectUpSize();
		if(up_size==null){
			return 0;
		}else{
			return Integer.parseInt(up_size);
		}
	}
	/**
	 * 门户---查询内容
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@Override
	public void queryKnowledge(InputObject inputObject, OutputObject outputObject) throws Exception{
		Map<String,Object> map = inputObject.getParams();
		map.put("upordown", Constants.WECHAT_KNOWLEDGE_STATE_SX);//上线
		List<Map<String,Object>> beans = weChatKnowledgeDao.queryKnowledge(map);
		outputObject.setBeans(beans);
		outputObject.settotal(beans.size());
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
	
	/**
	 * 门户---根据id查看单个知识内容
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void queryKnowledgeById(InputObject inputObject, OutputObject outputObject) throws Exception{
		Map<String,Object> map = inputObject.getParams();
		Map<String,Object> bean = weChatKnowledgeDao.queryKnowledgeById(map);
		outputObject.setBean(bean);
		outputObject.settotal(1);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
}

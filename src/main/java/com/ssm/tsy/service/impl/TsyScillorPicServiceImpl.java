package com.ssm.tsy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.tsy.bean.TsyScillorPic;
import com.ssm.tsy.dao.TsyScillorPicDao;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyScillorPicService;
import com.ssm.tsy.util.Constants;
@Service
public class TsyScillorPicServiceImpl implements TsyScillorPicService{

	@Resource
	private TsyScillorPicDao scillorPicDao;
	
	@Override
	public TsyScillorPic selectByPrimaryKey(int id) {
		return scillorPicDao.selectByPrimaryKey(id);
	}
	
	@Override
	public List<TsyScillorPic> SelectAll() {
		return scillorPicDao.SelectAll();
	}

	@Override
	public int add(TsyScillorPic bean) {
		return scillorPicDao.insertSelective(bean);
	}

	@Override
	public int update(TsyScillorPic bean) {
		return scillorPicDao.updateByPrimaryKeySelective(bean);
	}

	@Override
	public int delete(int id) {
		return scillorPicDao.deleteByPrimaryKey(id);
	}

	/**
	 * 门户---滚动图片
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void queryTsyScillorlist(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		map.put("scollorPicDisplay", Constants.TSY_SCOLLOR_PIC_STATE_SX);//查询上线内容
		map.put("scollorPicFb", Constants.TSY_SCOLLOR_PIC_FB_SX);//查询已发布内容
		List<Map<String,Object>> beans = scillorPicDao.queryTsyScillorlist(map);
		outputObject.setBeans(beans);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
	
	/**
	 * 门户---通知列表详情查看---从当前id查询十条已发布的数据展示出来
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void queryTsyScillorItemsByIdToTen(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		map.put("top", (Integer.parseInt(map.get("id").toString())-1));//从当前id开始查询
		map.put("limit", 10);//查询制定条数
		map.put("scollorPicFb", Constants.TSY_SCOLLOR_PIC_FB_SX);//已发布
		List<Map<String,Object>> beans = scillorPicDao.queryTsyScillorItemsByIdToTen(map);
		outputObject.setBeans(beans);
		outputObject.settotal(beans.size());
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
	
	/**
	 * 门户---通知列表详情查看---根据id查询详情
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void queryTsyScillorItemsContentById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		map.put("scollorPicFb", Constants.TSY_SCOLLOR_PIC_FB_SX);//已发布
		//查询通知信息
		Map<String,Object> bean = scillorPicDao.queryTsyScillorItemsContentById(map);
		//修改阅读量
		Map<String,Object> updateBean = new HashMap<String, Object>();
		updateBean.put("id", map.get("id"));
		updateBean.put("ydl", (Integer.parseInt(bean.get("ydl").toString())+1));
		scillorPicDao.updateTsyScillorYdl(updateBean);
		
		bean.put("ydl", (Integer.parseInt(bean.get("ydl").toString())+1));
		outputObject.setBean(bean);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
	
	/**
	 * 表格根据类型查找
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void queryTsyScillorTablelist(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		List<Map<String,Object>> beans = scillorPicDao.queryTsyScillorTablelist(map);
		outputObject.setBeans(beans);
		outputObject.settotal(beans.size());
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
	
	/**
	 * 发布
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateTsyScillorPicFb(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		map.put("scollorPicFb", Constants.TSY_SCOLLOR_PIC_FB_SX);//修改状态为已发布
		scillorPicDao.updateTsyScillorPicFb(map);
		outputObject.setreturnCode(Constants.UPDATE_SUCCESS);
		outputObject.setreturnMessage("修改成功");
	}

	/**
	 * 门户---通知列表详情查看---查询所有
	 * @param inputObject
	 * @param outputObject
	 */
	@Override
	public void queryTsyScillorItemsAll(InputObject inputObject, OutputObject outputObject) throws Exception{
		Map<String,Object> map = inputObject.getParams();
		map.put("scollorPicFb", Constants.TSY_SCOLLOR_PIC_FB_SX);//查询状态为已发布
		List<Map<String,Object>> beans = scillorPicDao.queryTsyScillorItemsAll(map);
		outputObject.setBeans(beans);
		outputObject.settotal(beans.size());
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	
}

package com.ssm.tsy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.ssm.tsy.bean.UserBean;
import com.ssm.tsy.bean.wechat.ResponseArticle;
import com.ssm.tsy.dao.TsyGraphicMessageDao;
import com.ssm.tsy.dao.WeChatDao;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyGraphicMessageService;
import com.ssm.tsy.util.Constants;
import com.ssm.tsy.util.DateUtil;
import com.ssm.tsy.util.TransUtil;
import com.wechat.service.SendMessageService;
@Service
public class TsyGraphicMessageServiceImpl implements TsyGraphicMessageService{

	@Resource
	private TsyGraphicMessageDao tsyGraphicMessageDao;
	
	@Resource
	private WeChatDao weChatDao;

	/**
     * 发送图文消息
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
	@SuppressWarnings("static-access")
	@Override
	public void insertMessage(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		int sendNum = 0;
		//查询所有关注用户的openId；
		List<Map<String,Object>> beans = weChatDao.SelectAllToMap(map);
		List<Map<String,Object>> graphicMessageBean = tsyGraphicMessageDao.insertMessageAndSelectById(map);
		List<ResponseArticle> resMessage = TransUtil.mapToResponseArticleList(graphicMessageBean);
		for(Map<String,Object> bean : beans){
			int sendWether = SendMessageService.sendNewsToUser(bean.get("openid").toString(),resMessage);
			if(sendWether==1){
				sendNum++;
			}
		}
		UserBean user = (UserBean) inputObject.getRequest().getSession().getAttribute("user");
		map.put("userId", user.getId());
		map.put("graphicMessageSendDate", DateUtil.getTimeAndToString());
		tsyGraphicMessageDao.updateSendStateByRowId(map);
		Map<String,Object> bean = new HashMap<>();
		bean.put("sendNum", sendNum);
		bean.put("allUser", beans.size());
		outputObject.setBean(bean);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
	
	/**
     * 查询所有的图文消息
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
	@Override
	public void queryGraphicMessage(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		int page = Integer.parseInt(map.get("page").toString());
		List<Map<String,Object>> beans = tsyGraphicMessageDao.queryGraphicMessage(map,new PageBounds(page, 5));
		for(int i = 0;i < beans.size();i++){
			Map<String,Object> item = new HashMap<>();
			item.put("GraphicMessageFatherid", beans.get(i).get("id"));
			item.put("graphicMessageJudgeSend", map.get("graphicMessageJudgeSend"));
			List<Map<String,Object>> items = tsyGraphicMessageDao.queryGraphicMessageByGraphicMessageFatherid(item);
			beans.get(i).put("childSize", items.size());
			beans.get(i).put("child", items);
		}
		outputObject.setBeans(beans);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 插入图文消息
	 */
	@Override
	public void insertGraphicMessage(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		map.put("graphicMessageJudgeSend", Constants.WECHAT_MESSAGE_GRAPHIC_MESSAGE_JUDGE_SEND_N);
		tsyGraphicMessageDao.insertGraphicMessage(map);
		outputObject.setBean(map);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 修改图文消息
	 */
	@Override
	public void updateGraphicMessage(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		tsyGraphicMessageDao.updateGraphicMessage(map);
		map = tsyGraphicMessageDao.selectByPrimaryKey(map);
		outputObject.setBean(map);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 根据ID查询图文消息
	 */
	@Override
	public void queryGraphicMessageById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		map = tsyGraphicMessageDao.selectByPrimaryKey(map);
		outputObject.setBean(map);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 删除图文消息
	 */
	@Override
	public void deleteGraphicMessageById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		tsyGraphicMessageDao.deleteByMessageId(map);
		tsyGraphicMessageDao.deleteByMessageFatherId(map);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 修改页面中查询图文消息
	 */
	@Override
	public void updateGraphicMessageSelect(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		Map<String,Object> bean = tsyGraphicMessageDao.selectByPrimaryKey(map);
		if(bean.get("graphic_message_fatherid").toString().equals("0")){//如果点击修改的正好是图文消息组的第一个
			map.put("GraphicMessageFatherid", map.get("id"));
			List<Map<String,Object>> beans = tsyGraphicMessageDao.queryGraphicMessageByGraphicMessageFatherid(map);
			outputObject.setBean(bean);
			outputObject.setBeans(beans);
			outputObject.settotal(beans.size());
		}else{
			map.put("id", bean.get("graphic_message_fatherid"));
			map.put("GraphicMessageFatherid", bean.get("graphic_message_fatherid"));
			Map<String,Object> item = tsyGraphicMessageDao.selectByPrimaryKey(map);
			List<Map<String,Object>> items = tsyGraphicMessageDao.queryGraphicMessageByGraphicMessageFatherid(map);
			outputObject.setBean(item);
			outputObject.setBeans(items);
			outputObject.settotal(items.size());
		}
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
		
	}

}

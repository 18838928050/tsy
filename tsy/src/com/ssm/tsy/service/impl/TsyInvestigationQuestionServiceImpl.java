package com.ssm.tsy.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.tsy.bean.UserBean;
import com.ssm.tsy.dao.TsyInvestigationQuestionDao;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyInvestigationQuestionService;
import com.ssm.tsy.util.Constants;
import com.ssm.tsy.util.DateUtil;
import com.ssm.tsy.util.JudgeUtil;


@Service
public class TsyInvestigationQuestionServiceImpl implements TsyInvestigationQuestionService{

	@Resource
	private TsyInvestigationQuestionDao tsyInvestigationQuestionDao;

	/**
	 * 查询签到信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void queryinvestigationQuestionList(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!JudgeUtil.isNull(map.get("startTime").toString())){
			map.put("startTime", map.get("startTime").toString()+" 00:00:00");
		}
		if(!JudgeUtil.isNull(map.get("endTime").toString())){
			map.put("endTime", map.get("endTime").toString()+" 23:59:59");
		}
		int page = Integer.parseInt(map.get("offset").toString())/Integer.parseInt(map.get("limit").toString());
		page++;
		int limit = Integer.parseInt(map.get("limit").toString());
		List<Map<String,Object>> beans = tsyInvestigationQuestionDao.queryinvestigationQuestionList(map,new PageBounds(page,limit,true));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>)beans;
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(beans);
		outputObject.settotal(total);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
	
	/**
	 * 投票开始操作和恢复操作
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateinvestigationQuestionState(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		tsyInvestigationQuestionDao.updateByPrimaryKeySelective(map);
		Map<String,Object> voteMation = tsyInvestigationQuestionDao.queryVoteMationById(map);
		outputObject.setreturnCode(Constants.WRONG);
		if(JudgeUtil.isNull(voteMation.get("tsyVoteTitle").toString())){
			outputObject.setreturnMessage("投票标题不能为空,请确认...");
		}else if(!voteMation.containsKey("tsyVoteStartTime")){
			outputObject.setreturnMessage("投票开始时间为空,请确认...");
		}else if(!voteMation.containsKey("tsyVoteEndTime")){
			outputObject.setreturnMessage("投票结束时间为空,请确认...");
		}else if(voteMation.get("tsyVoteWhetherSave").toString().equals(Constants.WECHAT_TSY_VOTE_NO_SAVE)){
			outputObject.setreturnMessage("此次投票还未保存,请确认...");
		}else if(voteMation.get("tsyVoteState").toString().equals(Constants.WECHAT_TSY_VOTE_STATE_RUNING)){
			outputObject.setreturnMessage("此次投票正在执行中,无需再次操作...");
		}else if(voteMation.get("tsyVoteState").toString().equals(Constants.WECHAT_TSY_VOTE_STATE_RUNOVER)){
			outputObject.setreturnMessage("此次投票已经结束,请创建新的投票内容...");
		}else{
			if(!voteMation.containsKey("tsyVoteTable")){//此次开始是第一次开始投票，要创建表并修改内容
				Map<String,Object> tableMation = new HashMap<String, Object>();
				tableMation.put("answer", Constants.VOTE_TABLE_BEAN + voteMation.get("id").toString());
				tsyInvestigationQuestionDao.CopyAnswerTable(tableMation);
				tableMation.put("tsyVoteTable", Constants.VOTE_TABLE_BEAN + voteMation.get("id").toString());
				tableMation.put("tsyVoteState", Constants.WECHAT_TSY_VOTE_STATE_RUNING);
				tableMation.put("id", voteMation.get("id").toString());
				tsyInvestigationQuestionDao.updateByPrimaryKeySelective(tableMation);
				outputObject.setreturnCode(Constants.ZERO);
				outputObject.setreturnMessage("投票已开始");
			}else{
				outputObject.setreturnMessage("数据操作失败...");
			}
		}
	}

	/**
	 * 根据用户权限查询投票信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@Override
	public void queryVoteList(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		UserBean user = (UserBean) inputObject.getRequest().getSession().getAttribute("user");
		map.put("userId", user.getId());
		if(String.valueOf(user.getQuanxian()).equals(Constants.USER_JURISDICTION_SUPER_ADMIN)){//超级管理员
			map.put("SelectAll", "1");
		}else{
			map.put("SelectAll", "");
		}
		int page = Integer.parseInt(map.get("offset").toString())/Integer.parseInt(map.get("limit").toString());
		page++;
		int limit = Integer.parseInt(map.get("limit").toString());
		map.put("noStateDelete", Constants.WECHAT_TSY_VOTE_STATE_DELETE);
		List<Map<String,Object>> beans = tsyInvestigationQuestionDao.queryVoteList(map,new PageBounds(page, limit));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>)beans;
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(beans);
		outputObject.settotal(total);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 修改投票信息，或者修改未保存投票信息的调用
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void queryVoteListByGeneralId(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		Map<String,Object> bean = tsyInvestigationQuestionDao.queryVoteByGeneralId(map);
		List<Map<String,Object>> beans = tsyInvestigationQuestionDao.queryVoteQuestionByGeneralId(map);
		for(Map<String,Object> item : beans){
			Map<String,Object> questionId = new HashMap<String, Object>();
			questionId.put("questionId", item.get("id"));
			List<Map<String,Object>> items = tsyInvestigationQuestionDao.queryVoteOptionByQuestionlId(questionId);
			for(Map<String,Object> be : items){
				be.put("optionsSize", items.size());
				be.put("questionId", item.get("id"));
			}
			item.put("options", items);
		}
		outputObject.setBean(bean);
		outputObject.setBeans(beans);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 查询未保存的投票信息的id
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@Override
	public void queryVoteListByNoSave(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		UserBean user = (UserBean) inputObject.getRequest().getSession().getAttribute("user");
		outputObject.setreturnCode(Constants.WRONG);
		if(JudgeUtil.isNull(String.valueOf(user.getAppid()))){//如果用户关联的微信公众号APPID为空
			outputObject.setreturnMessage("该用户暂时没有权限添加投票,请联系管理员分配权限...");
		}else{
			map.put("appId", user.getAppid());
			map.put("userId", user.getId());
			map.put("noStateDelete", Constants.WECHAT_TSY_VOTE_STATE_DELETE);
			Map<String,Object> bean = tsyInvestigationQuestionDao.queryVoteListByNoSave(map);
			if(bean==null||bean.size()==0){
				outputObject.setreturnCode(Constants.SAVE_NO);//1200
				outputObject.setreturnMessage("成功");
			}else{
				outputObject.setBean(bean);
				outputObject.setreturnCode(Constants.SAVE_YES);//1201
				outputObject.setreturnMessage("成功");
			}
		}
	}

	/**
	 * 删除未保存的投票信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void deleteVoteListByNoSave(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		tsyInvestigationQuestionDao.deleteVoteListByNoSaveOption(map);//删除选项
		tsyInvestigationQuestionDao.deleteVoteListByNoSaveQuestion(map);//删除问题
		tsyInvestigationQuestionDao.deleteVoteListByNoSaveGeneral(map);//删除标题
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 添加问题信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void insertQuestionMation(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		map.put("questionAddTime", DateUtil.getTimeAndToString());
		tsyInvestigationQuestionDao.insertQuestionMation(map);
		outputObject.setBean(map);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 添加标题信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@Override
	public void insertGeneralMation(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		UserBean user = (UserBean) inputObject.getRequest().getSession().getAttribute("user");
		if(JudgeUtil.isNull(String.valueOf(user.getAppid()))){
			outputObject.setreturnCode(Constants.WRONG);
			outputObject.setreturnMessage("成功");
		}else{
			map.put("generalAddTime", DateUtil.getTimeAndToString());
			map.put("generalAddUserId", user.getId());
			map.put("generalAddAppId", user.getAppid());
			map.put("generalWhetherSave", Constants.WECHAT_TSY_VOTE_NO_SAVE);
			map.put("generalState", Constants.WECHAT_TSY_VOTE_STATE_NO_RUN);
			tsyInvestigationQuestionDao.insertGeneralMation(map);
			outputObject.setBean(map);
			outputObject.setreturnCode(Constants.ZERO);
			outputObject.setreturnMessage("成功");
		}
	}

	/**
	 * 添加问题选项信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void insertOptionsMation(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		map.put("optionAddTime", DateUtil.getTimeAndToString());
		map.put("optionType", Constants.WECHAT_TSY_OPTIONS_TYPE_WZ);
		tsyInvestigationQuestionDao.insertOptionsMation(map);
		outputObject.setBean(map);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");

	}

	/**
	 * 修改标题【标题头】信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateGeneralMation(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		map.put("generalUpdateTime", DateUtil.getTimeAndToString());
		tsyInvestigationQuestionDao.updateGeneralMation(map);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 修改问题信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateQuestionMation(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		tsyInvestigationQuestionDao.updateQuestionMation(map);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 修改问题选项信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateOptionsMation(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		tsyInvestigationQuestionDao.updateOptionsMation(map);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 删除问题选项信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void deleteOptionsMation(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		tsyInvestigationQuestionDao.deleteOptionsMation(map);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 删除问题信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void deleteQuestionMation(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		tsyInvestigationQuestionDao.deleteQuestionMation(map);
		tsyInvestigationQuestionDao.deleteOptionsMationByQuestionId(map);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 保存投票信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateVoteMessage(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		tsyInvestigationQuestionDao.updateGeneralMation(map);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 删除投票信息--采用逻辑删除
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void deleteVoteMessage(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		tsyInvestigationQuestionDao.updateGeneralMation(map);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 用户投票界面调取数据
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void admTsyQueryVoteList(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		Map<String,Object> bean = tsyInvestigationQuestionDao.queryVoteByGeneralId(map);
		List<Map<String,Object>> beans = tsyInvestigationQuestionDao.queryVoteQuestionByGeneralId(map);
		for(Map<String,Object> item : beans){
			Map<String,Object> questionId = new HashMap<String, Object>();
			questionId.put("questionId", item.get("id"));
			List<Map<String,Object>> items = tsyInvestigationQuestionDao.queryVoteOptionByQuestionlId(questionId);
			for(Map<String,Object> be : items){
				be.put("optionsSize", items.size());
			}
			item.put("options", items);
		}
		outputObject.setBean(bean);
		outputObject.setBeans(beans);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 判断用户是否参与过此次调查
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void queryVoteByIdAndOpenId(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		Map<String,Object> bean = tsyInvestigationQuestionDao.queryVoteByGeneralId(map);
		map.put("tsyVoteTable", bean.get("tsyVoteTable"));
		List<Map<String,Object>> beans = tsyInvestigationQuestionDao.queryVoteByIdAndOpenId(map);
		outputObject.setBeans(beans);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 添加用户的调查结果
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void insertVoteAnswer(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		Map<String,Object> bean = tsyInvestigationQuestionDao.queryVoteByGeneralId(map);
		map.put("tsyVoteTable", bean.get("tsyVoteTable"));
		List<Map<String,Object>> whetherSave = tsyInvestigationQuestionDao.queryVoteByIdAndOpenId(map);
		if(!whetherSave.isEmpty()){
			outputObject.setreturnCode(Constants.WRONG);
			outputObject.setreturnMessage("您已提交过，无需再次提交...");
		}else{
			List<Map<String,Object>> beans = new ArrayList<Map<String,Object>>();
			String str[] = map.get("str").toString().split(",");
			for(int i = 0;i<str.length;i++){
				if(!JudgeUtil.isNull(str[i])){
					Map<String,Object> item = new HashMap<String, Object>();
					item.put("questionId", str[i].split("@@")[0]);
					item.put("answerId", str[i].split("@@")[1]);
					item.put("openId", map.get("openId"));
					beans.add(item);
				}
			}
			map.put("list", beans);
			tsyInvestigationQuestionDao.tsyInvestigationQuestionDao(map);
			outputObject.setreturnCode(Constants.ZERO);
			outputObject.setreturnMessage("成功");
		}
	}

	/**
	 * 根据问题ID查询选项和评价比
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void queryOptionsByQuestionId(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		Map<String,Object> bean = tsyInvestigationQuestionDao.queryVoteByGeneralId(map);
		map.put("tsyVoteTable", bean.get("tsyVoteTable"));
		List<Map<String,Object>> beans = tsyInvestigationQuestionDao.queryOptionsByQuestionId(map);
		Map<String,Object> item = tsyInvestigationQuestionDao.queryZongrenshu(map);
		outputObject.setBean(item);
		outputObject.setBeans(beans);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * Task扫描，每天凌晨修改执行中的并且
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateEndDateSmallNow() throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> beans = tsyInvestigationQuestionDao.queryEndDateSmallNow(map);
		for(Map<String,Object> bean : beans){
			Map<String,Object> item = new HashMap<String, Object>();
			item.put("generalState", "2");
			item.put("id", bean.get("id"));
			tsyInvestigationQuestionDao.updateGeneralMation(item);
		}
	}
	
	
	
}

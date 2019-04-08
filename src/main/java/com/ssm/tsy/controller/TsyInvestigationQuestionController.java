package com.ssm.tsy.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyInvestigationQuestionService;



@Controller
public class TsyInvestigationQuestionController {

	@Resource
	private TsyInvestigationQuestionService  tsyInvestigationQuestionService;
	
	/**
	 * 查询签到信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/investigationQuestion/queryinvestigationQuestionList")
	@ResponseBody
	public void queryinvestigationQuestionList(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.queryinvestigationQuestionList(inputObject, outputObject);
	}
	
	/**
	 * 根据用户权限查询投票信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/investigationQuestion/queryVoteList")
	@ResponseBody
	public void queryVoteList(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.queryVoteList(inputObject, outputObject);
	}
	
	/**
	 * 修改投票信息，或者修改未保存投票信息的调用
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/investigationQuestion/queryVoteListByGeneralId")
	@ResponseBody
	public void queryVoteListByGeneralId(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.queryVoteListByGeneralId(inputObject, outputObject);
	}
	
	/**
	 * 手机投票页面查询问题
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/investigationQuestion/queryAdmTsyVoteListByGeneralId")
	@ResponseBody
	public void queryAdmTsyVoteListByGeneralId(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.queryVoteListByGeneralId(inputObject, outputObject);
	}
	
	/**
	 * 查询未保存的投票信息的id
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/investigationQuestion/queryVoteListByNoSave")
	@ResponseBody
	public void queryVoteListByNoSave(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.queryVoteListByNoSave(inputObject, outputObject);
	}
	
	/**
	 * 删除未保存的投票信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/investigationQuestion/deleteVoteListByNoSave")
	@ResponseBody
	public void deleteVoteListByNoSave(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.deleteVoteListByNoSave(inputObject, outputObject);
	}
	
	/**
	 * 添加标题信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/investigationQuestion/insertGeneralMation")
	@ResponseBody
	public void insertGeneralMation(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.insertGeneralMation(inputObject, outputObject);
	}
	
	/**
	 * 修改标题【标题头】信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/investigationQuestion/updateGeneralMation")
	@ResponseBody
	public void updateGeneralMation(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.updateGeneralMation(inputObject, outputObject);
	}
	
	/**
	 * 添加问题信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/investigationQuestion/insertQuestionMation")
	@ResponseBody
	public void insertQuestionMation(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.insertQuestionMation(inputObject, outputObject);
	}
	
	/**
	 * 修改问题信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/investigationQuestion/updateQuestionMation")
	@ResponseBody
	public void updateQuestionMation(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.updateQuestionMation(inputObject, outputObject);
	}
	
	/**
	 * 添加问题选项信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/investigationQuestion/insertOptionsMation")
	@ResponseBody
	public void insertOptionsMation(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.insertOptionsMation(inputObject, outputObject);
	}
	
	/**
	 * 修改问题选项信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/investigationQuestion/updateOptionsMation")
	@ResponseBody
	public void updateOptionsMation(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.updateOptionsMation(inputObject, outputObject);
	}
	
	/**
	 * 删除问题信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/investigationQuestion/deleteQuestionMation")
	@ResponseBody
	public void deleteQuestionMation(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.deleteQuestionMation(inputObject, outputObject);
	}
	
	/**
	 * 删除问题选项信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/investigationQuestion/deleteOptionsMation")
	@ResponseBody
	public void deleteOptionsMation(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.deleteOptionsMation(inputObject, outputObject);
	}
	
	/**
	 * 保存投票信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/investigationQuestion/updateVoteMessage")
	@ResponseBody
	public void updateVoteMessage(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.updateVoteMessage(inputObject, outputObject);
	}
	
	/**
	 * 删除投票信息--采用逻辑删除
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/investigationQuestion/deleteVoteMessage")
	@ResponseBody
	public void deleteVoteMessage(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.deleteVoteMessage(inputObject, outputObject);
	}
	
	/**
	 * 开始投票
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/investigationQuestion/updateinvestigationQuestionState")
	@ResponseBody
	public void updateinvestigationQuestionState(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.updateinvestigationQuestionState(inputObject, outputObject);
	}
	
	/**
	 * 用户投票界面调取数据
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/investigationQuestion/admTsyQueryVoteList")
	@ResponseBody
	public void admTsyQueryVoteList(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.admTsyQueryVoteList(inputObject, outputObject);
	}
	
	/**
	 * 判断用户是否参与过此次调查
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/investigationQuestion/queryVoteByIdAndOpenId")
	@ResponseBody
	public void queryVoteByIdAndOpenId(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.queryVoteByIdAndOpenId(inputObject, outputObject);
	}
	
	/**
	 * 添加用户的调查结果
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/investigationQuestion/insertVoteAnswer")
	@ResponseBody
	public void insertVoteAnswer(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.insertVoteAnswer(inputObject, outputObject);
	}
	
	/**
	 * 根据问题ID查询选项和评价比
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/investigationQuestion/queryOptionsByQuestionId")
	@ResponseBody
	public void queryOptionsByQuestionId(InputObject inputObject,OutputObject outputObject) throws Exception {
		tsyInvestigationQuestionService.queryOptionsByQuestionId(inputObject, outputObject);
	}
	
	
}

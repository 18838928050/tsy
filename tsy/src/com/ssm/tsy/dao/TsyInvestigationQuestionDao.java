package com.ssm.tsy.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface TsyInvestigationQuestionDao {

	public List<Map<String, Object>> queryinvestigationQuestionList(Map<String, Object> map, PageBounds pageBounds) throws Exception;

	public Map<String, Object> queryinvestigationQuestionListByContent(Map<String, Object> map) throws Exception;

	public int insertinvestigationQuestionListByContent(Map<String, Object> map) throws Exception;

	public int CopyAnswerTable(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> queryVoteMationById(Map<String, Object> map) throws Exception;
	
	public int updateByPrimaryKeySelective(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> queryVoteList(Map<String, Object> map, PageBounds pageBounds) throws Exception;

	public Map<String, Object> queryVoteListByNoSave(Map<String, Object> map) throws Exception;

	public int deleteVoteListByNoSaveOption(Map<String, Object> map) throws Exception;

	public int deleteVoteListByNoSaveQuestion(Map<String, Object> map) throws Exception;

	public int deleteVoteListByNoSaveGeneral(Map<String, Object> map) throws Exception;

	public Map<String, Object> queryVoteByGeneralId(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> queryVoteQuestionByGeneralId(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> queryVoteOptionByQuestionlId(Map<String, Object> questionId) throws Exception;

	public int insertQuestionMation(Map<String, Object> map) throws Exception;

	public int insertGeneralMation(Map<String, Object> map) throws Exception;

	public int insertOptionsMation(Map<String, Object> map) throws Exception;

	public int updateGeneralMation(Map<String, Object> map) throws Exception;
	
	public int updateQuestionMation(Map<String, Object> map) throws Exception;
	
	public int updateOptionsMation(Map<String, Object> map) throws Exception;

	public int deleteOptionsMation(Map<String, Object> map) throws Exception;
	
	public int deleteOptionsMationByQuestionId(Map<String, Object> map) throws Exception;

	public int deleteQuestionMation(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> queryVoteByIdAndOpenId(Map<String, Object> map) throws Exception;

	public int tsyInvestigationQuestionDao(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> queryOptionsByQuestionId(Map<String, Object> map) throws Exception;

	public Map<String, Object> queryZongrenshu(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> queryEndDateSmallNow(Map<String, Object> map) throws Exception;
	
	
}

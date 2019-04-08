package com.ssm.tsy.service;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;

public interface TsyInvestigationQuestionService {

	public void queryinvestigationQuestionList(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void updateinvestigationQuestionState(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryVoteList(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryVoteListByGeneralId(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryVoteListByNoSave(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void deleteVoteListByNoSave(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void insertQuestionMation(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void insertGeneralMation(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void insertOptionsMation(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void updateGeneralMation(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void updateQuestionMation(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void updateOptionsMation(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void deleteOptionsMation(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void deleteQuestionMation(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void updateVoteMessage(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void deleteVoteMessage(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void admTsyQueryVoteList(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryVoteByIdAndOpenId(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void insertVoteAnswer(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryOptionsByQuestionId(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void updateEndDateSmallNow() throws Exception;
	
	
}

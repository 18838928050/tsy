package com.ssm.tsy.service;


import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;

public interface AdmTsyService {

	public void querySessionAlife(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void insertAdmTsyLogin(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void addContentAndNumStar(InputObject inputObject,OutputObject outputObject) throws Exception;

	public void insertAdmTsyRegist(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryContentAndNumStar(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void AdmTsyExit(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryContentAndParentid(InputObject inputObject,OutputObject outputObject) throws Exception;

	public void selectLoginMessage(InputObject inputObject,OutputObject outputObject) throws Exception;

	public void updateLoginMessage(InputObject inputObject,OutputObject outputObject) throws Exception;

	public void graphicMessageselectByIdToOne(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryMaxStarTopTenPeople(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryMaxIntegralTopTen(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void queryAdmTsyVedioTopForuth(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void queryVedioTopFifteen(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryVedioByOptionClassAndUser(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryVedioByOptionClassAndNotequalUser(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryVedio(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryVedioByRand(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryVedioByRandEleven(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void wechatSearch(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void wechatNetSearch(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryWechatKnowledge(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryWechatKnowledgeByRowId(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryWechatKnowledgeNewFifth(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void AdmTsyVoteMation(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void toVotePage(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void queryVedioFjByRowId(InputObject inputObject, OutputObject outputObject) throws Exception;


}

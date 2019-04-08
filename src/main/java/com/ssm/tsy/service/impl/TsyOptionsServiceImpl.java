package com.ssm.tsy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.tsy.bean.UserBean;
import com.ssm.tsy.dao.TsyOptionsMapper;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyOptionsService;
import com.ssm.tsy.util.Constants;
import com.ssm.tsy.util.DateUtil;
import com.ssm.tsy.util.VedioUtil;

@Service
public class TsyOptionsServiceImpl implements TsyOptionsService{
	@Resource
	TsyOptionsMapper tsyOptionsMapper;

	/**
	 * 添加附件
	 */
	@SuppressWarnings("static-access")
	@Override
	public void insertOptions(InputObject inputObject, OutputObject outputObject,CommonsMultipartFile files) throws Exception {
		Map<String , Object> map = VedioUtil.vedioUpload(inputObject, outputObject, files);
		UserBean user = (UserBean) inputObject.getRequest().getSession().getAttribute("user");
		map.put("userId", user.getId());//用户ID
		map.put("optionState", Constants.TSYOPTIONS_OPTIONSTATE_ZC);//状态1正常
		map.put("optionTime", DateUtil.getTimeAndToString());//附件添加时间
		tsyOptionsMapper.insertSelective(map);
		outputObject.setBean(map);
		outputObject.setreturnCode(Constants.ADD_SUCCESS);
		outputObject.setreturnMessage("成功");
	}
	
	/**
	 * 删除附件
	 */
	@Override
	public void deleteOptions(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String , Object> map = inputObject.getParams();
		map.put("optionState", Constants.TSYOPTIONS_OPTIONSTATE_SC);//删除 逻辑删除
		tsyOptionsMapper.updateByPrimaryKeySelective(map);
		outputObject.setreturnCode(Constants.DELETE_SUCCESS);
		outputObject.setreturnMessage("删除成功");
	}

	/**
	 * 根据用户ID查找对应附件
	 */
	@SuppressWarnings("static-access")
	@Override
	public void queryOptionsList(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String , Object> map = inputObject.getParams();
		UserBean user = (UserBean) inputObject.getRequest().getSession().getAttribute("user");
		int page = Integer.parseInt(map.get("offset").toString())/Integer.parseInt(map.get("limit").toString());
		page++;
		int limit = Integer.parseInt(map.get("limit").toString());
		map.put("userId", user.getId());//用户id
		List<Map<String,Object>> beans = tsyOptionsMapper.queryOptionsList(map,new PageBounds(page,limit,true));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>)beans;
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(beans);
		outputObject.settotal(total);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
	
	
	
	
}

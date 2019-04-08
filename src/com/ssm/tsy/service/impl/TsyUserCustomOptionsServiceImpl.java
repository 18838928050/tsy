package com.ssm.tsy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.tsy.bean.UserBean;
import com.ssm.tsy.dao.TsyUserCustomOptionsMapper;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyUserCustomOptionsService;
import com.ssm.tsy.util.Constants;
import com.ssm.tsy.util.DateUtil;
@Service
public class TsyUserCustomOptionsServiceImpl implements TsyUserCustomOptionsService{

	@Resource
	private TsyUserCustomOptionsMapper tsyUserCustomOptionsMapper;

	@SuppressWarnings("static-access")
	@Override
	public void insertTsyUserCustomOptions(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		UserBean user = (UserBean) inputObject.getRequest().getSession().getAttribute("user");
		map.put("optionState", Constants.TSY_USER_CUSTOM_OPTIONS_STATE_SHZ);//默认审核中
		map.put("userId", user.getId());//用户id
		map.put("optionTime", DateUtil.getTimeAndToString());//当前日期
		if(map.get("optionType").equals(Constants.TSY_USER_CUSTOM_OPTIONS_TYPE_SP)){//视频
			map.put("optionVedio", map.get("optionVedioId"));
		}else{
			map.put("optionContext", map.get("optionVedioId"));
		}
		tsyUserCustomOptionsMapper.insertSelective(map);
		outputObject.setreturnCode(Constants.ADD_SUCCESS);
		outputObject.setreturnMessage("添加成功");
	}

	@SuppressWarnings("static-access")
	@Override
	public void queryVedioList(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		UserBean user = (UserBean) inputObject.getRequest().getSession().getAttribute("user");
		int page = Integer.parseInt(map.get("offset").toString())/Integer.parseInt(map.get("limit").toString());
		page++;
		int limit = Integer.parseInt(map.get("limit").toString());
		map.put("userId", user.getId());//用户id
		List<Map<String,Object>> beans = tsyUserCustomOptionsMapper.queryVedioList(map,new PageBounds(page,limit,true));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>)beans;
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(beans);
		outputObject.settotal(total);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	@Override
	public void updateVedioState(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		String nowState = map.get("nowState").toString();
		String updateState = map.get("updateState").toString();
		if(nowState.equals(Constants.TSY_USER_CUSTOM_OPTIONS_STATE_SHZ)&&
				(updateState.equals(Constants.TSY_USER_CUSTOM_OPTIONS_STATE_SHTG)||updateState.equals(Constants.TSY_USER_CUSTOM_OPTIONS_STATE_SHBTG))){//审核中，执行审核通过或者审核不通过操作
			tsyUserCustomOptionsMapper.updateVedioState(map);
			outputObject.setreturnCode(Constants.UPDATE_SUCCESS);
			outputObject.setreturnMessage("修改成功");
		}else if((nowState.equals(Constants.TSY_USER_CUSTOM_OPTIONS_STATE_SHTG)||nowState.equals(Constants.TSY_USER_CUSTOM_OPTIONS_STATE_SHBTG))&&
				updateState.equals(Constants.TSY_USER_CUSTOM_OPTIONS_STATE_FFWJ)){//审核通过或者审核不通过，执行非法设置操作
			tsyUserCustomOptionsMapper.updateVedioState(map);
			outputObject.setreturnCode(Constants.UPDATE_SUCCESS);
			outputObject.setreturnMessage("修改成功");
		}else{
			outputObject.setreturnCode(Constants.UPDATE_ERROR_RES);
			outputObject.setreturnMessage("参数错误");
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void queryVedioListById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		UserBean user = (UserBean) inputObject.getRequest().getSession().getAttribute("user");
		map.put("userId", user.getId());//用户id
		Map<String,Object> bean = tsyUserCustomOptionsMapper.queryVedioListById(map);
		outputObject.setBean(bean);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	@Override
	public void updateVedioById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		int size = tsyUserCustomOptionsMapper.updateVedioById(map);
		if(size>0){
			outputObject.setreturnCode(Constants.ZERO);
			outputObject.setreturnMessage("成功");
		}else{
			outputObject.setreturnCode(Constants.UPDATE_ERROR);
			outputObject.setreturnMessage("修改失败");
		}
	}
	
}

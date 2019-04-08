package com.ssm.tsy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.tsy.bean.UserBean;
import com.ssm.tsy.dao.TsyOptionsIntegralGgMapper;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyOptionsIntegralGgService;
import com.ssm.tsy.util.Constants;
import com.ssm.tsy.util.DateUtil;

@Service
public class TsyOptionsIntegralGgServiceImpl implements TsyOptionsIntegralGgService{
	
	@Resource
	private TsyOptionsIntegralGgMapper tsyOptionsIntegralGgMapper;

	/**
	 * 添加积分标准
	 */
	@Override
	public void insertTsyOptionsIntegralGg(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(tsyOptionsIntegralGgMapper.queryTsyOptionsIntegralGgByoptionsIntegralType(map)!=null){
			outputObject.setreturnCode(Constants.TSY_OPTIONS_INTEGRAL_GG_OPTIONS_INTEGRAL_TYPE_RETURN);
			outputObject.setreturnMessage("该类型已存在在，请选择其他类型或直接对该类型进行修改");
		}else{
			UserBean user = (UserBean) inputObject.getRequest().getSession().getAttribute("user");
			map.put("userId", user.getId());
			map.put("optionsIntegralTime", DateUtil.getTimeAndToString());
			tsyOptionsIntegralGgMapper.insertSelective(map);
			outputObject.setreturnCode(Constants.ADD_SUCCESS);
			outputObject.setreturnMessage("操作成功");
		}
	}

	/**
	 * 查看积分标准
	 */
	@Override
	public void queryTsyOptionsIntegralGg(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		UserBean user = (UserBean) inputObject.getRequest().getSession().getAttribute("user");
		map.put("userId", user.getId());
		int page = Integer.parseInt(map.get("offset").toString())/Integer.parseInt(map.get("limit").toString());
		page++;
		int limit = Integer.parseInt(map.get("limit").toString());
		List<Map<String,Object>> beans = tsyOptionsIntegralGgMapper.queryTsyOptionsIntegralGg(map,new PageBounds(page,limit,null, true));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>)beans;
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(beans);
		outputObject.settotal(total);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
	
	/**
	 * 修改积分标准
	 */
	@Override
	public void updateTsyOptionsIntegralGg(InputObject inputObject, OutputObject outputObject) throws Exception{
		Map<String,Object> map = inputObject.getParams();
		tsyOptionsIntegralGgMapper.updateByPrimaryKeySelective(map);
		outputObject.setreturnCode(Constants.UPDATE_SUCCESS);
		outputObject.setreturnMessage("修改成功");
	}

	/**
	 * 删除积分标准
	 */
	@Override
	public void daleteTsyOptionsIntegralGg(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		tsyOptionsIntegralGgMapper.deleteByPrimaryKey(map);
		outputObject.setreturnCode(Constants.DELETE_SUCCESS);
		outputObject.setreturnMessage("删除成功");
	}

	/**
	 * 根据id查看积分标准
	 */
	@Override
	public void queryTsyOptionsIntegralGgById(InputObject inputObject, OutputObject outputObject) throws Exception {
		
	}
	
	
	
}

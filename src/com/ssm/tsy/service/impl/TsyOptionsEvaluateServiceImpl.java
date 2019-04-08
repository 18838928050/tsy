package com.ssm.tsy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.tsy.dao.TsyOptionsEvaluateMapper;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyOptionsEvaluateService;
import com.ssm.tsy.util.Constants;
import com.ssm.tsy.util.JudgeUtil;
import com.wechat.service.FastSoSoService;

@Service
public class TsyOptionsEvaluateServiceImpl implements TsyOptionsEvaluateService{
	
	@Resource
	private TsyOptionsEvaluateMapper tsyOptionsEvaluateMapper;

	@Override
	public void queryBaiDuSearch(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(JudgeUtil.isNull(map.get("titleName").toString())){
			outputObject.setreturnCode(Constants.ZERO);
			outputObject.setreturnMessage("请输入搜索内容");
			List<Map<String , Object>> beans = new ArrayList<Map<String,Object>>();
			outputObject.setBeans(beans);
			outputObject.settotal(0);
		}else{
			int page = Integer.parseInt(map.get("offset").toString())/Integer.parseInt(map.get("limit").toString());
			page++;
			List<Map<String , Object>> beans = FastSoSoService.getSearch(map.get("titleName").toString(), page);
			outputObject.setBeans(beans);
			outputObject.settotal(1000);
		}
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
	
}

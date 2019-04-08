package com.ssm.tsy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.tsy.dao.TsyOptionsIntegralMapper;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyOptionsIntegralService;
import com.ssm.tsy.util.Constants;

@Service
public class TsyOptionsIntegralServiceImpl implements TsyOptionsIntegralService{

	@Resource
	private TsyOptionsIntegralMapper tsyOptionsIntegralMapper;

	
}

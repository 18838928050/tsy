package com.ssm.tsy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.AdmTsyService;

@Controller
public class AdmTsyController {
	
	@Resource
	private AdmTsyService admTsyService;
	/**
	 * 测试
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/AIFace")
	@ResponseBody
	public void AIFace(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		List<Map<String,Object>> beans = new ArrayList<Map<String,Object>>();
		for(int i = 0;i<Integer.parseInt(map.get("length").toString());i++){
			Map<String,Object> bean = new HashMap<String, Object>();
			bean.put("name", "test");
			bean.put("id", "1");
			bean.put("sex", "男");
			beans.add(bean);
		}
		outputObject.setBeans(beans);
		outputObject.settotal(1000);
	}
	
	/**
	 * 门户---判断session是否存在
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/SessionAlife")
	@ResponseBody
	public void SessionAlife(InputObject inputObject,OutputObject outputObject) throws Exception {
		admTsyService.querySessionAlife(inputObject, outputObject);
	}
	
	/**
	 * 门户---登陆
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/AdmTsyLogin")
	@ResponseBody
	public void AdmTsyLogin(InputObject inputObject,OutputObject outputObject) throws Exception {
		admTsyService.insertAdmTsyLogin(inputObject, outputObject);
	}
	
	/**
	 * 门户---注册
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/AdmTsyRegist")
	@ResponseBody
	public void AdmTsyRegist(InputObject inputObject,OutputObject outputObject) throws Exception {
		admTsyService.insertAdmTsyRegist(inputObject, outputObject);
	}
	
	/**
	 * 门户---退出
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/AdmTsyExit")
	@ResponseBody
	public void AdmTsyExit(InputObject inputObject,OutputObject outputObject) throws Exception {
		admTsyService.AdmTsyExit(inputObject, outputObject);
	}
	
	/**
	 * 门户---重置密码
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/AdmTsyRetPassword")
	@ResponseBody
	public void AdmTsyRetPassword(InputObject inputObject,OutputObject outputObject) throws Exception {
		
	}

	/**
	 * 投票跳转
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/AdmTsyVoteMation")
	@ResponseBody
	public void AdmTsyVoteMation(InputObject inputObject,OutputObject outputObject) throws Exception {
		admTsyService.AdmTsyVoteMation(inputObject, outputObject);
	}
	
	/**
	 * 根据code获取openid,然后获取头像
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/wechat/tsy/toVotePage")
	@ResponseBody
	public void toVotePage(InputObject inputObject,OutputObject outputObject) throws Exception {
		admTsyService.toVotePage(inputObject, outputObject);
	}
	
	/**
	 * 门户微识系统搜索
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@RequestMapping("post/admTsy/wechatSearch")
	@ResponseBody
	public void wechatSearch(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.wechatSearch(inputObject, outputObject);
	}
	
	/**
	 * 门户微资源搜索
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@RequestMapping("post/admTsy/wechatNetSearch")
	@ResponseBody
	public void wechatNetSearch(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.wechatNetSearch(inputObject, outputObject);
	}
	
	/**
	 * 查询近半年活跃度最高的前二十五名
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@RequestMapping("post/admTsy/queryMaxStarTopTenPeople")
	@ResponseBody
	public void queryMaxStarTopTenPeople(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.queryMaxStarTopTenPeople(inputObject, outputObject);
	}
	
	/**
	 * 查询积分最高的系统的十篇文章
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@RequestMapping("post/admTsy/queryMaxIntegralTopTen")
	@ResponseBody
	public void queryMaxIntegralTopTen(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.queryMaxIntegralTopTen(inputObject, outputObject);
	}
	
	/**
	 * 查询半年内有最新更新的视频分类和分类下的视频
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@RequestMapping("post/admTsy/queryAdmTsyVedioTopForuth")
	@ResponseBody
	public void queryAdmTsyVedioTopForuth(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.queryAdmTsyVedioTopForuth(inputObject, outputObject);
	}
	
	/**
	 * 查询最新的十五条视频数据
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@RequestMapping("post/admTsy/queryVedioTopFifteen")
	@ResponseBody
	public void queryVedioTopFifteen(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.queryVedioTopFifteen(inputObject, outputObject);
	}
	
	/**
	 * 根据视频id查询附件
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@RequestMapping("post/admTsy/queryVedioFjByRowId")
	@ResponseBody
	public void queryVedioFjByRowId(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.queryVedioFjByRowId(inputObject, outputObject);
	}
	
	/**
	 * 根据分类，上传人和视频id查询视频
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@RequestMapping("post/admTsy/queryVedioByOptionClassAndUser")
	@ResponseBody
	public void queryVedioByOptionClassAndUser(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.queryVedioByOptionClassAndUser(inputObject, outputObject);
	}
	
	/**
	 * 根据分类，上传人和视频id查询视频,上传人不是当前选择视频的上传人的视频
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@RequestMapping("post/admTsy/queryVedioByOptionClassAndNotequalUser")
	@ResponseBody
	public void queryVedioByOptionClassAndNotequalUser(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.queryVedioByOptionClassAndNotequalUser(inputObject, outputObject);
	}
	
	/**
	 * 查询视频
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@RequestMapping("post/admTsy/queryVedio")
	@ResponseBody
	public void queryVedio(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.queryVedio(inputObject, outputObject);
	}
	
	/**
	 * 随机查询八个视频
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@RequestMapping("post/admTsy/queryVedioByRand")
	@ResponseBody
	public void queryVedioByRand(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.queryVedioByRand(inputObject, outputObject);
	}
	
	/**
	 * 随机查询十一个个视频
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@RequestMapping("post/admTsy/queryVedioByRandEleven")
	@ResponseBody
	public void queryVedioByRandEleven(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.queryVedioByRandEleven(inputObject, outputObject);
	}
	
	/**
	 * 添加评论和星数（Wtk2017.2.28）
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@RequestMapping("post/admTsy/AddContentAndNumStar")
	@ResponseBody
	public void AddContentAndNumStar(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.addContentAndNumStar(inputObject, outputObject);
	}
	
	/**
	 * 查询文章或者视频的星数和评价
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@RequestMapping("post/admTsy/queryContentAndNumStar")
	@ResponseBody
	public void queryContentAndNumStar(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.queryContentAndNumStar(inputObject, outputObject);
	}
	
	/**
	 * 回复评论
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@RequestMapping("post/admTsy/queryTysOptionsEvaluateContent")
	@ResponseBody
	public void queryTysOptionsEvaluateContent(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.queryContentAndParentid(inputObject, outputObject);
	}
	/**
	 * 查询登陆个人信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@RequestMapping("post/admTsy/selectLoginMessage")
	@ResponseBody
	public void selectLoginMessage(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.selectLoginMessage(inputObject, outputObject);
	}
	
	/**
	 * 修改登陆个人信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@RequestMapping("post/admTsy/updateLoginMessage")
	@ResponseBody
	public void updateLoginMessage(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.updateLoginMessage(inputObject, outputObject);
	}
	
	/**
	 * 图文消息查看
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/graphicMessage/selectByIdToOne")
	@ResponseBody
	public void graphicMessageselectByIdToOne(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.graphicMessageselectByIdToOne(inputObject,outputObject);
	}
	
	/**
	 * 手机页面搜索文章
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/queryWechatKnowledge")
	@ResponseBody
	public void queryWechatKnowledge(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.queryWechatKnowledge(inputObject,outputObject);
	}
	
	/**
	 * 手机页面搜索文章点击查看
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/queryWechatKnowledgeByRowId")
	@ResponseBody
	public void queryWechatKnowledgeByRowId(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.queryWechatKnowledgeByRowId(inputObject,outputObject);
	}
	
	/**
	 * 手机页面主页加载最新的五条数据
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/queryWechatKnowledgeNewFifth")
	@ResponseBody
	public void queryWechatKnowledgeNewFifth(InputObject inputObject,OutputObject outputObject) throws Exception{
		admTsyService.queryWechatKnowledgeNewFifth(inputObject,outputObject);
	}
	
}

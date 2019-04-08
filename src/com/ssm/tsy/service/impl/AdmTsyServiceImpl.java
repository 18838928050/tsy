package com.ssm.tsy.service.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.ssm.tsy.bean.WeChatAPP;
import com.ssm.tsy.dao.TsyGraphicMessageDao;
import com.ssm.tsy.dao.TsyMhUserMapper;
import com.ssm.tsy.dao.TsyOptionsEvaluateMapper;
import com.ssm.tsy.dao.TsyOptionsIntegralMapper;
import com.ssm.tsy.dao.UserDao;
import com.ssm.tsy.dao.WeChatAPPDao;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.AdmTsyService;
import com.ssm.tsy.util.Constants;
import com.ssm.tsy.util.DateUtil;
import com.ssm.tsy.util.JudgeUtil;
import com.ssm.tsy.util.ToolUtil;
import com.ssm.tsy.util.TransUtil;
import com.wechat.service.FastSoSoService;
import com.wechat.service.GetOpenIdByCode;

@Service
public class AdmTsyServiceImpl implements AdmTsyService{

	@Resource
	private TsyOptionsIntegralMapper tsyOptionsIntegralMapper;
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private TsyOptionsEvaluateMapper tsyOpetionsEvaluateMapper;
	
	@Resource
	private TsyMhUserMapper tsyMhUserMapper;
	
	@Resource
	private TsyGraphicMessageDao tsyGraphicMessageDao;
	
	@Resource
	private WeChatAPPDao weChatAPPDao;
	

	/**
	 * 门户---判断session是否存在
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void querySessionAlife(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String ,Object> map = inputObject.getLogParams();
		if(map==null||map.equals("")||map.size()==0){
			outputObject.setreturnCode(Constants.ADM_TSY_USER_SESSION_LOG_IS_NULL);//为空
			outputObject.setreturnMessage("请登录");
		}else{
			outputObject.setBean(map);
			outputObject.setreturnCode(Constants.ADM_TSY_USER_SESSION_LOG_NOT_IS_NULL);//不为空
			outputObject.setreturnMessage("登录成功");
		}
	}
	
	/**
	 * 门户---登陆
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void insertAdmTsyLogin(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		outputObject.setreturnCode(Constants.WRONG);
		if(JudgeUtil.isNull(map.get("mhUserNumber").toString())){
			outputObject.setreturnMessage("账号不能为空");
		}else if(JudgeUtil.isNull(map.get("mhUserPassword").toString())){
			outputObject.setreturnMessage("请输入密码");
		}else{
			Map<String,Object> bean = tsyMhUserMapper.selectByNumber(map);
			if(bean==null){
				outputObject.setreturnMessage("该账号未注册，请先注册...");
			}else{
				map.put("mhUserPassword", ToolUtil.MD5(map.get("mhUserPassword").toString()));
				Map<String,Object> beanPwd = tsyMhUserMapper.selectByNumberAndPwd(map);
				if(beanPwd==null){
					outputObject.setreturnMessage("密码输入错误,请确认密码...");
				}else{
					outputObject.setLogParams(beanPwd);
					outputObject.setreturnCode(Constants.ZERO);
					outputObject.setreturnMessage("登陆成功");
				}
			}
		}
	}

	/**
	 * 门户---注册
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void insertAdmTsyRegist(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		outputObject.setreturnCode(Constants.WRONG);
		Map<String,Object> bean = tsyMhUserMapper.selectByNumber(map);
		if(bean!=null){
			outputObject.setreturnMessage("该账号已注册,请输入其他账号...");
		}else{
			int roleId = tsyMhUserMapper.insertRole(map);
			if(roleId>0){
				map.put("roleId", map.get("id"));
				map.put("mhUserPassword", ToolUtil.MD5(map.get("mhUserPassword").toString()));
				tsyMhUserMapper.insert(map);
				outputObject.setreturnCode(Constants.ZERO);
				outputObject.setreturnMessage("注册成功");
			}else{
				outputObject.setreturnMessage("数据异常");
			}
		}
		
	}
	/**
	 * 
	 * 添加评论，星数
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * @SuppressWarnings("unused")
	 * 屏蔽java编译中的一些警告信息。
	 * unused这个参数是屏蔽：定义的变量在代码中并未使用且无法访问。
	 * java在编译的时候会出现这样的警告，加上这个注解之后就是告诉编译器，忽略这些警告，编译的过程中将不会出现这种类型的警告
	 */
	@Override
	public void addContentAndNumStar(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		Map<String,Object> bean = inputObject.getLogParams();
		map.put("evaluate_userid", bean.get("id"));
		map.put("date", DateUtil.getTimeAndToString());//得到评论时间
		Map<String,Object> starNumMap = tsyOpetionsEvaluateMapper.selectStraNum(map);
		//评论和行数保存到数据库中
		if(starNumMap!=null)
			map.put("starNum", "0");
		tsyOpetionsEvaluateMapper.addContentAndNumStar(map);
		//查询积分规格表
		map.put("options_integral_type", Constants.TSY_OPTIONS_INTEGRAL_GG_OPTIONS_INTEGRAL_TYPE_EVALUATE);//积分类型，评价
		Map<String,Object> TsyOptionsIntegralGgmap = tsyOpetionsEvaluateMapper.selectTsyOptionsIntegralGg(map);
		Map<String,Object> insertmap = new HashMap<String, Object>();
		insertmap.put("options_id", map.get("id"));//文章,视频 id
		insertmap.put("integral_type", Constants.TSY_OPTIONS_INTEGRAL_GG_OPTIONS_INTEGRAL_TYPE_EVALUATE);//积分类型，评价
		insertmap.put("integral", TsyOptionsIntegralGgmap.get("options_integral"));//可以获取积分数
		insertmap.put("integral_time", DateUtil.getTimeAndToString());
		tsyOpetionsEvaluateMapper.addTsyoptionsintegral(insertmap);
		outputObject.setreturnCode(Constants.ADD_SUCCESS);
		outputObject.setreturnMessage("评价成功");
	}

	/**
	 * 查询文章或者视频的星数和评价
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@Override
	public void queryContentAndNumStar(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		Map<String,Object> bean = inputObject.getLogParams();
		if(bean==null){
			map.put("userId", null);
		}else{
			map.put("userId", bean.get("id"));
		}
		Map<String,Object> returnMap = tsyOpetionsEvaluateMapper.queryTsyoptionsStar(map);//星数
		List<Map<String,Object>> beans = tsyOpetionsEvaluateMapper.queryTsyoptionsEvaluate(map);
		for(int i = 0;i<beans.size();i++){
			Map<String,Object> entity = new HashMap<String, Object>();
			entity.put("optionId", map.get("optionId"));
			entity.put("parentId", beans.get(i).get("id"));
			List<Map<String,Object>> evaluateReplyList = tsyOpetionsEvaluateMapper.queryTsyoptionsEvaluateReply(entity);
			beans.get(i).put("evaluateReplySize", evaluateReplyList.size());
			beans.get(i).put("evaluateReply", evaluateReplyList);
		}
		outputObject.setBean(returnMap);
		outputObject.setBeans(beans);
		outputObject.settotal(beans.size());
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 退出
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@Override
	public void AdmTsyExit(InputObject inputObject, OutputObject outputObject) throws Exception {
		inputObject.getRequest().getSession().removeAttribute("admTsyUser");
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("退出成功");
	}

	/**
	 * 回复评论
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@Override
	public void queryContentAndParentid(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		Map<String,Object> bean = inputObject.getLogParams();
		map.put("evaluate_userid", bean.get("id"));//得到登陆人ID
		map.put("date", DateUtil.getTimeAndToString());//得到评论时间
		tsyOpetionsEvaluateMapper.insertContentAndParentid(map);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("回复成功");
	}
	/**
	 * 查询登陆信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */

	@Override
	public void selectLoginMessage(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		Map<String,Object> bean = inputObject.getLogParams();
		map.put("roleId",bean.get("mh_user_role_id"));//得到登陆人ID
		Map<String,Object> LoginMessage= userDao.selectLoginMessage(map);
		outputObject.setBean(LoginMessage);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
	/**
	 * 修改登陆信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@Override
	public void updateLoginMessage(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		Map<String,Object> bean = inputObject.getLogParams();
		map.put("roleId",bean.get("mh_user_role_id"));//得到登陆人ID
		map.put("date", DateUtil.getTimeAndToString());//更新时间
		userDao.updateLoginMessage(map);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 图文消息查看
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void graphicMessageselectByIdToOne(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		map.put("id", new String(TransUtil.decode(map.get("rowId").toString())));
		Map<String,Object> bean = tsyGraphicMessageDao.selectByPrimaryKey(map);
		outputObject.setBean(bean);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 查询近半年活跃度最高的前二十五名
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@Override
	public void queryMaxStarTopTenPeople(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		List<Map<String,Object>> beans = tsyOptionsIntegralMapper.queryMaxStarTopTenPeople(map);
		outputObject.setBeans(beans);
		outputObject.settotal(beans.size());
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 查询积分最高的系统的十篇文章
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@Override
	public void queryMaxIntegralTopTen(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		List<Map<String,Object>> beans = tsyOptionsIntegralMapper.queryMaxIntegralTopTen(map);
		outputObject.setBeans(beans);
		outputObject.settotal(beans.size());
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 查询半年内有最新更新的视频分类和分类下的视频
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@Override
	public void queryAdmTsyVedioTopForuth(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		List<Map<String,Object>> vedioClassBeans = tsyOptionsIntegralMapper.queryVedioClassTopThree(map);
		for(Map<String,Object> vedioClassBean : vedioClassBeans){
			Map<String,Object> bean = new HashMap<>();
			bean.put("optionState", Constants.TSY_USER_CUSTOM_OPTIONS_STATE_SHTG);//审核通过
			bean.put("optionClass", vedioClassBean.get("classId"));//视频分类
			List<Map<String,Object>> vedioBeans = tsyOptionsIntegralMapper.queryVedioTopForuth(bean);
			vedioClassBean.put("vedioSize", vedioBeans.size());
			vedioClassBean.put("vedio", vedioBeans);
		}
		outputObject.setBeans(vedioClassBeans);
		outputObject.settotal(vedioClassBeans.size());
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 查询最新的十五条视频数据
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@Override
	public void queryVedioTopFifteen(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		map.put("optionState", Constants.TSY_USER_CUSTOM_OPTIONS_STATE_SHTG);//审核通过
		List<Map<String,Object>> vedioBeans = tsyOptionsIntegralMapper.queryVedioTopFifteen(map);
		outputObject.setBeans(vedioBeans);
		outputObject.settotal(vedioBeans.size());
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
	
	/**
	 * 根据分类，上传人和视频id查询视频
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@Override
	public void queryVedioByOptionClassAndUser(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		map.put("optionState", Constants.TSY_USER_CUSTOM_OPTIONS_STATE_SHTG);//审核通过
		List<Map<String,Object>> vedioBeans = tsyOptionsIntegralMapper.queryVedioByOptionClassAndUser(map);
		outputObject.setBeans(vedioBeans);
		outputObject.settotal(vedioBeans.size());
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
	
	/**
	 * 根据分类，上传人和视频id查询视频,上传人不是当前选择视频的上传人的视频
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@Override
	public void queryVedioByOptionClassAndNotequalUser(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		map.put("optionState", Constants.TSY_USER_CUSTOM_OPTIONS_STATE_SHTG);//审核通过
		List<Map<String,Object>> vedioBeans = tsyOptionsIntegralMapper.queryVedioByOptionClassAndNotequalUser(map);
		outputObject.setBeans(vedioBeans);
		outputObject.settotal(vedioBeans.size());
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 查询视频
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@Override
	public void queryVedio(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		map.put("optionState", Constants.TSY_USER_CUSTOM_OPTIONS_STATE_SHTG);//审核通过
		int page = Integer.parseInt(map.get("page").toString());
		List<Map<String,Object>> vedioBeans = tsyOptionsIntegralMapper.queryVedio(map,new PageBounds(page, 20));
		outputObject.setBeans(vedioBeans);
		outputObject.settotal(vedioBeans.size());
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
	
	/**
	 * 随机查询八个视频
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@Override
	public void queryVedioByRand(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		map.put("optionState", Constants.TSY_USER_CUSTOM_OPTIONS_STATE_SHTG);//审核通过
		List<Map<String,Object>> vedioBeans = tsyOptionsIntegralMapper.queryVedioByRand(map);
		outputObject.setBeans(vedioBeans);
		outputObject.settotal(vedioBeans.size());
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 随机查询十一个视频
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@Override
	public void queryVedioByRandEleven(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		map.put("optionState", Constants.TSY_USER_CUSTOM_OPTIONS_STATE_SHTG);//审核通过
		List<Map<String,Object>> vedioBeans = tsyOptionsIntegralMapper.queryVedioByRandEleven(map);
		outputObject.setBeans(vedioBeans);
		outputObject.settotal(vedioBeans.size());
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 门户微识系统搜索
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@Override
	public void wechatSearch(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		int page = Integer.parseInt(map.get("page").toString());
		List<Map<String,Object>> Beans = tsyOptionsIntegralMapper.wechatSearch(map,new PageBounds(page, 15));
		for(Map<String, Object> bean : Beans){
			if(bean.get("searchType").toString().equals("vedio")){
				bean.put("searchFatherId", 0);
			}else{
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("id", bean.get("searchId"));
				item = tsyOptionsIntegralMapper.wechatSearchFatherIdById(item);
				bean.put("searchFatherId", item.get("fatherid"));
			}
		}
		outputObject.setBeans(Beans);
		outputObject.settotal(Beans.size());
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 门户微资源搜索
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@Override
	public void wechatNetSearch(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		int page = Integer.parseInt(map.get("page").toString());
		List<Map<String , Object>> beans = FastSoSoService.getSearch(map.get("searchContent").toString(), page);
		outputObject.setBeans(beans);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 手机页面搜索文章
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void queryWechatKnowledge(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		int page = Integer.parseInt(map.get("page").toString());
		List<Map<String , Object>> beans = tsyOptionsIntegralMapper.queryWechatKnowledge(map,new PageBounds(page, 15));
		outputObject.setBeans(beans);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
	
	/**
	 * 手机页面搜索文章点击查看
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void queryWechatKnowledgeByRowId(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		Map<String , Object> bean = tsyOptionsIntegralMapper.queryWechatKnowledgeByRowId(map);
		outputObject.setBean(bean);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 手机页面主页加载最新的五条数据
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void queryWechatKnowledgeNewFifth(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		List<Map<String , Object>> beans = tsyOptionsIntegralMapper.queryWechatKnowledgeNewFifth(map);
		outputObject.setBeans(beans);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}
	
	/**
	 * 投票跳转
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void AdmTsyVoteMation(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		String uuid = UUID.randomUUID().toString();
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe99cf827a6f99e61&redirect_uri="
				+ URLEncoder.encode(map.get("urlpath")+"/jsp/html/admTsy/personVote.html?id="+map.get("id"),"UTF-8")
				+"&response_type=code&scope=snsapi_userinfo&state="+uuid+"#wechat_redirect";
		Map<String,Object> bean = new HashMap<String, Object>();
		bean.put("url", url);
		outputObject.setBean(bean);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 根据code获取openid，然后获取头像
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	@Override
	public void toVotePage(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		WeChatAPP bean_list = weChatAPPDao.SelectAll();
		String appId = bean_list.getAppid();
		String secret = bean_list.getAppSecret();
		String code = map.get("code").toString();
		HttpSession session = inputObject.getRequest().getSession();
		Map<String,Object> returnBean = (Map<String, Object>) session.getAttribute("returnBean");
		if(returnBean==null){
			returnBean = GetOpenIdByCode.getRequest1(appId, secret, code);
			Map<String,Object> bean = GetOpenIdByCode.getRequest2(returnBean.get("access_token").toString(), returnBean.get("openid").toString());
			outputObject.setBean(bean);
			session.setAttribute("returnBean", returnBean);
		}else{
			returnBean = (Map<String, Object>) session.getAttribute("returnBean");
			Map<String,Object> bean = GetOpenIdByCode.getRequest2(returnBean.get("access_token").toString(), returnBean.get("openid").toString());
			outputObject.setBean(bean);
		}
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}

	/**
	 * 根据视频id查询附件
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 * */
	@Override
	public void queryVedioFjByRowId(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		List<Map<String,Object>> beans = tsyOptionsIntegralMapper.queryVedioFjByRowId(map);
		outputObject.setBeans(beans);
		outputObject.setreturnCode(Constants.ZERO);
		outputObject.setreturnMessage("成功");
	}


}





package com.ssm.tsy.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ssm.tsy.bean.UserBean;
import com.ssm.tsy.bean.WeChatAPP;
import com.ssm.tsy.service.UserService;
import com.ssm.tsy.service.WeChatAPPService;
import com.ssm.tsy.util.Constants;
import com.ssm.tsy.util.JsonUtil;

@Controller
public class WeChatAPPController {

	@Resource
	private WeChatAPPService weChatAPPService;
	
	@Resource
	private UserService userService;

	/**
	 * 跳转到添加页面，同时从数据库中查找是否存在，如果存在直接显示
	 * 
	 * @return
	 */
	@RequestMapping("wechatapp/ToAddPage")
	public ModelAndView ToAddPage(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		UserBean user = (UserBean) session.getAttribute("user");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("appId", user.getAppid());
		WeChatAPP bean_list = weChatAPPService.Select(map);
		if(bean_list!=null){
			mav.addObject("wechatbean", bean_list);
		}else{
			mav.addObject("wechatbean", null);
		}
		mav.setViewName("jsp/wechatapp/wechatappadd");
		return mav;
	}
	
	/**
	 * 跳转到操作指南
	 * 
	 * @return
	 */
	@RequestMapping("wechatapp/ToZhinanPage")
	public ModelAndView ToZhinanPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsp/wechatapp/zhinan");
		return mav;
	}

	/**
	 * 添加内容到数据库
	 * @throws Exception 
	 * 
	 */
	@RequestMapping("wechatapp/ToAddSql")
	public void ToAddSql(WeChatAPP bean, HttpServletResponse response,HttpSession session) throws Exception {
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		Map<String,Object> map = weChatAPPService.add(bean);
		// 判断是否添加成功
		if (map != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			user.setAppid(Integer.parseInt(map.get("id").toString()));
			userService.Update(user);
			pramers.put("message", Constants.ADD_SUCCESS);// 添加成功
		} else {
			pramers.put("message", Constants.ADD_ERROR);// 添加失败
		}
		JsonUtil.ToJson(response, pramers);
	}
	
	/**
	 * 修改内容到数据库
	 * @throws Exception 
	 * 
	 */
	@RequestMapping("wechatapp/UpdateAddSql")
	public void UpdateAddSql(WeChatAPP bean, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		int size = weChatAPPService.update(bean);
		// 判断是否修改成功
		if (size > 0) {
			pramers.put("message", Constants.UPDATE_SUCCESS);// 修改成功
		} else {
			pramers.put("message", Constants.UPDATE_ERROR);// 修改失败
		}
		JsonUtil.ToJson(response, pramers);
	}

}

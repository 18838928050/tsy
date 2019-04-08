package com.ssm.tsy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssm.tsy.bean.MenuBean;
import com.ssm.tsy.bean.UserBean;
import com.ssm.tsy.bean.WeChatAPP;
import com.ssm.tsy.bean.wechat.AccessToken;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.MenuService;
import com.ssm.tsy.service.WeChatAPPService;
import com.ssm.tsy.util.Constants;
import com.ssm.tsy.util.DateUtil;
import com.ssm.tsy.util.JsonUtil;
import com.ssm.tsy.util.WeixinUtil;

@Controller
public class MenuController {

	@Resource
	private MenuService menuService;

	@Resource
	private WeChatAPPService weChatAPPService;
	
	/**
	 * 查询图文消息一级
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/GraphicMessage/queryGraphicMessageByFirst")
	@ResponseBody
	public void queryGraphicMessageByFirst(InputObject inputObject,OutputObject outputObject) throws Exception {
		menuService.queryGraphicMessageByFirst(inputObject, outputObject);
	}

	/**
	 * 跳转到自定义菜单页面
	 * 
	 * @return
	 */
	@RequestMapping("menu/ToListPage")
	public ModelAndView ToListPage(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		UserBean user = (UserBean) session.getAttribute("user");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("appId", user.getAppid());
		WeChatAPP bean_list = weChatAPPService.Select(map);
		// 判断微信appid是否添加
		if (bean_list!=null) {
			// 查询所有的一级菜单
			List<MenuBean> first_list = menuService.SelectAllByLevel(1);
			// 查询所有的二级菜单
			List<MenuBean> two_list = menuService.SelectAllByLevel(2);
			mav.addObject("first_list", first_list);
			mav.addObject("two_list", two_list);
			mav.setViewName("jsp/menu/menulist");
		} else {
			mav.setViewName("jsp/wechat/wechaterror");
		}
		return mav;
	}

	/**
	 * ajax添加自定义菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("menu/AddMenu")
	public void AddMenu(String menuname, int menulevel, int menubolong, HttpServletResponse response) throws Exception {
		// 返回前台的信息
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		MenuBean bean = new MenuBean();
		if (menulevel == 1) {
			bean.setMenuname(menuname);
			bean.setMenutype("click");// 菜单列表类型--->一般为click
			bean.setMenukey(DateUtil.getToString());// 按钮点击时的标识符
			bean.setHaschild(0);// 是否有子菜单,新添加的一级菜单没有子菜单
			bean.setRebackint(0);// 0为还未添加回复类别1：图文消息2：图片3：语音4：视频5：文字
			bean.setRebackcontext("");// 要回复的内容，根据类别不同，存的内容不同
			bean.setMenulevel(menulevel);// 菜单级别
			bean.setMenubolong(menubolong);// 所属菜单id
			int si = menuService.AddMenuBean(bean);
			if (si > 0) {
				pramers.put("message", Constants.ADD_SUCCESS);
				pramers.put("selectid", menuService.SelectAllByName(menuname).get(0).getId());
			} else {
				pramers.put("message", Constants.ADD_ERROR);
			}
		} else {
			// 如果是添加第一个二级菜单，记得修改判断是否有二级菜单的参数haschild
			MenuBean upbean = menuService.SelectItemById(menubolong);
			upbean.setHaschild(1);
			upbean.setMenutype("");
			menuService.UpdateMenuBean(upbean);
			// 查询当前一级菜单下有的二级菜单
			bean.setMenuname(menuname);
			bean.setMenutype("click");
			bean.setRebackint(0);// 0为还未添加回复类别1：图文消息2：图片3：语音4：视频5：文字
			bean.setRebackcontext("");// 要回复的内容，根据类别不同，存的内容不同
			bean.setMenukey(DateUtil.getToString());// 按钮点击时的标识符-----二级菜单自动生成
			bean.setHaschild(0);// 是否有子菜单
			bean.setMenulevel(2);// 菜单级别
			bean.setMenubolong(menubolong);// 所属菜单id
			int si = menuService.AddMenuBean(bean);
			if (si > 0) {
				pramers.put("message", Constants.ADD_SUCCESS);
				pramers.put("selectid", menuService.SelectAllByName(menuname).get(0).getId());
			} else {
				pramers.put("message", Constants.ADD_ERROR);
			}
		}
		/** 输出到前台 */
		JsonUtil.ToJson(response, pramers);
	}

	/**
	 * ajax修改自定义菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("menu/UpdateMenu")
	public void UpdateMenu(int id, int rebackint, String rebackcontext, String menuname, HttpServletResponse response) throws Exception {
		// 返回前台的信息
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		if (rebackint == 1) {
			// 图文消息
			MenuBean bean = menuService.SelectItemById(id);
			bean.setMenuname(menuname);
			bean.setRebackint(rebackint);
			bean.setRebackcontext(rebackcontext);
			int size = menuService.UpdateMenuBean(bean);
			if (size == 0) {
				pramers.put("message", Constants.UPDATE_ERROR);
			} else {
				pramers.put("message", Constants.UPDATE_SUCCESS);
			}
		} else if (rebackint == 2) {
			// 图片

		} else if (rebackint == 3) {
			// 语音

		} else if (rebackint == 4) {
			// 视频

		} else if (rebackint == 5) {
			// 文字
			MenuBean bean = menuService.SelectItemById(id);
			bean.setMenuname(menuname);
			bean.setRebackint(rebackint);
			bean.setRebackcontext(rebackcontext);
			int size = menuService.UpdateMenuBean(bean);
			if (size == 0) {
				pramers.put("message", Constants.UPDATE_ERROR);
			} else {
				pramers.put("message", Constants.UPDATE_SUCCESS);
			}
		} else if (rebackint == 6) {
			// 网页
			MenuBean bean = menuService.SelectItemById(id);
			bean.setMenuname(menuname);
			bean.setRebackint(rebackint);
			bean.setMenutype("view");
			bean.setRebackcontext(rebackcontext);
			int size = menuService.UpdateMenuBean(bean);
			if (size == 0) {
				pramers.put("message", Constants.UPDATE_ERROR);
			} else {
				pramers.put("message", Constants.UPDATE_SUCCESS);
			}
		} else if (rebackint == 0) {
			// 只修改名称
			MenuBean bean = menuService.SelectItemById(id);
			bean.setMenuname(menuname);
			bean.setRebackint(rebackint);
			bean.setRebackcontext("");
			int size = menuService.UpdateMenuBean(bean);
			if (size == 0) {
				pramers.put("message", Constants.UPDATE_ERROR);
			} else {
				pramers.put("message", Constants.UPDATE_SUCCESS);
			}
		}
		/** 输出到前台 */
		JsonUtil.ToJson(response, pramers);
	}

	/**
	 * ajax删除自定义菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("menu/DeleteMenu")
	public void DeleteMenu(int id, HttpServletResponse response) throws Exception {
		// 返回前台的信息
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		if (menuService.DeleteMenuBean(id) == 0) {
			pramers.put("message", Constants.DELETE_ERROR);
		} else {
			pramers.put("message", Constants.DELETE_SUCCESS);
		}
		/** 输出到前台 */
		JsonUtil.ToJson(response, pramers);
	}

	/**
	 * ajax查询自定义菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("menu/SelectMenu")
	public void SelectMenu(int id, HttpServletResponse response) throws Exception {
		// 返回前台的信息
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		if (menuService.SelectItemById(id) != null) {
			pramers.put("message", Constants.SUCCESS);
			pramers.put("menukey", menuService.SelectItemById(id).getMenukey());
			pramers.put("rebackint", menuService.SelectItemById(id) .getRebackint());
			pramers.put("rebackcontext", menuService.SelectItemById(id) .getRebackcontext().replaceAll("[\n\r]", "-"));
		} else {
			pramers.put("message", Constants.ERROR);
		}
		/** 输出到前台 */
		JsonUtil.ToJson(response, pramers);
	}

	/**
	 * ajax发布
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("menu/FabuMenu")
	public void FabuMenu(HttpServletResponse response,HttpSession session) throws Exception {
		UserBean user = (UserBean) session.getAttribute("user");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("appId", user.getAppid());
		WeChatAPP bean_list = weChatAPPService.Select(map);
		AccessToken at = WeixinUtil.getAccessToken(bean_list.getAppid(), bean_list.getAppSecret());
		// 返回前台的信息
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		if (null != at) {
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(menuService.getMenu(), at.getToken());
			// 判断菜单创建结果
			if (0 == result) {
				pramers.put("message", Constants.WECHAT_MENU_ADD_SUCCESS);
			} else {
				pramers.put("message", Constants.WECHAT_MENU_ADD_ERROR);
			}
		}
		JsonUtil.ToJson(response, pramers);
	}
}

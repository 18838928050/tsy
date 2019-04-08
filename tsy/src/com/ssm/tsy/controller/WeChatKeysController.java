package com.ssm.tsy.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ssm.tsy.bean.UserBean;
import com.ssm.tsy.bean.WeChatAPP;
import com.ssm.tsy.bean.WeChatKeys;
import com.ssm.tsy.service.WeChatAPPService;
import com.ssm.tsy.service.WeChatKeysService;
import com.ssm.tsy.util.Constants;
import com.ssm.tsy.util.JsonUtil;

@Controller
public class WeChatKeysController {

	@Resource
	private WeChatKeysService weChatKeysService;

	@Resource
	private WeChatAPPService weChatAPPService;
	
	/**
	 * 查询所有关键字列表
	 * 
	 * @return
	 */
	@RequestMapping("wechatkeys/SelectAll")
	public ModelAndView SelectAll(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		UserBean user = (UserBean) session.getAttribute("user");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("appId", user.getAppid());
		WeChatAPP bean_list = weChatAPPService.Select(map);
		//判断微信appid是否添加
		if (bean_list!=null) {
			// 查询出来所有的关键字列表
			List<WeChatKeys> weChatKeys_list = weChatKeysService.SelectAll();
			// 判断内容是否为空
			if (weChatKeys_list == null || weChatKeys_list.size() == 0) {
				mav.addObject("size", 0);
			} else {
				mav.addObject("size", weChatKeys_list.size());
				mav.addObject("weChatKeyslist", weChatKeys_list);
			}
			mav.setViewName("jsp/wechatkeys/wechatkeyslist");
		} else {
			mav.setViewName("jsp/wechat/wechaterror");
		}
		return mav;
	}

	/**
	 * 跳转到添加关键字界面
	 * 
	 * @return
	 */
	@RequestMapping("wechatkeys/ToAddWeChatKeysPage")
	public ModelAndView ToAddWeChatKeysPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsp/wechatkeys/wechatkeysadd");
		return mav;
	}

	/**
	 * 跳转到修改关键字界面
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("wechatkeys/ToUpdateWeChatKeysPage")
	public ModelAndView ToUpdateWeChatKeysPage(String keyvalue) throws Exception {
		// 获取关注人信息
		keyvalue = new String(keyvalue.getBytes("ISO-8859-1"),"UTF-8");
		WeChatKeys bean = weChatKeysService.SelectAllByKey(keyvalue);
		ModelAndView mav = new ModelAndView();
		mav.addObject("bean", bean);
		mav.setViewName("jsp/wechatkeys/wechatkeysupdate");
		return mav;
	}

	/**
	 * 修改关键字到数据库
	 * 
	 * @return
	 */
	@RequestMapping("wechatkeys/ToUpdateWeChatKeysSql")
	public ModelAndView ToUpdateWeChatKeysSql(WeChatKeys bean, Model model) {
		if (weChatKeysService.UpdateWeChatKeys(bean) > 0) {
			model.addAttribute("tishi", "修改成功");
		} else {
			model.addAttribute("tishi", "修改失败");
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("bean", bean);
		mav.setViewName("jsp/wechatkeys/wechatkeysupdate");
		return mav;
	}

	/**
	 * 添加关键字判断
	 * 
	 * @param keyvalue
	 * @param context
	 * @param keyclass
	 * @throws Exception 
	 */
	@RequestMapping("wechatkeys/ToAddWeChatKeysSql")
	public void ToAddWeChatKeysSql(String keyvalue, String context, String keyclass, HttpServletResponse response, HttpSession session, HttpServletRequest request) throws Exception {
		// 返回前台的信息
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		if (keyvalue == null || keyvalue.equals("")) {
			pramers.put("message", Constants.KEYVALUE_IS_NULL);// 关键字‘键’为空
		} else if (keyclass == null || keyclass.equals("")) {
			pramers.put("message", Constants.KEYCLASS_IS_NULL);// 关键字类型为空
		} else {
			if (keyclass.equals("0")) {
				pramers.put("message", Constants.KEYCLASS_IS_ZERO);// 请选择关键字
			} else if (keyclass.equals(Constants.KEYCLASS_NUMBER) || keyclass.equals(Constants.KEYCLASS_WINDOW) 
					|| keyclass.equals(Constants.KEYCLASS_SYMBOL) || keyclass.equals(Constants.KEYCLASS_WORDS)) {//四种关键字
				if (context == null || context.equals("")) {
					pramers.put("message", Constants.CONTENT_IS_NULL);// 如果是数字关键字，请输入回复内容，内容为空
				} else {
					if (weChatKeysService.SelectAllByKey(keyvalue) == null) {
						WeChatKeys bean = new WeChatKeys();
						bean.setContext(context);
						bean.setJudge(1);
						bean.setKeyclass(Integer.parseInt(keyclass));
						bean.setKeyvalue(keyvalue);
						weChatKeysService.AddWeChatKeys(bean);
						pramers.put("message", Constants.ADD_SUCCESS);
					} else {
						pramers.put("message", Constants.ADD_ERROR_RES);
					}
				}
			} else {
				pramers.put("message", Constants.KEYCLASS_IS_WRONG);// 如果不是选项中的内容，则报系统错误
			}
		}
		JsonUtil.ToJson(response, pramers);
	}

	/**
	 * 根据类别查找关键字
	 * 
	 * @return
	 */
	@RequestMapping("wechatkeys/SelectAllByKeyClass")
	public ModelAndView SelectAllByKeyClass(String jibie, String name) {
		ModelAndView mav = new ModelAndView();
		// 存入关键字信息
		WeChatKeys bean = new WeChatKeys();
		bean.setKeyclass(Integer.parseInt(jibie));
		bean.setKeyvalue(name);
		List<WeChatKeys> weChatKeys_list = weChatKeysService
				.SelectAllByKeyClass(bean);
		// 判断内容是否为空
		if (weChatKeys_list == null || weChatKeys_list.size() == 0) {
			mav.addObject("size", 0);
		} else {
			mav.addObject("size", weChatKeys_list.size());
			mav.addObject("weChatKeyslist", weChatKeys_list);
		}
		mav.setViewName("jsp/wechatkeys/wechatkeyslist");
		return mav;
	}

	/**
	 * 1.停止运作 2.启动运作
	 * 
	 * @return
	 */
	@RequestMapping("wechatkeys/YunzuoWeChatKeys")
	public ModelAndView YunzuoWeChatKeys(String keyvalue, int cs, Model model) {
		// 防止乱码
		try {
			keyvalue = new String(keyvalue.getBytes("iso8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 获取关注人信息
		WeChatKeys bean = weChatKeysService.SelectAllByKey(keyvalue);

		if (bean != null) {
			if (cs == 0) {
				// 启动运作
				bean.setJudge(1);
				weChatKeysService.UpdateWeChatKeys(bean);
				model.addAttribute("tishi", "启动成功！");
			} else {
				// 停止运作
				bean.setJudge(0);
				weChatKeysService.UpdateWeChatKeys(bean);
				model.addAttribute("tishi", "停止运作成功");
			}
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/wechatkeys/SelectAll");
		return mav;
	}

	/**
	 * 删除关键字
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("wechatkeys/DeleteWeChatKeys")
	public ModelAndView DeleteWeChatKeys(int id, Model model) {
		if (weChatKeysService.DeleteWeChatKeys(id) > 0) {
			model.addAttribute("tishi", "删除成功");
		} else {
			model.addAttribute("tishi", "删除失败");
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/wechatkeys/SelectAll");
		return mav;
	}
}

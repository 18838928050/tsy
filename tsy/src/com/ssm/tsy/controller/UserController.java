package com.ssm.tsy.controller;

import java.util.ArrayList;
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

import com.ssm.tsy.bean.Liebiao;
import com.ssm.tsy.bean.TsyUserLoginLog;
import com.ssm.tsy.bean.UserBean;
import com.ssm.tsy.bean.User_Liebiao;
import com.ssm.tsy.service.LiebiaoService;
import com.ssm.tsy.service.TsyUserLoginLogService;
import com.ssm.tsy.service.UserService;
import com.ssm.tsy.service.User_LiebiaoService;
import com.ssm.tsy.util.Constants;
import com.ssm.tsy.util.DateUtil;
import com.ssm.tsy.util.JsonUtil;
import com.ssm.tsy.util.JudgeUtil;
import com.ssm.tsy.util.ListSortUtil;
import com.ssm.tsy.util.ToolUtil;
import com.wechat.service.IPService;

@Controller
public class UserController {

	@Resource
	private UserService userService;

	@Resource
	private User_LiebiaoService user_LiebiaoService;

	@Resource
	private LiebiaoService liebiaoService;

	@Resource
	private TsyUserLoginLogService tsyUserLoginLogService;

	/**
	 * 使用Ajax检测密码是否正确
	 * 
	 * @param no
	 * @param password
	 * @param response
	 * @param session
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("login/user/loginCheck")
	public void loginCheck(String no, String password, HttpServletResponse response, HttpSession session) throws Exception {
		// 向前台发送消息
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		// 当文本框内容为固定关键字或空时，提示信息
		if ("".equals(no) || no == null || "请输入账号".equals(no)) {
			pramers.put("message", Constants.NUMBER_IS_NULL);
		} else if ("".equals(password) || password == null) {
			pramers.put("message", Constants.PASSWORD_IS_NULL);
		} else {
			// 获取用户信息
			UserBean user = userService.getUserByNo(no);
			if (user != null) {// 如果用户不为空，进行登录验证
				if (ToolUtil.MD5(password).equals(user.getPassword())) {// 如果密码正确，进行登录操作
					pramers.put("message", Constants.LOGIN);
					session.setAttribute("user", user);// 将用户信息存放到session中
				} else {// 如果密码不正确，返回错误
					pramers.put("message", Constants.PASSWORD_IS_WRONG);
				}
			} else {// 如果用户为空，没有改用户，返回错误
				pramers.put("message", Constants.NUMBER_IS_WRONG);
			}
		}
		/** 输出到前台 */
		JsonUtil.ToJson(response, pramers);
	}

	/**
	 * 用户登录
	 * 
	 * @param no
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	@RequestMapping("login/user/login")
	public ModelAndView login(String no, HttpSession session, Model model, HttpServletRequest request) throws Exception {
		UserBean user = (UserBean) session.getAttribute("user");
		// 创建排序对象
		ListSortUtil<Liebiao> sortList = new ListSortUtil<Liebiao>();
		//根据用户id查询该用户拥有的列表id
		List<User_Liebiao> user_Liebiaolist;
		user_Liebiaolist = user_LiebiaoService.SelectAll(user.getId());
		if (user_Liebiaolist == null) {
			model.addAttribute("user_Liebiaolistsize", 0);
			model.addAttribute("liebiaojibielist1size", 0);
			model.addAttribute("liebiaojibielist2size", 0);
		} else {
			model.addAttribute("user_Liebiaolistsize", user_Liebiaolist.size());
			Liebiao bean;
			List<Liebiao> liebiaojibielist1 = new ArrayList<Liebiao>();
			List<Liebiao> liebiaojibielist2 = new ArrayList<Liebiao>();
			for (int i = 0; i < user_Liebiaolist.size(); i++) {
				bean = new Liebiao();
				bean = liebiaoService.SelectLiebiaoById(user_Liebiaolist.get(i).getLiebiaoid());
				if (bean.getJibie() == 1) {
					//查询一级列表菜单
					liebiaojibielist1.add(bean);
				} else {
					//查询二级列表菜单
					liebiaojibielist2.add(bean);
				}
				// 根据列表名称将列表排序
				sortList.sort(liebiaojibielist1, "name", "asc");
				sortList.sort(liebiaojibielist2, "name", "asc");
			}
			if (liebiaojibielist1 == null) {
				model.addAttribute("liebiaojibielist1size", 0);
			} else {
				model.addAttribute("liebiaojibielist1size", liebiaojibielist1.size());
				model.addAttribute("liebiaojibielist1", liebiaojibielist1);
				if (liebiaojibielist2 == null) {
					model.addAttribute("liebiaojibielist2size", 0);
				} else {
					model.addAttribute("liebiaojibielist2size", liebiaojibielist2.size());
					model.addAttribute("liebiaojibielist2", liebiaojibielist2);
				}
			}
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", user);
		mav.addObject("no", no);
		mav.setViewName("jsp/pcsuccess");

		//添加登陆记录
		TsyUserLoginLog log = new TsyUserLoginLog();
		UserBean u_bean = (UserBean) session.getAttribute("user");
		log.setNo(u_bean.getNo());
		log.setLoginData(DateUtil.getTimeAndToString());
		log.setLoginIp(request.getRemoteHost());
		log.setLoginNumSuccess(1);
		log.setLoginPlace(IPService.Json(request.getRemoteHost()));
//		tsyUserLoginLogService.insert(log);
		return mav;
	}

	/**
	 * 用户退出登录
	 * 
	 * @param sessoin
	 * @return
	 */
	@RequestMapping("login/user/exit")
	public ModelAndView exit(HttpSession sessoin) {
		// 关闭会话，清除session
		if (sessoin != null) {
			sessoin.invalidate();
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("loginJudge");
		return mav;
	}

	/**
	 * 用户添加界面
	 * 
	 * @return
	 */
	@RequestMapping("login/user/addToPage")
	public ModelAndView addToPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsp/user/useradd");
		return mav;
	}

	/**
	 * 用户添加
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("login/user/add")
	public void add(String no, String password, String name, String quanxian, HttpServletResponse response, HttpSession session) throws Exception {
		// 返回前台的信息
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		// 判断用户名是否为空
		if (no == null || no.equals("")) {
			pramers.put("message", Constants.NUMBER_IS_NULL);
		}
		// 判断手机号格式是否正确
		else if (!JudgeUtil.isPhoneNO(no)) {
			pramers.put("message", Constants.NUMBER_IS_WRONG);
		}
		// 判断姓名是否为空
		else if (name == null || name.equals("")) {
			pramers.put("message", Constants.NAME_IS_NULL);
		} else {
			UserBean bean = new UserBean();
			bean.setName(name);
			bean.setNo(no);
			bean.setPassword(ToolUtil.MD5(password));
			bean.setQuanxian(Integer.parseInt(quanxian));
			bean.setRedata(DateUtil.getTimeAndToString());
			int size = userService.setUserBean(bean);
			if (size == 0) {
				pramers.put("message", Constants.ADD_ERROR);
			} else {
				pramers.put("message", Constants.ADD_SUCCESS);
			}
		}
		JsonUtil.ToJson(response, pramers);
	}

	/**
	 * 跳转到操作成功界面 ->>>用户添加界面
	 * 
	 * @return
	 */
	@RequestMapping("login/user/success")
	public ModelAndView success(HttpServletResponse response, HttpSession session, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsp/user/useradd");
		return mav;
	}

	/**
	 * 跳转到修改密码界面
	 * 
	 * @return
	 */
	@RequestMapping("login/user/ToUpdatePasswordPage")
	public ModelAndView ToUpdatePasswordPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsp/user/userupdatepassword");
		return mav;
	}

	/**
	 * 修改密码判断
	 * 
	 * @throws Exception
	 */
	@RequestMapping("login/user/UpdatePasswordJudge")
	public void UpdatePasswordJudge(String oldpwd, String newpwd, HttpServletResponse response, HttpSession session) throws Exception {
		// 传递给前台的数据
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		if (oldpwd == null || oldpwd.equals("")) {
			pramers.put("message", Constants.OLDPASSWORD_IS_NULL);// 原密码为空
		} else if (newpwd == null || newpwd.equals("")) {
			pramers.put("message", Constants.NEWPASSWORD_IS_NULL);// 新密码为空
		} else {
			// 都不为空
			UserBean user = (UserBean) session.getAttribute("user");
			if (ToolUtil.MD5(oldpwd).equals(user.getPassword())) {
				user.setPassword(ToolUtil.MD5(newpwd));
				if (userService.Update(user) > 0) {
					pramers.put("message", Constants.UPDATE_SUCCESS);// 修改成功
				} else {
					pramers.put("message", Constants.UPDATE_ERROR);// 修改失败
				}
			} else {
				pramers.put("message", Constants.OLDPASSWORD_IS_WRONG);// 原密码错误
			}
		}
		JsonUtil.ToJson(response, pramers);
	}

	/**
	 * 跳转到重置密码界面
	 * 
	 * @return
	 */
	@RequestMapping("login/user/ToChongzhiPasswordPage")
	public ModelAndView ToChongzhiPasswordPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsp/user/userchongzhipassword");
		return mav;
	}

	/**
	 * 重置密码判断
	 * 
	 * @throws Exception
	 */
	@RequestMapping("login/user/ChongzhiPasswordJudge")
	public void ChongzhiPasswordJudge(String newpwd, HttpServletResponse response, HttpSession session) throws Exception {
		// 传递给前台的数据
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		if (newpwd == null || newpwd.equals("")) {
			pramers.put("message", Constants.NEWPASSWORD_IS_NULL);// 新密码为空
		} else {
			// 都不为空
			UserBean user = (UserBean) session.getAttribute("user");
			user.setPassword(ToolUtil.MD5(newpwd));
			if (userService.Update(user) > 0) {
				pramers.put("message", Constants.UPDATE_SUCCESS);// 修改成功
			} else {
				pramers.put("message", Constants.UPDATE_ERROR);// 修改失败
			}
		}
		JsonUtil.ToJson(response, pramers);
	}

	/**
	 * 所有用户的界面
	 * 
	 * @return
	 */
	@RequestMapping("login/user/ToAllUserPage")
	public ModelAndView ToAllUserPage() {
		ModelAndView mav = new ModelAndView();
		// 查询所有的用户
		List<UserBean> userlist = userService.SelectAll();
		mav.addObject("size", userlist.size());
		mav.addObject("userlist", userlist);
		mav.setViewName("jsp/user/userlist");
		return mav;
	}

	/**
	 * 所有用户的界面 1.用户类别 2.用户账号 3.用户姓名
	 * 
	 * @return
	 */
	@RequestMapping("login/user/SelectToAllUserPage")
	public ModelAndView SelectToAllUserPage(UserBean bean) {
		ModelAndView mav = new ModelAndView();
		// 根据条件查询所有的用户
		List<UserBean> userlist = userService.SelectAllByNameAndQuanxian(bean);
		mav.addObject("size", userlist.size());
		mav.addObject("userlist", userlist);
		mav.setViewName("jsp/user/userlist");
		return mav;
	}

	/**
	 * 修改权限
	 * 
	 * @param bean
	 * @throws Exception
	 */
	@RequestMapping("login/user/UpdateQuanxian")
	public void UpdateQuanxian(UserBean bean, HttpServletResponse response) throws Exception {
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("message", Constants.ERROR);
		UserBean sel_user = userService.getUserByNo(bean.getNo());
		if(bean.getQuanxian()==1){
			sel_user.setAppid(6);
		}else{
			sel_user.setAppid(-1);
		}
		sel_user.setQuanxian(bean.getQuanxian());
		if (userService.Update(sel_user) == 1) {
			pramers.put("message", Constants.UPDATE_SUCCESS);
		} else {
			pramers.put("message", Constants.UPDATE_ERROR);
		}
		JsonUtil.ToJson(response, pramers);
	}
}

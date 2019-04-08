package com.ssm.tsy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssm.tsy.bean.Liebiao;
import com.ssm.tsy.bean.UserBean;
import com.ssm.tsy.bean.User_Liebiao;
import com.ssm.tsy.service.LiebiaoService;
import com.ssm.tsy.service.UserService;
import com.ssm.tsy.service.User_LiebiaoService;
import com.ssm.tsy.util.Constants;
import com.ssm.tsy.util.JsonUtil;

@Controller
public class User_LiebiaoController {

	@Resource
	private User_LiebiaoService user_LiebiaoService;

	@Resource
	private LiebiaoService liebiaoService;

	@Resource
	private UserService userService;

	/**
	 * 用户&列表关系->查询所有
	 * 
	 * @return
	 */
	@RequestMapping("/user_Liebiao/SelectAll")
	public String SelectAll(Model model, HttpServletRequest request) {
		/**
		 * 查询所有的用户
		 */
		List<UserBean> userlist;
		userlist = userService.SelectAll();
		if (userlist == null) {
			model.addAttribute("usersize", 0);
		} else {
			model.addAttribute("usersize", userlist.size());
			model.addAttribute("userlist", userlist);
		}
		/**
		 * 查询所有的列表菜单
		 */
		List<Liebiao> liebiaolist;
		liebiaolist = liebiaoService.SelectAll();
		List<Liebiao> liebiaojibielist;
		liebiaojibielist = liebiaoService.SelectAllByjibie(1);
		if (liebiaojibielist == null) {
			model.addAttribute("liebiaojibielistsize", 0);
		} else {
			model.addAttribute("liebiaojibielistsize", liebiaojibielist.size());
			request.setAttribute("liebiaojibielist", liebiaojibielist);
			if (liebiaolist == null) {
				model.addAttribute("liebiaolistsize", 0);
			} else {
				model.addAttribute("liebiaolistsize", liebiaolist.size());
				model.addAttribute("liebiaolist", liebiaolist);
			}
		}
		return "jsp/userliebiao/userliebiaolist";
	}

	/**
	 * 用户&列表关系->根据用户id查询对应列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/user_Liebiao/SelectAllById")
	public String SelectAllById(int userid, Model model) {
		//将选中的用户id存放到model里，方便之后移除权限使用
		model.addAttribute("userid", userid);
		//根据用户id查询该用户拥有的列表id
		List<User_Liebiao> user_Liebiaolist;
		user_Liebiaolist = user_LiebiaoService.SelectAll(userid);
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
				bean = liebiaoService.SelectLiebiaoById(user_Liebiaolist.get(i)
						.getLiebiaoid());
				if (bean.getJibie() == 1) {
					//查询一级列表菜单
					liebiaojibielist1.add(bean);
				} else {
					//查询二级列表菜单
					liebiaojibielist2.add(bean);
				}
			}
			if (liebiaojibielist1 == null) {
				model.addAttribute("liebiaojibielist1size", 0);
			} else {
				model.addAttribute("liebiaojibielist1size",
						liebiaojibielist1.size());
				model.addAttribute("liebiaojibielist1", liebiaojibielist1);
				if (liebiaojibielist2 == null) {
					model.addAttribute("liebiaojibielist2size", 0);
				} else {
					model.addAttribute("liebiaojibielist2size",
							liebiaojibielist2.size());
					model.addAttribute("liebiaojibielist2", liebiaojibielist2);
				}
			}
		}
		return "jsp/userliebiao/userliebiaoitemlist";
	}

	/**
	 * 添加内容到数据库
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/user_Liebiao/AddToSql")
	public void AddToSql(User_Liebiao bean, HttpServletResponse response) throws Exception {
		// 返回给前台json的数据
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		//如果要添加的列表项已经存在，则什么都不做
		if (user_LiebiaoService.SelectItem(bean)) {
			pramers.put("message", Constants.ADD_ERROR_RES);
		} else {
			user_LiebiaoService.add(bean);
			pramers.put("message", Constants.ADD_SUCCESS);
		}
		/** 输出到前台 */
		JsonUtil.ToJson(response, pramers);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/user_Liebiao/Delete")
	public String Delete(User_Liebiao bean) {
		//首次判断是一级菜单还是二级菜单
		Liebiao be = liebiaoService.SelectLiebiaoById(bean.getLiebiaoid());
		//如果是一级菜单
		if (be.getJibie() == 1) {
			// 1.查出来该一级菜单的子菜单有哪些
			List<Liebiao> belist = liebiaoService.SelectAllBybolongto(be
					.getId());
			// 如果没有子菜单，则直接移除
			if (belist == null) {
				List<User_Liebiao> item = user_LiebiaoService
						.SelectItemForBean(bean);
				// 如果当前要移除的权限存在，则删除
				if (item != null) {
					user_LiebiaoService.delete(item.get(0).getId());
				} else {

				}
			} else {
				// 如果有子菜单，就遍历子菜单，看是否在（用户，列表）关联表中有对应项
				User_Liebiao bea;
				int value = 0;
				for (int i = 0; i < belist.size(); i++) {
					bea = new User_Liebiao();
					bea.setUserid(bean.getUserid());
					bea.setLiebiaoid(belist.get(i).getId());
					List<User_Liebiao> items = user_LiebiaoService
							.SelectItemForBean(bea);
					// 都记下一个值
					// 如果没有
					if (items == null) {
						value = i + 1;
					} else {
						// 如果有
						value = i + 1;
						break;
					}
				}
				// 如果没有对应项，则删除
				if (value == belist.size()) {
					List<User_Liebiao> item = user_LiebiaoService
							.SelectItemForBean(bean);
					// 如果当前要移除的权限存在，则删除
					if (item != null) {
						user_LiebiaoService.delete(item.get(0).getId());
					} else {

					}
				} else {
					// 如果有对应项
				}
			}
		}
		//如果是二级菜单
		else {
			List<User_Liebiao> item = user_LiebiaoService
					.SelectItemForBean(bean);
			// 如果当前要移除的权限存在，则删除
			if (item != null) {
				user_LiebiaoService.delete(item.get(0).getId());
			} else {

			}
		}
		return "";
	}
}

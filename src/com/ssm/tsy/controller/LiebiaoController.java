package com.ssm.tsy.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ssm.tsy.bean.Liebiao;
import com.ssm.tsy.service.LiebiaoService;
import com.ssm.tsy.service.User_LiebiaoService;

@Controller
public class LiebiaoController {

	@Resource
	private LiebiaoService liebiaoService;

	@Resource
	private User_LiebiaoService user_LiebiaoService;

	/**
	 * 查询所有列表
	 * 
	 * @param response
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("liebiao/SelectAll")
	public ModelAndView SelectAll() {
		// 查询出来所有的列表菜单
		List<Liebiao> liebiao_list = liebiaoService.SelectAll();
		ModelAndView mav = new ModelAndView();
		// 判断内容是否为空
		if (liebiao_list == null || liebiao_list.size() == 0) {
			mav.addObject("size", 0);
		} else {
			mav.addObject("size", liebiao_list.size());
			mav.addObject("liebiaolist", liebiao_list);
		}
		mav.setViewName("jsp/liebiao/liebiaolist");
		return mav;
	}

	/**
	 * 跳转到添加列表菜单页面
	 * 
	 * ->查询出来一级菜单，以便添加二级菜单使用
	 * 
	 * @return
	 */
	@RequestMapping("liebiao/AddToPage")
	public ModelAndView AddToPage() {
		ModelAndView mav = new ModelAndView();
		// 查询一级菜单
		List<Liebiao> liebiaolist = liebiaoService.SelectAllByjibie(1);
		// 判断内容是否为空
		if (liebiaolist == null) {
			mav.addObject("size", 0);
		} else {
			mav.addObject("size", liebiaolist.size());
			mav.addObject("liebiaolist", liebiaolist);
		}
		mav.setViewName("jsp/liebiao/liebiaoadd");
		return mav;
	}

	/**
	 * 添加列表菜单到数据库
	 * 
	 * @return
	 */
	@RequestMapping("liebiao/AddToSql")
	public ModelAndView AddToSql(Liebiao bean, String belongtoid,
			HttpServletRequest request, Model model) {
		// 获取添加到数据库中影响的行数
		int size = liebiaoService.add(bean, belongtoid);
		// 如果影响的行数为0，说明添加失败
		if (size == 0) {
			model.addAttribute("tishi", "添加失败");
		} else {
			// 如果影响的行数为1，说明添加成功
			model.addAttribute("tishi", "添加成功");
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/liebiao/AddToPage");
		return mav;
	}

	/**
	 * 跳转到菜单修改界面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("liebiao/UpdateToPage")
	public ModelAndView UpdateToPage(int id, Model model) {
		ModelAndView mav = new ModelAndView();
		// 根据id查询要修改菜单
		Liebiao bean = liebiaoService.SelectLiebiaoById(id);
		model.addAttribute("bean", bean);
		mav.setViewName("jsp/liebiao/liebiaoupdate");
		return mav;
	}

	/**
	 * 修改菜单内容到数据库
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("liebiao/UpdateToSql")
	public ModelAndView UpdateToSql(Liebiao bean, String belongtoid,
			HttpServletRequest request, Model model) {
		// 获取修改到数据库中影响的行数
		int size = liebiaoService.update(bean);
		// 如果影响的行数为0，说明修改失败
		if (size == 0) {
			model.addAttribute("tishi", "修改失败");
		} else {
			// 如果影响的行数为1，说明修改成功
			model.addAttribute("tishi", "修改成功");
		}
		// 重新跳转到列表菜单查询界面
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/liebiao/SelectAll");
		return mav;
	}

	/**
	 * 删除列表菜单
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("liebiao/DeleteFromSql")
	public ModelAndView DeleteFromSql(int id, Model model) {
		//判断该列表是否有用户在使用，如果有，则不能删除
		if (user_LiebiaoService.SelectItembyliebiaoid(id) > 0) {
			model.addAttribute("tishi", "该列表正在被某个用户使用，无法删除");
		} else {
			// 获取删除影响的行数
			int size = liebiaoService.delete(id);
			// 如果影响的行数为0，说明修改失败
			if (size == 0) {
				model.addAttribute("tishi", "修改失败");
			} else {
				// 如果影响的行数为1，说明修改成功
				model.addAttribute("tishi", "修改成功");
			}
		}
		// 重新跳转到列表菜单查询界面
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/liebiao/SelectAll");
		return mav;
	}

	/**
	 * 根据条件查询列表菜单
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("liebiao/SelectFromSqlByTiaojian")
	public ModelAndView SelectFromSqlByTiaojian(Liebiao bean, Model model) {
		// 根据条件查询列表菜单
		List<Liebiao> liebiao_list;
		if (bean.getJibie() == 0) {
			if(bean.getName().equals("")||bean.getName()==null){
				liebiao_list = liebiaoService.SelectAll();
			}else{
				liebiao_list = liebiaoService.SelectAllByTiaojian(bean);
			}
		} else {
			liebiao_list = liebiaoService.SelectAllByTiaojian(bean);
		}
		ModelAndView mav = new ModelAndView();
		// 判断内容是否为空
		if (liebiao_list == null || liebiao_list.size() == 0) {
			mav.addObject("size", 0);
		} else {
			mav.addObject("size", liebiao_list.size());
			mav.addObject("liebiaolist", liebiao_list);
		}
		// 重新跳转到列表菜单查询界面
		mav.setViewName("jsp/liebiao/liebiaolist");
		return mav;
	}

}

package com.ssm.tsy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssm.tsy.bean.WeChatKnowledge;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.WeChatKnowledgeService;
import com.ssm.tsy.util.Constants;
import com.ssm.tsy.util.JsonUtil;

@Controller
public class WeChatKnowledgeController {

	@Resource
	private WeChatKnowledgeService weChatKnowledgeService;
	
	/**
	 * 门户---查询内容
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/queryKnowledge")
	@ResponseBody
	public void queryKnowledge(InputObject inputObject,OutputObject outputObject) throws Exception {
		weChatKnowledgeService.queryKnowledge(inputObject,outputObject);
	}
	
	/**
	 * 门户---根据id查看单个知识内容
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/queryKnowledgeById")
	@ResponseBody
	public void queryKnowledgeById(InputObject inputObject,OutputObject outputObject) throws Exception {
		weChatKnowledgeService.queryKnowledgeById(inputObject,outputObject);
	}

	/**
	 * 跳转到一级菜单列表
	 * 
	 * @return
	 */
	@RequestMapping("WeChatKnowledge/SelectAllIsOne")
	public ModelAndView SelectAllIsOne() {
		// 查询出来所有的列表菜单
		List<WeChatKnowledge> WeChatKnowledge_list = weChatKnowledgeService.SelectAllIsOne();
		int up_size = weChatKnowledgeService.SelectUpSize();
		ModelAndView mav = new ModelAndView();
		// 判断内容是否为空
		if (WeChatKnowledge_list == null || WeChatKnowledge_list.size() == 0) {
			mav.addObject("size", 0);
			mav.addObject("up_size", up_size);
		} else {
			mav.addObject("up_size", up_size);
			mav.addObject("size", WeChatKnowledge_list.size());
			mav.addObject("WeChatKnowledge_list", WeChatKnowledge_list);
		}
		mav.setViewName("jsp/wechatknowledge/wechatknowledgeisonelist");
		return mav;
	}

	/**
	 * 跳转到二级菜单列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("WeChatKnowledge/SelectAllIsTwo")
	public ModelAndView SelectAllIsTwo(int id) {
		// 查询出来所有的列表菜单
		List<WeChatKnowledge> WeChatKnowledge_list = weChatKnowledgeService.SelectByFatherid(id);
		ModelAndView mav = new ModelAndView();
		// 判断内容是否为空
		if (WeChatKnowledge_list == null || WeChatKnowledge_list.size() == 0) {
			mav.addObject("size", 0);
			mav.addObject("fatherid", id);
			mav.addObject("name", weChatKnowledgeService.SelectById(id).get(0)
					.getName());
		} else {
			mav.addObject("size", WeChatKnowledge_list.size());
			mav.addObject("fatherid", id);
			mav.addObject("WeChatKnowledge_list", WeChatKnowledge_list);
			mav.addObject("name", weChatKnowledgeService.SelectById(id).get(0)
					.getName());
		}
		mav.setViewName("jsp/wechatknowledge/wechatknowledgeistwolist");
		return mav;
	}

	/**
	 * 手机展示页面
	 * 
	 * @return
	 */
	@RequestMapping("WeChatKnowledge/ToPageZhanshi")
	public ModelAndView ToPageZhanshi(int id) {
		ModelAndView mav = new ModelAndView();
		// 查询出来所有的列表菜单
		List<WeChatKnowledge> WeChatKnowledge_list = weChatKnowledgeService
				.SelectByFatherid(id);
		// 判断内容是否为空
		if (WeChatKnowledge_list == null || WeChatKnowledge_list.size() == 0) {
			mav.addObject("size", 0);
		} else {
			mav.addObject("size", WeChatKnowledge_list.size());
			mav.addObject("WeChatKnowledge_list", WeChatKnowledge_list);
		}
		mav.setViewName("jsp/wechatknowledge/phonepage");
		return mav;
	}

	/**
	 * 手机单项展示页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("WeChatKnowledge/ToPageZhanshiItem")
	public ModelAndView ToPageZhanshiItem(int id) {
		ModelAndView mav = new ModelAndView();
		// 查询出来所有的列表菜单
		List<WeChatKnowledge> WeChatKnowledge_list = weChatKnowledgeService
				.SelectById(id);
		// 判断内容是否为空
		if (WeChatKnowledge_list == null || WeChatKnowledge_list.size() == 0) {
			mav.addObject("size", 0);
		} else {
			mav.addObject("size", WeChatKnowledge_list.size());
			mav.addObject("WeChatKnowledge", WeChatKnowledge_list.get(0));
		}
		mav.setViewName("jsp/wechatknowledge/phonepageitem");
		return mav;
	}

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("WeChatKnowledge/SelectById")
	public void SelectById(int id, HttpServletResponse response) throws Exception {
		// 查询出来所有的列表菜单
		List<WeChatKnowledge> WeChatKnowledge_list = weChatKnowledgeService
				.SelectById(id);
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		if (WeChatKnowledge_list.get(0).getFatherid() == 0) {
			// 一级菜单
			// 返回前台的信息
			pramers.put("message", Constants.SUCCESS);
			pramers.put("selectid", WeChatKnowledge_list.get(0).getId());
			pramers.put("name", WeChatKnowledge_list.get(0).getName());
			pramers.put("jieshao", WeChatKnowledge_list.get(0).getJieshao()
					.replaceAll("[\n\r]", "-"));
		} else {
			// 二级菜单
			// 返回前台的信息
			pramers.put("message", Constants.SUCCESS);
			pramers.put("selectid", WeChatKnowledge_list.get(0).getId());
			pramers.put("name", WeChatKnowledge_list.get(0).getName());
			pramers.put("content", WeChatKnowledge_list.get(0).getContent());
		}
		/** 输出到前台 */
		JsonUtil.ToJson(response, pramers);
	}

	/**
	 * 添加
	 * @throws Exception 
	 */
	@RequestMapping("WeChatKnowledge/AddWeChatKnowledge")
	public void AddWeChatKnowledge(WeChatKnowledge bean,
			HttpServletResponse response) throws Exception {
		// 返回前台的信息
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		// 判断该列表是否存在
		if (weChatKnowledgeService.SelectAllByNameAndFatherid(bean.getName(),
				bean.getFatherid()).size() > 0) {
			pramers.put("message", Constants.ADD_ERROR_RES);// 存在，则不添加
		} else {
			// 不存在
			bean.setUpordown(0);
			int size = weChatKnowledgeService.add(bean);
			if (size > 0) {
				pramers.put("message", Constants.ADD_SUCCESS);// 添加成功
			} else {
				pramers.put("message", Constants.ADD_ERROR);// 添加失败
			}
		}
		/** 输出到前台 */
		JsonUtil.ToJson(response, pramers);
	}

	/**
	 * 内容上线或下线
	 * 
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping("WeChatKnowledge/UpOrDown")
	public void UpOrDown(int id, HttpServletResponse response) throws Exception {
		// 返回前台的信息
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		// 查询出来所有的列表菜单
		List<WeChatKnowledge> WeChatKnowledge_list = weChatKnowledgeService
				.SelectById(id);
		if (WeChatKnowledge_list.get(0).getUpordown() == 0) {
			pramers.put("message", Constants.KNOWLEDGE_UP_SUCCESS);
			WeChatKnowledge_list.get(0).setUpordown(1);
			weChatKnowledgeService.update(WeChatKnowledge_list.get(0));
		} else {
			pramers.put("message", Constants.KNOWLEDGED_DOWN_SUCCESS);
			WeChatKnowledge_list.get(0).setUpordown(0);
			weChatKnowledgeService.update(WeChatKnowledge_list.get(0));
		}
		/** 输出到前台 */
		JsonUtil.ToJson(response, pramers);
	}

	/**
	 * 修改一级菜单
	 * 
	 * @throws Exception
	 */
	@RequestMapping("WeChatKnowledge/UpdateOneWeChatKnowledge")
	public void UpdateOneWeChatKnowledge(WeChatKnowledge bean,HttpServletResponse response) throws Exception {
		// 返回前台的信息
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		// 判断该列表是否存在
		if (weChatKnowledgeService.SelectAllByNameAndFatherid(bean.getName(),bean.getFatherid()).size() > 0) {
			// 如果是修改自身的内容，则可以修改
			if (weChatKnowledgeService.SelectAllByNameAndFatherid(bean.getName(),bean.getFatherid()).get(0).getId() == bean.getId()) {
				WeChatKnowledge newbean = weChatKnowledgeService.SelectById(bean.getId()).get(0);
				newbean.setJieshao(bean.getJieshao());
				newbean.setName(bean.getName());
				int size = weChatKnowledgeService.update(newbean);
				if (size > 0) {
					pramers.put("message", Constants.UPDATE_SUCCESS);// 修改成功
				} else {
					pramers.put("message", Constants.UPDATE_ERROR);// 修改失败
				}
			} else {
				pramers.put("message", Constants.UPDATE_ERROR_RES);// 存在，则不修改
			}
		} else {
			// 不存在
			WeChatKnowledge newbean = weChatKnowledgeService.SelectById(bean.getId()).get(0);
			newbean.setJieshao(bean.getJieshao());
			newbean.setName(bean.getName());
			int size = weChatKnowledgeService.update(newbean);
			if (size > 0) {
				pramers.put("message", Constants.UPDATE_SUCCESS);// 修改成功
			} else {
				pramers.put("message", Constants.UPDATE_ERROR);// 修改失败
			}
		}
		JsonUtil.ToJson(response, pramers);
	}

	/**
	 * 修改二级菜单
	 * 
	 * @throws Exception
	 */
	@RequestMapping("WeChatKnowledge/UpdateTwoWeChatKnowledge")
	public void UpdateTwoWeChatKnowledge(WeChatKnowledge bean, HttpServletResponse response) throws Exception {
		// 返回前台的信息
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);

		// 判断该列表是否存在
		if (weChatKnowledgeService.SelectAllByNameAndFatherid(bean.getName(), bean.getFatherid()).size() > 0) {
			// 如果是修改自身的内容，则可以修改
			if (weChatKnowledgeService.SelectAllByNameAndFatherid(bean.getName(),bean.getFatherid()).get(0).getId() == bean.getId()) {
				WeChatKnowledge newbean = weChatKnowledgeService.SelectById(bean.getId()).get(0);
				newbean.setContent(bean.getContent());
				newbean.setName(bean.getName());
				int size = weChatKnowledgeService.update(newbean);
				if (size > 0) {
					pramers.put("message", Constants.UPDATE_SUCCESS);// 修改成功
				} else {
					pramers.put("message", Constants.UPDATE_ERROR);// 修改失败
				}
			} else {
				pramers.put("message", Constants.UPDATE_ERROR_RES);// 存在，则不修改
			}
		} else {
			// 不存在
			WeChatKnowledge newbean = weChatKnowledgeService.SelectById(bean.getId()).get(0);
			newbean.setContent(bean.getContent());
			newbean.setName(bean.getName());
			int size = weChatKnowledgeService.update(newbean);
			if (size > 0) {
				pramers.put("message", Constants.UPDATE_SUCCESS);// 修改成功
			} else {
				pramers.put("message", Constants.UPDATE_ERROR);// 修改失败
			}
		}
		JsonUtil.ToJson(response, pramers);
	}

	/**
	 * 删除，如果是二级菜单直接删除，如果是一级菜单，则判断是否有二级内容，如果有 则在前台给出提示，是否确认删除，如果确认删除，则跳转到另一个删除方法。
	 * 
	 * @param bean
	 * @param response
	 * @param session
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("WeChatKnowledge/DeleteWeChatKnowledge")
	public void DeleteWeChatKnowledge(int id, HttpServletResponse response) throws Exception {
		// 返回前台的信息
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		WeChatKnowledge bean = weChatKnowledgeService.SelectById(id).get(0);
		// 二级菜单直接删除
		if (bean.getJibie() == 2) {
			int size = weChatKnowledgeService.delete(id);
			if (size > 0) {
				pramers.put("message", Constants.DELETE_SUCCESS);// 删除成功
			} else {
				pramers.put("message", Constants.DELETE_ERROR);// 删除失败
			}
		} else {
			// 一级菜单需要判断下面是否有子菜单
			// 根据fatherid查询
			List<WeChatKnowledge> WeChatKnowledge_list = weChatKnowledgeService.SelectByFatherid(id);
			if (WeChatKnowledge_list.size() > 0) {
				pramers.put("message", Constants.DELETE_JUDGE);// 含有二级菜单，返回前台并给出提示是否真正删除
			} else {
				// 没有二级菜单
				int size = weChatKnowledgeService.delete(id);
				if (size > 0) {
					pramers.put("message", Constants.DELETE_SUCCESS);// 删除成功
				} else {
					pramers.put("message", Constants.DELETE_ERROR);// 删除失败
				}
			}
		}
		JsonUtil.ToJson(response, pramers);
	}

	/**
	 * 删除一级菜单和他的子菜单
	 * 
	 * @param id
	 * @param response
	 * @param session
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("WeChatKnowledge/DeleteWeChatKnowledgeAll")
	public void DeleteWeChatKnowledgeAll(int id, HttpServletResponse response) throws Exception {
		// 返回前台的信息
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		// 根据fatherid查询
		List<WeChatKnowledge> WeChatKnowledge_list = weChatKnowledgeService.SelectByFatherid(id);
		// 首先删除所有的子菜单
		for (WeChatKnowledge bean : WeChatKnowledge_list) {
			weChatKnowledgeService.delete(bean.getId());
		}
		int size = weChatKnowledgeService.delete(id);
		if (size > 0) {
			pramers.put("message", Constants.DELETE_SUCCESS);// 删除成功
		} else {
			pramers.put("message", Constants.DELETE_ERROR);// 删除失败
		}
		JsonUtil.ToJson(response, pramers);
	}

}

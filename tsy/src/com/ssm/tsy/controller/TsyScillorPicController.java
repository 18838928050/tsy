package com.ssm.tsy.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ssm.tsy.bean.TsyScillorPic;
import com.ssm.tsy.bean.UserBean;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.TsyScillorPicService;
import com.ssm.tsy.util.Constants;
import com.ssm.tsy.util.DateUtil;
import com.ssm.tsy.util.JsonUtil;
import com.ssm.tsy.util.ListSortUtil;
import com.ssm.tsy.util.UpLoadImgUtil;

@Controller
public class TsyScillorPicController {

	@Resource
	private TsyScillorPicService scillorPicService;
	
	/**
	 * 门户---滚动图片
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/TsyScillor/list")
	@ResponseBody
	public void queryTsyScillorlist(InputObject inputObject,OutputObject outputObject) throws Exception {
		scillorPicService.queryTsyScillorlist(inputObject, outputObject);
	}
	
	/**
	 * 门户---通知列表详情查看---从当前id查询十条已发布的数据展示出来
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/TsyScillor/queryTsyScillorItemsByIdToTen")
	@ResponseBody
	public void queryTsyScillorItemsByIdToTen(InputObject inputObject,OutputObject outputObject) throws Exception {
		scillorPicService.queryTsyScillorItemsByIdToTen(inputObject, outputObject);
	}
	
	/**
	 * 门户---通知列表详情查看---查询所有
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/TsyScillor/queryTsyScillorItemsAll")
	@ResponseBody
	public void queryTsyScillorItemsAll(InputObject inputObject,OutputObject outputObject) throws Exception {
		scillorPicService.queryTsyScillorItemsAll(inputObject, outputObject);
	}
	
	/**
	 * 门户---通知列表详情查看---根据id查询详情
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/admTsy/TsyScillor/queryTsyScillorItemsContentById")
	@ResponseBody
	public void queryTsyScillorItemsContentById(InputObject inputObject,OutputObject outputObject) throws Exception {
		scillorPicService.queryTsyScillorItemsContentById(inputObject, outputObject);
	}
	
	/**
	 * 表格根据类型查找
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/TsyScillor/queryTsyScillorTablelist")
	@ResponseBody
	public void queryTsyScillorTablelist(InputObject inputObject,OutputObject outputObject) throws Exception {
		scillorPicService.queryTsyScillorTablelist(inputObject, outputObject);
	}
	
	/**
	 * 发布
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/TsyScillor/updateTsyScillorPicFb")
	@ResponseBody
	public void updateTsyScillorPicFb(InputObject inputObject,OutputObject outputObject) throws Exception {
		scillorPicService.updateTsyScillorPicFb(inputObject, outputObject);
	}

	/**
	 * 跳转到列表
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/scillorpic/SelectAll")
	public String SelectAll(Model model, HttpServletRequest request) {

		ListSortUtil<TsyScillorPic> sort = new ListSortUtil<TsyScillorPic>();
		List<TsyScillorPic> beans = scillorPicService.SelectAll();
		sort.sort(beans,"scollorPicDisplay","desc");
		model.addAttribute("size", beans.size());
		model.addAttribute("up_size", sort.List_Size(beans));
		model.addAttribute("scillorpiclist", beans);

		return "jsp/scillorpic/scillorpiclist";
	}

	/**
	 * 跳转到添加界面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/scillorpic/ToAddPage")
	public String ToAddPage(Model model, HttpServletRequest request) {
		return "jsp/scillorpic/scillorpicadd";
	}

	/**
	 * 跳转到修改界面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/scillorpic/ToUpdatePage")
	public String ToUpdatePage(Model model, int id, HttpServletRequest request) {
		model.addAttribute("item", scillorPicService.selectByPrimaryKey(id));
		return "jsp/scillorpic/scillorpicupdate";
	}

	/**
	 * 添加内容到数据库
	 * 
	 * @param model
	 * @param request
	 * @param img
	 * @param aa
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/scillorpic/AddToSql")
	public String AddToSql(HttpServletRequest request, @RequestParam("img") MultipartFile img, TsyScillorPic bean, HttpSession session) throws Exception {
		String scollorPicPath = UpLoadImgUtil.UploadImg(request, UpLoadImgUtil.MultipartFileToFile(img));
		if (scollorPicPath == null || scollorPicPath.equals("")) {
			// 图片上传失败
		} else {
			// 上传成功 获取当前用户
			UserBean user = (UserBean) session.getAttribute("user");
			// 设置参数
			bean.setScollorPicPath(scollorPicPath);// 设置图片路径
			bean.setScollorPicDisplay(0);// 未上线
			bean.setScollorPicUserid(user.getId());// 添加人ID
			bean.setScollorPicData(DateUtil.getTimeAndToString());// 添加日期
			if (scillorPicService.add(bean) == 1) {
				// 添加成功
			} else {
				// 添加失败,则删除之前上传的图片
				UpLoadImgUtil.DeleteImg(request, scollorPicPath);
			}
		}
		return "jsp/scillorpic/scillorpicadd";
	}
	
	/**
	 * 修改内容到数据库
	 * 
	 * @param request
	 * @param img
	 * @param bean
	 * @param session
	 * @return
	 */
	@RequestMapping("/scillorpic/UpdateToSql")
	public String UpdateToSql(Model model, HttpServletRequest request, TsyScillorPic bean, HttpSession session) {
		if (scillorPicService.update(bean) == 1) {
			// 修改成功
			model.addAttribute("tishi", "修改成功");
		} else {
			// 修改失败
			model.addAttribute("tishi", "修改失败");
		}
		return "redirect:/scillorpic/ToUpdatePage?id=" + bean.getId();
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/scillorpic/DeleteItem")
	public void DeleteItem(int id, String img_path, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("message", Constants.ERROR);
		if (scillorPicService.delete(id) == 1) {
			if (UpLoadImgUtil.DeleteImg(request, img_path)) {
				pramers.put("message", Constants.DELETE_SUCCESS);
			} else {
				pramers.put("message", Constants.DELETE_SUCCESS_CANLIU);// 删除成功，但是有图片数据残留
			}
		} else {
			pramers.put("message", Constants.DELETE_ERROR);
		}
		/** 输出到前台 */
		JsonUtil.ToJson(response, pramers);
	}

	/**
	 * 状态切换--上下线切换
	 * 
	 * @param id
	 * @param img_path
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/scillorpic/UpdateItemZhuangtai")
	public void UpdateItemZhuangtai(int id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 返回前台的信息
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		//上下线切换
		TsyScillorPic item = scillorPicService.selectByPrimaryKey(id);
		if (item.getScollorPicDisplay() == 0) {
			pramers.put("message", Constants.KNOWLEDGE_UP_SUCCESS);
			item.setScollorPicDisplay(1);
			scillorPicService.update(item);
		} else {
			pramers.put("message", Constants.KNOWLEDGED_DOWN_SUCCESS);
			item.setScollorPicDisplay(0);
			scillorPicService.update(item);
		}
		/** 输出到前台 */
		JsonUtil.ToJson(response, pramers);
	}

}

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

import com.ssm.tsy.bean.NewsBean;
import com.ssm.tsy.object.InputObject;
import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.service.NewsService;
import com.ssm.tsy.util.Constants;
import com.ssm.tsy.util.JsonUtil;
import com.wechat.service.NewService;
import com.wechat.service.NewsGuojiService;
import com.wechat.service.NewsKejiService;
import com.wechat.service.NewsPingguoService;
import com.wechat.service.NewsQiwenqushiService;
import com.wechat.service.NewsShehuiService;
import com.wechat.service.NewsShenghuojiankang;
import com.wechat.service.NewsTiyuService;
import com.wechat.service.NewsYuleService;

@Controller
public class NewsController {

	@Resource
	private NewsService newsService;
	
	/**
	 * 根据内容查询数据
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/news/queryMyList")
	@ResponseBody
	public void queryMyList(InputObject inputObject,OutputObject outputObject) throws Exception{
		newsService.queryMyList(inputObject, outputObject);
	}
	
	/**
	 * 下拉框数据填充
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/news/queryRealType")
	@ResponseBody
	public void queryRealType(InputObject inputObject,OutputObject outputObject) throws Exception{
		newsService.queryRealType(inputObject, outputObject);
	}

	/**
	 * 跳转到新闻列表界面
	 * 
	 * @return
	 */
	@RequestMapping("news/ToListPage")
	public ModelAndView ToListPage() {
		ModelAndView mav = new ModelAndView();
		List<NewsBean> news_list = NewService.Json();
		mav.addObject("newslist", news_list);
		mav.addObject("size", news_list.size());
		mav.setViewName("jsp/news/newslist");
		return mav;
	}
	
	/**
	 * 跳转到科技新闻列表界面
	 * 
	 * @return
	 */
	@RequestMapping("news/ToKejiListPage")
	public ModelAndView ToKejiListPage() {
		ModelAndView mav = new ModelAndView();
		List<NewsBean> news_list = NewsKejiService.json();
		mav.addObject("newslist", news_list);
		mav.addObject("size", news_list.size());
		mav.setViewName("jsp/news/newsKejilist");
		return mav;
	}
	
	/**
	 * 跳转到娱乐新闻列表界面
	 * 
	 * @return
	 */
	@RequestMapping("news/ToYuleListPage")
	public ModelAndView ToYuleListPage() {
		ModelAndView mav = new ModelAndView();
		List<NewsBean> news_list = NewsYuleService.json();
		mav.addObject("newslist", news_list);
		mav.addObject("size", news_list.size());
		mav.setViewName("jsp/news/newsYulelist");
		return mav;
	}
	
	/**
	 * 跳转到国际新闻列表界面
	 * 
	 * @return
	 */
	@RequestMapping("news/ToGuojiListPage")
	public ModelAndView ToGuojiListPage() {
		ModelAndView mav = new ModelAndView();
		List<NewsBean> news_list = NewsGuojiService.json();
		mav.addObject("newslist", news_list);
		mav.addObject("size", news_list.size());
		mav.setViewName("jsp/news/newsGujilist");
		return mav;
	}
	
	/**
	 * 跳转到苹果新闻列表界面
	 * 
	 * @return
	 */
	@RequestMapping("news/ToAppleListPage")
	public ModelAndView ToAppleListPage() {
		ModelAndView mav = new ModelAndView();
		List<NewsBean> news_list = NewsPingguoService.json();
		mav.addObject("newslist", news_list);
		mav.addObject("size", news_list.size());
		mav.setViewName("jsp/news/newsPingguolist");
		return mav;
	}
	
	/**
	 * 跳转到奇闻趣事列表界面
	 * 
	 * @return
	 */
	@RequestMapping("news/ToQiwenqushuListPage")
	public ModelAndView ToQiwenqushiListPage() {
		ModelAndView mav = new ModelAndView();
		List<NewsBean> news_list = NewsQiwenqushiService.json();
		mav.addObject("newslist", news_list);
		mav.addObject("size", news_list.size());
		mav.setViewName("jsp/news/newsQiwenqushilist");
		return mav;
	}
	
	/**
	 * 跳转到社会新闻列表界面
	 * 
	 * @return
	 */
	@RequestMapping("news/ToShehuiListPage")
	public ModelAndView ToShehuiListPage() {
		ModelAndView mav = new ModelAndView();
		List<NewsBean> news_list = NewsShehuiService.json();
		mav.addObject("newslist", news_list);
		mav.addObject("size", news_list.size());
		mav.setViewName("jsp/news/newsShehuilist");
		return mav;
	}
	
	/**
	 * 跳转到生活健康列表界面
	 * 
	 * @return
	 */
	@RequestMapping("news/ToShenghuoListPage")
	public ModelAndView ToShenghuoListPage() {
		ModelAndView mav = new ModelAndView();
		List<NewsBean> news_list = NewsShenghuojiankang.json();
		mav.addObject("newslist", news_list);
		mav.addObject("size", news_list.size());
		mav.setViewName("jsp/news/newsShenghuolist");
		return mav;
	}
	
	/**
	 * 跳转到体育新闻列表界面
	 * 
	 * @return
	 */
	@RequestMapping("news/ToTiyuListPage")
	public ModelAndView ToTiyuListPage() {
		ModelAndView mav = new ModelAndView();
		List<NewsBean> news_list = NewsTiyuService.json();
		mav.addObject("newslist", news_list);
		mav.addObject("size", news_list.size());
		mav.setViewName("jsp/news/newsTiyulist");
		return mav;
	}

	/**
	 * 我的新闻管理
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("news/MyList")
	public ModelAndView MyList() throws Exception {
		ModelAndView mav = new ModelAndView();
		List<NewsBean> news_list = newsService.SelectAll();
		mav.addObject("newslist", news_list);
		mav.addObject("size", news_list.size());
		mav.setViewName("jsp/news/mylist");
		return mav;
	}

	/**
	 * 添加新闻
	 * @throws Exception 
	 */
	@RequestMapping("news/AddNewsBean")
	public void AddNewsBean(NewsBean bean, HttpServletResponse response) throws Exception {
		Map<String, Object> pramers = new HashMap<String, Object>();
		pramers.put("success", true);
		pramers.put("message", Constants.ERROR);
		bean.setFasongdata("");
		bean.setShifoufasong(0);
		if (newsService.Add(bean) > 0) {
			pramers.put("message", Constants.ADD_SUCCESS);
		} else {
			pramers.put("message", Constants.ADD_ERROR);
		}
		/** 输出到前台 */
		JsonUtil.ToJson(response, pramers);
	}
}

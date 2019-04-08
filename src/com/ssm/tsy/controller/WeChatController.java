package com.ssm.tsy.controller;

import java.io.PrintWriter;
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
import com.ssm.tsy.bean.WeChatUser;
import com.ssm.tsy.service.WeChatAPPService;
import com.ssm.tsy.service.WeChatService;
import com.ssm.tsy.util.SignUtil;
import com.ssm.util.TokenThread;
import com.wechat.service.GetUserMationService;

@Controller
public class WeChatController {

	@Resource
	private WeChatService weChatService;

	@Resource
	private WeChatAPPService weChatAPPService;

	/**
	 * 微信提交接收
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("wechat/WechatPost")
	public void WechatPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("进入校验方法");
		/**
		 * 1.确认请求来自微信服务器
		 */
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		  System.out.println("signature:" + signature);
          System.out.println("timestamp:" + timestamp);
		  System.out.println("nonce:" + nonce);
		  System.out.println("echostr:" + echostr);
		  PrintWriter out = response.getWriter();
		System.out.println("如果返回的echostr和timestamp相同，则接入成功，若校验成功则原样返回echostr，表示接入成功，否则接入失败");
		// 通过检验signature对请求进行校验
		if (SignUtil.checkSignature(signature, timestamp, nonce))
			System.out.println("接入成功："+echostr);
			out.print(echostr);//原样返回 
		/**
		 * 2.处理微信服务器发来的消息
		 */
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 调用核心业务类接收消息、处理消息
		String respMessage = weChatService.WechatReply(request);
		// 响应消息
		PrintWriter out1 = response.getWriter();
		out1.print(respMessage);
		out1.close();

	}

	/**
	 * 查询所有微信关注人
	 * 
	 * @param response
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("wechat/SelectAll")
	public ModelAndView SelectAll(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		UserBean user = (UserBean) session.getAttribute("user");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("appId", user.getAppid());
		WeChatAPP bean_list = weChatAPPService.Select(map);
		// 判断微信appid是否添加
		if (bean_list != null) {
			List<WeChatUser> weChat_list = weChatService.SelectAll();// 查询出来所有的列表菜单
			// 判断内容是否为空
			if (weChat_list == null || weChat_list.size() == 0) {
				mav.addObject("size", 0);
			} else {
				mav.addObject("size", weChat_list.size());
				mav.addObject("weChatlist", weChat_list);
			}
			mav.setViewName("jsp/wechat/wechatuserlist");
		} else {
			mav.setViewName("jsp/wechat/wechaterror");
		}
		return mav;
	}

	/**
	 * 检索更新用户
	 * 
	 * @return
	 */
	@RequestMapping("wechat/Jiansuo")
	public ModelAndView Jiansuo(HttpSession session) {

		ModelAndView mav = new ModelAndView();
		UserBean user = (UserBean) session.getAttribute("user");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("appId", user.getAppid());
		WeChatAPP bean_list = weChatAPPService.Select(map);
		// 判断微信appid是否添加
		if (bean_list != null) {
			String accessToken = TokenThread.accessToken.getToken();
			// 1.查出所有已经关注的用户的标识符
			List<String> opendids = weChatService.getAllWeiXinUser(accessToken);
			for (int i = 0; i < opendids.size(); i++) {
				// 判断该用户是否存在数据库
				if (weChatService.SelectByOpenId(opendids.get(i)) == null) {
					// 获取关注用户信息
					WeChatUser bean = GetUserMationService.getRequest1(opendids.get(i), TokenThread.accessToken.getToken());
					// 判断是否取到了用户信息
					if (bean == null) {
					} else {
						weChatService.AddWeChat(bean);
					}
				} else {
				}
			}
			mav.setViewName("redirect:/wechat/SelectAll");
		} else {
			mav.setViewName("jsp/wechat/wechaterror");
		}
		return mav;
	}

	/**
	 * 根据昵称查询
	 * 
	 * @return
	 */
	@RequestMapping("wechat/SelectAllByNickName")
	public ModelAndView SelectAllByNickName(String nickname) {

		// 查询出来所有的列表菜单
		List<WeChatUser> weChat_list = weChatService.SelectAllByNickName(nickname);
		ModelAndView mav = new ModelAndView();
		// 判断内容是否为空
		if (weChat_list == null || weChat_list.size() == 0) {
			mav.addObject("size", 0);
		} else {
			mav.addObject("size", weChat_list.size());
			mav.addObject("weChatlist", weChat_list);
		}
		mav.setViewName("jsp/wechat/wechatuserlist");
		return mav;
	}

	/**
	 * 1.冻结用户 2.解除冻结
	 * 
	 * @return
	 */
	@RequestMapping("wechat/DongjieWeChatUser")
	public ModelAndView DongjieWeChatUser(String openid, int cs, Model model) {
		// 获取关注人信息
		WeChatUser bean = weChatService.SelectByOpenId(openid);
		if (bean != null) {
			if (cs == 0) {
				// 解除冻结
				bean.setFrozen(1);
				weChatService.UpdateWeChat(bean);
				model.addAttribute("tishi", "昵称为[" + bean.getNickname() + "]用户成功解除冻结！");
			} else {
				// 冻结用户
				bean.setFrozen(0);
				weChatService.UpdateWeChat(bean);
				model.addAttribute("tishi", "昵称为[" + bean.getNickname() + "]用户冻结成功！");
			}
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/wechat/SelectAll");
		return mav;
	}
}

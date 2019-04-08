package com.ssm.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ssm.tsy.bean.UserBean;

public class SessionFilter implements Filter {
	
	private static final String EXIT = "/tsy/login/user/exit";//退出
	private static final String LOGIN_JUDEG = "/tsy/login/user/loginCheck";//登陆判断
	private static final String LOGIN = "/tsy/login/user/login";//登陆
	
	private static final String WECHAT_POST = "/tsy/wechat/WechatPost";//微信请求--直接通过请求
	
	private static final String LOGIN_PAGE = "/tsy/loginJudge.jsp";//登陆界面
	
	private static final String ADM_TSY = "/admTsy";
	
	private static final String WECHAT_PHONE = "/wechatPhone";
	
	private static final String POST_ADM_TSY = "/post/admTsy";
	
	public void init(FilterConfig arg0) throws ServletException {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 获得在下面代码中要用的request,response,session对象
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = servletRequest.getSession();
		String url = servletRequest.getContextPath() + servletRequest.getServletPath();
		UserBean user = (UserBean) session.getAttribute("user");
		if (EXIT.equals(url)) {
			chain.doFilter(request, response);
		} else if (WECHAT_POST.equals(url)) {
			chain.doFilter(request, response);
		} else if (url.indexOf(ADM_TSY)!=-1||url.indexOf(POST_ADM_TSY)!=-1||url.indexOf(WECHAT_PHONE)!=-1) {
			chain.doFilter(request, response);
		} else {
			if (user == null) {
				if (LOGIN_JUDEG.equals(url)
						|| LOGIN.equals(url)
						|| url.contains(".css") || url.contains(".js")
						|| url.contains(".png") || url.contains(".jpg")
						|| url.contains(".ico") || url.contains(".gif")
						|| url.contains(".eot") || url.contains(".svg")
						|| url.contains(".ttf") || url.contains(".woff")
						|| url.contains(".woff2") || url.contains(".exe")
						|| url.contains(".mp4") || url.contains(".rmvb")
						|| url.contains(".avi") || url.contains(".3gp")
						|| EXIT.equals(url)) {
					chain.doFilter(request, response);
				} else {
					servletResponse.sendRedirect(LOGIN_PAGE);
				}
			} else {// 如果用户不为空，放行
				try {
					chain.doFilter(request, response);
				} catch (Exception e) {
					session.removeAttribute("user");
					servletResponse.sendRedirect(LOGIN_PAGE);
				}
			}
		}
	}

	public void destroy() {
		System.out.println("销毁");
	}

}

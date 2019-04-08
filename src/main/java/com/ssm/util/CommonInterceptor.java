package com.ssm.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ssm.tsy.bean.UserBean;


public class CommonInterceptor extends HandlerInterceptorAdapter {
    private static final String[] IGNORE_URI = {"/login.jsp", "/Login/","/user/loginCheck","/user/insert"};
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        String url = request.getRequestURL().toString();
        System.out.println(">>>: " + url);
        for (String s : IGNORE_URI) {
            if (url.contains(s)) {
            	System.out.println("非拦截");
                flag = true;
                break;
            }
        }
        if (!flag) {
        UserBean user = (UserBean) request.getSession().getAttribute("user");
        if(user == null){
        	System.out.println(user);
        	request.getRequestDispatcher("/login.jsp").forward(request, response);
        	return false;
        }
        }
        return true;
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}

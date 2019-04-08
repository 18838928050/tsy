package com.ssm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.*;

import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.object.PutObject;
import com.ssm.tsy.util.Constants;


public class HandlerInterceptorMain implements HandlerInterceptor{

	//在进入Handler方法之前执行  
	//用于身份认证、身份授权、  
	//比如身份认证，如果认证不通过表示当前用户没有登录，需要此方法拦截不再向下执行
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
    	new PutObject(request,response);
    	if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
        	//如果是ajax请求响应头会有x-requested-with
    		return true;
        }else{
            //非ajax请求时
        	return true;
        }
    }

    //进入Handler方法之后，在返回ModelAndView之前执行  
    //应用场景从modelAndView对象出发：将公用的模型数据(比如菜单的导航)在这里传到视图，也可以在这里面来统一指定视图
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
        	//如果是ajax请求响应头会有x-requested-with
//        	if(OutputObject.getCode().equals(Constants.WRONG)){
//        		OutputObject.setCode(0);
//            	OutputObject.setMessage("成功");
//        	}
        	OutputObject.put();
        	PutObject.removeRequest();
        }else{
        	//非ajax请求时
//        	if(OutputObject.getCode().equals(Constants.WRONG)){
//        		OutputObject.setCode(0);
//            	OutputObject.setMessage("成功");
//        	}
        	OutputObject.put();
        	PutObject.removeRequest();
        }
    }

    //执行Handler完成后执行此方法  
  	//应用场景：统一的异常处理，统一的日志处理  
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}

package com.ssm.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.ssm.tsy.object.OutputObject;
import com.ssm.tsy.util.JsonUtil;
import com.ssm.tsy.util.ToolUtil;

public class CustomExceptionResolver implements HandlerExceptionResolver{
	
	private static final String[] str = {"DataAccessException","NullPointerException","IOException","ClassNotFoundException",
		"ArithmeticException","ArrayIndexOutOfBoundsException","IllegalArgumentException","ClassCastException","SecurityException","SQLException"
		,"NoSuchMethodError","InternalError","InvocationTargetException","Exception"};
	
	private static final String[] strMessage = {"数据库操作失败","调用了未经初始化的对象或者是不存在的对象","IO异常","指定的类不存在",
		"数学运算异常","数组下标越界","方法的参数错误","类型强制转换错误","违背安全原则异常","操作数据库异常"
		,"方法末找到异常","Java虚拟机发生了内部错误","程序内部错误，操作失败","程序内部错误，操作失败"};

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception) {
		String excetionType = null;
		if(exception != null) {
			exception.printStackTrace();
		}
		CustomException customException = null;
		if(exception instanceof CustomException){
			customException = (CustomException)exception;
		}else{
			excetionType =  exception.getClass().toString().substring(exception.getClass().toString().lastIndexOf(".")+1);
			if(ToolUtil.contains(str, excetionType)>-1){
				customException = new CustomException("错误提示:"+strMessage[ToolUtil.contains(str, excetionType)]+"<br/>错误类型:"+excetionType);
			}else{
				customException = new CustomException("错误提示:数据操作失败!!!"+"<br/>错误类型:"+excetionType);
			}
		}
		
		// 判断是否ajax请求
        if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
                .getHeader("X-Requested-With") != null && request.getHeader(
                "X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
            // 如果不是ajax，JSP格式返回
        	//错误信息
    		String message = customException.getMessage();
    		ModelAndView modelAndView = new ModelAndView();
    		//将错误信息传到页面
    		modelAndView.addObject("message", message);
    		//指向错误页面
    		modelAndView.setViewName("error");
    		return modelAndView;
        } else {
            // 如果是ajax请求，JSON格式返回
        	String message = customException.getMessage();
        	OutputObject outputObject = new OutputObject();
        	outputObject.setreturnCode("-9999");
        	outputObject.setreturnMessage(message);
        	try {
				JsonUtil.ToJson(response, outputObject.getObject());
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
		return null;

	}

}

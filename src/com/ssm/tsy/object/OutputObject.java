package com.ssm.tsy.object;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;


public class OutputObject extends PutObject{
	
	public static int total;
	public static Map<String,Object> bean;
	public static List<Map<String,Object>> beans;
	public static Map<String,Object> object;
	public static Object returnMessage;
	public static Object returnCode;
	
	
	public OutputObject(){
		bean = new HashMap<String, Object>();
		beans = new ArrayList<Map<String,Object>>();
		object = new HashMap<String, Object>();
		setNull();
	}

	public void settotal(int total){
		OutputObject.object.put("total",total);
	}
	
	public void setreturnMessage(Object returnMessage){
		OutputObject.object.put("returnMessage",returnMessage);
	}
	
	public void setreturnCode(Object returnCode){
		OutputObject.object.put("returnCode",returnCode);
	}
	
	public void setBean(Map<String,Object> bean) throws Exception {
		OutputObject.object.put("bean",bean);
	}
	
	public void setBeans(List<Map<String,Object>> beans) throws Exception {
		OutputObject.object.put("rows",beans);
	}
	
	public void setLogParams(Map<String,Object> login) throws Exception {
		getRequest().getSession().setAttribute("admTsyUser", login);
	}
	
	public static void setCode(int code){
		OutputObject.object.put("returnCode",code);
	}
	
	public static void setMessage(String Message){
		OutputObject.object.put("returnMessage",Message);
	}
	
	public static String getCode(){
		return OutputObject.object.get("returnCode").toString();
	}
	
	public static String getMessage(){
		return OutputObject.object.get("returnMessage").toString();
	}
	
	public static void put(){
		Object context = JSON.toJSON(object);
		PrintWriter out = null;
		getResponse().setCharacterEncoding("UTF-8");
		getResponse().setContentType("text/html;charset=UTF-8");
		try {
			out = getResponse().getWriter();// 获取输入流
			out.print(context);// 将信息发送到前台
			out.flush();// 刷新
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();// 关闭输入流
		}
	}
	
	public static void setNull(){
		object.put("returnCode","-9999");
		object.put("returnMessage","失败");
		object.put("total",0);
		object.put("bean","");
		object.put("rows","");
	}

	public Map<String, Object> getBean() {
		return bean;
	}
	public List<Map<String,Object>> getBeans() {
		return beans;
	}
	public Map<String, Object> getObject() {
		return object;
	}

	
}

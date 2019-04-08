package com.ssm.tsy.util;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

public class JsonUtil {

	/**
	 * 将map集合转成json输出到前台
	 * 
	 * @param response
	 * @param pramers
	 */
	public static void ToJson(HttpServletResponse response, Map<String, Object> pramers) throws Exception {
		JSON context = JSONSerializer.toJSON(pramers);
		PrintWriter out = null;
		// 指定返回值的编码方式，必须放在out声明之前
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		out = response.getWriter();// 获取输入流
		out.print(context);// 将信息发送到前台
		out.flush();// 刷新
		out.close();// 关闭输入流
	}
	
	/**
	 * 将list集合转成json
	 * 
	 * @param response
	 * @param pramers
	 */
	public static String getJsonStrFromList(List<?> pramers) throws Exception {
		JSON context = JSONSerializer.toJSON(pramers);
		return context.toString();
	}
}

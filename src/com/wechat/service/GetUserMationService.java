package com.wechat.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.ssm.tsy.bean.WeChatUser;
import com.ssm.tsy.util.TransUtil;

import net.sf.json.JSONObject;

public class GetUserMationService {

	/**
	 * 查看用户信息
	 **/
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	public static WeChatUser getRequest1(String openid, String access_token) {
		WeChatUser bean = null;
		String result = null;
		String url = "https://api.weixin.qq.com/cgi-bin/user/info";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("access_token", access_token);// 调用接口凭证
		params.put("openid", openid);// 普通用户的标识，对当前公众号唯一
		params.put("lang", "zh_CN");// 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语

		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			
			int sex = Integer.parseInt(object.getString("sex"));
			String nickname = object.getString("nickname");
//			String remark = object.getString("remark");
			String city = object.getString("city");
			String country = object.getString("country");
			String subscribe_time = TransUtil.WeChatData(object.getString("subscribe_time"));
			String subscribe = object.getString("subscribe");
			String province = object.getString("province");
			int groupid = Integer.parseInt(object.getString("groupid"));
			String headimgurl = object.getString("headimgurl");
			bean = new WeChatUser(openid, nickname, city, country,
					subscribe_time, sex, province, groupid, headimgurl,
					subscribe, "0", 0, 1,0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 
	 * @param strUrl
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param method
	 *            请求方法
	 * @return 网络请求字符串
	 * @throws Exception
	 */
	public static String net(String strUrl, Map<String, Object> params, String method)
			throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			if (method == null || method.equals("GET")) {
				strUrl = strUrl + "?" + urlencode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if (method == null || method.equals("GET")) {
				conn.setRequestMethod("GET");
			} else {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			if (params != null && method.equals("POST")) {
				try {
					DataOutputStream out = new DataOutputStream(
							conn.getOutputStream());
					out.writeBytes(urlencode(params));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}

	// 将map型转为请求参数型
	@SuppressWarnings("rawtypes")
	public static String urlencode(Map<String, Object> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=")
						.append(URLEncoder.encode(i.getValue() + "", "UTF-8"))
						.append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	// public static void main(String[] args) {
	// getRequest1();
	// }
}

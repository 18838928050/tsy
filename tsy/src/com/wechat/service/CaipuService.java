package com.wechat.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.ssm.tsy.bean.wechat.ResponseArticle;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CaipuService {

	/**
	 * 
	 * 菜谱分类
	 * 
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request() {
		String httpUrl = "http://apis.baidu.com/tngou/cook/classify";
		String httpArg = "id=0";

		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey",
					"e0e023e4a535563f76697782c9668503");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * 菜谱详情
	 * 
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request1() {
		String httpUrl = "http://apis.baidu.com/tngou/cook/show";
		String httpArg = "id=10";
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey",
					"e0e023e4a535563f76697782c9668503");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * 菜谱列表
	 * 
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request2() {
		String httpUrl = "http://apis.baidu.com/tngou/cook/list";
		String httpArg = "id=0&page=1&rows=20";
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey",
					"e0e023e4a535563f76697782c9668503");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * 菜谱名称-------菜的佳肴做法
	 * 
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request3(String name) {
		String httpUrl = "http://apis.baidu.com/tngou/cook/name";
		String httpArg = "name=" + name;
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey",
					"e0e023e4a535563f76697782c9668503");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * 解析------------菜谱名称-------菜的佳肴做法
	 * 
	 * @return
	 */
	public static List<ResponseArticle> JsonreQuest3(String name) {
		List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();
		ResponseArticle article1;
		article1 = new ResponseArticle();
		article1.setTitle(name);
		article1.setDescription("出状况啦，程序猿正在努力解决问题。。。");
		articleList.add(article1);

		String result = request3(name);
		JSONObject data = JSONObject.fromObject(result);
		JSONArray res = data.getJSONArray("tngou");

		for (int i = 0; i < res.size(); i++) {
			JSONObject bean = res.getJSONObject(i);
			// System.out.println("食材：" + bean.getString("food"));
			// System.out.println("信息：" + bean.getString("message"));
			ResponseArticle article = new ResponseArticle();
			article.setTitle("食材：" + bean.getString("food") + "\n信息："
					+ bean.getString("message"));
			article.setDescription("");
			article.setPicUrl("http://tnfs.tngou.net/image"
					+ bean.getString("img") + "_80x80");
			articleList.add(article);
		}
		// System.out.println(name + res.size() + "############"
		// + articleList.size());

		return articleList;
	}

	// public static void main(String[] args) {
	// JsonreQuest3("宫保鸡丁");
	// }

}

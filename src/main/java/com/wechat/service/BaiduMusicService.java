package com.wechat.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.ssm.tsy.bean.wechat.ResponseMusic;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/** 
 * 百度音乐搜索API操作类
 * 
 */
public class BaiduMusicService {

	/**
	 * 接口地址 http://apistore.baidu.com/apiworks/servicedetail/1020.html
	 */

	/**
	 * 百度音乐搜索API操作类
	 */
	public static String httpUrl = "http://apis.baidu.com/geekery/music/query";
	// 配置您申请的KEY
	public static final String APPKEY = "e0e023e4a535563f76697782c9668503";

	/**
	 * @param urlAll
	 *            :请求接口
	 * @param musicname
	 *            :参数音乐名称
	 * @param size
	 *            :参数返回条数 10
	 * @param page
	 *            :参数分页1
	 * @return 返回结果
	 * 
	 * @调用函数 DateUtil.ascii2native(String theString)
	 * 
	 * @获取歌曲信息
	 */
	public static String request(String musicname, int size, int page) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		String httpArg = "s=" + musicname + "&size=" + size + "&page=" + page;
		httpUrl = httpUrl + "?" + httpArg;
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", APPKEY);
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
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 * 
	 * @获取歌曲播放地址
	 */
	public static String request1(String hash) {

		String httpUrl = "http://apis.baidu.com/geekery/music/playinfo";
		String httpArg = "hash=" + hash;

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
			connection.setRequestProperty("apikey", APPKEY);
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
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 * 
	 * @获取歌手信息
	 */
	public static String request2(String single) {

		String httpUrl = "http://apis.baidu.com/geekery/music/singer";
		String httpArg = "name=" + single;
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
			connection.setRequestProperty("apikey", APPKEY);
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
	 * // 音乐名称 private String Title; // 音乐描述 private String Description; // 音乐链接
	 * private String MusicUrl; // 高质量音乐链接，WIFI环境优先使用该链接播放音乐 private String
	 * HQMusicUrl;
	 */
	public static ResponseMusic json(String musicname) {

		ResponseMusic bean = new ResponseMusic();

		String jsonResult = request(musicname, 1, 1);

		JSONObject data = JSONObject.fromObject(jsonResult);
		System.out.println(data+musicname);
		if (data.get("status").equals("success")) {
			JSONArray music_list = data.getJSONObject("data").getJSONArray("data");
			String title = music_list.getJSONObject(0).getString("filename");//获取歌曲名称
			String single = music_list.getJSONObject(0).getString("singername");//获取歌手
			String hash = music_list.getJSONObject(0).getString("hash");//获取歌手
			System.out.println(single);
			//获取歌手图片
			String singlemation = request2(single);
			System.out.println(singlemation);
			JSONObject singleobject = JSONObject.fromObject(singlemation);
			String singleimg = singleobject.getJSONObject("data").getString("image");
			
			//获取歌曲链接
			String titlemation = request1(hash);
			System.out.println(titlemation);
			JSONObject titleobject = JSONObject.fromObject(titlemation);
			String titleurl = titleobject.getJSONObject("data").getString("url");
			
			if(title!=null&&singleimg!=null&&titleurl!=null){
				bean.setTitle(title);
				bean.setDescription(singleimg);
				bean.setMusicUrl(titleurl);
				bean.setHQMusicUrl(titleurl);
			}
			
		}

		return bean;

	}

	public static void main(String[] args) {
		
		//System.out.println(request2("汪峰"));
		//System.out.println(request1("cf9edc16e8a5971e761bf9fd3e7d8ba2"));
		ResponseMusic bean = json("十年");
		System.out.println(bean.getTitle());
		System.out.println(bean.getDescription());
		System.out.println(bean.getHQMusicUrl());
		System.out.println(bean.getMusicUrl());
	}
}

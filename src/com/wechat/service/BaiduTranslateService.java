package com.wechat.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


public class BaiduTranslateService {

	// 申请者开发者id，实际使用开发者自己的appid
	private static final String appId = "20160331000017282";

	// 申请成功后的证书token，实际使用开发者自己的token
	private static final String token = "p5EOoujMeffSMSJ01PsB";

	private static final String url = "http://api.fanyi.baidu.com/api/trans/vip/translate";

	private static final String UTF8 = "utf-8";

	// 随机数，用于生成md5值，开发者使用时请激活下边第四行代码
	private static final Random random = new Random();

	/**
	 * 翻译（中->英 英->中 日->中 ）
	 * 
	 * @param source
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws JSONException
	 */
	public static String translate(String source)
			throws ClientProtocolException, IOException, JSONException {
//		// 本演示使用指定的随机数为new Date().getTime()
//		long salt = new Date().getTime();
		// 用于md5加密
		int salt = random.nextInt(10000);
		// 对appId+源文+随机数+token计算md5值
		StringBuilder md5String = new StringBuilder();
		md5String.append(appId).append(source).append(salt).append(token);
		String md5 = DigestUtils.md5Hex(md5String.toString());
		// 使用Post方式，组装参数
		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("q", source));
		nvps.add(new BasicNameValuePair("from", "auto"));
		nvps.add(new BasicNameValuePair("to", "auto"));
		nvps.add(new BasicNameValuePair("appid", appId));
		nvps.add(new BasicNameValuePair("salt", String.valueOf(salt)));
		nvps.add(new BasicNameValuePair("sign", md5));
		httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

		// 创建httpclient链接，并执行
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = httpclient.execute(httpost);

		// 对于返回实体进行解析
		HttpEntity entity = response.getEntity();
		InputStream returnStream = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(returnStream, UTF8));
		StringBuilder result = new StringBuilder();
		String str = null;
		while ((str = reader.readLine()) != null) {
			result.append(str).append("\n");
		}

		// 转化为json对象，注：Json解析的jar包可选其它
		JSONObject resultJson = new JSONObject(result.toString());

		// 开发者自行处理错误，本示例失败返回为null
		try {
			String error_code = resultJson.getString("error_code");
			if (error_code != null) {
				System.out.println("出错代码:" + error_code);
				System.out.println("出错信息:" + resultJson.getString("error_msg"));
				return null;
			}
		} catch (Exception e) {
		}

		// 获取返回翻译结果
		JSONArray array = (JSONArray) resultJson.get("trans_result");
		JSONObject dst = (JSONObject) array.get(0);
		String text = dst.getString("dst");
		text = URLDecoder.decode(text, UTF8);

		return text;
	}

	// 实际抛出异常自己处理
	@SuppressWarnings("static-access")
	public static String translateToEn(String q, HttpServletRequest request) throws Exception {
		String f1 = "xml//" + "baidu.xml";
		String path = request.getSession().getServletContext().getRealPath(f1);
		System.out.println(path);
		ApplicationContext container = new FileSystemXmlApplicationContext(path);
		BaiduTranslateService baidu = (BaiduTranslateService) container.getBean("baidu");
		String result = null;
		try {
			result = baidu.translate(q);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// public static void main(String[] args) throws Exception {
	// String source = "苹果";
	// String result = BaiduTranslateService.translate(source);
	// if(result == null){
	// System.out.println("翻译出错，参考百度错误代码和说明。");
	// return;
	// }
	// System.out.println(source + "：" + result);
	// }
}

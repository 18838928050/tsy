package com.wechat.service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.ssm.tsy.util.JsonUtil;
import com.ssm.tsy.util.JudgeUtil;
import com.ssm.tsy.util.WeixinUtil;
import com.ssm.util.TokenThread;


public class SendMessageService {
	
	//这个地址是根据分组id来群发消息  
//	private String groupUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
//	//这个地址是根据openid来群发消息
//	private String groupUrl1 = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
//
//	//这个是通过分组id发送的普通文本消息
//	private String group1data = "{\"filter\":{\"is_to_all\":false,\"group_id\":\"2\"},\"text\":{\"content\":\"群发消息测试\"},\"msgtype\":\"text\"}\";";
//	//这个是通过openid发送的普通文本消息 
//	private String openid1data = "{\"touser\":[\"<span style='color:#FF0000;'>obGXiwHTGN_4HkR2WToFj_3ua</span>\",\"<span style='color:#FF0000;'>obGXiwNu0z2o_RRWaODvaZctd</span>\"],\"msgtype\": \"text\",\"text\": {\"content\": \"测试文本消息\"}}"; 


	/** 
     * 模拟form表单的形式 ，上传文件 以输出流的形式把文件写入到url中，然后用输入流来获取url的响应  获取media_id
     * @param url 
     *            请求地址 form表单url地址 
     * @param filePath 
     *            文件在服务器保存路径 
     * @return String url的响应信息返回值 
     * @throws IOException 
     */  
    public static String send(String url, String filePath) throws IOException {  
		String result = null;  
		File file = new File(filePath);  
		if (!JudgeUtil.isNetFileAvailable(filePath)&&(!file.isFile()||!file.exists())) {  
		    throw new IOException("文件不存在");  
		}  
		/** 
		 * 第一部分 
		 */  
		URL urlObj = new URL(url);  
		// 连接  
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();  
		/** 
		 * 设置关键值 
		 */  
		con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式  
		con.setDoInput(true);  
		con.setDoOutput(true);  
		con.setUseCaches(false); // post方式不能使用缓存  
		// 设置请求头信息  
		con.setRequestProperty("Connection", "Keep-Alive");  
		con.setRequestProperty("Charset", "UTF-8");  
		// 设置边界  
		String BOUNDARY = "---------------------------" + System.currentTimeMillis();  
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);  
		// 请求正文信息  
		// 第一部分：  
		StringBuilder sb = new StringBuilder();  
		sb.append("--"); // 必须多两道线  
		sb.append(BOUNDARY);  
		sb.append("\r\n");  
		sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + file.getName() + "\"\r\n");  
		sb.append("Content-Type:application/octet-stream\r\n\r\n");  
		byte[] head = sb.toString().getBytes("utf-8");  
		// 获得输出流  
		OutputStream out = new DataOutputStream(con.getOutputStream());  
		// 输出表头  
		out.write(head);  
		// 文件正文部分  
		// 把文件已流文件的方式 推入到url中  
		DataInputStream in = new DataInputStream(new FileInputStream(file));  
		int bytes = 0;  
		byte[] bufferOut = new byte[1024];  
		while ((bytes = in.read(bufferOut)) != -1) {  
		    out.write(bufferOut, 0, bytes);  
		}  
		in.close();  
		// 结尾部分  
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线  
		out.write(foot);  
		out.flush();  
		out.close();  
		StringBuffer buffer = new StringBuffer();  
		BufferedReader reader = null;  
		try {  
		    // 定义BufferedReader输入流来读取URL的响应  
		    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));  
		    String line = null;  
		    while ((line = reader.readLine()) != null) {  
		        buffer.append(line);  
		    }  
		    if (result == null) {  
		        result = buffer.toString();  
		    }  
		} catch (IOException e) {  
		    System.out.println("发送POST请求出现异常！" + e);  
		    e.printStackTrace();  
		    throw new IOException("数据读取异常");  
		} finally {  
		    if (reader != null) {  
		        reader.close();  
		    }  
		}  
		return result;  
    }
    
    public static void sendImage() throws Exception{
//    	String filePath = "F:\\微信公众号\\187229463608273535.jpg";//本地或服务器文件路径
//    	String filePath = "http://www.weizhiqiang.ngrok.cc/tsy/upload/170112151201.jpg";//本地或服务器文件路径
//    	String filePath = "http://wx4.sinaimg.cn/mw690/47145978ly1fbest7m7frj22io1ogkjm.jpg";//本地或服务器文件路径
//        String sendUrl = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="+TokenThread.accessToken.getToken()+"&type=image";//ACCESS_TOKEN是获取到的access_token  
    }
    
    /**
     *  发送图文给近期24小时活跃用户---
     *  oGBT1wZtPsBd7fT2BSOn5eoSBae4
     * @param openId 用户的id
     * @throws Exception 
     **/
    public static int sendNewsToUser(String openId,List<?> beans) throws Exception{
		String str = JsonUtil.getJsonStrFromList(beans);
		String json = "{\"touser\":\""+openId+"\",\"msgtype\":\"news\",\"news\":" + "{\"articles\":" +str +"}"+"}";
		//获取请求路径
		String action = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+TokenThread.accessToken.getToken();
		return WeixinUtil.connectWeiXinInterface(action,json);
    }
    
}

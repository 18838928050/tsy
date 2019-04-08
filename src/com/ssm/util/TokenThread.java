package com.ssm.util;


import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  

import com.ssm.tsy.bean.wechat.AccessToken;
import com.ssm.tsy.util.WeixinUtil;
  
/** 
 * 定时获取微信access_token的线程 
 *  
 * @date 
 */  
public class TokenThread implements Runnable {  
	
    private static Logger log = LoggerFactory.getLogger(TokenThread.class);  
    // 第三方用户唯一凭证  
    public static String appid = "";  
    // 第三方用户唯一凭证密钥  
    public static String appsecret = "";  
    public static AccessToken accessToken = null;  
  
    public void run() {  
        while (true) {  
            try {
                accessToken = WeixinUtil.getAccessToken(appid,appsecret);  
                if (null != accessToken) {
                    log.info("获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), accessToken.getToken());  
                    // 休眠7000秒  
                    Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);  
                } else {  
                    // 如果access_token为null，60秒后再获取  
                    Thread.sleep(60 * 1000);  
                }  
            } catch (InterruptedException e) {  
                try {  
                    Thread.sleep(60 * 1000);  
                } catch (InterruptedException e1) {  
                    log.error("{}", e1);  
                }  
                log.error("{}", e);  
            } catch (Exception e) {
				e.printStackTrace();
			}  
        }  
    }  
}  

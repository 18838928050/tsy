package com.ssm.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ssm.tsy.bean.WeChatAPP;
import com.ssm.tsy.dao.WeChatAPPDao;
import com.ssm.tsy.util.WeixinUtil;

public class InitServlet extends HttpServlet {  
    private static final long serialVersionUID = 1L;  
    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);  
    
    private WeChatAPPDao weChatAPPDao;
  
    public void init() throws ServletException {
    	ServletContext servletContext = this.getServletContext();  
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);  
        weChatAPPDao = (WeChatAPPDao)ctx.getBean("weChatAPPDao");  
    	WeChatAPP bean = weChatAPPDao.SelectAll();
    	TokenThread.appid = bean.getAppid();
    	TokenThread.appsecret = bean.getAppSecret();	
    	log.info("weixin api appid:{}", TokenThread.appid);  
        log.info("weixin api appsecret:{}", TokenThread.appsecret);  
        // 未配置appid、appsecret时给出提示  
        if ("".equals(TokenThread.appid) || "".equals(TokenThread.appsecret)) {  
            log.error("appid and appsecret configuration error, please check carefully.");  
        } else {  
            // 启动定时获取access_token的线程  
            new Thread(new TokenThread()).start();  
        }  
    }

	public WeChatAPPDao getWeChatAPPDao() {
		return weChatAPPDao;
	}

	public void setWeChatAPPDao(WeChatAPPDao weChatAPPDao) {
		this.weChatAPPDao = weChatAPPDao;
	} 
}  

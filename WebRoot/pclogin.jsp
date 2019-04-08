<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>微信公众号管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="icon" href="images/wechat.png" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<style>
body {
	height: 100%;
	background: #16a085;
	overflow: hidden;
}
canvas {
	z-index: -1;
	position: absolute;
}
</style>
<script src="js/url.js"></script>
<script src="js/jquery.js"></script>
<script src="js/verificationNumbers.js"></script>
<script src="js/Particleground.js"></script>
<script>
	function login() {
		var no = $.trim($("#no").val());
		var password = $.trim($("#password").val());
		$.ajax({
			type : "post",
			url : LOGIN_JUDGE,
			data : {
				no : no,
				password : password
			},
			timeout : 2000,
			dataType : 'json',
			success : function(data) {
				var str = data.message;
				if (str == '0100') {
					alert("请您输入账号后再登录");
					return false;
				} else if (str == '0101') {
					alert("请您输入密码后再登录");
					return false;
				}
				var inp_Code = $.trim($("#code").val());
				if (false) {
					alert("请输入验证码");
					return false;
				} else if (false) {
					alert("验证码输入错误");
					return false;
				} else {
					if (str == '0103') {
						alert("账号不存在！");
						return false;
					} else if (str == '0104') {
						alert("密码错误！");
						return false;
					} else {
						$("#loginForm").submit();
					}
				}
			}
		});
	}
	$(document).ready(function() {
		//粒子背景特效
		$('body').particleground({
			dotColor : '#5cbdaa',
			lineColor : '#5cbdaa'
		});
		//验证码
		createCode();
		//测试提交，对接程序删除即可
	});
	function validate() {
		var inp_Code = $.trim($("#code").val());
		if (inp_Code == "" || inp_Code == null) {
			alert("请输入验证码");
			return false;
		} else if (inp_Code.toLowerCase() != code.toLowerCase()) {
			alert("验证码输入错误");
			return false;
		} else {
			alert("正确");
			return false;
		}
	}
	document.onkeydown=keyDownSearch;  
	function keyDownSearch(e) {    
	    // 兼容FF和IE和Opera    
	    var theEvent = e || window.event;    
	    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
	    if (code == 13) {
	        //具体处理函数    
	        $(".submit_btn").click=login();
	        return false;
	    }    
	    return true;    
	}
</script>
</head>
<body>
	<form action="login/user/login" id="loginForm" method="post">
		<dl class="admin_login">
			<dt>
				<strong>微信公众号后台管理系统</strong> <em>微识</em>
			</dt>
			<dd class="user_icon">
				<input id="no" type="text" placeholder="账号" class="login_txtbx" />
			</dd>
			<dd class="pwd_icon">
				<input id="password" type="password" placeholder="密码" class="login_txtbx" />
			</dd>
			<dd class="val_icon">
				<div class="checkcode">
					<input id="code" type="text" id="J_codetext" placeholder="验证码" maxlength="4" class="login_txtbx">
					<canvas class="J_codeimg" id="myCanvas" onclick="createCode()">对不起，您的浏览器不支持canvas，请下载最新版浏览器!</canvas>
				</div>
				<input type="button" value="验证码核验" class="ver_btn" onClick="validate();">
			</dd>
			<dd>
				<input type="button" onclick="login()" value="立即登陆" class="submit_btn" />
			</dd>
			<dd>
				<p>© 2016-2017 doc.wei 版权所有</p>
			</dd>
		</dl>
	</form>
</body>
</html>

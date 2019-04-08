<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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

<title>微信公众号开发</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="icon" href="images/wechat.png" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="css/style.css">
<!--必要样式-->
<link rel="stylesheet" href="css/caidan/css/style.css">
<script src="js/url.js"></script>
<script src="js/jquery.js"></script>
<script src="js/jquery.mCustomScrollbar.concat.min.js"></script>
<!-- 阻止后退键 -->
<script src="js/stopbackspace.js"></script>
<!--[if lt IE 9]>
<script src="js/html5.js"></script>
<![endif]-->
<!--[if IE]>
	<script src="http://libs.useso.com/js/html5shiv/3.7/html5shiv.min.js"></script>
<![endif]-->
<script>
	(function($) {
		$(window).load(
				function() {
					$("a[rel='load-content']").click(
							function(e) {
								e.preventDefault();
								var url = $(this).attr("href");
								$.get(url, function(data) {
									$(".content .mCSB_container").append(data); //load new content inside .mCSB_container
									//scroll-to appended content 
									$(".content").mCustomScrollbar("scrollTo",
											"h2:last");
								});
							});
					$(".content").delegate(
							"a[href='top']",
							"click",
							function(e) {
								e.preventDefault();
								$(".content").mCustomScrollbar("scrollTo",
										$(this).attr("href"));
							});
				});
	})(jQuery);
</script>
<style type="text/css">
html,body {
	width: 100%;
	height: 100%;
	margin: 0;
	padding: 0;
	overflow: hidden;
	-moz-user-select: none;
	/*火狐*/
	-webkit-user-select: none;
	/*webkit浏览器*/
	-ms-user-select: none;
	/*IE10*/
	-khtml-user-select: none;
	/*早期浏览器*/
	user-select: none;
}

.box {
	width: 100%;
	height: 100%;
	border: 1px solid #00457b;
}
</style>

</head>

<body>
	<%
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
	%>
	<div class="box">
		<!--header-->
		<header>
		<h1>
			<img src="images/admin_logo.png" />
		</h1>
		<ul class="rt_nav">
			<c:if test="${user.quanxian==1 }">
				<li>
					<a id="adduser" href="javaScript:return void(0);" class="website_icon" target="MyIframe" onclick="show()">添加用户</a>
				</li>
			</c:if>
			<c:if test="${user.quanxian==1 }">
				<li>
					<a id="update" href="javaScript:return void(0);" class="set_icon" target="MyIframe" onclick="show()">账号设置</a>
				</li>
			</c:if>
			<c:if test="${user.quanxian==1 }">
				<li>
					<a id="liebiao" href="javaScript:return void(0);" class="set_icon" target="MyIframe" onclick="show()">列表权限</a>
				</li>
			</c:if>
			<li><a id="exit" href="login.html" class="quit_icon">安全退出</a>
			</li>
			<script type="text/javascript">
				$("#adduser").attr("href", USER_ADD_PAGE);
				$("#mation").attr("href", USER_ADD_PAGE);
				$("#update").attr("href", "jsp/html/wechatsetup/wechatsetuplist.html");
				$("#liebiao").attr("href", LIEBIAO_SELECTALL);
				$("#exit").attr("href", LOGIN_EXIT);
			</script>
		</ul>
		</header>
		<!--aside nav-->
		<aside class="lt_aside_nav content mCustomScrollbar">
		<h2>
			<a href="jsp/success/main.jsp" target="MyIframe" onclick="show()">起始页</a>
		</h2>
		<ul class="mainmenu">
			<c:if test="${liebiaojibielist1size!=0 }">
				<c:forEach var="bean" items="${liebiaojibielist1 }">
					<li>
						<img src="${bean.nameicon }" alt="Cog icon" class="icon" />
						<span>${bean.name }</span>
					</li>
					<ul class="submenu">
						<div class="expand-triangle"></div>
						<c:forEach var="bean1" items="${liebiaojibielist2 }">
							<c:if test="${bean1.belongto==bean.id}">
								<li>
									<span>
										<img src="${bean1.nameicon }" alt="Cog icon" class="icon" />
										<a style="margin-left: 10px" href="${bean1.nameurl }" target="MyIframe" onclick="show()">${bean1.name}</a>
									</span>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</c:forEach>
			</c:if>
		</ul>
		</aside>
		<div class="rt_content">
			<!--加载-->
			<section class="loading_area">
			<div class="loading_cont">
				<div class="loading_icon">
					<i></i><i></i><i></i><i></i><i></i>
				</div>
				<div class="loading_txt">
					<mark>数据正在加载，请稍后！</mark>
				</div>
			</div>
			</section>
			<!--加载-->
			<iframe src="jsp/success/main.jsp" id="MyIframe" name="MyIframe" width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
		</div>
	</div>
	<script type="text/javascript" src="js/caidan/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="js/caidan/script.js"></script>
	<script type="text/javascript">
		document.getElementById("MyIframe").onload = function() {
			//alert();
			/*setTimeout(function() {
				$(".loading_area").fadeOut(1500);
			}, 1000);*/
			$(".loading_area").fadeOut(500);
		};

		function show() {
			$(".loading_area").fadeIn();
		}
	</script>
</body>
</html>

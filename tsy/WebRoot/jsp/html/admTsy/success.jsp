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

<meta charset="UTF-8">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type="text/css" rel="stylesheet" href="css/index.css" />
		<link type="text/css" rel="stylesheet" href="js/bootstrap/dist/css/bootstrap.min.css" />
		<link type="text/css" rel="stylesheet" href="js/bootstrap/docs/examples/carousel/carousel.css" />
		<link href="js/bootstrap/docs/examples/sticky-footer-navbar/sticky-footer-navbar.css" rel="stylesheet">
		<title></title>
	</head>
	<body>
		<div class="panel panel-success" style="margin-top: 40%">
			<div class="panel-body" style="text-align: center;">
				<img alt="" src="images/se.png">
			</div>
		</div>
		<div class="panel panel-success">
			<div class="panel-body" style="text-align: center;">
				<font>登陆成功</font>
			</div>
		</div>
		
		<script type="text/javascript" src="js/jquery.js"></script>
		<!--[if lt IE 9]>
			<script src="js/bootstrap/assets/js/ie8-responsive-file-warning.js"></script>
			<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.2/html5shiv.min.js"></script>
			<script src="https://oss.maxcdn.com/libs/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
		<script src="js/bootstrap/dist/js/bootstrap.min.js"></script>
		<script src="js/bootstrap/assets/js/ie10-viewport-bug-workaround.js"></script>
		<script src="js/bootstrap/assets/js/ie-emulation-modes-warning.js"></script>
	</body>
</html>

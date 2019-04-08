<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<body>
	<section class="rt_wrap content mCustomScrollbar">
		<form action="login/user/success" id="regitsForm" method="post">
			<div class="rt_content">
				<div class="page_title">
					<h2 class="fl">用户添加</h2>
				</div>
				<ul class="ulColumn2">
					<li><span class="item_name" style="width:130px;">用户账号：</span>
						<input type="text" class="textbox textbox_225" value=""
						placeholder="用户账号(手机号)" maxlength="11" id="no" name="no"
						onblur="add()" /> <br /> <span id="no2" class="errorTips">用户账号不能为空...</span>
					</li>
					<li><span class="item_name" style="width:130px;">用户姓名：</span>
						<input type="text" class="textbox textbox_225" value=""
						placeholder="用户姓名" id="name" name="name" onblur="add()" /> <br />
						<span id="name2" class="errorTips">用户姓名不能为空...</span></li>
					<li><span class="item_name" style="width:130px;">用户类别：</span>
						<select id="quanxian" name="quanxian"
						class="select textbox textbox_225">
							<option value="0">普通员工</option>
							<option value="3">高级管理员</option>
							<option value="1">超级管理员</option>
							<option value="2">普通管理员</option>
					</select></li>
					<li><span class="item_name" style="width:130px;">登陆密码：</span>
						<input type="password" class="textbox textbox_225" value=""
						placeholder="用户密码" id="password" name="password" onblur="add()" />
						<br /> <span id="password2" class="errorTips">密码不能为空...</span></li>
					<li><span class="item_name" style="width:130px;">再次输入登陆密码：</span>
						<input type="password" class="textbox textbox_225" value=""
						placeholder="确认用户密码" id="password1" name="password1"
						onblur="add()" /> <br /> <span id="password12" class="errorTips">两次输入密码不一致...</span>
					</li>
					<li><input type="button" class="link_btn" value="注册"
						onclick="return add();" /></li>
				</ul>
			</div>
		</form>
	</section>
	<script type="text/javascript">
		//用户添加表单提交验证
		function add() {
			var no = $.trim($("#no").val());
			var name = $.trim($("#name").val());
			var quanxian = $.trim($("#quanxian").val());
			var password = $.trim($("#password").val());
			var password1 = $.trim($("#password1").val());
			if (no == null || no == "") {
				document.getElementById("no2").style.display = "block";
				return false;
			} else {
				document.getElementById("no2").style.display = "none";
			}

			if (name == null || name == "") {
				document.getElementById("name2").style.display = "block";
				return false;
			} else {
				document.getElementById("name2").style.display = "none";
			}
			if (password == null || password == "") {
				document.getElementById("password2").style.display = "block";
				return false;
			} else {
				document.getElementById("password2").style.display = "none";
			}

			if (password != password1) {
				document.getElementById("password12").style.display = "block";
				return false;
			} else {
				document.getElementById("password12").style.display = "none";
			}
		}
		$(document).ready(function() {
			$(".link_btn").click(function() {
				var no = $.trim($("#no").val());
				var name = $.trim($("#name").val());
				var quanxian = $.trim($("#quanxian").val());
				var password = $.trim($("#password").val());
				$.ajax({
					type : "post",
					url : USER_ADD,
					data : {
						no : no,
						password : password,
						name : name,
						quanxian : quanxian
					},
					timeout : 2000,
					dataType : 'json',
					success : function(data) {
						var str = data.message;
						if (str == '0404') {
							alert("系统繁忙,请稍后尝试...");
						} else if (str == '0100') {
							alert("用户账号不能为空...");
						} else if (str == '0103') {
							alert("用户账号为手机号,手机号格式不正确...");
						} else if (str == '0102') {
							alert("姓名不能为空");
						} else if (str == '1095') {
							alert("该账户已存在");
						} else if (str == '1096') {
							alert("新用户注册成功");
							$("#regitsForm").submit();
						} else {
							alert("繁忙");
						}
					}
				});
			});
		});
	</script>
</body>
</html>
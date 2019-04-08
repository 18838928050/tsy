<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<body>
	<section class="rt_wrap content mCustomScrollbar">
	<form action="" id="regitsForm" method="post">
		<div class="rt_content">
			<div class="page_title">
				<h2 class="fl">修改密码</h2>
			</div>
			<ul class="ulColumn2">
				<li><span class="item_name" style="width:130px;">用户账号：</span><span
					style="color: red;">${user.no }</span>
				</li>
				<li><span class="item_name" style="width:130px;">用户姓名：</span><span
					style="color: red;">${user.name }</span></li>
				<li><span class="item_name" style="width:130px;">旧密码：</span> <input
					type="password" class="textbox textbox_225" value=""
					placeholder="旧密码" id="oldpwd" name="oldpwd" /></li>
				<li><span class="item_name" style="width:130px;">新密码：</span> <input
					type="password" class="textbox textbox_225" value=""
					placeholder="新密码" id="newpwd" name="newpwd" /></li>
				<li><span class="item_name" style="width:130px;">再次输入新密码：</span>
					<input type="password" class="textbox textbox_225" value=""
					placeholder="再次输入新密码" id="newpwd1" name="newpwd1" /></li>
				<li><input type="button" class="link_btn" value="提交"
					onclick="return update();" /></li>
			</ul>
		</div>
	</form>
	</section>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".link_btn").click(function() {
				var oldpwd = $.trim($("#oldpwd").val());
				var newpwd = $.trim($("#newpwd").val());
				var newpwd1 = $.trim($("#newpwd1").val());
				if (newpwd == newpwd1) {
					$.ajax({
						type : "post",
						url : USER_TOUPDATEPASSWORD_SQL,
						data : {
							oldpwd : oldpwd,
							newpwd : newpwd,
						},
						timeout : 2000,
						dataType : 'json',
						success : function(data) {
							var str = data.message;
							if (str == '0404') {
								alert("系统繁忙,请稍后尝试...");
							} else if (str == '0110') {
								alert("请输入旧密码...");
							} else if (str == '0111') {
								alert("请输入新密码...");
							} else if (str == '0105') {
								alert("旧密码错误,请确认后输入...");
							} else if (str == '1085') {
								alert("修改失败...");
							} else if (str == '1086') {
								alert("修改成功...");
							} else {
								alert("繁忙");
							}
						}
					});
				} else {
					alert("两次密码输入不一致...");
				}

			});
		});
	</script>
</body>
</html>
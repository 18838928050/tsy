<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<body oncontextmenu="return false;" onselectstart="return false;">
	<section class="rt_wrap content mCustomScrollbar">
		<div class="rt_content">
			<div class="page_title">
				<h2 class="fl">绑定微信公众号</h2>
			</div>
			<div style="width: 100%;height: 84%;">
				<div style="width: 48%; height: 100%;float: left;margin-left: 1%;border:1px dashed #787878;">
					<div style="width: 98%;height: 10%;background-color: #F0F0F0;margin-left: 1%;margin-top: 1%;text-align: left;float: left;">
						<div style="width: 10%;height:60%;float: left; margin-left: 1%;">
							<img alt="警告" src="images/jinggao.png">
						</div>
						<div style="width: 78%;height: 60%;margin-left: 1%;float: left;">
							<b style="color: red;margin-left: 2%;margin-top: 2%;float: left;font-size: 15px">重要提示</b>
							<span
								style="color: red;margin-left: 2%;margin-top: 2%;float: left;font-size: 12px">后期只有在所有使用微信授权登录的会员关联绑定系统用户名以后，您才能更换绑定其他微信公众号，请谨慎操作！</span>
						</div>
					</div>

					<div style="width: 98%;height: 25%;margin-left: 1%;margin-top: 1%;text-align: left;float: left;">
						<div style="width: 100%;height:10%;float: left;margin-top: 2%">
							<span style="font-size: 20px">第一步：</span>
							<hr />
						</div>
						<div style="width: 78%;height: 85%;margin-left: 13%;float: left;margin-top: 1%">
							<p style="margin-left: 2%;margin-top: 4%;font-size: 14px">
								复制以下信息至 <a href="https://mp.weixin.qq.com/" target="_Blank">微信公众平台</a>
								->开发者中心->服务器配置中，并提交！
							</p>
							<table
								style="width: 98%;margin-left: 2%;margin-top: 2%;height: 80%">
								<tr>
									<td style="text-align: right;">Url：</td>
									<td style="text-align: left;">http://weizhiqiang.ngrok.cc/wechat/WechatPost
									</td>
								</tr>
								<tr>
									<td style="text-align: right;">Token：</td>
									<td style="text-align: left;">weixinCourse</td>
								</tr>
							</table>
						</div>
					</div>

					<div style="width: 98%;height: 50%;margin-left: 1%;margin-top: 1%;text-align: left;float: left;">
						<div style="width: 100%;height:10%;float: left;margin-top: 2%">
							<span style="font-size: 20px">第二步：</span>
							<hr />
						</div>
						<div
							style="width: 78%;height: 85%;margin-left: 13%;float: left;margin-top: 1%">
							<p style="margin-left: 2%;margin-top: 2%;font-size: 14px">
								在<a href="https://mp.weixin.qq.com/" target="_Blank">微信公众平台</a>开启自定义菜单，将开发者中心->开发者ID中的AppId和Appsecret复制到下方对应的输入框，并保存！
							</p>
							<form action="" method="post">
								<table
									style="width: 98%;margin-left: 2%;margin-top: 2%;height: 80%">
									<tr>
										<td style="text-align: right;">AppId:</td>
										<td style="text-align: left;"><input type="text"
											id="appid" name="appid" class="textbox textbox_225">
											<br> <span style="color: #B0B0B0;font-size: 13px">微信公众号身份的唯一标识</span>
										</td>
									</tr>
									<tr>
										<td style="text-align: right;">AppSecret:</td>
										<td style="text-align: left;"><input type="text"
											id="appSecret" name="appSecret" class="textbox textbox_225">
											<br> <span style="color: #B0B0B0;font-size: 13px">审核后在公众平台开启开发模式后可查看</span>
										</td>
									</tr>
									<tr>
										<td style="text-align: center;" colspan="2"><input
											id="baocun" type="button" class="link_btn" value="保存"
											onclick="addtosql();" />
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
				<div style="width: 48%; height: 100%;float: left;margin-left: 1%;border:1px dashed #787878;background-color: RGB(244,245,249); ">
					<iframe style="width: 100%;height: 100%;" frameborder="0"
						scrolling="auto" src="wechatapp/ToZhinanPage"></iframe>
				</div>
			</div>

		</div>
	</section>
	<script type="text/javascript">
		function addtosql() {
			var appid = $("#appid").val();
			var appSecret = $("#appSecret").val();

			if (appid == "" || appid == null) {
				alert("请填写AppId");
			} else if (appSecret == "" || appSecret == null) {
				alert("请填写AppSecret");
			} else {
				$.ajax({
					type : "post",
					url : "wechatapp/ToAddSql",
					data : {
						appid : appid,
						appSecret : appSecret
					},
					timeout : 2000,
					dataType : 'json',
					success : function(data) {
						var str = data.message;
						if (str == '0404') {
							alert("数据异常");
						} else if (str == '1095') {
							alert("保存失败");
						} else if (str == '1096') {
							alert("保存成功");
							$("#appid").attr("disabled", true);
							$("#appSecret").attr("disabled", true);
						}
					},
					error : function(data) {
						alert("请求服务器失败");
					}
				});
			}
		}
		var bean = '${wechatbean}';

		if (bean == null || bean == "") {

		} else {
			var appid = '${wechatbean.appid}';
			var appSecret = '${wechatbean.appSecret}';
			$("#baocun").hide();
			$("#appid").val(appid);
			$("#appSecret").val(appSecret);
			$("#appid").attr("disabled", true);
			$("#appSecret").attr("disabled", true);
		}
	</script>
</body>
</html>

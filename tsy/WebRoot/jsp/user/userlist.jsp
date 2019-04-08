<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<body>
	<section class="rt_wrap content mCustomScrollbar">
		<div class="rt_content">
			<div class="page_title">
				<h2 class="fl">用户列表</h2>
			</div>
			<form action="login/user/SelectToAllUserPage" method="post">
				<section class="mtb">
					<select id="quanxian" name="quanxian" class="select">
						<option value="-1">用户身份</option>
						<option value="0">普通员工</option>
						<option value="2">普通管理员</option>
						<option value="3">高级管理员</option>
						<option value="1">超级管理员</option>
					</select> <input type="text" name="no" class="textbox textbox_225"
						placeholder="手机号" /> <input type="text" name="name"
						class="textbox textbox_225" placeholder="姓名" /> <input
						type="submit" value="查询" class="group_btn" />
				</section>
			</form>
			<table class="table">
				<tr>
					<th style="width: 60px">序号</th>
					<th>用户账号</th>
					<th>真实姓名</th>
					<th>身份</th>
					<th>注册时间</th>
					<th>操作</th>
				</tr>
				<tbody id="tablelsw">
					<c:if test="${size==0 }">
						<td colspan="6" style="text-align: center;color: red;">暂时没有数据</td>
					</c:if>
					<c:if test="${size!=0 }">
						<c:forEach var="bean" items="${userlist}" varStatus="status">
							<tr>
								<td class="center"><a>${status.index + 1}</a>
								</td>
								<td class="center">${bean.no }</td>
								<td class="center">${bean.name}</td>
								<c:if test="${bean.quanxian==0 }">
									<td class="center">普通员工</td>
								</c:if>
								<c:if test="${bean.quanxian==1 }">
									<td class="center">超级管理员</td>
								</c:if>
								<c:if test="${bean.quanxian==2 }">
									<td class="center">普通管理员</td>
								</c:if>
								<c:if test="${bean.quanxian==3 }">
									<td class="center">高级管理员</td>
								</c:if>
								<td class="center">${bean.redata }</td>
								<td class="center"><a id="upordown"
									href="javaScript:return void(0);"
									onclick="updateQuanxian('${bean.no}','${bean.quanxian}')"
									title="重置权限"><img src="images/shouquan.png"
										style="margin-top: -7px;width: 20px;height: 20px" /> </a>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<!-- 修改权限 -->
			<section id="update" class="pop_bg">
				<div class="pop_cont">
					<!--title-->
					<h3>修改权限</h3>
					<!--content-->
					<div class="pop_cont_input">
						<ul>
							<input id="updateno" name="updateno" value="" type="hidden" />
							<li><a
								style="float: left;font-size: 15px;margin-top: 10px;margin-left: 20px"><input
									type="radio" name="field_name" value="0" id="Like1" /><label
									for="Like1">普通员工</label> </a></li>
							<li><a
								style="float: left;font-size: 15px;margin-top: 10px;margin-left: 20px"><input
									type="radio" name="field_name" value="2" id="Like2" /><label
									for="Like2">普通管理员</label> </a>
							</li>
							<li><a
								style="float: left;font-size: 15px;margin-top: 10px;margin-left: 20px"><input
									type="radio" name="field_name" value="3" id="Like3" /><label
									for="Like3">高级管理员</label> </a>
							</li>
							<li><a
								style="float: left;font-size: 15px;margin-top: 10px;margin-left: 20px"><input
									type="radio" name="field_name" value="1" id="Like4" /><label
									for="Like4">超级管理员</label> </a>
							</li>
						</ul>
					</div>
					<!--bottom:operate->button-->
					<div class="btm_btn">
						<input type="button" value="修改" class="input_btn trueBtn"
							onclick="xiugai();" /> <input type="button" value="关闭"
							class="input_btn falseBtn" onclick="guanbi();" />
					</div>
				</div>
			</section>

			<aside class="paging">
				<span id="spanFirst"><a style="color:#8B8386;">第一页</a> </span> <span
					id="spanPre"><a style="color:#8B8386;">上一页</a> </span> <span
					id="spanNext"><a>下一页</a> </span> <span id="spanLast"><a>最后一页</a>
				</span> 第<span id="spanPageNum"></span>页/共<span id="spanTotalPage"></span>页
				共<span id="alltiao"></span>条
			</aside>
		</div>
	</section>
	<script type="text/javascript" src="js/fenye.js"></script>
	<script type="text/javascript">
		$(function() {
			if ('${tishi}' == null || '${tishi}' == "") {

			} else {
				alert('${tishi}');
			}
		});

		/**
		 *修改权限
		 *
		 **/
		function updateQuanxian(no, quanxian) {
			$("#updateno").val(no);
			if (quanxian == 0) {
				$("#Like2").removeAttr("checked");
				$("#Like4").removeAttr("checked");
				$("#Like3").removeAttr("checked");
				$("#Like1").prop('checked', true);
			} else if (quanxian == 1) {
				$("#Like2").removeAttr("checked");
				$("#Like1").removeAttr("checked");
				$("#Like3").removeAttr("checked");
				$("#Like4").prop('checked', true);
			} else if (quanxian == 3) {
				$("#Like2").removeAttr("checked");
				$("#Like1").removeAttr("checked");
				$("#Like4").removeAttr("checked");
				$("#Like3").prop('checked', true);
			} else if (quanxian == 2) {
				$("#Like4").removeAttr("checked");
				$("#Like1").removeAttr("checked");
				$("#Like3").removeAttr("checked");
				$("#Like2").prop('checked', true);
			}
			xianshi();
		}

		function xianshi() {
			$("#update").fadeIn();
		}

		function guanbi() {
			$("#update").fadeOut();
		}

		function xiugai() {
			var updateno = $("#updateno").val();
			var quanxian = $('input:radio[name="field_name"]:checked').val();
			$.ajax({
				type : "post",
				url : "login/user/UpdateQuanxian",
				data : {
					no : updateno,
					quanxian : quanxian
				},
				timeout : 2000,
				dataType : 'json',
				success : function(data) {
					var str = data.message;
					if (str == '0404') {
						alert("数据库链接出错");
					} else if (str == '1086') {
						alert("重置权限成功");
						window.location.reload();
					} else if (str == '1085') {
						alert("重置权限失败");
					}
				},
				error : function(data) {
					alert("请求服务器失败");
				}
			});
		}
	</script>
</body>
</html>

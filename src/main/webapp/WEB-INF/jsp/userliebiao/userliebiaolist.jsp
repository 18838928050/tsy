<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<%@ include file="/jsp/public/tree.jspf"%>
<body>
	<input type="hidden" value="-1" id="judgeValue" />
	<section class="rt_wrap content mCustomScrollbar">
		<div class="rt_content">
			<div class="page_title">
				<h2 class="fl">列表权限分配</h2>
			</div>
			<div style="width: 100%;height: 80%;">
				<div style="width: 50%; height: 100%;float: left;">
					<div style="width: 100%; height: 40%;float: top;border:1px dashed #787878;">
						<div>
							<h3>
								列表菜单<input type="button" value="分配权限" style="margin-left: 10px;height: 25px;margin-top: 5px;"
									onclick="submitJudegValue()" class="group_btn" />
							</h3>
						</div>
						<div style="width: 97%;height: 70%">
							<ul id="treeDemo" class="ztree"></ul>
						</div>
					</div>
					<div
						style="width: 100%; height: 55%;float: top;margin-top: 1%;border:1px dashed #787878;">
						<div class="title-alt">
							<h6>用户列表</h6>
						</div>
						<div style="width: 100%;height: 70%">
							<table class="table">
								<tr>
									<th>序号</th>
									<th>账号</th>
									<th>账号类型</th>
									<th>操作</th>
								</tr>
								<tbody id="tablelsw">
									<c:if test="${usersize==0}">
										<tr>
											<th align="center" colspan="7">内容为空!</th>
										</tr>
									</c:if>
									<c:forEach var="bean" items="${userlist}" varStatus="status">
										<tr>
											<td class="center"><a>${status.index + 1}</a></td>
											<td class="center">${bean.no }</td>
											<td class="center"><c:if test="${bean.quanxian==0 }">
														普通员工
													</c:if> <c:if test="${bean.quanxian==1 }">
														超级管理员
													</c:if> <c:if test="${bean.quanxian==2 }">
														普通管理员
													</c:if> <c:if test="${bean.quanxian==3 }">
														高级管理员
													</c:if></td>
											<td class="center"><a
												href="user_Liebiao/SelectAllById?userid=${bean.id }"
												onclick="toJudegValue('${bean.id }');" target="CourseMain1"
												class="inner_btn">查看权限</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<aside class="paging">
								<span id="spanFirst"><a style="color:#8B8386;">第一页</a> </span> <span
									id="spanPre"><a style="color:#8B8386;">上一页</a> </span> <span
									id="spanNext"><a>下一页</a> </span> <span id="spanLast"><a>最后一页</a>
								</span>第<span id="spanPageNum"></span>页/共<span id="spanTotalPage"></span>页
								共<span id="alltiao"></span>条
							</aside>
						</div>
					</div>
				</div>
				<div
					style="width: 48%; height: 100%;float: left;margin-left: 1%;border:1px dashed #787878;">
					<div>
						<h6>用户当前所拥有的权限</h6>
					</div>
					<div style="width: 100%;height: 70%;">
						<iframe id="CourseMain1" name="CourseMain1" width="100%" src=""
							height="100%" scrolling="no"> </iframe>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- 一级菜单 -->
	<c:forEach items="${liebiaojibielist}" var="a">
		<input type="hidden" value="${a.name }" name="liebiaojibiename" />
		<input type="hidden" value="${a.id }" name="liebiaojibieid" />
	</c:forEach>

	<!-- 所有菜单 -->
	<c:forEach items="${liebiaolist}" var="a">
		<input type="hidden" value="${a.name }" name="liebiaoname" />
		<input type="hidden" value="${a.id }" name="liebiaoid" />
		<input type="hidden" value="${a.jibie }" name="liebiaojibie" />
		<input type="hidden" value="${a.belongto }" name="liebiaobelongto" />
	</c:forEach>
	<style type="text/css">
a {
	text-decoration: none;
}
</style>

	<script type="text/javascript">
		//一级菜单
		var liebiaojibieid = document.getElementsByName("liebiaojibieid");//获取id
		var liebiaojibiename = document.getElementsByName("liebiaojibiename");//获取名称

		//所有菜单
		var liebiaoid = document.getElementsByName("liebiaoid");//获取id
		var liebiaoname = document.getElementsByName("liebiaoname");//获取名称
		var liebiaojibie = document.getElementsByName("liebiaojibie");//获取菜单级别
		var liebiaobelongto = document.getElementsByName("liebiaobelongto");//获取父菜单
		//<!--
		var setting = {
			check : {
				enable : true
			},
			data : {
				key : {
					title : "title"
				},
				simpleData : {
					enable : true
				}
			},
			callback : {
				onCheck : onCheck
			}
		};
		var zNodes = new Array();
		for ( var i = 0; i < '${liebiaojibielistsize}'; i++) {
			zNodes.push({
				id : liebiaojibieid[i].value,
				pId : 0,
				name : liebiaojibiename[i].value,
				title : liebiaojibiename[i].value
			});
			for ( var j = 0; j < '${liebiaolistsize}'; j++) {
				if (liebiaobelongto[j].value == liebiaojibieid[i].value) {
					zNodes.push({
						id : liebiaoid[j].value,
						pId : liebiaojibieid[i].value,
						name : liebiaoname[j].value,
						title : liebiaoname[j].value
					});
				}
			}
		}

		function onCheck(e, treeId, treeNode) {
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo"), nodes = treeObj
					.getCheckedNodes(true), v = "";
			for ( var i = 0; i < nodes.length; i++) {
				//alert(nodes[i].title); //获取选中节点的值
			}
		}
		$(document).ready(function() {
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});

		//给要分配的用户赋值id
		function toJudegValue(id) {
			document.getElementById("judgeValue").value = id;
		}

		function submitJudegValue() {
			var id = document.getElementById("judgeValue").value;

			if (id == -1) {
				alert("请选择要分配权限的用户...\n点击下面‘用户列表’内要分配权限的用户对应的‘查看权限’");
			} else {
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo"), nodes = treeObj
						.getCheckedNodes(true), v = "";
				if (nodes.length <= 0) {
					alert("请选择要分配的菜单列表");
				} else {
					for ( var i = 0; i < nodes.length; i++) {
						//alert(nodes[i].id); //获取选中节点的值
						$.ajax({
							type : "POST",
							url : "user_Liebiao/AddToSql",
							data : {
								userid : id,
								liebiaoid : nodes[i].id
							},
							dataType : "json",
							timeout : 3000,
							success : function(data) {
							},
							error : function() {

							}
						});
					}
					document.getElementById('CourseMain1').src = "user_Liebiao/SelectAllById?userid="
							+ id;
					alert("权限分配成功");
				}
			}
		}
	</script>
	<script type="text/javascript" src="js/fenyebyfive.js"></script>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<%@ include file="/jsp/public/tree.jspf"%>
<body>
	<input type="hidden" value="${userid }" id="judgeValue" />
	<div style="width: 100%; height: 100%;">
		<div style="width: 97%;height: 70%">
			<c:if test="${user_Liebiaolistsize==0 }">
								该用户暂时不具备任何权限
						</c:if>
			<c:if test="${user_Liebiaolistsize!=0 }">
				<a href="javaScript:return void(0);" class="pull-right btn btn-info"
					style="margin-right: 10px" onclick="removeJudegValue()"
					title="Filter using the Filter API">移除权限</a>
			</c:if>
			<ul id="treeDemo1" class="ztree">
			</ul>
		</div>
	</div>
	<!-- 一级菜单 -->
	<c:forEach items="${liebiaojibielist1}" var="a">
		<input type="hidden" value="${a.name }" name="liebiaojibiename" />
		<input type="hidden" value="${a.id }" name="liebiaojibieid" />
		<input type="hidden" value="${a.jibie }" name="liebiaojibiexianshi" />
	</c:forEach>

	<!-- 二级菜单 -->
	<c:forEach items="${liebiaojibielist2}" var="a">
		<input type="hidden" value="${a.name }" name="liebiaoname" />
		<input type="hidden" value="${a.id }" name="liebiaoid" />
		<input type="hidden" value="${a.jibie }" name="liebiaojibie" />
		<input type="hidden" value="${a.belongto }" name="liebiaobelongto" />
	</c:forEach>
	<script type="text/javascript">
		//一级菜单
		var liebiaojibieid = document.getElementsByName("liebiaojibieid");//获取id
		var liebiaojibiename = document.getElementsByName("liebiaojibiename");//获取名称
		var liebiaojibiexianshi = document.getElementsByName("liebiaojibiexianshi");//获取名称

		//二级菜单
		var liebiaoid = document.getElementsByName("liebiaoid");//获取id
		var liebiaoname = document.getElementsByName("liebiaoname");//获取名称
		var liebiaojibie = document.getElementsByName("liebiaojibie");//获取菜单级别
		var liebiaobelongto = document.getElementsByName("liebiaobelongto");//获取父菜单
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

		for ( var i = 0; i < '${liebiaojibielist1size}'; i++) {
			zNodes.push({
				id : liebiaojibieid[i].value,
				pId : 0,
				name : liebiaojibiename[i].value,
				title : liebiaojibiexianshi[i].value
			});
			for ( var j = 0; j < '${liebiaojibielist2size}'; j++) {
				if (liebiaobelongto[j].value == liebiaojibieid[i].value) {
					zNodes.push({
						id : liebiaoid[j].value,
						pId : liebiaojibieid[i].value,
						name : liebiaoname[j].value,
						title : liebiaojibie[j].value
					});
				}
			}
		}

		function onCheck(e, treeId, treeNode) {
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo1"), nodes = treeObj
					.getCheckedNodes(true), v = "";
			for ( var i = 0; i < nodes.length; i++) {
				//alert(nodes[i].title); //获取选中节点的值
			}
		}
		$(document).ready(function() {
			$.fn.zTree.init($("#treeDemo1"), setting, zNodes);
		});

		function removeJudegValue() {
			var id = document.getElementById("judgeValue").value;
			if (id == -1) {
				alert("请选择要移除权限的用户...\n点击左下角‘用户列表’内要分配权限的用户对应的‘查看权限’");
			} else {
				/**
				 *获取被选中的列表
				 */
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo1"), nodes = treeObj
						.getCheckedNodes(true), v = "";
				if (nodes.length <= 0) {
					alert("请选择要移除的菜单列表");
				} else {
					//先移除级别为二级菜单的内容
					for ( var i = 0; i < nodes.length; i++) {
						//alert(nodes[i].id); //获取选中节点的值
						if (nodes[i].title == 2) {
							$.ajax({
								type : "POST",
								url : "${dp}user_Liebiao/Delete",
								data : {
									userid : id,
									liebiaoid : nodes[i].id
								},
								dataType : "json",
								timeout : 200000,
								success : function(data) {
								},
								error : function() {

								}
							});
						}
					}
					//再移除级别为一级菜单的内容
					for ( var i = 0; i < nodes.length; i++) {
						//alert(nodes[i].id); //获取选中节点的值
						if (nodes[i].title == 1) {
							$.ajax({
								type : "POST",
								url : "user_Liebiao/Delete",
								data : {
									userid : id,
									liebiaoid : nodes[i].id
								},
								dataType : "json",
								timeout : 200000,
								success : function(data) {
								},
								error : function() {

								}
							});
						}
					}
					window.location.reload();
					alert("权限移除成功...");
				}
			}
		}
	</script>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<%@ include file="/jsp/public/wechatknowledgeistwo.jspf"%>
<body oncontextmenu="return true;" onselectstart="return true;">
	<section class="rt_wrap content mCustomScrollbar">
		<form action="login/user/success" id="regitsForm" method="post">
			<div class="rt_content">
				<div class="page_title">
					<h2 class="fl">
						微知识专区<font color="red">--->${name },共${size}条 <input
							id="fatherid" name="fatherid" value="${fatherid }" type="hidden" />
						</font>
					</h2>
					<a id="xintianleibie" href="javascript:return void(0);"
						class="fr top_rt_btn add_icon" onclick="add_xianshi()">新添类别</a> <a
						id="liulan" href="javascript:return void(0);"
						style="margin-right: 15px" class="fr top_rt_btn add_icon"
						onclick="jiazaiUrl()">浏览</a>
				</div>
				<div style="width: 100%;height: 84%;overflow: auto;">
					<div id="accordion">
						<c:if test="${size==0 }">
						暂无数据
					</c:if>
						<c:if test="${size!=0 }">
							<c:forEach var="bean" items="${WeChatKnowledge_list}"
								varStatus="status">
								<h3 id="${bean.id }">${bean.name }</h3>
								<div name="cont" style="width: 70%;margin-left: 15%">
									${bean.content }</div>
							</c:forEach>
						</c:if>
					</div>
				</div>
				<div id="dialog" title="Basic dialog">
					<div class="cd-iphone-6 cd-rosegold cd-scale-60">
						<div class="cd-body">
							<div class="cd-sound"></div>
							<div class="cd-sleep"></div>
							<div class="cd-camera"></div>
							<div class="cd-ear"></div>
							<div class="cd-home"></div>
							<div class="cd-screen">
								<iframe frameborder="0" width="100%" height="100%"
									scrolling="no" id="PhonePage" name="PhonePage"
									src="WeChatKnowledge/ToPageZhanshi?id=${fatherid }"></iframe>
							</div>
						</div>
					</div>
				</div>
				<!--弹出框效果 添加-->
				<section id="add" class="pop_bg">
					<div class="pop_cont"
						style="width: 1200px;height: 700px;margin-left: -500px;margin-top: -140px">
						<!--title-->
						<h3>新添类别</h3>
						<!--content-->
						<div class="pop_cont_input">
							<ul>
								<li><span>标题</span> <input type="text" id="name"
									name="name" placeholder="定义标题..." class="textbox" />
								</li>
								<li><span class="ttl">内容</span> <script id="editor"
										type="text/plain"
										style="width:100%;height:400px;margin-top:0;"></script></li>
							</ul>
						</div>
						<!--bottom:operate->button-->
						<div class="btm_btn">
							<input type="button" value="添加" class="input_btn trueBtn"
								onclick="tianjia();" /> <input type="button" value="关闭"
								class="input_btn falseBtn" onclick="add_xiaoshi()" />
						</div>
					</div>
				</section>
				<!--结束：弹出框效果-->

				<!--弹出框效果 修改-->
				<section id="update" class="pop_bg">
					<div class="pop_cont"
						style="width: 1200px;height: 700px;margin-left: -500px;margin-top: -140px">
						<!--title-->
						<h3>修改类别</h3>
						<!--content-->
						<div class="pop_cont_input">
							<ul>
								<input id="updateid" name="updateid" value="" type="hidden" />
								<li><span>标题</span> <input type="text" id="name1"
									name="name1" placeholder="定义标题..." class="textbox" />
								</li>
								<li><span class="ttl">内容</span> <script id="editor1"
										type="text/plain"
										style="width:100%;height:400px;margin-top:0;"></script></li>
							</ul>
						</div>
						<!--bottom:operate->button-->
						<div class="btm_btn">
							<input type="button" value="修改" class="input_btn trueBtn"
								onclick="update();" /> <input type="button" value="关闭"
								class="input_btn falseBtn" onclick="update_xiaoshi()" />
						</div>
					</div>
				</section>
				<!--结束：弹出框效果-->
			</div>
		</form>
	</section>
	<script type="text/javascript">
		//加载折叠面板
		$(function() {
			$("#accordion").accordion({
				collapsible : true,
				heightStyle : "content"
			});

			//弹出框----手机端展示
			$("#dialog").dialog({
				modal : true,
				autoOpen : false,
				width : 500,
				closed : false,
				cache : false,
				height : 600,
				title : "手机端展示",
				show : {
					effect : "blind",
					duration : 1000
				},
				hide : {
					effect : "blind",
					duration : 500
				},
			});

		});

		//加载滚动条
		var myscroll;
		function loaded() {
			myscroll = new iScroll("myscroll");
		}
		window.addEventListener("DOMContentLoaded", loaded, false);

		/**
		 *弹出手机端浏览
		 **/
		function jiazaiUrl() {
			$("#dialog").dialog("open");
		}

		/**
		 *添加框显示
		 **/
		function add_xianshi() {
			$("#add").fadeIn();
		}

		/**
		 *添加框消失
		 **/
		function add_xiaoshi() {
			$("#add").fadeOut();
		}

		/**
		 *修改框显示
		 **/
		function update_xianshi(id) {
			$.ajax({
				type : "post",
				url : "WeChatKnowledge/SelectById",
				data : {
					id : id
				},
				timeout : 2000,
				dataType : 'json',
				success : function(data) {
					var str = data.message;
					if (str == '0506') {
						var newid = data.selectid;
						var name = data.name;
						var content = unescape(data.content);
						//清空之前的
						$("#updateid").val("");
						$("#name1").val("");
						//重置数据
						$("#updateid").val(newid);
						$("#name1").val(name);
						UE.getEditor('editor1').setContent(content);
						$("#update").fadeIn();
					} else {
						alert("繁忙");
					}
				},
				error : function() {
					alert("错误警报");
				}
			});
		}

		/**
		 *转换
		 */
		function formatStr(str) {
			str = str.replace(/-/g, "\r\n");
			return str;
		}

		/**
		 *修改框消失
		 **/
		function update_xiaoshi() {
			$("#update").fadeOut();
		}

		/**
		 *添加到sql
		 **/
		function tianjia() {
			var name = $("#name").val();
			var fatherid = $("#fatherid").val();
			var content = escape(UE.getEditor('editor').getContent());
			if (name == "" || name == null) {
				alert("请输入标题");
			} else if (content == "" || content == null) {
				alert("请描述该标题");
			} else {
				$.ajax({
					type : "post",
					url : "WeChatKnowledge/AddWeChatKnowledge",
					data : {
						name : name,
						jieshao : "",
						jibie : '2',
						content : content,
						fatherid : fatherid
					},
					timeout : 2000,
					dataType : 'json',
					success : function(data) {
						var str = data.message;
						if (str == '0404') {
							alert("系统繁忙,请稍后尝试...");
						} else if (str == '1097') {
							alert("该标题已经存在...");
						} else if (str == '1096') {
							alert("新标题添加成功");
							$("#add").fadeOut();
							window.location.reload();
						} else if (str == '1095') {
							alert("添加失败...");
						} else {
							alert("繁忙");
						}
					},
					error : function() {
						alert("错误警报");
					}
				});
			}
		}

		/**
		 *修改到sql
		 **/
		function update() {
			var id = $("#updateid").val();
			var name = $("#name1").val();
			var content = escape(UE.getEditor('editor1').getContent());
			$.ajax({
				type : "post",
				url : "WeChatKnowledge/UpdateTwoWeChatKnowledge",
				data : {
					id : id,
					name : name,
					content : content,
				},
				timeout : 2000,
				dataType : 'json',
				success : function(data) {
					var str = data.message;
					if (str == '0404') {
						alert("系统繁忙,请稍后尝试...");
					} else if (str == '1087') {
						alert("该标题已经存在...");
					} else if (str == '1086') {
						alert("修改成功");
						$("#update").fadeOut();
						window.location.reload();
					} else if (str == '1085') {
						alert("修改失败...");
					} else {
						alert("繁忙");
					}
				},
				error : function() {
					alert("错误警报");
				}
			});
		}

		/**
		 *删除
		 **/
		function deletebean(id) {
			$.ajax({
				type : "post",
				url : "WeChatKnowledge/DeleteWeChatKnowledge",
				data : {
					id : id
				},
				timeout : 2000,
				dataType : 'json',
				success : function(data) {
					var str = data.message;
					if (str == '0404') {
						alert("系统繁忙,请稍后尝试...");
					} else if (str == '1078') {
						if (confirm("该类别下有内容，你确认要删除吗？")) {
							$
									.ajax({
										type : "post",
										url : "WeChatKnowledge/DeleteWeChatKnowledgeAll",
										data : {
											id : id
										},
										timeout : 2000,
										dataType : 'json',
										success : function(data) {
											var str = data.message;
											if (str == '0404') {
												alert("系统繁忙,请稍后尝试...");
											} else if (str == '1076') {
												alert("删除成功");
												window.location
														.reload();
											} else if (str == '1075') {
												alert("删除失败...");
											} else {
												alert("繁忙");
											}
										},
										error : function() {
											alert("错误警报");
										}
									});
						} else {
							//一级类别下面有内容，取消删除
						}
					} else if (str == '1076') {
						alert("删除成功");
						window.location.reload();
					} else if (str == '1075') {
						alert("删除失败...");
					} else {
						alert("繁忙");
					}
				},
				error : function() {
					alert("错误警报");
				}
			});
		}
		//实例化编辑器
		//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
		var ue = UE.getEditor('editor');
		var ue1 = UE.getEditor('editor1');
		$("#accordion h3").bind("mousedown", (function(e) {
			if (e.which == 3) {
				var opertionn = {
					name : "",
					offsetX : 2,
					offsetY : 2,
					textLimit : 10,
					beforeShow : $.noop,
					afterShow : $.noop
				};

				var imageMenuData = [ [ {
					text : "编辑",
					func : function() {
						var id = $(this).attr('id');
						update_xianshi(id);
					}
				} ], [ {
					text : "删除",
					func : function() {
						var id = $(this).attr('id');
						if (confirm("删除是不可恢复的，你确认要删除吗？")) {
							deletebean(id);
						}
					}
				} ] ];
				$(this).smartMenu(imageMenuData, opertionn);
			}
		}));

		var cont = document.getElementsByName("cont");//获取cont
		for ( var i = 0; i < cont.length; i++) {
			cont[i].innerHTML = unescape(cont[i].innerHTML);
		}
	</script>

</body>
</html>

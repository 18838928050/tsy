<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<%@ include file="/jsp/public/wechatknowledgeisone.jspf"%>
<body>
	<section class="rt_wrap content mCustomScrollbar">
		<div class="rt_content">
			<div class="page_title">
				<h2 class="fl">微知识专区&nbsp;&nbsp;&nbsp;&nbsp;共${size }个分类</h2>
				<font style="font-size: 15px;color: black; margin-left: 700px;font-style:normal">
					当前上线<img alt="上线" src="images/up.png" style="margin-left: 10px" />：${up_size}个，
					下线<img alt="下线" src="images/down.png" style="margin-left: 10px" />：${size-up_size }个，共${size }个
				</font> 
				<a id="xintianleibie" href="javascript:return void(0);" class="fr top_rt_btn add_icon" onclick="xianshi();">新添类别</a>
			</div>
			<div style="width: 100%;height: 84%;">
				<div
					style="width: 98%; height: 100%;float: left;margin-left: 1%;overflow-y: scroll;overflow-x: hidden;text-align: center;">
					<c:if test="${size==0 }">
						暂无数据
					</c:if>
					<c:if test="${size!=0 }">
						<ul class="ulclass">
							<c:forEach var="bean" items="${WeChatKnowledge_list}"
								varStatus="status">
								<li class="ulli" id="${bean.id }"
									onclick="chakan('${bean.id }')">
									<div class="fontcanvas">
										<c:if test="${bean.upordown==1 }">
											<div style="position: relative;">
												<img alt="上线" src="images/up.png"
													style="margin-left: -140px;margin-top: 10px;position: absolute;" />
											</div>
										</c:if>
										<c:if test="${bean.upordown==0 }">
											<div style="position: relative;">
												<img alt="下线" src="images/down.png"
													style="margin-left: -140px;margin-top: 10px;position: absolute;" />
											</div>
										</c:if>
										<div class="top1">
											<font>${bean.name }</font>
										</div>
										<div class="buttom1">
											<pre>${bean.jieshao }
												</pre>
										</div>
									</div></li>
							</c:forEach>
						</ul>
					</c:if>
				</div>
			</div>
		</div>
	</section>
	<!--弹出框效果 添加-->
	<section id="add" class="pop_bg">
		<div class="pop_cont">
			<!--title-->
			<h3>新添知识分类</h3>
			<!--content-->
			<div class="pop_cont_input">
				<ul>
					<li><span>标题</span> <input type="text" id="name" name="name"
						placeholder="定义标题..." class="textbox" />
					</li>
					<li><span class="ttl">介绍</span> <textarea id="jieshao"
							name="jieshao" class="textarea" style="height:100px;width:100%;"></textarea>
					</li>
				</ul>
			</div>
			<!--bottom:operate->button-->
			<div class="btm_btn">
				<input type="button" value="添加" class="input_btn trueBtn"
					onclick="tianjia();" /> <input type="button" value="关闭"
					class="input_btn falseBtn" onclick="guanbi();" />
			</div>
		</div>
	</section>
	<!--结束：弹出框效果-->
	<!--弹出框效果 修改-->
	<section id="update" class="pop_bg">
		<div class="pop_cont">
			<!--title-->
			<h3>修改知识分类</h3>
			<!--content-->
			<div class="pop_cont_input">
				<ul>
					<input id="updateid" name="updateid" value="" type="hidden" />
					<li><span>标题</span> <input type="text" id="updatename"
						name="updatename" placeholder="定义标题..." class="textbox" />
					</li>
					<li><span class="ttl">介绍</span> <textarea id="updatejieshao"
							name="updatejieshao" class="textarea"
							style="height:100px;width:100%;"></textarea></li>
				</ul>
			</div>
			<!--bottom:operate->button-->
			<div class="btm_btn">
				<input type="button" value="修改" class="input_btn trueBtn"
					onclick="xiugai();" /> <input type="button" value="关闭"
					class="input_btn falseBtn" onclick="updateguanbi();" />
			</div>
		</div>
	</section>
	<!--结束：弹出框效果-->
	<script type="text/javascript">
		function tianjia() {
			var name = $("#name").val();
			var jieshao = $("#jieshao").val();
			if (name == "" || name == null) {
				alert("请输入标题");
			} else if (jieshao == "" || jieshao == null) {
				alert("请描述该标题");
			} else {
				tijiao(name, jieshao);
			}
		}

		function guanbi() {
			$("#add").fadeOut();
		}

		function xianshi() {
			$("#add").fadeIn();
		}
		/**
		 *添加保存
		 */
		function tijiao(name, jieshao) {
			$.ajax({
				type : "post",
				url : "WeChatKnowledge/AddWeChatKnowledge",
				data : {
					name : name,
					jieshao : jieshao,
					jibie : '1',
					content : "",
					fatherid : '0'
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
							$.ajax({
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

		/**
		 *根据id进行查询修改
		 **/
		function select(id) {
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
						var jieshao = data.jieshao;
						//清空之前的
						$("#updateid").val("");
						$("#updatename").val("");
						$("#updatejieshao").val("");
						//重置数据
						$("#updateid").val(newid);
						$("#updatename").val(name);
						$("#updatejieshao").val(formatStr(jieshao));
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
		 * 状态切换
		 **/
		function upordown(id) {
			$.ajax({
				type : "post",
				url : "WeChatKnowledge/UpOrDown",
				data : {
					id : id
				},
				timeout : 2000,
				dataType : 'json',
				success : function(data) {
					var str = data.message;
					if (str == '1069') {
						alert("当前状态：[上线]");
						window.location.reload();
					} else if (str == '1068') {
						alert("当前状态：[下线]");
						window.location.reload();
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
		 * 修改
		 */
		function xiugai() {
			var id = $("#updateid").val();
			var name = $("#updatename").val();
			var jieshao = $("#updatejieshao").val();
			$.ajax({
				type : "post",
				url : "WeChatKnowledge/UpdateOneWeChatKnowledge",
				data : {
					id : id,
					name : name,
					jieshao : jieshao,
				},
				timeout : 2000,
				dataType : 'json',
				success : function(data) {
					var str = data.message;
					if (str == '0404') {
						alert("系统繁忙,请稍后尝试...");
					} else if (str == '1077') {
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
		 *修改框关闭
		 **/
		function updateguanbi() {
			$("#update").fadeOut();
		}

		/**
		 *查看
		 **/
		function chakan(id) {
			window.location.href = "WeChatKnowledge/SelectAllIsTwo?id=" + id;
		}
		$(".ulclass li").bind("mousedown", (function(e) {
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
						select(id);
					}
				} ], [ {
					text : "删除",
					func : function() {
						var id = $(this).attr('id');
						if (confirm("删除是不可恢复的，你确认要删除吗？")) {
							deletebean(id);
						}
					}
				} ], [ {
					text : "查看",
					func : function() {
						var id = $(this).attr('id');
						chakan(id);
					}
				} ], [ {
					text : "状态切换",
					func : function() {
						var id = $(this).attr('id');
						if (confirm("确定切换？")) {
							upordown(id);
						}
					}
				} ] ];
				$(this).smartMenu(imageMenuData, opertionn);
			}
		}));
	</script>
</body>
</html>

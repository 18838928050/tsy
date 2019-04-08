<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<body style="overflow: scroll;">
	<section class="rt_wrap content">
	<form action="scillorpic/AddToSql" method="post"
		enctype="multipart/form-data">
		<div class="rt_content">
			<div class="page_title">
				<h2 class="fl">通知栏公告新增</h2>
			</div>
			<ul class="ulColumn2">
				<li style="text-align: left;margin-left: 400px">
					<hr style="width: 60%;color: black;height: 1px;margin-left: 0px" />
				</li>
				<li style="text-align: left;margin-left: 400px"><span
					class="item_name">文章标题</span>
				</li>
				<li style="text-align: left;margin-left: 400px">
					<hr style="width: 60%;color: black;height: 1px;margin-left: 0px" />
				</li>
				<li style="text-align: left;margin-left: 400px"><input
					type="text" class="textbox textbox_225" value=""
					id="scollorPicName" name="scollorPicName" style="width: 60%" />
				</li>
				<li style="text-align: left;margin-left: 400px">
					<hr style="width: 60%;color: black;height: 1px;margin-left: 0px" />
				</li>
				<li style="text-align: left;margin-left: 400px"><span
					class="item_name">上传图片(建议大小：1920*400)</span></li>
				<li style="text-align: left;margin-left: 400px">
					<hr style="width: 60%;color: black;height: 1px;margin-left: 0px" />
				</li>
				<li style="text-align: left;margin-left: 400px"><label
					class="uploadImg"> <input type="file" id="img" name="img" onchange="change()" accept="image/*"/>
						<span>上传图片</span> </label>
				</li>
				<li style="text-align: left;margin-left: 400px">
					已选<span id="size" style="color: red">0</span>张图片
				</li>
				<li id="lujingli" style="text-align: left;margin-left: 400px;display: none;">
					路径：<span id="lujing" style="color: red"></span>
				</li>
				<li style="text-align: left;margin-left: 400px">
					<hr style="width: 60%;color: black;height: 1px;margin-left: 0px" />
				</li>
				<li style="text-align: left;margin-left: 400px"><span
					class="item_name"
					style="font-size: 12px;color: gray;font-weight: 2">摘要：（默认自动提取您文章的前100字显示在博客首页作为文章摘要，您也可以在这里自行编辑
						）</span>
				</li>
				<li style="text-align: left;margin-left: 400px"><textarea
						id="scollorPicIntroduce" name="scollorPicIntroduce"
						class="textarea" style="height:100px;width:60%;"></textarea></li>
				<li style="text-align: left;margin-left: 400px">
					<hr style="width: 60%;color: black;height: 1px;margin-left: 0px" />
				</li>
				<li style="text-align: left;margin-left: 400px"><span
					class="item_name">文章内容</span></li>
				<li style="text-align: left;margin-left: 400px"><script
						id="editor1" type="text/plain"
						style="width:78%;height:600px;margin-top:0;"></script>
				</li>
				<input style="display: none;" id="scollorPicContent" name="scollorPicContent"/>
				<li style="text-align: left;margin-left: 400px">
					<hr style="width: 60%;color: black;height: 1px;margin-left: 0px" />
				</li>
				<li><input type="submit" onclick="javaScript:return tianjia()" class="link_btn" value="添加" /></li>
				<li style="text-align: left;margin-left: 400px">
					<hr style="width: 60%;color: black;height: 1px;margin-left: 0px" />
				</li>
			</ul>
		</div>
		<div style="height: 200px"></div>
	</form>
	</section>
	<script type="text/javascript">
		/**
		 *添加到sql
		 **/
		function tianjia() {
			var img = $("#lujing").html();
			var scollorPicName = $("#scollorPicName").val();
			var content = escape(UE.getEditor('editor1').getContent());
			var chucontent = UE.getEditor('editor1').getContentTxt();
			if (scollorPicName == "" || scollorPicName == null) {
				alert("请输入标题");
				return false;
			} else if (content == "" || content == null) {
				alert("请输入文章内容");
				return false;
			} else if(img==""||img==null){
				alert("请选择图片");
				return false;
			} else if(chucontent.length<150){
				alert("文本内容不得小于150字");
				return false;
			}else{
				if($("#scollorPicIntroduce").val()==null||""==$("#scollorPicIntroduce").val()){
					$("#scollorPicIntroduce").val(chucontent.substring(0,100));
				}
				$("#scollorPicContent").val(content);
				return true;
			}
		}
		function change(){
			$("#size").html(1);
			$("#lujing").html($("#img").val());
			$("#lujingli").show();
		}
	</script>
	<script src="js/ueditor.config.js"></script>
	<script src="js/ueditor.all.min.js">
		
	</script>
	<script type="text/javascript">
		//实例化编辑器
		//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
		var ue1 = UE.getEditor('editor1');
	</script>
</body>
</html>

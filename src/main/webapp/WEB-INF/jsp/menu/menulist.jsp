<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<%@ include file="/jsp/public/phone.jspf"%>
<body oncontextmenu="return false;" onselectstart="return false;">
	<section class="rt_wrap content mCustomScrollbar">
		<div class="rt_content">
			<div class="page_title">
				<h2 class="fl">自定义菜单</h2>
				<a id="jiansuogengxin" href="javaScript:;" class="fr top_rt_btn add_icon" onclick="fabu()">发布</a>
			</div>
			<div style="width: 100%;height: 84%;">
				<div style="width: 48%; height: 100%;float: left;margin-left: 1%;border:1px dashed #787878;">
					<div class="big">
						<div class="box">
							<div class="box_call">
								<div class="shexiangtou">
									<div class="shexiangtou_1"></div>
								</div>
								<div class="shengyin">
									<div class="shengyin_1"></div>
									<div class="shengyin_2">
										<div class="shengyin_3"></div>
									</div>
								</div>
								<div class="neirong" style="background: url('images/abg.png');background-size: 100% 100%;background-position: center;background-repeat: no-repeat;">
									<div id="btn3" class="btn3 clearfix">
										<c:forEach var="bean" items="${first_list}" varStatus="status">
											<div class="menu" onclick="ps1('ul${status.index}','${status.index}','${bean.id }');">
												<div class="bt-name">
													<a id="${bean.id }" onclick="Select('${bean.id }','${bean.menulevel }','${bean.haschild }','${bean.menuname }');">${bean.menuname}</a>
												</div>
												<div class="sanjiao"></div>
												<div class="new-sub">
													<ul id="ul${status.index}">
														<c:forEach var="bean1" items="${two_list}" varStatus="status1">
															<c:if test="${bean1.menubolong==bean.id }">
																<li>
																	<a onclick="Select('${bean1.id }','${bean1.menulevel }','${bean1.haschild }','${bean1.menuname }');">${bean1.menuname}</a>
																</li>
															</c:if>
														</c:forEach>
													</ul>
													<div class="tiggle"></div>
													<div class="innertiggle"></div>
												</div>
											</div>
										</c:forEach>
										<!--menu-->
									</div>
								</div>
								<div class="retu">
									<div class="retu_1">
										<div class="retu_2"></div>
									</div>
								</div>

							</div>
						</div>
						<div class="button"></div>
						<div class="button1"></div>
						<div class="button2"></div>
					</div>
				</div>
				<div style="width: 48%; height: 100%;float: left;margin-left: 1%;border:1px dashed #787878;background-color: RGB(244,245,249);">
					<div style="width: 100%; height: 8%;float: left;border-bottom:1px solid RGB(231,231,235);">
						<b style="float: left;font-size: 12px;margin-top: 20px;margin-left: 20px">
						菜单名称 <span id="xitong" style="display: none;">---系统KEY</span> <span id="key"></span>
						</b> 
						<a style="float: right;font-size: 12px;margin-top: 20px;color: blue;margin-right: 20px" onclick="deletecaidan()">删除菜单</a>
					</div>
					<div id="mingcheng" style="width: 100%; height: 15%;float: left;margin-top: 30px;display: none;">
						<p>
							<b style="float: left;font-size: 13px;margin-top: 10px;margin-left: 20px">菜单名称</b>
							<input type="text" class="textbox textbox_225" id="menuname" name="menuname" value="" style="margin-left: 20px" onchange="kp(this)" /> 
							<input type="hidden" class="textbox textbox_225" id="selectid" name="selectid" value="-1" />
						</p>
						<p>
							<span style="color: #C1CDC1;margin-left: 100px;">字数不超过4个汉字或8个字母</span>
						</p>
					</div>
					<div id="caidanneirong" style="width: 100%; height: 50%;float: left;margin-top: 15px;display: none;">
						<b style="float: left;font-size: 13px;margin-top: 10px;margin-left: 20px">菜单内容</b>
						<a style="float: left;font-size: 13px;margin-top: 10px;margin-left: 20px">
							<input type="radio" name="field_name" value="1" id="Like1" onclick="tiaozhuan('1')" />
							<label for="Like1" onclick="tiaozhuan('1')">发送消息</label> 
						</a> 
						<a style="float: left;font-size: 13px;margin-top: 10px;margin-left: 20px;">
							<input type="radio" name="field_name" value="2" id="Like2" onclick="tiaozhuan('2')" />
							<label for="Like2" onclick="tiaozhuan('2')">跳转网页</label> 
						</a> <br />
						<div id="xiaoxi" style="width: 90%;height: 80%;background-color: #fff;margin-left: 5%;margin-top: 30px;">
							<div style="width: 100%; height: 15%;float: left;border-bottom:1px solid RGB(231,231,235);">
								<b id="ca1" class="gong xscolor" onclick="chongzhi('ca1')">
									<img id="imgca1" alt="图文消息" src="images/tuwen1.png" width="15px" height="15px" /><font>图文消息</font> 
								</b>
								<b id="ca2" class="gong nxscolor" onclick="chongzhi('ca2')">
									<img id="imgca2" alt="图片" src="images/tupian.png" width="15px" height="15px" /><font>图片</font> 
								</b> 
								<b id="ca3" class="gong nxscolor" onclick="chongzhi('ca3')">
									<img id="imgca3" alt="语音" src="images/yuyin.png" width="15px" height="15px" /><font>语音</font> 
								</b> 
								<b id="ca4" class="gong nxscolor" onclick="chongzhi('ca4')">
									<img id="imgca4" alt="视频" src="images/shipin.png" width="15px" height="15px" /><font>视频</font> 
								</b> 
								<b id="ca5" class="gong nxscolor" onclick="chongzhi('ca5')">
									<img id="imgca5" alt="文字" src="images/wenzi.png" width="15px" height="15px" /><font>文字</font> 
								</b>
							</div>
							<div id="tuwen" class="checkMessage" >
								<div style="width: 100%;height: 100%;">
									<ul id="graphicMessage" style="width: 100%;height: 84%;overflow-y: scroll;overflow-x:hidden">
										
									</ul>
								</div>
							</div>
							<div id="tupian" class="checkMessage" style="display: none;">
								暂未开放</div>
							<div id="yuyin" class="checkMessage" style="display: none;">
								暂未开放</div>
							<div id="shipin" class="checkMessage" style="display: none;">
								暂未开放</div>
							<div id="wenzi" class="checkMessage" style="display: none;">
								<textarea id="context" name="context" placeholder="摘要信息" class="textarea" style="width:100%;height:84%;"></textarea>
							</div>
						</div>
						<div id="wangye" style="width: 90%;height: 60%;background-color: #fff;margin-left: 5%;margin-top: 30px;display: none;">
							<div style="width: 100%; height: 15%;float: left;">
								<b style="float: left;font-size: 12px;margin-top: 15px;margin-left: 30px;color: #C1CDC1">订阅者点击该子菜单会跳到以下链接</b>
							</div>
							<div style="width: 100%; height: 85%;float: left;margin-top: 20px">
								<p style="margin-left: 30px;">
									页面地址 <input type="text" id="wytext" name="wytext" class="textbox textbox_225" value="" style="margin-left: 20px" />
								</p>
							</div>
						</div>
					</div>
					<div id="wanchenganniu" style="width: 90%;height: 5%;background-color: #fff;margin-left: 5%;margin-top: 30px;float: left;display: none;">
						<input type="button" class="link_btn" value="完成" onclick="updateMenu()" />
					</div>
				</div>
			</div>
		</div>
	</section>
	<script id="select-Top" type="text/x-handlebars-template"> 
 		{{#each rows}}
			<li  style="width: 100%;height: 20px;text-align: left;padding-left: 70px;padding-top: 10px;">
				<input type="radio" rowId="{{id}}" name="graphicMessageId"/>
				<font>{{graphicMessageTitle}}</font>
			</li>
		{{/each}}
	</script>
	<script type="text/javascript" src="jsp/js/util/main.js"></script>
	<script src="jsp/js/util/handlebars-v4.0.5.js"></script>
	<script type="text/javascript" src="jsp/js/menu/menu.js"></script>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<%@ include file="/jsp/public/news.jspf"%>
<body>
	<input id="p_add" value="-1" type="hidden" />
	<section class="rt_wrap content mCustomScrollbar">
	<div class="rt_content">
		<div class="page_title">
			<h2 class="fl">体育新闻</h2>
			<a id="opener" href="news/ToTiyuListPage" class="fr top_rt_btn add_icon">刷新</a>
		</div>
		<div id="dialog" title="Basic dialog">
			<p>
				<iframe scrolling="auto" style="width: 100%;height: 100%;" id="load"
					frameborder="0"></iframe>
			</p>
		</div>
		<div id="wrap">
			<c:forEach items="${newslist}" var="bean" varStatus="status">
				<div class="box">
					<div class="info">
						<div class="pic">
							<img src="${bean.thumbnail_pic_s }"
								onclick="jiazaiUrl('${bean.url}','${status.index }')">
						</div>
						<div class="title">
							<a href="#">${bean.title }</a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	</section>
	<c:forEach items="${newslist}" var="a">
		<input type="hidden" value="${a.realtype }" name="realtype" />
		<input type="hidden" value="${a.title }" name="title" />
		<input type="hidden" value="${a.thumbnail_pic_s03 }"
			name="thumbnail_pic_s03" />
		<input type="hidden" value="${a.uniquekey }" name="uniquekey" />
		<input type="hidden" value="${a.thumbnail_pic_s02 }"
			name="thumbnail_pic_s02" />
		<input type="hidden" value="${a.author_name }" name="author_name" />
		<input type="hidden" value="${a.thumbnail_pic_s }"
			name="thumbnail_pic_s" />
		<input type="hidden" value="${a.type }" name="type" />
		<input type="hidden" value="${a.date }" name="date" />
		<input type="hidden" value="${a.url }" name="url" />
	</c:forEach>
	<script type="text/javascript">
		var thumbnail_pic_s = document.getElementsByName("thumbnail_pic_s");//获取一张图片
		var thumbnail_pic_s2 = document.getElementsByName("thumbnail_pic_s03");//获取一张图片
		var thumbnail_pic_s3 = document.getElementsByName("thumbnail_pic_s02");//获取一张图片
		var title_all = document.getElementsByName("title");//获取标题
		var realtype = document.getElementsByName("realtype");//获取链接
		var uniquekey = document.getElementsByName("uniquekey");
		var author_name = document.getElementsByName("author_name");
		var type = document.getElementsByName("type");
		var date = document.getElementsByName("date");
		var url = document.getElementsByName("url");
	
		var index = 0;//下标
	
		var size = '${size}';
	
		var data = new Array();
		for ( var i = 0; i < size; i++) {
			data.push({
				src : thumbnail_pic_s[i].value,
				thumbnail_pic_s2 : thumbnail_pic_s2[i].value,
				thumbnail_pic_s3 : thumbnail_pic_s3[i].value,
				title : title_all[i].value,
				realtype : realtype[i].value,
				uniquekey : uniquekey[i].value,
				author_name : author_name[i].value,
				type : type[i].value,
				date : date[i].value,
				url : url[i].value
			});
		}
	
		window.onload = function() {
			//运行瀑布流主函数
			PBL('wrap', 'box');
			//设置滚动加载
			document.getElementById("wrap").onscroll = function() {
				//校验数据请求
				if (getCheck()) {
	
				}
			};
			//弹出框
			$("#dialog").dialog({
				modal : true,
				autoOpen : false,
				width : 1000,
				closed : false,
				cache : false,
				height : 600,
				title : "内容",
				show : {
					effect : "blind",
					duration : 1000
				},
				hide : {
					effect : "blind",
					duration : 500
				},
				buttons : {
					"添加" : function() {
						//这里可以加入一些操作
						add();
					},
					"取消" : function() {
						$("#dialog").dialog("close");
					}
				},
			});
		};
	
		/**
		 * 瀑布流主函数
		 * @param  wrap	[Str] 外层元素的ID
		 * @param  box 	[Str] 每一个box的类名
		 */
		function PBL(wrap, box) {
			//	1.获得外层以及每一个box
			var wrap = document.getElementById(wrap);
			var boxs = getClass(wrap, box);
			//	2.获得屏幕可显示的列数
			var boxW = boxs[0].offsetWidth;
			var colsNum = Math.floor(document.documentElement.clientWidth / boxW);
			wrap.style.width = boxW * colsNum + 'px';//为外层赋值宽度
			//	3.循环出所有的box并按照瀑布流排列
			var everyH = [];//定义一个数组存储每一列的高度
			for ( var i = 0; i < boxs.length; i++) {
				if (i < 5) {
					everyH[i] = boxs[i].offsetHeight;
				} else {
					var minH = Math.min.apply(null, everyH);//获得最小的列的高度
					var minIndex = getIndex(minH, everyH); //获得最小列的索引
					getStyle(boxs[i], minH, boxs[minIndex].offsetLeft, i);
					everyH[minIndex] += boxs[i].offsetHeight;//更新最小列的高度
				}
			}
		}
		/**
		 * 获取类元素
		 * @param  warp		[Obj] 外层
		 * @param  className	[Str] 类名
		 */
		function getClass(wrap, className) {
			var obj = wrap.getElementsByTagName('*');
			var arr = [];
			for ( var i = 0; i < obj.length; i++) {
				if (obj[i].className == className) {
					arr.push(obj[i]);
				}
			}
			return arr;
		}
		/**
		 * 获取最小列的索引
		 * @param  minH	 [Num] 最小高度
		 * @param  everyH [Arr] 所有列高度的数组
		 */
		function getIndex(minH, everyH) {
			for (ind in everyH) {
				if (everyH[ind] == minH)
					return ind;
			}
		}
		/**
		 * 数据请求检验
		 */
		function getCheck() {
			var documentH = document.documentElement.clientHeight;
			var scrollH = document.documentElement.scrollTop
					|| document.getElementById("wrap").scrollTop;
			return documentH + scrollH >= getLastH() ? true : false;
		}
		/**
		 * 获得最后一个box所在列的高度
		 */
		function getLastH() {
			var wrap = document.getElementById('wrap');
			var boxs = getClass(wrap, 'box');
			return boxs[boxs.length - 1].offsetTop
					+ boxs[boxs.length - 1].offsetHeight;
		}
		/**
		 * 设置加载样式
		 * @param  box 	[obj] 设置的Box
		 * @param  top 	[Num] box的top值
		 * @param  left 	[Num] box的left值
		 * @param  index [Num] box的第几个
		 */
		var getStartNum = 0;//设置请求加载的条数的位置
		function getStyle(box, top, left, index) {
			if (getStartNum >= index)
				return;
			$(box).css({
				'position' : 'absolute',
				'top' : top,
				'left' : left,
				'opacity' : 0
			});
			$(box).stop().animate({
				"opacity" : "1"
			}, 999);
			getStartNum = index;//更新请求数据的条数位置
		}
		function jiazaiUrl(url, index) {
			$("#p_add").val(index);
			$("#dialog").dialog("open");
			$("#load").attr("src", url);
		}
	
		function add() {
			var index = $("#p_add").val();
			$.ajax({
				type : "post",
				url : "news/AddNewsBean",
				data : {
					thumbnail_pic_s : data[index].src,
					thumbnail_pic_s2 : data[index].thumbnail_pic_s2,
					thumbnail_pic_s3 : data[index].thumbnail_pic_s3,
					title : data[index].title,
					realtype : data[index].realtype,
					uniquekey : data[index].uniquekey,
					author_name : data[index].author_name,
					type : data[index].type,
					date : data[index].date,
					url : data[index].url,
				},
				timeout : 2000,
				dataType : 'json',
				success : function(data) {
					var str = data.message;
					if (str == '0404') {
						alert("数据异常");
					} else if (str == '1095') {
						alert("添加失败");
					} else if (str == '1096') {
						alert("添加成功");
						$("#dialog").dialog("close");
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

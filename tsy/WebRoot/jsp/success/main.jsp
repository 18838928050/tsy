<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
</head>
<body>
	<section class="rt_wrap content mCustomScrollbar">
	<div class="rt_content">
		<!--统计图-->
		<section style="overflow:hidden"> <!--柱状图-->
			<div class="div-W100-H40"></div>
			<div class="dataStatistic fl peoplenum">
				<div class="numIcon">
					<img alt="" src="images/qxgz.png">
					<font id="qxrs">0</font>
				</div>
				<div class="numcontent">
					<font>取关人数</font>
				</div>
			</div>
			<div class="dataStatistic fl peoplenum">
				<div class="numIcon">
					<img alt="" src="images/djrs.png">
					<font id="djrs">0</font>
				</div>
				<div class="numcontent">
					<font>冻结人数</font>
				</div>
			</div>
			<div class="dataStatistic fl peoplenum peoplenumall">
				<div class="numIcon">
					<img alt="" src="images/gzr.png">
					<font id="zrs">0</font>
				</div>
				<div class="numcontent">
					<font>总人数</font>
				</div>
			</div>
			<div class="div-W100-H60"></div>
			<div class="dataStatistic fl">
				<div id="cylindrical"></div>
			</div>
			<!--线性图-->
			<div class="dataStatistic fl">
				<div id="line"></div>
			</div>
			<!--饼状图-->
			<div class="dataStatistic fl">
				<div id="pie"></div>
			</div>
		</section>
	</div>
	</section>
	<script src="js/amcharts.js" type="text/javascript"></script>
	<script src="js/serial.js" type="text/javascript"></script>
	<script src="js/pie.js" type="text/javascript"></script>
	<script src="jsp/js/util/main.js" type="text/javascript"></script>
	<script src="jsp/js/success/main.js" type="text/javascript"></script>
</body>
</html>

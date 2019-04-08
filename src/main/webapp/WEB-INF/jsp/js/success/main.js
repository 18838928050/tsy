
$(function(e){
	dataInit();
	eventInit();
});

function dataInit(){
	numInit();
}

function eventInit(){

}

function numInit(){
	AjaxUtil.request({url:path+"/post/TsyMhUserController/queryUserStatistics",params:{},type:'json',callback:function(json){
		if(json.returnCode=="0"){
			$("#zrs").html(json.bean.allNum);
			$("#djrs").html(json.bean.fzNum);
			$("#qxrs").html(json.bean.subscribeNum);
			loginNumInit();
		}
		}
	});
}

function loginNumInit(){
	AjaxUtil.request({url:path+"/post/TsyMhUserController/queryLatelyMonthLog",params:{},type:'json',callback:function(json){
		if(json.returnCode=="0"){
			MakeChart(json.rows);
			loginNumThisEightDayInit();
		}
		}
	});
}

function loginNumThisEightDayInit(){
	AjaxUtil.request({url:path+"/post/TsyMhUserController/queryLatelyEightDayLog",params:{},type:'json',callback:function(json){
		if(json.returnCode=="0"){
			GetZxtu(json.rows);
			GetSerialChart(json.rows);
		}
		}
	});
}

function GetZxtu(json){
	var chart = new AmCharts.AmSerialChart();
	chart.dataProvider = json;
	chart.categoryField = "loginShowData";
	chart.angle = 30;
	chart.depth3D = 20;
	//标题
	chart.addTitle("最近八天登录情况折线报告图", 15);  
	var graph = new AmCharts.AmGraph();
	chart.addGraph(graph);
	graph.valueField = "loginNum";
	//背景颜色透明度
	graph.fillAlphas = 0.3;
	//类型
	graph.type = "line";
	//圆角
	graph.bullet = "round";
	//线颜色
	graph.lineColor = "#8e3e1f";
	//提示信息
	graph.balloonText = "[[loginData]]登录[[loginNum]]次";
	var categoryAxis = chart.categoryAxis;
	categoryAxis.autoGridCount = false;
	categoryAxis.gridCount = json.length;
	categoryAxis.gridPosition = "start";
	chart.write("line");
}

//柱状图  
function GetSerialChart(json) {
	chart = new AmCharts.AmSerialChart();
	chart.dataProvider = json;
	//json数据的key  
	chart.categoryField = "loginShowData";
	chart.addTitle("最近八天登录情况柱状报告图", 15);  
	//不选择      
	chart.rotate = false;
	//值越大柱状图面积越大  
	chart.depth3D = 20;
	//柱子旋转角度角度
	chart.angle = 30;
	var mCtCategoryAxis = chart.categoryAxis;
	mCtCategoryAxis.axisColor = "#efefef";
	//背景颜色透明度
	mCtCategoryAxis.fillAlpha = 0.5;
	//背景边框线透明度
	mCtCategoryAxis.gridAlpha = 0;
	mCtCategoryAxis.fillColor = "#efefef";
	var valueAxis = new AmCharts.ValueAxis();
	//左边刻度线颜色  
	valueAxis.axisColor = "#ccc";
	//标题
	valueAxis.title = "3D柱状图Demo";
	//刻度线透明度
	valueAxis.gridAlpha = 0.2;
	chart.addValueAxis(valueAxis);
	var graph = new AmCharts.AmGraph();
	graph.title = "loginNum";
	graph.valueField = "loginNum";
	graph.type = "column";
	//鼠标移入提示信息
	graph.balloonText = "[[category]]登录[[loginNum]]次";
	//边框透明度
	graph.lineAlpha = 0.3;
	//填充颜色 
	graph.fillColors = "#b9121b";
	graph.fillAlphas = 1;
	chart.addGraph(graph);
	// CURSOR
	var chartCursor = new AmCharts.ChartCursor();
	chartCursor.cursorAlpha = 0;
	chartCursor.zoomable = false;
	chartCursor.categoryBalloonEnabled = false;
	chart.addChartCursor(chartCursor);
	chart.creditsPosition = "top-right";
	//显示在Main div中
	chart.write("cylindrical");
}

//饼图
//根据json数据生成饼状图，并将饼状图显示到div中
function MakeChart(value) {
	chartData = eval(value);
	//饼状图
	chart = new AmCharts.AmPieChart();
	chart.dataProvider = chartData;
	//标题数据
	chart.titleField = "login_place";
	//值数据
	chart.valueField = "loginNum";
	//边框线颜色
	chart.outlineColor = "#fff";
	//边框线的透明度
	chart.outlineAlpha = .8;
	//边框线的狂宽度
	chart.outlineThickness = 1;
	chart.depth3D = 20;
	chart.angle = 30;
	chart.write("pie");
}


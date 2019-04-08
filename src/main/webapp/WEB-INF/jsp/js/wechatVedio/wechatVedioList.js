
var optionTitle = null;
var optionState = null;

$(function(){
	init();
});

function init(){
	dataInit();
	eventInit();
}

function dataInit(){
	//1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
}

function eventInit(){
	$('body').on('click', '#select', function(e){
		optionTitle = $("#optionTitle").val();
		optionState = $("#optionState").val();
		$('#massage').bootstrapTable('refresh', {url: path+'/post/TsyUserCustomOptions/queryVedioList'});  
	});
	$('body').on('click', '#addVedio', function(e){
		location.href = "addwechatVedio.html"; 
	});
}

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#massage').bootstrapTable({
            url: path+'/post/TsyUserCustomOptions/queryVedioList',             //请求后台的URL（*）
            method: 'post',                     //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: true,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                    //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            contentType: "application/x-www-form-urlencoded",         //发送到服务器的数据编码类型
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            height: 650,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                  //是否显示父子表
            columns: [{
                checkbox: true
            }, {
            	field: 'Number',
            	title: '序号',
            	width: '50',
            	align: 'center',
            	formatter: function (value, row, index) {
            		return index+1;
            	}
            }, {
                field: 'option_title',
                title: '名称',
                align: 'center',
                width: '150',
                formatter: function (value, row, index) {
                	return '<div style="word-wrap:break-word;">'+value+'</div>';
                }
            }, {
                field: 'customTitle',
                title: '所属模块',
                align: 'center',
                width: '150',
                formatter: function (value, row, index) {
                	return '<div style="word-wrap:break-word;">'+value+'</div>';
                }
            }, {
                field: 'optionPicPath',
                title: '展示',
                align: 'center',
                width: '170',
                formatter: function (value, row, index) {
                	return '<img style="width:160px;height:90px" src='+path+'\\'+value+'/>';
                }
            }, {
                field: 'option_time',
                title: '创建时间',
                align: 'center',
                width: '120',
                formatter: function (value, row, index) {
                	return datetimeFormat_1(value).substring(0,10);
                }
            },{
                field: 'no',
                title: '创建人',
                align: 'center',
                width: '120'
            }, {
                field: 'option_state',
                title: '状态',
                align: 'center',
                width: '120',
                formatter: function (value, row, index) {
                	if(value=="0"){
                		return "审核中";
                	}else if(value=="1"){
                		return "审核通过";
                	}else if(value=="2"){
                		return "审核不通过";
                	}else if(value=="3"){
                		return "非法文件";
                	}else{
                		return "数据错误";
                	}
                }
            }, {
                field: 'option_type',
                title: '类型',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	if(value=='1'){
                		return '视频';
                	}else{
                		return '文档';
                	}
                }
            }, {
                field: 'labalTitle',
                title: '标签',
                align: 'center',
                width: '200',
                formatter: function (value, row, index) {
                	return '<div style="word-wrap:break-word;">'+value+'</div>';
                }
            }, {
                field: 'optionNameSize',
                title: '附件数',
                align: 'center',
                width: '100',
            }, {
	            field: 'operate',
	            title: '操作',
	            width: '300',
	            align: 'center',
	            events: EvenInit,
	            formatter: operateFormatter
            }],
            onLoadSuccess: function(){  //加载成功时执行
//            	console.log("加载成功");
            },
            onLoadError: function(){  //加载失败时执行
            	console.log("加载数据失败", {time : 1500, icon : 2});
            }
        });
    };
    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {
        	limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            optionTitle: optionTitle,
            optionSte: optionState
        };
        return temp;
    };
    return oTableInit;
}

window.EvenInit = {
    'click .RoleOfA': function (e, value, row, index) {
        var param = {
        		id:row.id,
        		nowState:row.option_state,
        		updateState:'1',
        };
        updateState(param);
    },
    'click .RoleOfB': function (e, value, row, index) {
    	var param = {
        		id:row.id,
        		nowState:row.option_state,
        		updateState:'2',
        };
    	updateState(param);
    },
    'click .RoleOfC': function (e, value, row, index) {
    	var param = {
        		id:row.id,
        		nowState:row.option_state,
        		updateState:'3',
        };
    	updateState(param);
    },
    'click .RoleOfD': function (e, value, row, index) {
    	var vedioId = row.id;
    	location.href = "updatewechatVedio.html?id="+vedioId;
    }
};
function operateFormatter(value, row, index) {
	var str = "";
	if(row.option_state=="0"){
		str = [ '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">审核通过</button>',
				'<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">审核不通过</button>',];
	}else if(row.option_state=="1"||row.option_state=="2"){
		str = [ '<button type="button" class="RoleOfD btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
		        '<button type="button" class="RoleOfC btn btn-default  btn-sm" style="margin-right:15px;">非法设置</button>'];
	}else{
		str = [];
	}
    return str.join('');
}

function updateState(param){
	AjaxUtil.request({url:path+"/post/TsyUserCustomOptions/updateVedioState",params:param,type:'json',callback:function(json){
			if(json.returnCode=="1086"){
				qiao.bs.msg({msg:json.returnMessage,type:'success'});
				optionTitle = $("#optionTitle").val();
				optionState = $("#optionState").val();
				$('#massage').bootstrapTable('refresh', {url: path+'/post/TsyUserCustomOptions/queryVedioList'});  
			}else if(json.returnCode=="1087"){
				qiao.bs.msg({msg:json.returnMessage,type:'warning'});
			}
		}
	});
}

function datetimeFormat_1(longTypeDate){  
    var datetimeType = "";  
    var date = new Date();  
    date.setTime(longTypeDate);  
    datetimeType+= date.getFullYear();   //年  
    datetimeType+= "-" + getMonth(date); //月   
    datetimeType += "-" + getDay(date);   //日  
    datetimeType+= "&nbsp;&nbsp;" + getHours(date);   //时  
    datetimeType+= ":" + getMinutes(date);      //分
    datetimeType+= ":" + getSeconds(date);      //分
    return datetimeType;
} 
//返回 01-12 的月份值   
function getMonth(date){  
    var month = "";  
    month = date.getMonth() + 1; //getMonth()得到的月份是0-11  
    if(month<10){  
        month = "0" + month;  
    }  
    return month;  
}  
//返回01-30的日期  
function getDay(date){  
    var day = "";  
    day = date.getDate();  
    if(day<10){  
        day = "0" + day;  
    }  
    return day;  
}
//返回小时
function getHours(date){
    var hours = "";
    hours = date.getHours();
    if(hours<10){  
        hours = "0" + hours;  
    }  
    return hours;  
}
//返回分
function getMinutes(date){
    var minute = "";
    minute = date.getMinutes();
    if(minute<10){  
        minute = "0" + minute;  
    }  
    return minute;  
}
//返回秒
function getSeconds(date){
    var second = "";
    second = date.getSeconds();
    if(second<10){  
        second = "0" + second;  
    }  
    return second;  
}



var wechatMessageFromUser = null;

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
		wechatMessageFromUser = $("#OpenId").val();
		$('#massage').bootstrapTable('refresh', {url: path+'/post/WechatMessage/selectWechatMessage'});  
	});
}

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#massage').bootstrapTable({
            url: path+'/post/WechatMessage/selectWechatMessage',             //请求后台的URL（*）
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
                field: 'wechat_message_from_user',
                title: 'OpenId',
                align: 'center',
                width: '250'
            }, {
                field: 'nickname',
                title: '发送者',
                align: 'center',
                width: '250'
            }, {
                field: 'wechat_message',
                title: '消息体',
                align: 'center',
            }, {
                field: 'wechat_message_time',
                title: '发送时间',
                align: 'center',
                width: '200',
                formatter: function (value, row, index) {
                	return value.substring(0,10);
                }
            }, {
                field: 'wechat_message_res',
                title: '回复状态',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	if(value=='1'){
                		return '<a style="color:green">已回复</a>';
                	}else{
                		return '<a style="color:red">待回复</a>';
                	}
                }
            }, {
                field: 'frozen',
                title: '发送人状态',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	if(value=='1'){
                		return '正常';
                	}else{
                		return '已冻结';
                	}
                }
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
            wechatMessageFromUser: wechatMessageFromUser
        };
        return temp;
    };
    return oTableInit;
}


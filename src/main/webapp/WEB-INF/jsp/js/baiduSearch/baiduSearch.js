
var titleName = null;

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
		titleName = $("#titleName").val();
		$('#massage').bootstrapTable('refresh', {url: path+'/post/TsyOptionsEvaluate/queryBaiDuSearch'});  
	});
	$('body').on('click', '#reset', function(e){
		$("#titleName").val("");
	});
}

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#massage').bootstrapTable({
            url: path+'/post/TsyOptionsEvaluate/queryBaiDuSearch',             //请求后台的URL（*）
            method: 'post',                     //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                    //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            contentType: "application/x-www-form-urlencoded",         //发送到服务器的数据编码类型
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10],        //可供选择的每页的行数（*）
            height:700,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                  //是否显示父子表
            columns: [{
                field: 'title',
                title: '目录名',
                align: 'center',
                width: '150',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            }, {
                field: 'titleURI',
                title: '目录URL',
                align: 'center',
                width: '300',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            }, {
                field: 'name',
                title: '上传人',
                align: 'center',
                width: '200',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            }, {
                field: 'nameURI',
                title: '上传人其他分享',
                align: 'center',
                width: '300',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            }, {
	            field: 'operate',
	            title: '操作',
	            width: '160',
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
            titleName: titleName
        };
        return temp;
    };
    return oTableInit;
}

window.EvenInit = {
    'click .RoleOfA': function (e, value, row, index) {
        window.open(row.titleURI);
    },
    'click .RoleOfB': function (e, value, row, index) {
    	window.open(row.nameURI);
    }
};
function operateFormatter(value, row, index) {
    return [
        '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">查看</button>',
        '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">他的分享</button>',
    ].join('');
}
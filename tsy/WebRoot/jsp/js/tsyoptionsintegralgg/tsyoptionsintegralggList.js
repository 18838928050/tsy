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
    
    $('#optionsIntegralType').multiselect({
		enableClickableOptGroups: true,
		inheritClass: true,
		enableCollapsibleOptGroups: true,
		includeSelectAllOption: false,
		enableFiltering: true,
		buttonWidth:'100%',
		maxHeight: '200px',
		nonSelectedText: '请选择',
        onChange: function(option, checked) {
            
        }
	});
    
    $('#optionsIntegralTypeUpdate').multiselect({
		enableClickableOptGroups: true,
		inheritClass: true,
		enableCollapsibleOptGroups: true,
		includeSelectAllOption: false,
		enableFiltering: true,
		buttonWidth:'100%',
		maxHeight: '200px',
		nonSelectedText: '请选择',
        onChange: function(option, checked) {
            
        }
	});
}

function eventInit(){
	
	$('body').on('click', '#addBz', function(e){
		$('#myModal').modal('show');
	});
	
	$('#jadd').on('click', function() {
		$('#optionsIntegral').val( parseInt($('#optionsIntegral').val(), 10) + 1);
	});
	$('#jiadd').on('click', function() {
		$('#optionsIntegral').val( parseInt($('#optionsIntegral').val(), 10) - 1);
	});
	
	$('#jupdate').on('click', function() {
		$('#optionsIntegralUpdate').val( parseInt($('#optionsIntegralUpdate').val(), 10) + 1);
	});
	$('#jiupdate').on('click', function() {
		$('#optionsIntegralUpdate').val( parseInt($('#optionsIntegralUpdate').val(), 10) - 1);
	});
	
	$('body').on('click', '#model_submit', function(e){
		addOptionsIntegralGg();
	});
	
	$('body').on('click', '#model_submit_update', function(e){
		UpdateOptionsIntegralGg();
	});
	
}

function UpdateOptionsIntegralGg(){
	var OptionsId = $("#OptionsId").val();
	var optionsIntegral = $("#optionsIntegralUpdate").val();
	var optionsIntegralTypeCont = $("#optionsIntegralTypeContUpdate").val();
	var optionsIntegralType = $("#optionsIntegralTypeUpdate").val();
	if(isNumber(optionsIntegral)){
		if(optionsIntegral==0){
			qiao.bs.msg({msg:'积分标准不能为0',type:'danger'});
			return;
		}
	}else{
		qiao.bs.msg({msg:'积分标准只能位数字',type:'danger'});
		return;
	}
	if(isNull(optionsIntegralTypeCont)){
		qiao.bs.msg({msg:'请对该积分标准进行描述',type:'danger'});
		return;
	}else{
		var params = {
				optionsIntegral:optionsIntegral,
				optionsIntegralTypeCont:optionsIntegralTypeCont,
				optionsIntegralType:optionsIntegralType,
				id:OptionsId,
		};
		AjaxUtil.request({url:path+"/post/TsyOptionsIntegralGg/updateTsyOptionsIntegralGg",params:params,type:'json',callback:function(json){
			if(json.returnCode=="1086"){
				qiao.bs.msg({msg:json.returnMessage,type:'success'});
				$('#myModalUpdate').modal('hide');
				$("#massage").bootstrapTable('refresh', {url: path+'/post/TsyOptionsIntegralGg/queryTsyOptionsIntegralGg'});
			}else{
				qiao.bs.msg({msg:'修改失败',type:'warning'});
			}
			}
		});
	}
}

function addOptionsIntegralGg(){
	var optionsIntegral = $("#optionsIntegral").val();
	var optionsIntegralTypeCont = $("#optionsIntegralTypeCont").val();
	var optionsIntegralType = $("#optionsIntegralType").val();
	if(isNumber(optionsIntegral)){
		if(optionsIntegral==0){
			qiao.bs.msg({msg:'积分标准不能为0',type:'danger'});
			return;
		}
	}else{
		qiao.bs.msg({msg:'积分标准只能位数字',type:'danger'});
		return;
	}
	if(isNull(optionsIntegralTypeCont)){
		qiao.bs.msg({msg:'请对该积分标准进行描述',type:'danger'});
		return;
	}else{
		var params = {
				optionsIntegral:optionsIntegral,
				optionsIntegralTypeCont:optionsIntegralTypeCont,
				optionsIntegralType:optionsIntegralType,
		};
		AjaxUtil.request({url:path+"/post/TsyOptionsIntegralGg/insertTsyOptionsIntegralGg",params:params,type:'json',callback:function(json){
			if(json.returnCode=="1096"){
				qiao.bs.msg({msg:json.returnMessage,type:'success'});
				$('#myModal').modal('hide');
				$("#massage").bootstrapTable('refresh', {url: path+'/post/TsyOptionsIntegralGg/queryTsyOptionsIntegralGg'});
			}else if(json.returnCode=="1100"){
				qiao.bs.msg({msg:json.returnMessage,type:'warning'});
			}else{
				qiao.bs.msg({msg:'添加失败',type:'warning'});
			}
			}
		});
	}
}

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#massage').bootstrapTable({
            url: path+'/post/TsyOptionsIntegralGg/queryTsyOptionsIntegralGg',             //请求后台的URL（*）
            method: 'post',                     //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: false,                      //是否显示行间隔色
            cache: true,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
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
            silent: true,
            onEditableSave: function (field, row, oldValue, $el) {
            	
            },
            columns: [{
            	field: 'Number',
            	title: '序号',
            	width: '50',
            	align: 'center',
            	formatter: function (value, row, index) {
            		return index+1;
            	}
            }, {
                field: 'options_integral_type',
                title: '积分类型',
                align: 'center',
                formatter: function (value, row, index) {
            		if(value=="1"){
            			return "新添";
            		}else if(value=="2"){
            			return "评价";
            		}else if(value=="3"){
            			return "违规";
        			}
            	}
            }, {
                field: 'options_integral',
                title: '积分标准',
                align: 'center',
                formatter: function (value, row, index) {
            		if(value>0){
            			return "+" + value;
            		}else{
            			return value;
            		}
            	}
            }, {
                field: 'options_integral_time',
                title: '规格添加时间',
                align: 'center',
                width: '200',
                formatter: function (value, row, index) {
                	return value.substring(0,16);
                }
            }, {
                field: 'options_integral_type_cont',
                title: '积分规格描述',
                align: 'center',
                width: '300',
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
        };
        return temp;
    };
    return oTableInit;
}

window.EvenInit = {
    'click .RoleOfA': function (e, value, row, index) {//删除
        AjaxUtil.request({url:path+"/post/TsyOptionsIntegralGg/daleteTsyOptionsIntegralGg",params:{id:row.id},type:'json',callback:function(json){
			if(json.returnCode=="1076"){
				qiao.bs.msg({msg:json.returnMessage,type:'success'});
				$("#massage").bootstrapTable('refresh', {url: path+'/post/TsyOptionsIntegralGg/queryTsyOptionsIntegralGg'});
			}else{
				qiao.bs.msg({msg:'操作失败',type:'warning'});
			}
			}
		});
    },
	'click .RoleOfB': function (e, value, row, index) {//修改
		$("#OptionsId").val(row.id);
		$("#optionsIntegralUpdate").val(row.options_integral);
		$("#optionsIntegralTypeContUpdate").val(row.options_integral_type_cont);
		var newProducts = new Array();
        var obj = new Object();
		obj = {
            label : function(){if(row.options_integral_type==1)return "新添";else if(row.options_integral_type==2)return "评价";else if(row.options_integral_type==3)return "违规"},
            value : row.options_integral_type
        };
        newProducts.push(obj);
        $("#optionsIntegralTypeUpdate").multiselect('dataprovider', newProducts);
		$('#myModalUpdate').modal('show');
	}
};
function operateFormatter(value, row, index) {
    return [
        '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">删除</button>',
        '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">修改</button>',
    ].join('');
}
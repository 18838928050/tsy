
var chnNumChar = ["零","一","二","三","四","五","六","七","八","九"];
var chnUnitSection = ["","万","亿","万亿","亿亿"];
var chnUnitChar = ["","十","百","千"];

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
	
	$('body').on('click', '.selectMap', function(e){
		var rowId = $(this).attr("rowId");
		var id = $("#seeRowIdMation").val();
		AjaxUtil.request({url:path+"/post/investigationQuestion/queryOptionsByQuestionId",params:{questionId:rowId,id:id},type:'json',callback:function(json){
				if(json.rows.length>0){
					MakeChart(json.rows);
					$("#bzt").show();
					$("#bztNone").hide();
				}else{
					$("#bztNone").show();
					$("#bzt").hide();
				}
				if(json.bean){
					$("#zrs").html("参与总人数:"+json.bean.cyrs+"人");
				}else{
					$("#zrs").html("参与总人数:0人");
				}
				$('#selectSeeMap').modal('show');
			}
		});
	});
	
	$('body').on('click', '#addVote', function(e){
		AjaxUtil.request({url:path+"/post/investigationQuestion/queryVoteListByNoSave",params:{},type:'json',callback:function(json){
				if(json.returnCode=="-9999"){
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}else if(json.returnCode=="1200"){
					location.href = "addVote.html?id="+"";
				}else if(json.returnCode=="1201"){
					qiao.bs.confirm('当前有未保存的投票信息,是否继续编辑?',function(){
						location.href = "addVote.html?id="+json.bean.id;
					},function(){
						AjaxUtil.request({url:path+"/post/investigationQuestion/deleteVoteListByNoSave",params:{id:json.bean.id},type:'json',callback:function(json){
								if(json.returnCode=="0"){
									location.href = "addVote.html?id="+"";
								}else{
									qiao.bs.msg({msg:"数据操作失败",type:'danger'});
								}
							}
						});
					});
				}else{
					qiao.bs.msg({msg:"数据操作失败",type:'danger'});
				}
			}
		});
	});
	
	$('body').on('click', '#Bq_submit', function(e){
		if($("#tsyVoteStartTime").val()==""){  
		    qiao.bs.msg({msg:"请输入投票起始时间！",type:'danger'});
		    return;  
		}else if($("#tsyVoteEndTime").val()==""){  
		    qiao.bs.msg({msg:"请输入投票结束时间！",type:'danger'});
		    return;  
		}else if(!checkEndTime()){  
		    qiao.bs.msg({msg:"结束时间必须晚于起始时间！",type:'danger'});
		    return;  
		}else if(!compareDate(show(),$("#tsyVoteEndTime").val())){
			qiao.bs.msg({msg:"结束时间必须晚于当前时间！",type:'danger'});
		    return;  
		}else{
			qiao.bs.confirm('时间如下：投票日期为：'+$("#tsyVoteStartTime").val() + ' 00:00:00到'+$("#tsyVoteEndTime").val()+' 23:59:59'+'是否确认？',function(){
				var param = {
						id:$("#voteRowId").val(),
						tsyVoteStartTime:$("#tsyVoteStartTime").val()+' 00:00:00',
						tsyVoteEndTime:$("#tsyVoteEndTime").val()+' 23:59:59',
					};
				AjaxUtil.request({url:path+"/post/investigationQuestion/updateinvestigationQuestionState",params:param,type:'json',callback:function(json){
						if(json.returnCode=="0"){
							$('#massage').bootstrapTable('refresh', {url: path+'/post/investigationQuestion/queryVoteList'});
							$('#myModal2').modal('hide');
							qiao.bs.msg({msg:"投票已开始,请将链接复制到菜单链接内...",type:'success'});
						}else{
							qiao.bs.msg({msg:json.returnMessage,type:'danger'});
						}
					}
				});
			},function(){
			});
		}
	});
}

//根据json数据生成饼状图，并将饼状图显示到div中
function MakeChart(value) {
	chartData = eval(value);
	//饼状图
	chart = new AmCharts.AmPieChart();
	chart.dataProvider = chartData;
	//标题数据
	chart.titleField = "name";
	//值数据
	chart.valueField = "value";
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

function show(){
	var mydate = new Date();
	var str = "" + mydate.getFullYear() + "-";
	str += (mydate.getMonth()+1) + "-";
	str += mydate.getDate();
	return str;
}

function compareDate(startTime,endTime){
	var start=new Date(startTime.replace("-", "/").replace("-", "/"));  
	var end=new Date(endTime.replace("-", "/").replace("-", "/"));  
    if(end<start){  
        return false;  
    }  
    return true;  
}

function checkEndTime(){  
    var startTime=$("#tsyVoteStartTime").val();  
    var endTime=$("#tsyVoteEndTime").val();  
    return compareDate(startTime,endTime);
} 

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#massage').bootstrapTable({
            url: path+'/post/investigationQuestion/queryVoteList',             //请求后台的URL（*）
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
            	field: 'Number',
            	title: '序号',
            	width: '50',
            	align: 'center',
            	formatter: function (value, row, index) {
            		return index+1;
            	}
            }, {
                field: 'tsyVoteTitle',
                title: '投票昵称',
                align: 'center',
                width: '150'
            }, {
                field: 'tsyVoteWhetherSave',
                title: '投票保存状态',
                align: 'center',
                width: '150',
            	formatter: function (value, row, index) {
            		if(value=='1'){
            			return "已保存";
            		}else{
            			return "未保存";
            		}
            	}
            }, {
                field: 'tsyVoteState',
                title: '投票执行状态',
                align: 'center',
                width: '120',
            	formatter: function (value, row, index) {
            		if(value=='0'){
            			return "<font style='color:#787878'>未执行</font>";
            		}else if(value=="1"){
            			return "<font style='color:green'>执行中</font>";
            		}else if(value=="2"){
            			return "<font style='color:red'>执行完毕</font>";
            		}else{
            			return "——";
            		}
            	}
            }, {
                field: 'tsyVoteState',
                title: 'URI',
                align: 'center',
                width: '200',
            	formatter: function (value, row, index) {
            		if(value=='0'){
            			return "——";
            		}else if(value=="1"){
            			var url = null;
            			$.ajax({
            		        url : path+'/post/admTsy/AdmTsyVoteMation',
            		        type : "post",
            		        data : {id:row.id,urlpath:path},
            		        async : false,
            		        dataType:"JSON",
            		        success : function(data) {
            		        	url = data.bean.url;
            		        }
            		    });
            			return "<font style='color:green;word-wrap:break-word;'>"+url+"</font>";
            		}else if(value=="2"){
            			var url = null;
            			$.ajax({
            		        url : path+'/post/admTsy/AdmTsyVoteMation',
            		        type : "post",
            		        data : {id:row.id,urlpath:path},
            		        async : false,
            		        dataType:"JSON",
            		        success : function(data) {
            		        	url = data.bean.url;
            		        }
            		    });
            			return "<font style='color:red;word-wrap:break-word;'>"+url+"</font>";
            		}else{
            			return "——";
            		}
            	}
            }, {
                field: 'tsyVoteStartTime',
                title: '开始时间',
                align: 'center',
                width: '120',
                formatter: function (value, row, index) {
                	return value;
                }
            }, {
                field: 'tsyVoteEndTime',
                title: '结束时间',
                align: 'center',
                width: '120',
                formatter: function (value, row, index) {
                	return value;
                }
            }, {
                field: 'tsyVoteTable',
                title: '操作码',
                align: 'center',
                width: '150',
                formatter: function (value, row, index) {
                	if(value==""||value==null){
                		return "——";
                	}else{
                		return value;
                	}
                }
            }, {
                field: 'createName',
                title: '创建人',
                align: 'center',
                width: '120',
                formatter: function (value, row, index) {
                	return value;
                }
            }, {
                field: 'createNo',
                title: '创建人ID',
                align: 'center',
                width: '120',
                formatter: function (value, row, index) {
                	return value;
                }
            }, {
                field: 'operate',
                title: '操作',
                align: 'center',
                width: '200',
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
    'click .RoleOfA': function (e, value, row, index) {//编辑
    	location.href = "addVote.html?id="+row.id;
    },
    'click .RoleOfB': function (e, value, row, index) {//删除
    	var param = {
    			generalState:'3',
    			id:row.id
    	};
    	AjaxUtil.request({url:path+"/post/investigationQuestion/deleteVoteMessage",params:param,type:'json',callback:function(json){
    			if(json.returnCode=="0"){
    				$('#massage').bootstrapTable('refresh', {url: path+'/post/investigationQuestion/queryVoteList'});  
    				qiao.bs.msg({msg:"成功删除",type:'success'});
    			}else{
    				qiao.bs.msg({msg:"数据操作失败",type:'danger'});
    			}
    		}
    	});
    },
    'click .RoleOfC': function (e, value, row, index) {//查看
    	$("#seeRowIdMation").val(row.id);
    	AjaxUtil.request({url:path+"/post/investigationQuestion/queryVoteListByGeneralId",params:{id:row.id},type:'json',callback:function(json){
    			if(json.returnCode=="0"){
    				Handlebars.registerHelper("compareIndex", function(v1,options){
						v1++;
						return NumberToChinese(v1);
					});
    				var source = $("#select-temp").html();  
				    var template = Handlebars.compile(source);
				    $("#selectSeeMationBody").html(template(json));
				    $('#selectSeeMation').modal('show');
    			}else{
    				qiao.bs.msg({msg:"数据操作失败",type:'danger'});
    			}
    		}
    	});
    },
    'click .RoleOfD': function (e, value, row, index) {//开始
    	$('#myModal2').modal('show');
    	$("#voteRowId").val(row.id);
    	$("#tsyVoteStartTime").val("");
    	$("#tsyVoteEndTime").val("");
    }
};

function operateFormatter(value, row, index) {
	var str = "";
	if(row.belongToMain==1){//属于自己的问卷
		if(row.tsyVoteState==0){//未执行
			str = [ '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
			        '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">删除</button>',
			        '<button type="button" class="RoleOfD btn btn-default  btn-sm" style="margin-right:15px;">开始</button>',];
		}else if(row.tsyVoteState==1){//执行中
			str = [ '<button type="button" class="RoleOfC btn btn-default  btn-sm" style="margin-right:15px;">查看</button>',];
		}else if(row.tsyVoteState==2){//执行完毕
			str = [ '<button type="button" class="RoleOfC btn btn-default  btn-sm" style="margin-right:15px;">查看</button>',
			        '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">删除</button>',];
		}else{
			str = [];
		}
	}else{//不属于自己的问卷
		str = [ '<button type="button" class="RoleOfC btn btn-default  btn-sm" style="margin-right:15px;">查看</button>',];
	}
    return str.join('');
}

function SectionToChinese(section){
	var strIns = '', chnStr = '';
	var unitPos = 0;
	var zero = true;
	while(section > 0){
	  var v = section % 10;
	  if(v === 0){
	    if(!zero){
	      zero = true;
	      chnStr = chnNumChar[v] + chnStr;
	    }
	  }else{
	    zero = false;
	    strIns = chnNumChar[v];
	    strIns += chnUnitChar[unitPos];
	    chnStr = strIns + chnStr;
	  }
	  unitPos++;
	  section = Math.floor(section / 10);
	}
	return chnStr;
}

function NumberToChinese(num){
	var unitPos = 0;
	var strIns = '', chnStr = '';
	var needZero = false;
	if(num === 0){
		return chnNumChar[0];
	}
	while(num > 0){
	var section = num % 10000;
	if(needZero){
		chnStr = chnNumChar[0] + chnStr;
	}
	strIns = SectionToChinese(section);
	strIns += (section !== 0) ? chnUnitSection[unitPos] : chnUnitSection[0];
	chnStr = strIns + chnStr;
	needZero = (section < 1000) && (section > 0);
	num = Math.floor(num / 10000);
	unitPos++;
	}
	return chnStr;
}



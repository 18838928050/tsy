
var up = 0;
var down = 0;

$(function(){
	init();
});

function init(){
	dataInit();
	eventInit();
}

function dataInit(){
	
}

function eventInit(){
	$('body').on('click', '#select', selectByType);
}

function selectByType(){
	var params = {
			display:$("#display").val(),
			name:$("#name").val()
	};
	AjaxUtil.request({url:path+"/post/TsyScillor/queryTsyScillorTablelist",params:params,type:'json',callback:function(json){
		if(json.total==0){
			$("#tablelsw").html('<td colspan="7" style="text-align: center;color: red;">暂时没有数据</td>');
		}else{
			Handlebars.registerHelper("compare1", function(v1,options){
				v1++
				return v1;
			});
			Handlebars.registerHelper("compare2", function(v1,options){
				if(v1==1){
					up++;
					return '<span style="color: green;"><img alt="上线" src="images/up.png" style="margin-left: 10px" />[线上]</span>';
				}else if(v1==0){
					down++;
					return '<span style="color: red"><img alt="下线" src="images/down.png" style="margin-left: 10px" />[线下]</span>';
				}
			});
			Handlebars.registerHelper("compare3", function(v1,options){
				if(v1==1){
					return '<span style="color: green;">[已发布]</span>';
				}else if(v1==0){
					return '<span style="color: red">[未发布]</span>';
				}
			});
			Handlebars.registerHelper("compare4", function(v1,v2,options){
				if(v1==1){
					return '';
				}else if(v1==0){
					return '<a id="fb" href="javaScript:return void(0);" onclick="fb('+v2+')" title="发布"><img src="images/fb.png" style="margin-top: -7px;width: 20px;" /> </a>';
				}
			});
			var source = $("#select-temp").html();  
		    var template = Handlebars.compile(source);
		    $("#tablelsw").html(template(json)); 
		    hide();
		    $("#up").html(up);
		    $("#down").html(down);
		    up = 0;
		    down = 0;
		}
		}
	});
}

function deleteItem(id, img_path) {
	$.ajax({
		type : "post",
		url : "scillorpic/DeleteItem",
		data : {
			id : id,
			img_path : img_path
		},
		timeout : 2000,
		dataType : 'json',
		success : function(data) {
			var str = data.message;
			if (str == '0404') {
				alert("数据异常");
			} else if (str == '1075') {
				alert("删除失败");
			} else if (str == '1076') {
				alert("删除成功");
				window.location.reload();
			} else if (str == '1079') {
				alert("删除成功,但数据有残留");
				window.location.reload();
			}
		},
		error : function(data) {
			alert("请求服务器失败");
		}
	});
}

/**
 * 状态切换
 **/
function upordown(id) {
	$.ajax({
		type : "post",
		url : "scillorpic/UpdateItemZhuangtai",
		data : {
			id : id
		},
		timeout : 2000,
		dataType : 'json',
		success : function(data) {
			var str = data.message;
			if (str == '1069') {
				selectByType();
				qiao.bs.msg({msg:'当前状态：[线上]',type:'success'});
			} else if (str == '1068') {
				selectByType();
				qiao.bs.msg({msg:'当前状态：[线下]',type:'success'});
			} else {
				qiao.bs.msg({msg:'繁忙',type:'error'});
			}
		},
		error : function() {
			qiao.bs.msg({msg:"错误警报",type:'danger'});
		}
	});
}

function fb(id){
	$.ajax({
		type : "post",
		url : "post/TsyScillor/updateTsyScillorPicFb",
		data : {
			id : id
		},
		timeout : 2000,
		dataType : 'json',
		success : function(data) {
			var str = data.returnCode;
			if (str == '1086') {
				selectByType()
				qiao.bs.msg({msg:'发布成功',type:'success'});
			} else {
				qiao.bs.msg({msg:'发布失败',type:'danger'});
			}
		},
		error : function() {
			qiao.bs.msg({msg:"错误警报",type:'danger'});
		}
	});
}

var page = 1;

var content = null;

$(function(e){
	init();
});

function init(){
	eventInit();
	if($("#searchContent").val()==""||$("#searchContent").val()==null){
	}else{
		dataInit();
	}
}

function dataInit(){
	content = $("#searchContent").val();
	AjaxUtil.request({url:path+"/post/admTsy/queryWechatKnowledge",params:{page:page,searchContent:$("#searchContent").val()},type:'json',callback:function(json){
		$(".strategy").find("#loadMore").remove();
		if(json.rows.length>0){
			Handlebars.registerHelper("compareZf", function(v1,options){
				if(v1==""||v1==null||v1=="0"){
					return "-";
				}else{
					return v1;
				}
			});
			var source = $("#select-Top").html();  
			var template = Handlebars.compile(source);
			$(".strategy").html($(".strategy").html()+template(json));
		}
		}
	});
}

function eventInit(){
	$('body').on('click', '#search', function(e){
		page=1;
		if($("#searchContent").val()==""||$("#searchContent").val()==null){
			alert("请输入搜索内容");
		}else{
			dataInit();
		}
	});
	
	$('body').on('click', '#loadM', function(e){
		page++;
		if($("#searchContent").val()==""||$("#searchContent").val()==null){
			alert("请输入搜索内容");
		}else{
			dataInit();
		}
	});
	
	$('body').on('click', '.strategy>li>a', function(e){
		var rowId = $(this).attr("rowId");
		location.href = "content.html?type=2&rowId="+rowId;
	});
}



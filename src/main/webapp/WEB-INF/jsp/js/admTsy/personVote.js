
var id = "";

var code = "";

var state = "";

var openId = "";

var arr = new Array();

var chooseArr = new Array();

var chnNumChar = ["零","一","二","三","四","五","六","七","八","九"];
var chnUnitSection = ["","万","亿","万亿","亿亿"];
var chnUnitChar = ["","十","百","千"];

$(function(){
	init();
});

function init(){
	receiveData();
	dataInit();
	eventInit();
}

function dataInit(){
	id = $.req("id");
	code = $.req("code");
	state = $.req("state");
	
	console.log("before  openId="+openId);
	if(openId==""||openId==null){
		AjaxUtil.request({url:path+"/post/admTsy/wechat/tsy/toVotePage",params:{code:code},type:'json',callback:function(json){
			$("#headImg").attr("src",json.bean.headImgUrl);
			openId = json.bean.openId;
//			alert("code="+code+"<br/>openId="+openId);
			console.log("end  openId="+openId);
			$("#nickName").html(json.bean.nickName);
			judgeInitQuestion();
		}
		});
	}
}

function judgeInitQuestion(){
	AjaxUtil.request({url:path+"/post/admTsy/investigationQuestion/queryVoteByIdAndOpenId",params:{openId:openId,id:id},type:'json',callback:function(json){
			if(json.rows.length>0){
				$("#submitBtn").hide();
				var source = $("#select").html();  
			    var template = Handlebars.compile(source);
			    $("#question").html(template(json));
			}else{
				initQuestion();
			}
		}
	});
}

function initQuestion(){
	AjaxUtil.request({url:path+"/post/admTsy/investigationQuestion/queryAdmTsyVoteListByGeneralId",params:{id:id},type:'json',callback:function(json){
		if(json.returnCode=="0"){
			if(json.bean.tsyVoteState=="2"){
				$("#submitBtn").hide();
				var source = $("#select1").html();  
			    var template = Handlebars.compile(source);
			    $("#question").html(template(json));
			}else{
				Handlebars.registerHelper("compareIndex", function(v1,v2,options){
					v1++;
					arr.push(v2);
					return NumberToChinese(v1);
				});
				Handlebars.registerHelper("compare", function(v1,options){
					if(v1 == '1'){
						return options.fn(this);
					}else{
						return options.inverse(this);
					}
				});
				Handlebars.registerHelper("compareIndexSome", function(v1,options){
					return v1++;
				});
				var source = $("#select-temp").html();  
			    var template = Handlebars.compile(source);
			    $("#question").html(template(json));
			}
		}else{
			qiao.bs.msg({msg:"数据操作失败",type:'danger'});
		}
	}
	});
}

function eventInit(){
	
	//收起按钮
	$('body').on('click', '.Stop', function(e){
		$(this).hide();
		$(this).parent().parent().parent().find(".panel-body").hide();
		$(this).parent().find(".stopedit").show();
	});
	
	//编辑按钮
	$('body').on('click', '.stopedit', function(e){
		$(this).hide();
		$(this).parent().parent().parent().find(".panel-body").show();
		$(this).parent().find(".Stop").show();
	});
	
	$('body').on('click', '#saveMessage', function(e){
		var redioStr = $("input:checked");
		for(var i = 0;i<redioStr.length;i++){
			console.log(redioStr[i].getAttribute("rowId"));
			var questionId = redioStr[i].getAttribute("rowId").split("@@")[0];
			if(chooseArr.indexOf(questionId)!=-1) {
			}else{
				chooseArr.push(questionId);
			}
		}
		for(var i = 0;i<arr.length;i++){
			if(chooseArr.indexOf(arr[i])!=-1) {
			}else{
				qiao.bs.msg({msg:"您还有未完成的作答,请耐心完成.\n"+$("a[rowNameId='"+arr[i]+"']").html(),type:'danger'});
				chooseArr.splice(0,chooseArr.length);
				return;
			}
		}
		chooseArr.splice(0,chooseArr.length);
		for(var i = 0;i<redioStr.length;i++){
			var questionId = redioStr[i].getAttribute("rowId");
			if(chooseArr.indexOf(questionId)!=-1) {
			}else{
				chooseArr.push(questionId);
			}
		}
		AjaxUtil.request({url:path+"/post/admTsy/investigationQuestion/insertVoteAnswer",params:{str:chooseArr.toString(","),openId:openId,id:id},type:'json',callback:function(json){
				if(json.returnCode=="0"){
					qiao.bs.msg({msg:"已完成作答,感谢您的参与",type:'success'});
					setTimeout(function(){
						location.reload();
					}, 1000);
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}
		});
	});
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


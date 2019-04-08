var type = null;
var rowId = null;
function init(){
	receiveData();
	HeightInit('index-iframe', 400,'content',"index-iframe","content");
	eventInit();
	dataInit();
}

function dataInit(){
	type = $.req("type");
	rowId = $.req("rowId");
	AjaxUtil.request({url:path+"/post/admTsy/queryKnowledge",params:{type:type},type:'json',callback:function(json){
		if(json.total=='0'){
			$(".sidebar").hide();
			$("#cont").hide();
			$("#ts").show();
		}else{
			$(".sidebar").show();
			$("#cont").show();
			$("#ts").hide();
			Handlebars.registerHelper("comparestar", function(v1,options){
				if(v1!=null&&v1!=""){
					return v1+'<img src="../../../images/star.png" style="margin-top:-5px;margin-left:5px">';
				}else{
					return '5'+'<img src="../../../images/star.png" style="margin-top:-5px;margin-left:5px">';
				}
			});
			var source = $("#select-ol").html();  
			var template = Handlebars.compile(source);
			$("#nameUl").html(template(json));
			if(rowId==""||rowId==null||rowId=="undefined"){
				$("#nameUl").find(".rowLi:first").click();
			}else{
				$("#nameUl").find(".rowLi[rowid='"+rowId+"']").click();
			}
		}
		}
	});
}

function eventInit(){
	$('body').on('click', '.rowLi', function(e){
		var id = $(this).attr("rowId");
		$('#index-iframe').contents().find("#rowId").val(id); 
		AjaxUtil.request({url:path+"/post/admTsy/queryKnowledgeById",params:{id:id},type:'json',callback:function(json){
				$('html,body').animate({scrollTop:0},1000);//回到顶端
				$('#index-iframe').contents().find("#content").html(unescape(json.bean.content));
				$('#title').html(json.bean.name);
				HeightInit('index-iframe', 400,'content',"index-iframe","content");
				reloadEvaluate(id);
			}
		});
	});
	//提交星数
	$('body').on('click', '#starSubmit', function(e){
		var starNum = $("#input-7-sm").val();//评论星级
		AjaxUtil.request({url:getRootPath()+"/post/admTsy/SessionAlife",params:{},type:'json',callback:function(json){
				if(json.returnCode=="1341"){
					$('#myModal').modal('show');
					qiao.bs.msg({msg:"您还未登录,请先登录...",type:'danger'});
				}else{
					var content=$("#input-7-sms").val();//评论内容
					var id = $('#index-iframe').contents().find("#rowId").val();
			        AjaxUtil.request({url:path+"/post/admTsy/AddContentAndNumStar",params:{starNum:starNum,content:content,id:id},type:'json',callback:function(json){
			        		qiao.bs.msg({msg:"评论成功",type:'success'});
			        		reloadStar();
				    	}
				    });
				}
			}
		});
	});
	
	$('body').on('click', '.badge', function(e){
		$(".con-panel-body").hide();
		$(this).next(".con-panel-body").show();
	});
	$('body').on('click', '.sqevaluateReply', function(e){
		$(".con-panel-body").hide();
	});
	$('body').on('click', '.sqevaluateReplysubmit', function(e){
		var optionid = $('#index-iframe').contents().find("#rowId").val(); 
		var parentId = $(this).attr("option");
		var content = $(this).siblings("textarea").val();
		AjaxUtil.request({url:getRootPath()+"/post/admTsy/SessionAlife",params:{},type:'json',callback:function(json){
				if(json.returnCode=="1341"){
					$('#myModal').modal('show');
					qiao.bs.msg({msg:"您还未登录,请先登录...",type:'danger'});
				}else{
					console.log(content);
			        AjaxUtil.request({url:path+"/post/admTsy/queryTysOptionsEvaluateContent",params:{optionid:optionid,parentId:parentId,content:content},type:'json',callback:function(json){
			        		//qiao.bs.msg({msg:"评论成功",type:'success'});
			        		reloadEvaluate(optionid);
				    	}
				    });
				}
			}
		});
	});
}

function isHidden(className){
	var count = $(className).size();
    var hidCount = 0;
	$(className).each(function() {
        if ($(this).is(":hidden")) {
            hidCount++;
        }
    });
    if (hidCount != count) {
        return false;
    }else {
    	return true;
    }
}

function reloadStar(){
	AjaxUtil.request({url:path+"/post/admTsy/queryKnowledge",params:{type:type},type:'json',callback:function(json){
		Handlebars.registerHelper("comparestar", function(v1,options){
			if(v1!=null&&v1!=""){
				return v1+'<img src="../../../images/star.png" style="margin-top:-5px;margin-left:5px">';
			}else{
				return '5'+'<img src="../../../images/star.png" style="margin-top:-5px;margin-left:5px">';
			}
		});
		var source = $("#select-ol").html();  
		var template = Handlebars.compile(source);
		$("#nameUl").html(template(json));
		var id = $('#index-iframe').contents().find("#rowId").val(); 
		reloadEvaluate(id);
		}
	});
}

function reloadEvaluate(id){
	AjaxUtil.request({url:path+"/post/admTsy/queryContentAndNumStar",params:{optionId:id},type:'json',callback:function(json){
			AjaxUtil.request({url:path+"/post/admTsy/SessionAlife",params:{optionId:id},type:'json',callback:function(sessionJson){
					if(sessionJson.returnCode=="1341"){
						$("#start").html("登陆进行星级评价");
					}else{
						if(!json.bean){
							$("#start").html("尊敬的用户,您还未进行星级评价");
							$("#input-7-sm").rating('refresh', {
						        disabled: false
						    });
						}else{
							if(!json.bean.userStar){
								$("#start").html("尊敬的用户,您还未进行星级评价");
								$("#input-7-sm").rating('refresh', {
							        disabled: false
							    });
							}else{
								$("#start").html("此文章您给了"+json.bean.userStar+"星哦");
								$("#input-7-sm").rating('refresh', {
									disabled: true
								});
							}
						}
					}
					if(json.total=="0"){
						$("#evaluateFont").show();
						$("#evaluateUl").hide();
					}else{
						$("#evaluateFont").hide();
						$("#evaluateUl").show();
						Handlebars.registerHelper("compareImg", function(v1,options){
							if(v1!=null&&v1!=""){
								return v1;
							}else{
								return '../../../images/icon/DefaultAvatar.jpg';
							}
						});
						Handlebars.registerHelper("compareTime", function(v1,options){
							return datetimeFormat_1(v1);
						});
						var source = $("#evaluate").html();  
						var template = Handlebars.compile(source);
						$("#evaluateUl").html(template(json));
						$(".form-control").val("");
					}
				}
			});
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


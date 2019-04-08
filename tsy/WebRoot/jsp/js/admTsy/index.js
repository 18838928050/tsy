
var nowArr = ["241","242","243","210","6","4","5","7","180","244","185","186","187"];

function init(){
	dataInit();
}

function dataInit(){
	AjaxUtil.request({url:path+"/post/admTsy/TsyScillor/list",params:{},type:'json',callback:function(json){
		Handlebars.registerHelper("compare1", function(v1,options){
			if(v1==0){
				return "active";
			}
		});
		Handlebars.registerHelper("compare2", function(v1,options){
			return path+"/"+v1;
		});
		var source = $("#select-temp").html();  
	    var template = Handlebars.compile(source);
	    $("#scllor").html(template(json));
	    
	    Handlebars.registerHelper("compare3", function(v1,options){
			return v1++;
		});
	    Handlebars.registerHelper("compare4", function(v1,options){
			if(v1==0){
				return 'class="active"';
			}
		});
	    var source = $("#select-ol").html();  
	    var template = Handlebars.compile(source);
	    $("#scllorId").html(template(json));
	    loadMaxStarPeople();
		}
	});
}

//根据评价加载近半年活跃度最高的25名用户
function loadMaxStarPeople(){
	AjaxUtil.request({url:path+"/post/admTsy/queryMaxStarTopTenPeople",params:{},type:'json',callback:function(json){
	    Handlebars.registerHelper("conpareImg", function(v1,options){
			if(v1==""||v1==null){
				return path + "/images/icon/DefaultAvatar.jpg";
			}else{
				return path + "/" + v1;
			}
		});
	    var source = $("#select-img").html();  
	    var template = Handlebars.compile(source);
	    $(".third").html(template(json));
	    loadVedioByRandEleven();
		}
	});
}

//随机加载11个视频资料
function loadVedioByRandEleven(){
	AjaxUtil.request({url:path+"/post/admTsy/queryVedioByRandEleven",params:{},type:'json',callback:function(json){
	    Handlebars.registerHelper("compareIndexVedio", function(v1,options){
			v1++;
			if(v1==1){
				return "left-firstli";
			}else if(v1==5||v1==11){
				return "left-secondli";
			}
		});
	    Handlebars.registerHelper("compareImg", function(v1,options){
	    	var picPath = path + "/" + v1;
	    	if(isHasImg(picPath)){
	    		return picPath;
	    	}
			return path + "/images/noshow.png";
		});
	    Handlebars.registerHelper("vedioUploadUser", function(v1,options){
			return v1.substring(0,3)+"****"+v1.substring(7,11);
		});
	    Handlebars.registerHelper("vadioUploadTime", function(v1,options){
			return datetimeFormat_1(v1).substring(0,10);
		});
	    var source = $("#vedio-rand").html();  
	    var template = Handlebars.compile(source);
	    $("#randVedio").html(template(json));
	    loadMaxIntegralKnowledge();
		}
	});
}

//加载评价最高的十个官方文章
function loadMaxIntegralKnowledge(){
	AjaxUtil.request({url:path+"/post/admTsy/queryMaxIntegralTopTen",params:{},type:'json',callback:function(json){
		Handlebars.registerHelper("compareIndex", function(v1,v2,options){
			v1++;
			if(v1==1){
				return "<font style='color:red'>"+v1+"."+v2+"</font>";
			}else if(v1==2){
				return "<font style='color:burlywood'>"+v1+"."+v2+"</font>";
			}else if(v1==3){
				return "<font style='color:gainsboro'>"+v1+"."+v2+"</font>";
			}else{
				return "<font style='color:black'>"+v1+"."+v2+"</font>";
			}
		});
	    var source = $("#select-Top").html();  
	    var template = Handlebars.compile(source);
	    $(".list-one").html(template(json));
	    loadTopFourthVedio();
		}
	});
}

//加载最新的视频(三类，每类四个)
function loadTopFourthVedio(){
	AjaxUtil.request({url:path+"/post/admTsy/queryAdmTsyVedioTopForuth",params:{},type:'json',callback:function(json){
		Handlebars.registerHelper("compareClass", function(v1,options){
			v1++;
			if(v1==1){
				return "li-first li-hover";
			}else if(v1==2){
				return "li-second";
			}else{
				return "li-third";
			}
		});
	    var source = $("#vedio-tab").html();  
	    var template = Handlebars.compile(source);
	    $("#vedioTab").html(template(json));
	    
	    Handlebars.registerHelper("compareContentClass", function(v1,options){
			v1++;
			if(v1==1){
				return "div-first";
			}else if(v1==2){
				return "div-second";
			}else{
				return "div-third";
			}
		});
	    Handlebars.registerHelper("compareShowClass", function(v1,options){
			v1++;
			if(v1==1){
				return "style='display: block;'";
			}else{
				return "";
			}
		});
	    Handlebars.registerHelper("vedioPic", function(v1,options){
	    	var picPath = path + "/" + v1;
	    	if(isHasImg(picPath)){
	    		return picPath;
	    	}
			return path + "/images/noshow.png";
		});
	    Handlebars.registerHelper("vedioUploadUser", function(v1,options){
			return v1.substring(0,3)+"****"+v1.substring(7,11);
		});
	    Handlebars.registerHelper("vadioUploadTime", function(v1,options){
			return datetimeFormat_1(v1);
		});
	    var source = $("#vedio-content").html();  
	    var template = Handlebars.compile(source);
	    $("#content").html( $("#content").html()+template(json));
	    eventInit();
		}
	});
}

function eventInit(){
	$('body').on('click', '.xiang-q', function(e){
		var id = $(this).attr("Sc_id").replace("scId","");
		location.href = "noticeItem.html?id="+id;
	});
	
	$('body').on('click', 'dt>p>a', function(e){
		var rowId = $(this).attr("vedioId");
		location.href = "vedio.html?rowId="+rowId;
	});
	
	$('body').on('click', '.list-one>.fn-text-overflow>a', function(e){
		var rowId = $(this).attr("rowId");
		var fatherId = $(this).attr("fatherId");
		if(nowArr.indexOf(fatherId)!=-1){
			location.href = "content.html?type="+fatherId+"&rowId="+rowId;
		}else{
			qiao.bs.msg({msg:"该内容当前处于未开放模式,暂时无法查看,请等待官方通知...",type:'success'});
		}
	});
	
	$(".tab .li-first").mouseover(function(){
		$(this).addClass("li-hover");
		$(".li-second").removeClass("li-hover");
		$(".li-third").removeClass("li-hover");
		$(".div-first").hide();
		$(".div-second").hide();
		$(".div-third").hide();
		$(".div-first").show();
	});
	$(".tab .li-second").mouseover(function(){
		$(".li-first").removeClass("li-hover");
		$(".li-third").removeClass("li-hover");
		$(this).addClass("li-hover");
		$(".div-first").hide();
		$(".div-second").hide();
		$(".div-third").hide();
		$(".div-second").show();
	});
	$(".tab .li-third").mouseover(function(){
		$(".li-second").removeClass("li-hover");
		$(".li-first").removeClass("li-hover");
		$(this).addClass("li-hover");
		$(".div-first").hide();
		$(".div-second").hide();
		$(".div-third").hide();
		$(".div-third").show();
	});
	var imgleng=$(".third a").length;
	for(var i=0;i<imgleng;i++){
		var local=parseInt(Math.random()*800);
		var arrimg=$(".third a")[i];
		$(arrimg).attr("class","a"+i+" img-circle");
		$(arrimg).css("left",local);
		local=parseInt(Math.random()*500);
		$(arrimg).css("top",local);
	};
	$('.sponsorFlip').bind("click",function(){
		var elem = $(this);
		if(elem.data('flipped'))
		{
			elem.revertFlip();
			elem.data('flipped',false)
		}else{
			elem.flip({
				direction:'lr',
				speed: 350,
				onBefore: function(){
					elem.html(elem.siblings('.sponsorData').html());
				}
			});
			elem.data('flipped',true);
		}
	});
}

function isHasImg(pathImg){
    var ImgObj=new Image();
    ImgObj.src= pathImg;
     if(ImgObj.fileSize > 0 || (ImgObj.width > 0 && ImgObj.height > 0))
     {
       return true;
     } else {
       return false;
    }
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
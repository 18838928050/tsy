var jPlayerPlay;

var rowId = null;

function init(){
	receiveData();
	rowId = $.req("rowId");
	dataInit();
	eventInit();
}

function dataInit(){
	loadVedioTopFifteen();
}

function loadVedioTopFifteen(){
	AjaxUtil.request({url:path+"/post/admTsy/queryVedioTopFifteen",params:{},type:'json',callback:function(json){
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
	    $("#newRedio").html(template(json));
	    loadVedioNoequealUser();
		}
	});
}

function loadVedioNoequealUser(){
	AjaxUtil.request({url:path+"/post/admTsy/queryVedioByOptionClassAndNotequalUser",params:{rowId:rowId},type:'json',callback:function(json){
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
		var source = $("#vedio-model").html();  
	    var template = Handlebars.compile(source);
	    $("#showmodel1").html(template(json));
	    loadVedioByRand();
		}
	});
}

function loadVedioByRand(){
	AjaxUtil.request({url:path+"/post/admTsy/queryVedioByRand",params:{},type:'json',callback:function(json){
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
		var source = $("#vedio-model").html();  
	    var template = Handlebars.compile(source);
	    $("#showmodel2").html(template(json));
	    loadFj();
		}
	});
}

function loadFj(){
	AjaxUtil.request({url:path+"/post/admTsy/queryVedioFjByRowId",params:{rowId:rowId},type:'json',callback:function(json){
		Handlebars.registerHelper("compareUrl", function(v1,options){
			var picPath = path + "/" + v1;
			return picPath;
		});
		var source = $("#select-Fj").html();  
	    var template = Handlebars.compile(source);
	    $("#Fj").html(template(json));
		loadVedioList();
		}
	});
}

function loadVedioList(){
	AjaxUtil.request({url:path+"/post/admTsy/queryVedioByOptionClassAndUser",params:{rowId:rowId},type:'json',callback:function(json){
		var arrayObj = new Array();　//创建一个数组
		for(var i = 0;i<json.rows.length;i++){
			if(json.rows[i].id==rowId){
				arrayObj.push({
					title:json.rows[i].option_title,
					free:true,
					m4v: path+"/"+json.rows[i].option_path,
					poster:path+"/"+json.rows[i].option_pic_path
				});
			}else{
				arrayObj.push({
					title:json.rows[i].option_title,
					artist:"Pixar",
					m4v: path+"/"+json.rows[i].option_path,
					poster: path+"/"+json.rows[i].option_pic_path
				});
			}
		}
		console.log(arrayObj);
	    playerInit(arrayObj);
		}
	});
}

function eventInit(){
	
	$('body').on('click', '#newRedio>li>a', function(e){
		var rowId = $(this).attr("rowId");
		location.href = "vedio.html?rowId="+rowId;
	});
	
	$('body').on('click', '#showmodel1>dl>dt>a', function(e){
		var rowId = $(this).attr("rowId");
		location.href = "vedio.html?rowId="+rowId;
	});
	
	$('body').on('click', '#showmodel2>dl>dt>a', function(e){
		var rowId = $(this).attr("rowId");
		location.href = "vedio.html?rowId="+rowId;
	});
}

function playerInit(arrayObj){
	jPlayerPlay =
		new jPlayerPlaylist({
			jPlayer: "#jquery_jplayer_1",
			cssSelectorAncestor: "#jp_container_1"
		}, 
		    arrayObj
		, {
			swfPath: "jPlayer-2.9.2/dist/jplayer",
			supplied: "webmv, ogv, m4v",
			useStateClassSkin: true,
			autoBlur: false,
			smoothPlayBar: true,
			keyEnabled: true,
			size:{
                width:"100%",
                height:"480px"
            },
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

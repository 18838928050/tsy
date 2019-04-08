
function init(){
	dataInit();
	eventInit();
}

function dataInit(){
	AjaxUtil.request({url:path+"/post/admTsy/selectLoginMessage",params:{},type:'json',callback:function(json){
			Handlebars.registerHelper("compareSex", function(v1,options){
				if(v1!=null&&v1!=""){
					if(v1=="1"){
						return "男";
					}else{
						return "女";
					}
				}else{
					return "";
				}
			});	
			Handlebars.registerHelper("compareSfby", function(v1,options){
				if(v1!=null&&v1!=""){
					if(v1=="1"){
						return "已毕业";
					}else{
						return "未毕业";
					}
				}else{
					return "";
				}
			});	
			var source = $("#select-ol").html();  
			var template = Handlebars.compile(source);
			$("#mationTbody").html(template(json));
			$("#mationTbody input").hide();
			$("#mationTbody select").hide();
			$("#mationTbody textarea").hide();
			$("label[class='change']").show();
			$("#save").hide();
			$("#cancle").hide();
			$("#edit").show();
			$('body').find("#sex").val(json.bean.sex);
			$('body').find("#whether_graduate").val(json.bean.whether_graduate);
		}
	});
}

function eventInit(){
	$('body').on('click', '#edit', function(e){
		$("#save").show();
		$("#cancle").show();
		$("#edit").hide();
		$("#mationTbody input").show();
		$("#mationTbody select").show();
		$("#mationTbody textarea").show();
		$("label[class='change']").hide();
	});
	
	$('body').on('click', '#cancle', function(e){
		dataInit();
	});
	
	$('body').on('keyup', '#age', function(e){
		var reg = $(this).val().match(/\d+\.?\d{0,2}/);
        var txt = '';
        if (reg != null) {
            txt = reg[0];
        }
        $(this).val(txt);
	});
	
	$('body').on('change', '#age', function(e){
		 $(this).keypress();
	        var v = $(this).val();
	        if (/\.$/.test(v))
	        {
	            $(this).val(v.substr(0, v.length - 1));
	        }
	});
	
	$('body').on('click', '#save', function(e){
		if(!checkIdNum()){
			return;
		}else if(!isEmail($("#email").val())&&$("#email").val()!=""){
			qiao.bs.msg({msg:"邮箱格式不正确,请确认后输入",type:'danger'});
			return;
		}else if(!isQQ($("#qq").val())&&$("#qq").val()!=""){
			qiao.bs.msg({msg:"QQ格式不正确,请确认后输入",type:'danger'});
			return;
		}
		var param = {
				realname:$("#realname").val(),
				sex:$("#sex").val(),
				age:$("#age").val(),
				work_time:$("#work_time").val(),
				csri:$("#csri").val(),
				education:$("#education").val(),
				major:$("#major").val(),
				email:$("#email").val(),
				whether_graduate:$("#whether_graduate").val(),
				bysj:$("#bysj").val(),
				nation:$("#nation").val(),
				political_landscape:$("#political_landscape").val(),
				qq:$("#qq").val(),
				idcard:$("#idcard").val(),
				now_address:$("#now_address").val(),
				home_address:$("#home_address").val(),
				work_address:$("#work_address").val(),
				message:$("#message").val(),
		};
		AjaxUtil.request({url:path+"/post/admTsy/updateLoginMessage",params:param,type:'json',callback:function(json){
				if(json.returnCode=="0"){
					dataInit();
				}
			}
		});
	});
}

function checkIdNum(){
	var card = $('#idcard').val();
	if(card==null||card==""){
	}else{
		var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
		if(reg.test(card) === false)  
		{  
			qiao.bs.msg({msg:"身份证输入不合法",type:'danger'});
			return  false;  
		}  
	}
	return true;
}

function isQQ(str){
	if(/^[1-9]\d{4,10}$/.test(str)){
		return true;
	}else{
		return false;
	}
}

function isEmail(str){
	if(/^\w+([-+.]\w+)*@\w+([-.]\\w+)*\.\w+([-.]\w+)*$/.test(str)){
		return true;
	}else{
		return false;
	}
}



$(function(){
	init();
});

function init(){
	dataInit();
}

function dataInit(){
	AjaxUtil.request({url:path+"/post/WechatSetup/queryWechatSetupList",params:{},type:'json',callback:function(json){
			if(json.returnCode=="0"){
				$("#content").html();
				var str = "";
				for(var i = 0;i<json.rows.length;i++){
					str = str + setUpTypeOne(json.rows[i].setup_state,json.rows[i].setup_name,json.rows[i].id);
				}
				$("#content").html(str);
				eventInit();
			}else{
				qiao.bs.msg({msg:'数据异常',type:'warning'});
			}
		}
	});
}

function eventInit(){
	$("body div[class='switch']").each(function() {
	    $this = $(this);
	    var onColor = $this.attr("onColor");
	    var offColor = $this.attr("offColor");
	    var onText = $this.attr("onText");
	    var offText = $this.attr("offText");
	    var labelText = $this.attr("labelText");
	    var $switch_input = $(" :only-child", $this);
	    $switch_input.bootstrapSwitch({
	        onColor : onColor,
	        offColor : offColor,
	        onText : onText,
	        offText : offText,
	        labelText : labelText
	    });
	});
	
	$("#AIzn").on('switchChange.bootstrapSwitch', function (e, state) {
		var setupState;
		var id = $(this).attr("item");
        if(state){
        	setupState = 1;
		}else{
			setupState = 0;
		}
        AjaxUtil.request({url:path+"/post/WechatSetup/updateWechatSetupList",params:{"setupState":setupState,"id":id},type:'json',callback:function(json){
				if(json.returnCode=="0"){
					qiao.bs.msg({msg:json.returnMessage,type:'success'});
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'warning'});
				}
			}
		});
	});
	
	$("#BIzn").on('switchChange.bootstrapSwitch', function (e, state) {
		var setupState;
		var id = $(this).attr("item");
        if(state){
        	setupState = 1;
		}else{
			setupState = 0;
		}
        AjaxUtil.request({url:path+"/post/WechatSetup/updateWechatSetupList",params:{"setupState":setupState,"id":id},type:'json',callback:function(json){
				if(json.returnCode=="0"){
					qiao.bs.msg({msg:json.returnMessage,type:'success'});
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'warning'});
				}
			}
		});
	});
}

function setUpTypeOne(state,name,id){
	var str = "";
	if(name=="智能回复"){
		str = '<div class="row fileupload-buttonbar" style="margin-left:120px;float: left;width:240px"><div class="form-group btstp-div"><label for="firstname" class="col-sm-2 control-label btstp-label">智能回复:</label><div class="switch" onColor="success" offColor="warning" onText="开" offText="关" labelText="状态" style="height: 30px">';
		if(state=="1"){
			str = str + '<input id="AIzn" type="checkbox" item="'+id+'" checked /></div></div></div>';
		}else{
			str = str + '<input id="AIzn" type="checkbox" item="'+id+'" /></div></div> </div>';
		}
	}else if(name=="智能搜索"){
		str = '<div class="row fileupload-buttonbar" style="margin-left: 400px;float: left;width:240px"><div class="form-group btstp-div"><label for="firstname" class="col-sm-2 control-label btstp-label">智能搜索:</label><div class="switch" onColor="success" offColor="warning" onText="开" offText="关" labelText="状态" style="height: 30px">';
		if(state=="1"){
			str = str + '<input id="BIzn" type="checkbox" item="'+id+'" checked /></div></div></div>';
		}else{
			str = str + '<input id="BIzn" type="checkbox" item="'+id+'" /></div></div> </div>';
		}
	}
	return str;
}

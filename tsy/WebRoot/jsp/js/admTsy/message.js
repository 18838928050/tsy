
function init(){
	dataInit();
	eventInit();
}

function dataInit(){

}

function eventInit(){
	$('body').on('click', '#btn-default', function(e){
		var firstname = $("#firstname").val();//昵称
		var lastname = $("#lastname").val();//邮箱/手机号
		var liuyan = $("#liuyan").val();//内容
		var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if(""==firstname||firstname==null){
			qiao.bs.msg({msg:"请填写姓名...",type:'danger'});
		}else if(!(/^1[34578]\d{9}$/.test(lastname))||filter.test(lastname)){
			qiao.bs.msg({msg:"邮箱或手机号格式不正确...",type:'danger'});
		}else if(""==liuyan||liuyan==null){
			qiao.bs.msg({msg:"请填写留言...",type:'danger'});
		}else{
			AjaxUtil.request({url:path+"/post/admTsy/insertLeavingMessage",params:{firstname:firstname,lastname:lastname,liuyan:liuyan},type:'json',callback:function(json){
					qiao.bs.msg({msg:"您的留言已收到,我们会加大进度改进,谢谢您的建议...",type:'success'});
					setTimeout(function(){
						location.reload()
					},2000);
				}
		    });
		}
	});
}
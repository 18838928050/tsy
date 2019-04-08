
var id = null;

var nowStr = "";

var chnNumChar = ["零","一","二","三","四","五","六","七","八","九"];
var chnUnitSection = ["","万","亿","万亿","亿亿"];
var chnUnitChar = ["","十","百","千"];

$(function(){
	init();
});

function init(){
	receiveData();
	id = $.req("id");
	dataInit();
	eventInit();
}

function dataInit(){
	if(id!=null&&id!=""){
		AjaxUtil.request({url:path+"/post/investigationQuestion/queryVoteListByGeneralId",params:{id:id},type:'json',callback:function(json){
			if(json.returnCode=="0"){
				$("#voteTitle").attr("whethersave",""+json.bean.id);
				$("#voteTitle").val(json.bean.tsyVoteTitle);
				if(json.rows.length==0){
					addQuestionItem();
				}else{
					Handlebars.registerHelper("eachSize", function(v1,options){
						if(v1.length==0){
							return '<li class="list-group-item optionsItem" style="height: 50px">'+
										'<font style="font-size: 15px;float: left;margin-left: 35px;line-height: 30px;">选项一</font>'+
										'<input type="text" whethersave="0" lx="questionOptions" class="form-control optionsName" placeholder="请输入选项" style="float: left;margin-left: 2%;width: 73%;margin-top: -3px;"/>'+
									'</li>';
						}else{
							return "";
						}
					});
					Handlebars.registerHelper("compareIndex", function(v1,options){
						v1++;
						return NumberToChinese(v1);
					});
					Handlebars.registerHelper("cpmpareOptionIndex", function(v1,options){
						v1++;
						return NumberToChinese(v1);
					});
					Handlebars.registerHelper("optionDelete", function(v1,v2,options){
						v1++;
						if(v1==v2){
							return '<a style="font-size: 13px;float: left;margin-left: 35px;line-height: 30px;color: blue;" class="deleteOptionsItem" href="javascript:;">删除选项</a>';
						}else{
							return "";
						}
					});
					Handlebars.registerHelper("compareDelete", function(v1,v2,options){
						v1++;
						if(v1==1){
							return ""; 
						}else{
							return '<a style="float: right;color: blue;font-size: 15px;margin-right: 5px" rowId="'+v2+'" href="javascript:;" class="stopdelete">删除</a>';
						}
					});
					Handlebars.registerHelper("compareIndexSome", function(v1,options){
						return v1++;
					});
					Handlebars.registerHelper("compareChoose", function(v1,v2,options){
						if(v2==null||v2==""){
							return '0';
						}else{
							return id;
						}
					});
					Handlebars.registerHelper("compareChooseCheck", function(v1,v2,v3,options){
						if(v2==null||v2==""){
							return "";
						}else{
							if(v2=='1'&&v3=='danx'){
								return 'checked';
							}else if(v2=='2'&&v3=='duox'){
								return 'checked';
							}else{
								return '';
							}
						}
					});
					var source = $("#select-temp").html();  
				    var template = Handlebars.compile(source);
				    $("#questionPanel").html(template(json));
				}
			}else{
				qiao.bs.msg({msg:"数据操作失败",type:'danger'});
			}
		}
		});
	}else{
		addQuestionItem();
	}
}

function eventInit(){
	
	$('body').on('focus', 'input', function(e){
		nowStr = $(this).val();
	});
	
	//投票名称
	$('body').on('change', '#voteTitle', function(e){
		if($(this).val()==null||$(this).val()==""){
			qiao.bs.msg({msg:"请填写投票名称...",type:'danger'});
			$(this).val(nowStr);
			return;
		}
		if($(this).attr("whethersave")=="0"){
			var param = {
					generalTitle:$(this).val()
			};
			AjaxUtil.request({url:path+"/post/investigationQuestion/insertGeneralMation",params:param,type:'json',callback:function(json){
					if(json.returnCode=="0"){
						$("#voteTitle").attr("whethersave",""+json.bean.id);
						qiao.bs.msg({msg:"投票名称编辑成功",type:'success'});
					}else{
						qiao.bs.msg({msg:"数据操作失败",type:'danger'});
					}
				}
			});
		}else{
			var param = {
					generalTitle:$(this).val(),
					id:$(this).attr("whethersave")
			};
			AjaxUtil.request({url:path+"/post/investigationQuestion/updateGeneralMation",params:param,type:'json',callback:function(json){
					if(json.returnCode=="0"){
						qiao.bs.msg({msg:"投票名称编辑成功",type:'success'});
					}else{
						qiao.bs.msg({msg:"数据操作失败",type:'danger'});
					}
				}
			});
		}
	});
	
	//问题标题变化保存事件
	$('body').on('change', '.questionName', function(e){
		if($("#voteTitle").attr("whethersave")=="0"){
			qiao.bs.msg({msg:"请填写投票名称...",type:'danger'});
			$(this).val(nowStr);
			return;
		}
		if($(this).val()==null||$(this).val()==""){
			qiao.bs.msg({msg:"请填写问题标题...",type:'danger'});
			$(this).val(nowStr);
			return;
		}
		if($(this).attr("whethersave")=="0"){
			var param = {
					questionName:$(this).val(),
					questionGeneralId:$("#voteTitle").attr("whethersave")
			};
			var mythis = $(this);
			AjaxUtil.request({url:path+"/post/investigationQuestion/insertQuestionMation",params:param,type:'json',callback:function(json){
					if(json.returnCode=="0"){
						mythis[0].setAttribute("whethersave",""+json.bean.id);
						qiao.bs.msg({msg:"问题保存成功",type:'success'});
					}else{
						qiao.bs.msg({msg:"数据操作失败",type:'danger'});
					}
				}
			});
		}else{
			var param = {
					questionName:$(this).val(),
					id:$(this).attr("whethersave")
			};
			AjaxUtil.request({url:path+"/post/investigationQuestion/updateQuestionMation",params:param,type:'json',callback:function(json){
					if(json.returnCode=="0"){
						qiao.bs.msg({msg:"问题编辑成功",type:'success'});
					}else{
						qiao.bs.msg({msg:"数据操作失败",type:'danger'});
					}
				}
			});
		}
	});
	
	//问题类型变化保存事件
	$('body').on('click', '.questionType', function(e){
		if($("#voteTitle").attr("whethersave")=="0"){
			qiao.bs.msg({msg:"请填写投票名称...",type:'danger'});
			$(this).removeAttr("checked");
			return;
		}
		if($(this).parent().parent().parent().find("input[lx='questionName']").attr("whethersave")==0){
			qiao.bs.msg({msg:"请填写问题标题...",type:'danger'});
			$(this).removeAttr("checked");
			return;
		}
		var param = {
				id:$(this).parent().parent().parent().find("input[lx='questionName']").attr("whethersave"),
				questionType:$(this).val(),
		};
		var mythis = $(this);
		AjaxUtil.request({url:path+"/post/investigationQuestion/updateQuestionMation",params:param,type:'json',callback:function(json){
				if(json.returnCode=="0"){
					for(var i = 0 ;i<mythis.parent().parent().find("input[type='radio']").length;i++){
						mythis.parent().parent().find("input[type='radio']")[i].setAttribute("whethersave",mythis.parent().parent().parent().find("input[lx='questionName']").attr("whethersave"));
					}
					qiao.bs.msg({msg:"问题类型编辑成功",type:'success'});
				}else{
					qiao.bs.msg({msg:"数据操作失败",type:'danger'});
				}
			}
		});
	});
	
	//问题选项保存事件
	$('body').on('change', '.optionsName', function(e){
		if($("#voteTitle").attr("whethersave")=="0"){
			qiao.bs.msg({msg:"请填写投票名称...",type:'danger'});
			$(this).removeAttr("checked");
			return;
		}
		if($(this).parent().parent().find("input[lx='questionName']").attr("whethersave")==0){
			qiao.bs.msg({msg:"请填写问题标题...",type:'danger'});
			$(this).val(nowStr);
			return;
		}
		if($(this).val()==null||$(this).val()==""){
			qiao.bs.msg({msg:"请填写选项...",type:'danger'});
			$(this).val(nowStr);
			return;
		}
		if($(this).attr("whethersave")=="0"){
			var param = {
					optionName:$(this).val(),
					optionQuestionId:$(this).parent().parent().find("input[lx='questionName']").attr("whethersave")
			};
			var mythis = $(this);
			AjaxUtil.request({url:path+"/post/investigationQuestion/insertOptionsMation",params:param,type:'json',callback:function(json){
					if(json.returnCode=="0"){
						mythis[0].setAttribute("whethersave",""+json.bean.id);
						qiao.bs.msg({msg:"选项保存成功",type:'success'});
					}else{
						qiao.bs.msg({msg:"数据操作失败",type:'danger'});
					}
				}
			});
		}else{
			var param = {
					optionName:$(this).val(),
					id:$(this).attr("whethersave")
			};
			AjaxUtil.request({url:path+"/post/investigationQuestion/updateOptionsMation",params:param,type:'json',callback:function(json){
					if(json.returnCode=="0"){
						qiao.bs.msg({msg:"选项编辑成功",type:'success'});
					}else{
						qiao.bs.msg({msg:"数据操作失败",type:'danger'});
					}
				}
			});
		}
	});
	
	//添加问题
	$('body').on('click', '#addQuestion', function(e){
		if($("#questionPanel").find("input[whethersave='0']").length>0){
			qiao.bs.msg({msg:"请填写完成之前为空的内容...",type:'danger'});
		}else{
			addQuestionItem();
		}
	});
	
	//保存问题
	$('body').on('click', '#saveMessage', function(e){
		if($("#questionPanel").find("input[whethersave='0']").length>0){
			qiao.bs.msg({msg:"请填写完成之前为空的内容...",type:'danger'});
		}else{
			var param = {
					generalwehetherSave : '1',
					id : $("#voteTitle").attr("whethersave")
			}
			AjaxUtil.request({url:path+"/post/investigationQuestion/updateVoteMessage",params:param,type:'json',callback:function(json){
					if(json.returnCode=="0"){
						qiao.bs.msg({msg:"保存成功",type:'success'});
						setTimeout(function(){
							location.href = "voteList.html";
						}, 1000);  
					}else{
						qiao.bs.msg({msg:"数据操作失败",type:'danger'});
					}
				}
			});
		}
	});
	
	//添加问题选项
	$('body').on('click', '.addQuestionOptionsItem', function(e){
		var thisQutionOptionsLength = $(this).parent().parent().find(".optionsItem").length+1;
		addQuestionOptionsItem(thisQutionOptionsLength,$(this));
	});
	
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
	
	//删除选项按钮
	$('body').on('click', '.deleteOptionsItem', function(e){
		var optionsLength = $(this).parent().parent().find(".optionsItem").length;
		var param = {
				id:$(this).parent().find("input[lx='questionOptions']").attr("whethersave")
		};
		if($(this).parent().find("input[lx='questionOptions']").attr("whethersave")!=0){
			var mythis = $(this);
			AjaxUtil.request({url:path+"/post/investigationQuestion/deleteOptionsMation",params:param,type:'json',callback:function(json){
				if(json.returnCode=="0"){
					if(optionsLength!=2){
						var deleteStr = '<a style="font-size: 13px;float: left;margin-left: 35px;line-height: 30px;color: blue;" class="deleteOptionsItem" href="javascript:;">删除选项</a>';
						mythis.parent().prev().append(deleteStr);
					}
					mythis.parent().remove();
					qiao.bs.msg({msg:"删除成功",type:'success'});
				}else{
					qiao.bs.msg({msg:"数据操作失败",type:'danger'});
				}
			}
			});
		}else{
			if(optionsLength!=2){
				var deleteStr = '<a style="font-size: 13px;float: left;margin-left: 35px;line-height: 30px;color: blue;" class="deleteOptionsItem" href="javascript:;">删除选项</a>';
				$(this).parent().prev().append(deleteStr);
			}
			$(this).parent().remove();
			qiao.bs.msg({msg:"删除成功",type:'success'});
		}
	});
	
	//删除问题按钮
	$('body').on('click', '.stopdelete', function(e){
		var param = {
				id:$(this).parent().parent().parent().find("input[lx='questionName']").attr("whethersave")
		};
		if($(this).parent().parent().parent().find("input[lx='questionName']").attr("whethersave")!=0){
			var mythis = $(this);
			AjaxUtil.request({url:path+"/post/investigationQuestion/deleteQuestionMation",params:param,type:'json',callback:function(json){
				if(json.returnCode=="0"){
					mythis.parent().parent().parent().remove();
					qiao.bs.msg({msg:"删除成功",type:'success'});
					dataInit();
				}else{
					qiao.bs.msg({msg:"数据操作失败",type:'danger'});
				}
			}
			});
		}else{
			$(this).parent().parent().parent().remove();
			qiao.bs.msg({msg:"删除成功",type:'success'});
		}
	});
	
}

function addQuestionOptionsItem(thisQutionOptionsLength,main){
	main.parent().parent().find(".optionsItem>.deleteOptionsItem").remove();
	var str = '<li class="list-group-item optionsItem" style="height: 50px">'+
					'<font style="font-size: 15px;float: left;margin-left: 35px;line-height: 30px;">选项'+NumberToChinese(thisQutionOptionsLength)+'</font>'+
					'<input type="text" whethersave="0" lx="questionOptions" class="form-control optionsName" placeholder="请输入选项" style="float: left;margin-left: 2%;width: 73%;margin-top: -3px;"/>'+
					'<a style="font-size: 13px;float: left;margin-left: 35px;line-height: 30px;color: blue;" class="deleteOptionsItem" href="javascript:;">删除选项</a>'+
				'</li>';
	main.parent().before(str);
}

function addQuestionItem(){
	var panelLength = $("#questionPanel").find(".panel-default").length+1;
	var deleteStr = '';
	if(panelLength!=1){
		deleteStr = '<a style="float: right;color: blue;font-size: 15px;margin-right: 5px" href="javascript:;" class="stopdelete">删除</a>'
	}
	var str = '<div class="panel panel-default">'+
		   			'<div class="panel-heading">'+
					    '<h3 class="panel-title">'+
					    	'<a href="javascript:;">问题'+NumberToChinese(panelLength)+'</a>'+
					    	'<a style="float: right;color: blue;font-size: 15px;margin-right: 5px" href="javascript:;" class="Stop">收起</a>'+
					    	'<a style="float: right;color: blue;font-size: 15px;display:none;margin-right: 5px" href="javascript:;" class="stopedit">编辑</a>'+
					    	deleteStr+
					    '</h3>'+
					 '</div>'+
			 '<div class="panel-body">'+
			   	'<ul class="list-group">'+
			  		'<li class="list-group-item" style="height: 50px">'+
							'<font style="font-size: 15px;float: left;margin-left: 35px;line-height: 30px;">标题</font>'+
							'<input type="text"  whethersave="0" lx="questionName" class="form-control questionName" placeholder="请输入标题" style="float: left;margin-left: 2%;width: 73%;margin-top: -3px;"/>'+
						'</li>'+
						'<li class="list-group-item" style="height: 50px">'+
							'<font style="font-size: 15px;float: left;margin-left: 35px;line-height: 30px;">类型</font>'+
							'<div class="opt" style="width: 10%;float: left;margin-left: 22px;margin-top: 5px;">'+
								'<input class="magic-radio questionType" whethersave="0" lx="questionType" value="1" type="radio" name="radio'+panelLength+'" id="fr'+panelLength+'">'+
								'<label for="fr'+panelLength+'" style="font-size: 15px;">单选</label>'+
							'</div>'+
							'<div class="opt" style="width: 10%;float: left;margin-top: 5px;">'+
								'<input class="magic-radio questionType"  whethersave="0" lx="questionType" value="2" type="radio" name="radio'+panelLength+'" id="fd'+panelLength+'">'+
								'<label for="fd'+panelLength+'" style="font-size: 15px;">多选</label>'+
							'</div>'+
						'</li>'+
						'<li class="list-group-item optionsItem" style="height: 50px">'+
							'<font style="font-size: 15px;float: left;margin-left: 35px;line-height: 30px;">选项一</font>'+
							'<input type="text" whethersave="0" lx="questionOptions" class="form-control optionsName" placeholder="请输入选项" style="float: left;margin-left: 2%;width: 73%;margin-top: -3px;"/>'+
						'</li>'+
						'<li class="list-group-item optionsItem" style="height: 50px">'+
							'<font style="font-size: 15px;float: left;margin-left: 35px;line-height: 30px;">选项二</font>'+
							'<input type="text" whethersave="0" lx="questionOptions" class="form-control optionsName" placeholder="请输入选项" style="float: left;margin-left: 2%;width: 73%;margin-top: -3px;"/>'+
						'</li>'+
						'<li class="list-group-item optionsItem" style="height: 50px">'+
							'<font style="font-size: 15px;float: left;margin-left: 35px;line-height: 30px;">选项三</font>'+
							'<input type="text" whethersave="0" lx="questionOptions" class="form-control optionsName" placeholder="请输入选项" style="float: left;margin-left: 2%;width: 73%;margin-top: -3px;"/>'+
							'<a style="font-size: 13px;float: left;margin-left: 35px;line-height: 30px;color: blue;" class="deleteOptionsItem" href="javascript:;">删除选项</a>'+
						'</li>'+
						'<li class="list-group-item" style="height: 50px">'+
							'<a style="font-size: 13px;float: left;margin-left: 35px;line-height: 30px;color: blue;" class="addQuestionOptionsItem" href="javascript:;">添加选项</a>'+
						'</li>'+
					'</ul>'+
			'</div>'+
			'</div>';
	$("#questionPanel").append(str);
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


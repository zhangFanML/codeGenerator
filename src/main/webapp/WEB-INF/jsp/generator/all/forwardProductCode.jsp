<%@ page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<!-- jsp文件头和头部 -->
		<%@ include file="../../index/top.jsp"%>
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/ace/css/chosen.css" />
		<link rel="stylesheet" href="static/ace/css/select2.css" />

		<style type="text/css">
		#dialog-add,#dialog-message,#dialog-comment{width:100%; height:100%; position:fixed; top:0px; z-index:10000; display:none;}
		.commitopacity{position:absolute; width:100%; height:100%; background:#7f7f7f; filter:alpha(opacity=50); -moz-opacity:0.2; -khtml-opacity: 0.2; opacity: 0.2; top:0px; z-index:20000;}
		.commitbox{width:95%; padding-left:42px; padding-top:5px; position:absolute; top:0px; z-index:20000;}
		.commitbox_inner{width:96%; height:235px;  margin:6px auto; background:#efefef; border-radius:5px;}
		.commitbox_top{width:100%; height:233px; margin-bottom:10px; padding-top:10px; background:#FFF; border-radius:5px; box-shadow:1px 1px 3px #e8e8e8;}
		.commitbox_top textarea{width:95%; height:165px; display:block; margin:0px auto; border:0px;}
		.commitbox_cen{width:95%; height:40px; padding-top:10px;}
		.commitbox_cen div.left{float:left;background-size:15px; background-position:0px 3px; padding-left:18px; color:#f77500; font-size:16px; line-height:27px;}
		.commitbox_cen div.left img{width:30px;}
		.commitbox_cen div.right{float:right; margin-top:7px;}
		.commitbox_cen div.right span{cursor:pointer;}
		input[type=checkbox].ace.ace-switch.ace-switch-4:checked + .lbl::before {
			text-indent: 12px;
		}
		input[type=checkbox].ace.ace-switch.ace-switch-4 + .lbl::before {
			content: "是\a0\a0\a0\a0\a0\a0\a0\a0\a0\a0\a0否";
			font-size: 12px;
			line-height: 21px;
			height: 24px;
			overflow: hidden;
			border-radius: 12px;
			background-color: #8b9aa3;
			border: 1px solid #8b9aa3;
			color: #FFF;
			width: 56px;
			text-indent: -18px;
			text-shadow: 0 0 0 #FFF;
			display: inline-block;
			position: relative;
			box-shadow: none;
			-webkit-transition: all .3s ease;
			-o-transition: all .3s ease;
			transition: all .3s ease;
		}
		.inputStyle
		{
			text-align:center;
			border-color:white;
			border-width:0px;
			width: 99%;
		}

		.trStyle
		{
			background-color: rgba(42, 71, 75, 0.57);
		}

		.fixedTable {
			position: fixed;
			bottom: -10px;
			z-index: 1999;
			background-color: rgba(255, 255, 255, 0.5);
		}
		</style>
	</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div id="dialog-add">
				<div class="commitopacity"></div>
				<div class="commitbox">
					<div class="commitbox_inner">
						<div class="commitbox_top">
							<table class="table table-striped table-bordered table-hover" style="margin-bottom: 50px;">
								<tbody id="taL">
									<tr id="trL">
										<td style="text-align: center;width:260px;">表名</td>
										<td style="text-align: center;width:260px;">字段</td>
										<td style="text-align: center;">&</td>
										<td style="text-align: center;width:260px;">表名</td>
										<td style="text-align: center;width:260px;">字段</td>
										<td style="text-align: center;width:50px;"></td>
									</tr>
								</tbody>
							</table>
							<table class="table table-striped table-bordered table-hover" style="bottom: 10px;margin: 0px;">
								<tr>
									<td style="text-align: center;" colspan="10">
										<a id="btn_add" class="btn btn-mini btn-primary" onclick="addL();">增加</a>
										<a id="btn_sub" class="btn btn-mini btn-primary" onclick="saveL();">确定</a>
										<a id="btn_reset" class="btn btn-mini btn-danger" onclick="cancel_pl();">取消</a>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div id="zhongxin">
			<form action="createCode/proCode?flag=${pd.flag}" name="Form" id="Form" method="post">
				<input type="hidden" name="zindex" id="zindex" value="0">
				<input type="hidden" name="FIELDLIST" id="FIELDLIST" value="">
				<input type="hidden" name="faobject" id="faobject" value="">
				<input type="hidden" name="tabletops" id="tabletops" value="">
				<input type="hidden" name="FHTYPE" id="FHTYPE" value="full"/>
				<div class="panel-collapse collapse in" id="collapseTwo" aria-expanded="true">
					<div class="panel-body" style="padding-bottom: 2px">
						<div style="float: left;">
							<table>
								<tr>
									<td>&nbsp;所属子系统：</td>
									<td><input type="text" name="belongSystem" id="belongSystem" value="${pd.BELONGSYSTEM }" maxlength="200" placeholder="输入子系统名称" title="子系统名称"/></td>
									<td>领域名称：</td>
									<td>
										<select name="businessLine" id="businessLine" data-placeholder="请选择所属领域"  style="vertical-align:top;width: 100%;">
											<option value="" ${pd.BUSINESSLINE == ""?"selected":""}>无</option>
											<option value=".person" ${pd.BUSINESSLINE == ".person"?"selected":""}>个人</option>
											<option value=".corporation" ${pd.BUSINESSLINE == ".corporation"?"selected":""}>企业</option>
										</select>
									</td>
									<td>&nbsp;业务模块：</td>
									<td><input type="text" name="packageName" id="packageName" value="${pd.PACKAGENAME}"  maxlength="10" placeholder="这里输入所属模块 (不要输入特殊字符,请用纯字母)" title="这里输入所属模块 (不要输入特殊字符,请用纯字母)"/></td>
									<td>&nbsp;业务名称：</td>
									<td><input type="text" name="TITLE" id="TITLE" value="${pd.TITLE}" maxlength="200" placeholder="这里输入模块说明内容" title="这里输入模块说明内容"/></td>
									<td>&nbsp;处理类：</td>
									<td><input type="text" name="objectName" id="objectName" value="${pd.OBJECTNAME}"  maxlength="10" placeholder="类名首字母必须为大写字母或下划线" title="类名首字母必须为大写字母或下划线"/></td>
								</tr>
								<tr><td colspan="100" style="height: 3px;"></td></tr>
								<tr>
									<td>&nbsp;目标表：</td>
									<td colspan="10">
										<select name="tabletop" id="tabletop" data-placeholder="请选择需要的表" multiple  style="vertical-align:top;width: 1171px;">
											<!-- 开始循环 -->
											<c:forEach items="${tblist}" var="var" varStatus="vs">
												<option value="${var}">${var}</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr><td colspan="100" style="height: 3px;"></td></tr>
								<tr>
									<td>&nbsp;关联关系：</td>
									<td colspan="10">
										<div class="nav-search" style="float: left;"><input class="nav-search-input" type="text"   onclick="dialog_open();" name="whereis" id="whereis" value="${pd.whereis}" placeholder="请添加关联关系" style="width:1150px;" title="关联关系" readonly/></div>
										<a class="btn btn-white btn-sm btn-primary" title="添加关联" onclick="dialog_open();" style="width: 20px;height:28px;padding: 4px 1px 4px 3px;margin-left: 1px;">
											<i class="ace-icon glyphicon glyphicon-plus" title="添加关联"></i>
										</a>
									</td>
								</tr>
							</table>

							<table id="table_report" class="table table-bordered" style="margin-top: 3px;">

								<thead>
									<tr>
										<th class="center" style="width:40px;">序号</th>
										<th class="center"style="width:150px;">表名</th>
										<th class="center"style="width:135px;">属性名</th>
										<th class="center">注释</th>
										<th class="center" style="width:100px;">类型</th>
										<th class="center" style="width:100px;">字典</th>
										<th class="center" style="width:60px;">长度</th>
										<th class="center" style="width:60px;">小数位</th>
										<th class="center" style="width:79px;">表单录入</th>
										<th class="center" style="width:79px;">列表显示</th>
										<th class="center" style="width:79px;">是否必输</th>
										<th class="center" style="width:79px;">是否查询</th>
										<th class="center" style="width:79px;">是否模糊</th>
										<th class="center" style="width:70px;">默认值</th>
										<th class="center" style="width:50px;">操作</th>
									</tr>
								</thead>
								<tbody id="fields"></tbody>
							</table>
				</div>
			</div>
			</div>
			</form>
			</div>
				<div id="E" style="position: fixed ;z-index: 200;left:0%; display: block; top: 45%;width: 40px;">
					<ul style="list-style-type:none;margin: 0px;">
						<li >
							<a class="btn btn-success pull-left" title="上移" onclick="up();" style="border-width:3px;border-right-width:0px;margin: 0px 0px;width: 20px;height:36px;padding: 4px;">
								<i class="ace-icon fa fa-caret-up icon-only" title="上移"></i>
							</a>
							<a class="btn btn-success pull-left" title="下移" onclick="below();" style="border-width:3px;border-left-width:0px;margin: 0px 0px;width: 20px;height:36px;padding: 4px;">
								<i class="ace-icon fa fa-caret-down icon-only" title="下移"></i>
							</a>
						</li>
						<li id="huoqu">
							<a class="btn btn-info btn-sm" title="获取字段" onclick="selectTableRow();" style="width: 40px;">
								<i class="ace-icon fa fa-briefcase" title="获取字段"></i>
							</a>
						</li>
						<li >
							<a class="btn btn-primary btn-sm" title="添加字段" onclick="addTableRow();" style="width: 40px;">
								<i class="ace-icon glyphicon glyphicon-plus" title="添加字段"></i>
							</a>
						</li>
						<li>
							<a class="btn btn-info btn-sm" title="生成代码" onclick="toCode();" style="width: 40px;">
								<i class="ace-icon fa fa-floppy-o" title="生成代码"></i>
							</a>
						</li>
					</ul>

				</div>

				<div id="zhongxin2" class="center" style="position:fixed;z-index: 20001;top: 0%;left: 0%;display:none;width: 100%;height: 100%;padding: auto;background-color: rgba(212,216,184,0.2);">
					<div style="position:fixed;top:40%;left: 45%;">
						<img src="static/images/jiazai.gif"/>
						<h4 class="lighter block green">
							<strong id="second_show">10秒</strong>
						</h4>
					</div>
				</div>
				<div id="zhongxin3" class="center" style="position:fixed;z-index: 20001;top: 0%;left: 0%;display:none;width: 100%;height: 100%;padding: auto;background-color: rgba(212,216,184,0.2);">
					<div style="position:fixed;top:40%;left: 45%;">
						<img src="static/images/jiazai.gif"/>
						<h4 class="lighter block green">
							<strong id="second_show2">获取字段中,请耐心等待...</strong>
						</h4>
					</div>
				</div>
		</div>
	</div>

<!-- 页面底部js¨ -->
<%@ include file="../../index/foot.jsp"%>
<!-- 下拉框 -->
<script src="static/ace/js/chosen.jquery.js"></script>
<script src="static/ace/js/ace/ace.js"></script>
<script src="static/ace/js/select2.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<%--<script src="static/js/myjs/productCode.js"></script>--%>
<script type="text/javascript">
	$(top.hangge());//关闭加载状态

	var arField = new Array();
	var index = 0;

	$(reductionFields());
	//修改时还原属性列表
	function reductionFields(){
		var msg = '${msg}';
		if('edit' == msg){
			var nowarField = '${pd.FIELDLIST}';
			var fieldarray = nowarField.split('|@@|');
			for(var i=0;i<fieldarray.length;i++){
				if(fieldarray[i] != ''){
					appendRow(fieldarray[i]);
					arField[i] = fieldarray[i];
				}
			}
		}else{
			/*var tabletop = $("#tabletop").val();
			alert(tabletop != null && tabletop != "" && tabletop != '');
			if(tabletop != null && tabletop != "" && tabletop != ''){
				selectTableRow();
			}*/
		}
	}

	//此处为表名赋值
	var select_id = "${pd.TABLENAME}";
	arr = select_id.split(",");//注意：arr为select的id值组成的数组
	$('#tabletop').val(arr).trigger('change');

	var tabletop = [];
	$("#tabletop").select2({
		data: tabletop,
		placeholder:'请选择目标表',//默认文字提示
		language: "zh-CN",//汉化
		allowClear: true//允许清空
	});
	//获取字段
	function selectTableRow(){
		var tabletop = $("#tabletop").val();
		if(tabletop != null && tabletop != "" && tabletop != ''){
			$("#zhongxin3").show();
			$.ajax({
				type:"post",
				url:"<%=basePath%>createCode/getTableRow?tabletop="+tabletop + "${info != null ? info : ""}",
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					if(data != null && data != "" && data != ''){
						$("#fields").children().remove();
						var fieldarray = data.split('|@@|');
						for(var i=0;i<fieldarray.length;i++){
							if(fieldarray[i] != ''){
								appendRow(fieldarray[i]);
								arField[i] = fieldarray[i];
							}
						}
						$("#zhongxin3").hide();
					}
				}
			});
		}
	}

	//初始化(反显)列表
	function appendRow(value){
		index = $("#fields").children().length;
		var fieldarray = value.split('|@|');
		$("#fields").append(
				'<tr onclick="changeTrOrder(this)"  draggable="true">'+
				'<td class="center">'+Number(index+1)+'</td>'+
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" >'+fieldarray[10]+'</td>'+	//表名
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" >'+fieldarray[0]+'</td>'+	    //属性名称
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" >'+fieldarray[2]+'</td>'+	    //中文说明
				'<td class="center" onmouseenter="changeToInput(this,\'select\')" onmouseleave="changeToText(this)" >'+fieldarray[1]+'</td>'+	    //字段类型
				'<td class="center" onmouseenter="changeToInput(this,\'Dictionary\')" onmouseleave="changeToText(this)" >'+fieldarray[8]+'</td>'+	    //字段码值
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" >'+fieldarray[5]+'</td>'+	    //长度
				'<td class="center" onmouseenter="changeToInput(this,\'Double\')" onmouseleave="changeToText(this)" >'+fieldarray[6]+'</td>'+	    //小数点
				'<td>' +
					'<label class="pull-right inline">' +
					'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
					'<input id="fieldIs1'+index+'" name="fieldIs1'+index+'" type="checkbox" '+ (fieldarray[3] == '是'? 'checked value="是"':'value="否"') + ' onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
					'<span class="lbl middle"></span>' +
					'</label>' +
				'</td>' +
				/*'<td class="center">'+fieldarray[3]+'<input type="hidden" name="field3'+index+'" value="'+fieldarray[3]+'"></td>'+	//表单录入*/
				'<td>' +
					'<label class="pull-right inline">' +
					'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
					'<input  id="fieldIs2'+index+'" name="fieldIs2'+index+'" type="checkbox" '+ (fieldarray[9] == '是'? 'checked value="是"':'value="否"') + 'onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
					'<span class="lbl middle"></span>' +
					'</label>' +
				'</td>' +
				/*'<td class="center">'+fieldarray[9]+'<input type="hidden" name="field8'+index+'" value="'+fieldarray[9]+'"></td>'+	//列表显示*/
				'<td>' +
					'<label class="pull-right inline">' +
					'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
					'<input id="fieldIs3'+index+'" name="fieldIs3'+index+'" type="checkbox" '+ (fieldarray[7] == '是'? 'checked value="是"':'value="否"') + 'onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
					'<span class="lbl middle"></span>' +
					'</label>' +
				'</td>' +
				/*'<td class="center">'+fieldarray[7]+'<input type="hidden" name="field3'+index+'" value="'+fieldarray[7]+'"></td>'+	//是否必输*/
				'<td>' +
					'<label class="pull-right inline">' +
					'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
					'<input id="fieldIs4'+index+'" name="fieldIs4'+index+'" type="checkbox" '+ (fieldarray[11] == '是'? 'checked value="是"':'value="否"') + 'onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
					'<span class="lbl middle"></span>' +
					'</label>' +
				'</td>' +
				/*'<td class="center">'+fieldarray[11]+'<input type="hidden" name="field3'+index+'" value="'+fieldarray[11]+'"></td>'+	//是否查询*/
				'<td>' +
					'<label class="pull-right inline">' +
					'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
					'<input id="fieldIs5'+index+'" name="fieldIs5'+index+'" type="checkbox" '+ (fieldarray[12] == '是'? 'checked value="是"':'value="否"') + 'onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
					'<span class="lbl middle"></span>' +
					'</label>' +
				'</td>' +
				/*'<td class="center">'+fieldarray[12]+'<input type="hidden" name="field3'+index+'" value="'+fieldarray[12]+'"></td>'+	//是否模糊查询*/
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" >'+fieldarray[4]+'</td>'+	//默认值
				'<td class="center">'+
				'<input type="hidden" name="field'+ index +'" value="'+ value +'">'+
				'<a class="btn btn-mini btn-danger" title="删除" onclick="removeField('+ index +')"><i class="ace-icon fa fa-trash-o bigger-120"></i></a> '+
				'</td>'+
				'</tr>'
		);
		index++;
		$("#zindex").val(index);
	}

	//添加字段
	function addTableRow(){
		// console.log("添加字段");
		index = $("#fields").children().length;
		$("#fields").append(
				'<tr onclick="changeTrOrder(this)" draggable="true">'+
					'<td class="center" >'+Number(index+1)+'</td>'+
					'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" ></td>'+	//表名
					'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" ></td>'+	    //属性名称
					'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" ></td>'+	    //中文说明
					'<td class="center" onmouseenter="changeToInput(this,\'select\')" onmouseleave="changeToText(this)" ></td>'+	    //字段类型
					'<td class="center" onmouseenter="changeToInput(this,\'Dictionary\')" onmouseleave="changeToText(this)" ></td>'+	    //字段码值
					'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" >255</td>'+	    //长度
					'<td class="center" onmouseenter="changeToInput(this,\'Double\')" onmouseleave="changeToText(this)" >0</td>'+	    //小数点
					'<td>' +
						'<label class="pull-right inline">' +
						'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
						'<input id="fieldIs1'+index+'" name="fieldIs1'+index+'" type="checkbox" value="是" checked onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
						'<span class="lbl middle"></span>' +
						'</label>' +
					'</td>' +
					/*'<td class="center">'+fieldarray[3]+'<input type="hidden" name="field3'+index+'" value="'+fieldarray[3]+'"></td>'+	//表单录入*/
					'<td>' +
						'<label class="pull-right inline">' +
						'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
						'<input  id="fieldIs2'+index+'" name="fieldIs2'+index+'" type="checkbox"value="是" checked onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
						'<span class="lbl middle"></span>' +
						'</label>' +
					'</td>' +
					/*'<td class="center">'+fieldarray[9]+'<input type="hidden" name="field8'+index+'" value="'+fieldarray[9]+'"></td>'+	//列表显示*/
					'<td>' +
						'<label class="pull-right inline">' +
						'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
						'<input id="fieldIs3'+index+'" name="fieldIs3'+index+'" type="checkbox" value="是" checked onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
						'<span class="lbl middle"></span>' +
						'</label>' +
					'</td>' +
					/*'<td class="center">'+fieldarray[7]+'<input type="hidden" name="field3'+index+'" value="'+fieldarray[7]+'"></td>'+	//是否必输*/
					'<td>' +
						'<label class="pull-right inline">' +
						'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
						'<input id="fieldIs4'+index+'" name="fieldIs4'+index+'" type="checkbox" value="否" onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
						'<span class="lbl middle"></span>' +
						'</label>' +
					'</td>' +
					'<td>' +
						'<label class="pull-right inline">' +
						'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
						'<input id="fieldIs5'+index+'" name="fieldIs5'+index+'" type="checkbox" value="否" onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
						'<span class="lbl middle"></span>' +
						'</label>' +
					'</td>' +
					'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" >无</td>'+	//默认值
					'<td class="center"">'+
						'<input type="hidden" name="field'+ index +'" value="">'+
						'<a class="btn btn-mini btn-danger" title="删除" onclick="removeField('+ index +')"><i class="ace-icon fa fa-trash-o bigger-120"></i></a> '+
					'</td>'+
				'</tr>'
		);
		var tds = $("#fields tr").eq(index).children();
		var table = (tds.eq(1).html());  //表名
		var dname = (tds.eq(2).html());  //属性名
		var dbz	  = (tds.eq(3).html());  //注释说明
		var dtype = (tds.eq(4).html());  //字段类型
		var dict  = (tds.eq(5).html());  //字典
		var flength = (tds.eq(6).html());  //长度
		var decimal = (tds.eq(7).html());  //小数位
		var ddefault = (tds.eq(13).html()); //默认值
		var isQian = (tds.eq(8).find("input").val());      //是否表单录入
		var isShow = (tds.eq(9).find("input").val());      //是否列表显示
		var isRequired = (tds.eq(10).find("input").val()); //是否必输
		var isQuery = (tds.eq(11).find("input").val());    //是否查询
		var isVague = (tds.eq(12).find("input").val());      //是否模糊查询
		var fields = dname + '|@|' + dtype + '|@|' + dbz + '|@|' +isQian + '|@|' + ddefault + '|@|' + flength + '|@|' + decimal + '|@|' + isRequired + '|@|' + dict + '|@|' + isShow + '|@|' + table + '|@|' + isQuery + '|@|' + isVague ;
		$("input[name='field"+ index +"']").val(fields);
		arField[index] = fields;
		index++;
		$("#zindex").val(index);
	}

	//表名校验
	function caseCheck(tableName){
		if(tableName == ''){
			return;
		}
		var reg = /^[a-zA-Z_0-9]+$/;
		if(reg.test(tableName)){   //完全由数字字母下划线 组成
			$("#table").val(tableName.toUpperCase());
		}else{
			$("#table").val("");
			$("#table").tips({
				side:3,
				msg:'输入符合规范的表名',
				bg:'#AE81FF',
				time:2
			});
			$("#table").focus();
		}
	}

	//为保存多表存值传值
	function tackTabletops(tabletops){
		$("#tabletops").val($("#tabletop").val());
	}

	//是否按钮的切值变换
	function updateFilesO(Obj){
		var obj = $(Obj);
		var objVal = obj.val();
		if(objVal == "是"){
			obj.val("否");
		}else if(objVal == "否"){
			obj.val("是");
		}
	}

	map = new Map();
	map.set(1,"table");
	map.set(2,"dname");
	map.set(3,"dbz");
	map.set(4,"dtype");
	map.set(5,"dict");
	map.set(6,"flength");
	map.set(7,"decimal");
	map.set(13,"ddefault");

	//监控所有修改 以进行必要的更新
	function getFields(Obj,change){
		var tdIndex = $(Obj).closest("td").index();
		var trIndex = $(Obj).closest("tr").index();
		var tds = $(Obj).closest("tr").children();
		var table = (tds.eq(1).html());  //表名
		var dname = (tds.eq(2).html());  //属性名
		var dbz	  = (tds.eq(3).html());  //注释说明
		var dtype = (tds.eq(4).html());  //字段类型
		var dict  = (tds.eq(5).html());  //字典
		var flength = (tds.eq(6).html());  //长度
		var decimal = (tds.eq(7).html());  //小数位
		var ddefault = (tds.eq(13).html()); //默认值
		if(change != '' && change != null && change != ""){
			var some = tds.eq(tdIndex).children().val();
			var so = map.get(tdIndex);
			if(so == 'dname'){
				dname = some;
			}else if(so == 'dtype'){
				dtype = some;
			}else if(so == 'dbz'){
				dbz = some;
			}else if(so == 'dict'){
				dict = some;
			}else if(so == 'ddefault'){
				ddefault = some;
			}else if(so == 'flength'){
				flength = some;
			}else if(so == 'decimal'){
				decimal = some;
			}else if(so == 'table'){
				table = some;
			}
		}
		var isQian = (tds.eq(8).find("input").val());      //是否表单录入
		var isShow = (tds.eq(9).find("input").val());      //是否列表显示
		var isRequired = (tds.eq(10).find("input").val()); //是否必输
		var isQuery = (tds.eq(11).find("input").val());    //是否查询
		var isVague = (tds.eq(12).find("input").val());    //是否模糊查询
		var fields = dname + '|@|' + dtype + '|@|' + dbz + '|@|' +isQian + '|@|' + ddefault + '|@|' + flength + '|@|' + decimal + '|@|' + isRequired + '|@|' + dict + '|@|' + isShow + '|@|' + table + '|@|' + isQuery + '|@|' + isVague ;
		$("input[name='field"+ trIndex +"']").val(fields);
		//console.log("fields "+fields);
		arField[trIndex] = fields;
	}

	//删除数组添加元素 并重组列表
	function removeField(index){
		$("#fields").html('');
		arField.splice(index,1);
		for( var i = 0 ; i < arField.length ; i++ ){
			appendRow(arField[i]);
		}
	}

	//鼠标掠过事件
	function changeToInput(Obj,flag){
		var obj = $(Obj);
		var value = obj.html();
		var html = '<input type="text" class="inputStyle" style="font-weight:bold" onchange="getFields(this,'+'\'change\''+')" value="' + value + '">';
		if(flag == "select"){
			html = '<select  data-placeholder="请选择数据类型"  style="inputStyle" onchange="getFields(this,'+'\'change\''+')" value="' + value + '">\n' +
					'\t\t\t\t\t\t\t\t<option value="String" '+ (value == "String" ? "selected" : "") +'>String</option>\n' +
					'\t\t\t\t\t\t\t\t<option value="Double" ' + (value == "Double" ? "selected" : "") +'>Double</option>\n' +
					'\t\t\t\t\t\t\t\t<option value="Integer" ' + (value == "Integer" ? "selected" : "") +'>Integer</option>\n' +
					'\t\t\t\t\t\t\t\t<option value="Date" ' + (value == "Date" ? "selected" : "") +'>Date</option>\n' +
					'\t\t\t\t\t\t\t\t<option value="Dictionary" ' + (value == "Dictionary" ? "selected" : "") +'>Dictionary</option>\n' +
					'\t\t\t\t\t\t\t</select>';
		}else if(flag == "Dictionary"){
			if(obj.prev().html() != "Dictionary"){
				return;
			}
		}else if(flag == "Double"){
			if(obj.prev().prev().prev().html() != "Double"){
				return;
			}
		}
		obj.html(html);
	}
	//鼠标移开事件
	function changeToText(Obj){
		var obj = $(Obj);
		obj.html(obj.children().val());
	}


	//生成
	function toCode(){
		if($("#belongSystem").val()==""){
			$("#belongSystem").tips({
				side:3,
				msg:'输入所属子系统的名称',
				bg:'#AE81FF',
				time:2
			});
			$("#belongSystem").focus();
			return false;
		}
		if($("#TITLE").val()==""){
			$("#TITLE").tips({
				side:3,
				msg:'输入说明',
				bg:'#AE81FF',
				time:2
			});
			$("#TITLE").focus();
			return false;
		}
		if($("#packageName").val()==""){
			$("#packageName").tips({
				side:3,
				msg:'输入包名',
				bg:'#AE81FF',
				time:2
			});
			$("#packageName").focus();
			return false;
		}else{
			var pat = new RegExp("^[A-Za-z]+$");
			if(!pat.test($("#packageName").val())){
				$("#packageName").tips({
					side:3,
					msg:'只能输入字母',
					bg:'#AE81FF',
					time:2
				});
				$("#packageName").focus();
				return false;
			}
		}

		if($("#objectName").val()==""){
			$("#objectName").tips({
				side:3,
				msg:'输入类名',
				bg:'#AE81FF',
				time:2
			});
			$("#objectName").focus();
			return false;
		}else{
			var headstr = $("#objectName").val().substring(0,1);
			var pat = new RegExp("^[a-z0-9]+$");
			if(pat.test(headstr)){
				$("#objectName").tips({
					side:3,
					msg:'类名首字母必须为大写字母或下划线',
					bg:'#AE81FF',
					time:2
				});
				$("#objectName").focus();
				return false;
			}
		}

		if($("#fields").html() == ''){
			$("#table_report").tips({
				side:3,
				msg:'请添加属性',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		if($("#tabletop").val()==""){
			$("#tabletop").tips({
				side:3,
				msg:'选择或者输入表名',
				bg:'#AE81FF',
				time:2
			});
			$("#tabletop").focus();
			return false;
		}

		if(!confirm("确定要生成吗?")){
			return false;
		}
		var strArField = '';
		for(var i=0;i<arField.length;i++){
			strArField = strArField + arField[i] + "|@@|";
		}
		$("#FIELDLIST").val(strArField); 	//属性集合
		$("#Form").submit();				//提交
		//$("#objectName").val('');
		//$("#fields").html('');
		$("#productc").tips({
			side:3,
			msg:'提交成功,等待下载',
			bg:'#AE81FF',
			time:9
		});
		//window.jzts();
		//$("#zhongxin").hide();
		$("#zhongxin2").show();
		timer(9);
		setTimeout("$('#zhongxin2').hide()",10000);
		//setTimeout("top.Dialog.close()",10000);
	}

	//倒计时
	function timer(intDiff){
		window.setInterval(function(){
			$('#second_show').html('<s></s>'+intDiff+'秒');
			intDiff--;
		}, 1000);
	}

    trNum = null;
	function up(){
		//console.log("触发上移   " + trNum);
		if(trNum != null && trNum > 0){
			var middle = arField[trNum - 1];
			arField[trNum - 1] = arField[trNum];
			arField[trNum] = middle;

			var tag_u = $("#fields tr:nth-child(" + (trNum+1) + ")");
			tag_u.children().first().html(trNum);
			var tag_d = $("#fields tr:nth-child(" + trNum + ")");
			tag_d.children().first().html(trNum+1);
			tag_u.insertBefore(tag_d);

			var h1 = tag_u.children().last().html().replace("field" + trNum,"field" + (trNum-1)).replace("removeField("+ trNum +")","removeField("+ (trNum-1) +")")
			tag_u.children().last().html(h1);
			var h2 = tag_d.children().last().html().replace("field" + (trNum-1),"field" + trNum).replace("removeField("+ (trNum-1) +")","removeField("+ trNum +")")
			tag_d.children().last().html(h2);
			trNum--;
			//console.log("trNum :" + trNum);
			//nth-child(n) 选择器匹配属于其父元素的第 N 个子元素，不论元素的类型
		}
	}
	function below(){
		//console.log("触发下移    " + trNum);
		var X = $("#fields").children().length - 1;
		if(trNum != null && trNum < X){
			var middle = arField[trNum + 1];
			arField[trNum + 1] = arField[trNum];
			arField[trNum] = middle;
			var tag_u = $("#fields tr:nth-child(" + (trNum+2) + ")");
			tag_u.children().first().html(trNum+1);
			var tag_d = $("#fields tr:nth-child(" + (trNum+1) + ")");
			tag_d.children().first().html(trNum+2);
			tag_u.insertBefore(tag_d);

			var h1 = tag_u.children().last().html().replace("field" + (trNum+1),"field" + (trNum)).replace("removeField("+ (trNum+1) +")","removeField("+ (trNum) +")")
			tag_u.children().last().html(h1);
			var h2 = tag_d.children().last().html().replace("field" + (trNum),"field" + (trNum+1)).replace("removeField("+ (trNum) +")","removeField("+ (trNum+1) +")")
			tag_d.children().last().html(h2);

			trNum++;
			//console.log("trNum :" + trNum);
		}
	}

	//行 单击事件
	function changeTrOrder(Obj){
		trNum = $(Obj).closest("tr").index();
		var obj = $(Obj);
		$("#table_report tr").removeClass("trStyle");
		obj.addClass("trStyle");
	}

	//判断属性名是否重复  暂时没有实现校验
	function isSame(dname,table){
		for(var i=0;i<arField.length;i++){
			var array = arField[i].split('|@|');
			var array0 = array[0]; //字段名
			var array10 = array[10]; //表名
			if(array0 == dname && array10==table){
				return false;
			}
		}
		return true;
	}

	//选择类型
	function selectType(value){
		if("sontable" == value){//明细表
			$("#faobjectid_td").show();
			$("#faobjectid").removeAttr("disabled");
			$("#faobjectid").css("background","white");
		}else{
			$("#faobjectid_td").hide();
			$("#faobjectid").attr("disabled","disabled");
			$("#faobjectid").css("background","#F5F5F5");
			$("#faobjectid").val("");
			inpOpen();
		};
	}

	//选择主表
	function selectFa(CREATECODE_ID){
		if("" != CREATECODE_ID){
			inpClose();
			$.ajax({
				type: "POST",
				url: locat+'/createCode/findById',
				data: {CREATECODE_ID:CREATECODE_ID,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					$("#TITLE").val(data.pd.TITLE + '(明细)');
					$("#packageName").val(data.pd.PACKAGENAME);
					$("#objectName").val(data.pd.OBJECTNAME+"Mx");
					var tb = data.pd.TABLENAME.split("|@|");
					$("#tabletop").val(tb[0]);
					$("#faobject").val(data.pd.OBJECTNAME);
				}
			});
		}else{
			inpOpen();
		}
	}

	/***********************
	 * 函数：判断滚轮滚动方向
	 * 参数：event
	 * 返回：滚轮方向 1：向上 -1：向下
	 *************************/
	var scrollFunc = function (e) {
		// console.log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		/*console.log("网页可见区域宽："+ document.body.clientWidth);
		console.log("网页可见区域高："+ document.body.clientHeight );
		console.log("网页可见区域宽："+ document.body.offsetWidth + "(包括边线的宽)");
		console.log("网页可见区域高："+ document.body.offsetHeight + "(包括边线的高)");
		console.log("网页正文全文宽："+ document.body.scrollWidth);
		console.log("网页正文全文高："+ document.body.scrollHeight);
		console.log("网页被卷去的高："+ document.body.scrollTop);
		console.log("网页被卷去的左："+ document.body.scrollLeft);
		console.log("网页正文部分上："+ window.screenTop);
		console.log("网页正文部分左："+ window.screenLeft);
		console.log("屏幕分辨率的高："+ window.screen.height);
		console.log("屏幕分辨率的宽："+ window.screen.width);
		console.log("=============")
		console.log("屏幕可用工作区高度："+ window.screen.availHeight);
		console.log("屏幕可用工作区宽度："+ window.screen.availWidth);
		console.log("=============")
		console.log("jquery")
		console.log("=============")
		console.log("获取浏览器显示区域的高度 ："+ $(window).height());
		console.log("获取浏览器显示区域的宽度 ："+ $(window).width());
		console.log("获取页面的文档高度 ："+ $(document).height());
		console.log("获取页面的文档宽度 ："+ $(document).width());
		console.log("=============")*/
		// console.log("获取滚动条到顶部的垂直高度 ："+ $(document).scrollTop());
		/*console.log("获取滚动条到左边的垂直宽度 ："+ $(document).scrollLeft());*/
		e = e || window.event;
		var single = 12;
		var double = 24;
		if (e.wheelDelta) {//IE/Opera/Chrome
			if(e.wheelDelta > 0){
				if(e.wheelDelta <=120){
					UPE(single);
				}else{
					UPE(double);
				}
			}else{
				if(e.wheelDelta >= -120){
					DOE(single);
				}else{
					DOE(double);
				}
			}
		} else if (e.detail) {//Firefox
			if(e.detail > 0){
				if(e.detail <= 3){
					DOE(single);
				}else{
					DOE(double);
				}
			}else{
				if(e.detail >= -3){
					UPE(single);
				}else{
					UPE(double);
				}
			}
		}
	}


	/*注册事件*/
	if (document.addEventListener) {
		document.addEventListener('DOMMouseScroll', scrollFunc, false);
	}//W3C
	window.onmousewheel = document.onmousewheel = scrollFunc;//IE/Opera/Chrome/Safari

	//判断页面是否存在滚动条
	function hasScrollbar() {
		return document.body.scrollHeight > (window.innerHeight || document.documentElement.clientHeight);
	}

	function UPE(order){
		var upL = $("#fields").offset().top;
		var downL = (document.documentElement.clientHeight) - 25;  //619
		var top = $("#E").offset().top;
		// console.log("E的此刻高度为 "+ top);
		var toTop = top - order;
		if(toTop <= upL){
			toTop = upL;
		}else{
			if(toTop >= downL){
				toTop = downL - 135;
			}
		}
		// console.log("向上活动设置高度为 "+toTop);
		$("#E").css("top",toTop);
	}
	function DOE(order){
		var upL = $("#fields").offset().top;
		var downL = (document.documentElement.clientHeight) - 20;  //619
		var top = $("#E").offset().top;
		// console.log("E的此刻高度为 "+ top);
		var toTop = top + order;
		if(toTop >= downL){
			if(hasScrollbar()){
				//toTop = ($(document).scrollTop()) + downL;
				toTop = upL + order;
			}else{
				toTop = upL;
			}
		}
		// console.log("向下活动设置高度为 "+toTop);
		$("#E").css("top",toTop);

	}


	//添加关联关系
	function dialog_open(){
		var tabletop = $("#tabletop").val();
		if(tabletop != null && tabletop != "" && tabletop != ''){
			if( tabletop.toString().split(",").length > 1){
				$("#dialog-add").css("display","block");
				conL();
				if($("#fields").children().length == 0){
					selectTableRow();
				}
			}
		}else{
			$("#tabletop").tips({
				side:3,
				msg:'请先选择所涉及的表!',
				bg:'#AE81FF',
				time:2
			});
		}
	}

	function addL(){
		if($("#taL").children().length <= 9){
			conL();
		}
	}
	function conL(){
		var tabletop = $("#tabletop").val();
		var tables = tabletop.toString().split(",");
		var optionL = "";
		$.each(tables,function(i,n){
			optionL += "\t\t\t\t\t\t\t\t\t\t\t<option value=\""+n+"\">"+n+"</option>\n";
		});
		$.each(arField,function(i,n){
			var fieldarray = n.split('|@|');
			if(tabletop.indexOf(fieldarray[10]) == -1 ){
				optionL += "\t\t\t\t\t\t\t\t\t\t\t<option value=\""+ fieldarray[10] +"\">" + fieldarray[10] + "</option>\n";
			}
		});
		var htmL = "<tr>\n" +
				"\t\t\t\t\t\t\t\t\t<td style=\"text-align: center;\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<select data-placeholder=\"请选择目标表\" name=\"nnnnnn\" style=\"width:90%;\" onchange=\"findTableRow(this)\">\n" +
				"\t\t\t\t\t\t\t\t\t\t\t<option value=\"\">-----------------请选择-----------------</option>\n" + optionL +
				"\t\t\t\t\t\t\t\t\t\t</select>\n" +
				"\t\t\t\t\t\t\t\t\t</td>\n" +
				"\t\t\t\t\t\t\t\t\t<td style=\"text-align: center\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<select data-placeholder=\"请选择目标字段\" style=\"width:90%;\">\n" +
				"\t\t\t\t\t\t\t\t\t\t\t<option value=\"\">-----------------请选择-----------------</option>\n" +
				"\t\t\t\t\t\t\t\t\t\t</select>\n" +
				"\t\t\t\t\t\t\t\t\t</td>\n" +
				"\t\t\t\t\t\t\t\t\t<td style=\"text-align: center;padding-top:8px;font-size: 25px;\">=</td>\n" +
				"\t\t\t\t\t\t\t\t\t<td style=\"text-align: center\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<select data-placeholder=\"请选择目标表\" style=\"width:90%;\" onchange=\"findTableRow(this)\">\n" +
				"\t\t\t\t\t\t\t\t\t\t\t<option value=\"\">-----------------请选择-----------------</option>\n" + optionL +
				"\t\t\t\t\t\t\t\t\t\t</select>\n" +
				"\t\t\t\t\t\t\t\t\t</td>\n" +
				"\t\t\t\t\t\t\t\t\t<td style=\"text-align: center\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<select data-placeholder=\"请选择目标字段\" style=\"width:90%;\">\n" +
				"\t\t\t\t\t\t\t\t\t\t\t<option value=\"\">-----------------请选择-----------------</option>\n" +
				"\t\t\t\t\t\t\t\t\t\t</select>\n" +
				"\t\t\t\t\t\t\t\t\t</td>\n" +
				"\t\t\t\t\t\t\t\t\t<td style=\"text-align: center;\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<a class=\"btn btn-white btn-sm btn-primary\" title=\"移除关联\" onclick=\"delL(this);\" style=\"border-width:1px;width: 20px;height:28px;padding: 4px 2px 4px 2px;margin-left: 1px;\">\n" +
				"\t\t\t\t\t\t\t\t\t\t\t<i class=\"ace-icon glyphicon glyphicon-minus\" title=\"移除关联\"></i>\n" +
				"\t\t\t\t\t\t\t\t\t\t</a>\n" +
				"\t\t\t\t\t\t\t\t\t</td>\n" +
				"\t\t\t\t\t\t\t\t</tr>"
		$("#taL").append(htmL);
	}

	function delL(Obj){
		$(Obj).closest("tr").remove();
	}

	function findTableRow(Obj){
		var obj = $(Obj);
		var val = obj.val();
		var optionL = "";
		if(val != null && val != ''){
			$.each(arField,function(i,n){
				var fieldarray = n.split('|@|');
				if(val == fieldarray[10]){
					optionL += "\t\t\t\t\t\t\t\t\t\t\t<option value=\""+ fieldarray[0] +"\">" + fieldarray[0] + "</option>\n";
				}
			});
		}else{
			optionL = "\t\t\t\t\t\t\t\t\t\t\t<option value=\"\">-----------------请选择-----------------</option>\n";
		}
		obj.parent().next().children().html(optionL);

	}

	function saveL(){
		var whereis = "";
		var taL = $("#taL").children();
		for (var i = 1; i < taL.length; i++) {
			whereis += taL.eq(i).children().eq(0).children().val() + "." + taL.eq(i).children().eq(1).children().val() + taL.eq(i).children().eq(2).html() +
					taL.eq(i).children().eq(3).children().val() +"." + taL.eq(i).children().eq(4).children().val();
			if(i !=taL.length -1){
				whereis += ";";
			}
		}
		$("#whereis").val(whereis);
		cancel_pl();
	}

	//编辑关联关系(修改)
	function editField(value,msgIndex){
		$("#dialog-add").css("display","block");

	}

	//关闭编辑属性
	function cancel_pl(){
		$("#dialog-add").css("display","none");
		$("#trL").nextAll().remove();
	}


</script>

</body>
</html>
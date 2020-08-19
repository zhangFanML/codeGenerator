/**
 * 
 */	
var locat = (window.location+'').split('/'); 
$(function(){if('createCode'== locat[3]){locat =  locat[0]+'//'+locat[2];}else{locat =  locat[0]+'//'+locat[2]+'/'+locat[3];};});

	//生成
	function save(){
		
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
		$("#objectName").val('');
		$("#fields").html('');
		$("#productc").tips({
			side:3,
            msg:'提交成功,等待下载',
            bg:'#AE81FF',
            time:9
        });
		window.parent.jzts();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
		timer(9);
		setTimeout("top.Dialog.close()",10000);
	}
	
	//倒计时
	function timer(intDiff){
		window.setInterval(function(){
		$('#second_show').html('<s></s>'+intDiff+'秒');
		intDiff--;
		}, 1000);
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
				url: locat+'/createCode/findById.do',
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
	
	//input启用
	function inpOpen(){
		$("#TITLE").attr("readonly",false);
		$("#packageName").attr("readonly",false);
		$("#objectName").attr("readonly",false);
		$("#tabletop").attr("readonly",false);
	}
	
	//input禁用
	function inpClose(){
		$("#TITLE").attr("readonly",true);
		$("#packageName").attr("readonly",true); 
		$("#objectName").attr("readonly",true); 
		$("#tabletop").attr("readonly",true); 
	}
	
	//保存编辑属性
	function saveD(){
		
		var dname = $("#dname").val(); 	 		 //属性名
		var dtype = $("#dtype").val(); 	 		 //类型
		var dbz	  = $("#dbz").val();   	 		 //中文说明
		var isQian = $("#isQian").val(); 		 //是否表单录入
		var isShow = $("#isShow").val(); 		 //是否列表显示
		var isRequired = $("#isRequired").val(); //是否必输
		var dict = $("#dict").val(); 		     //字典编码
		var ddefault = $("#ddefault").val(); 	 //默认值
		var msgIndex = $("#msgIndex").val(); 	 //msgIndex不为空时是修改
		var flength = $("#flength").val(); 	 	 //长度
		var decimal = $("#decimal").val(); 	 	 //小数
		var table = $("#table").val(); 	 	     //所属表名

		if(dname==""){
			$("#dname").tips({
				side:3,
	            msg:'输入属性名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#dname").focus();
			return false;
		}else{
			dname = dname.toUpperCase();		//转化为大写
			if(isSame(dname,table)){
				var headstr = dname.substring(0,1);
				var pat = new RegExp("^[0-9]+$");
				if(pat.test(headstr)){
					$("#dname").tips({
						side:3,
			            msg:'属性名首字母必须为字母或下划线',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#dname").focus();
					return false;
				}
			}else{
				if(msgIndex != ''){
					var hcdname = $("#hcdname").val();
					if(hcdname != dname){
						if(!isSame(dname,table)){
							$("#dname").tips({
								side:3,
					            msg:'属性名重复',
					            bg:'#AE81FF',
					            time:2
					        });
							$("#dname").focus();
							return false;
						};
					};
				}else{
					$("#dname").tips({
						side:3,
			            msg:'属性名重复',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#dname").focus();
					return false;
				}
			}
		}
		
		if(dbz==""){
			$("#dbz").tips({
				side:3,
	            msg:'输入备注',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#dbz").focus();
			return false;
		}

		if(table==""){
			$("#table").tips({
				side:3,
	            msg:'输入所属表名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#table").focus();
			return false;
		}

		if((0-flength >=0) || flength==""){
			$("#flength").tips({
				side:3,
	            msg:'输入长度',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#flength").focus();
			return false;
		}
		
		if('' == decimal) decimal = 0;
		
		dbz = dbz == '' ? '无':dbz;
		ddefault = ddefault == '' ? '无':ddefault;
		dict = dict == '' ? '无' : dict;
		isRequired = isRequired == '' ? '是' : isRequired;
		var fields = dname + '|@|' + dtype + '|@|' + dbz + '|@|' +isQian + '|@|' + ddefault + '|@|' + flength + '|@|' + decimal + '|@|' + isRequired + '|@|' + dict + '|@|' + isShow + '|@|' + table ;
		
		if(msgIndex == ''){  //新增保存
			arrayField(fields);
		}else{    //编辑修改保存
			editArrayField(fields,msgIndex);
		}
		$("#dialog-add").css("display","none");
	}
	
	//打开编辑属性(新增)
	function dialog_open(){
		$("#dialog-add").css("display","block");
		$("#dname").val('');
		$("#dbz").val('');
		$("#ddefault").val('');
		$("#msgIndex").val('');
		$("#dtype").val('String');
		$("#isQian").val('是');
		$("#isRequired").val('是');
		$("#form-field-radio1").attr("checked",true);  //属性类型 设置默认值
		$("#form-field-radio1").click();
		$("#form-field-radio4").attr("checked",true);  //前台录入 设置默认值 为是
		$("#form-field-radio4").click();
		$("#form-field-radio6").attr("checked",true);  //是否必输 设置默认值 为是
		$("#form-field-radio6").click();
		$("#form-field-radio8").attr("checked",true);  //列表显示 设置默认值 为是
		$("#form-field-radio8").click();
		$("#flength").val(255);    //长度 默认为255
		$("#ddefault").attr("disabled",true);  //默认值设置为不可输入
		$("#dict").val("");  //字典编码 置为空
		$("#dict").attr("disabled",true);
	}
	
	//打开编辑属性(修改)
	function editField(value,msgIndex){
		$("#dialog-add").css("display","block");
		var efieldarray = value.split('|@|');
		//(efieldarray);
		$("#dname").val(efieldarray[0]);		//中文说明
		$("#hcdname").val(efieldarray[0]);		//属性名 备份一份
		$("#dbz").val(efieldarray[2]);			//中文名称
		$("#ddefault").val(efieldarray[4]);		//默认值
		$("#msgIndex").val(msgIndex);			//数组ID
		$("#table").val(efieldarray[10]);			//表名
		if(efieldarray[1] == 'String'){			//类型
			$("#form-field-radio1").attr("checked",true);
			$("#form-field-radio1").click();
			$("#dtype").val('String');
			$("#dict").attr("disabled",true);
		}else if(efieldarray[1] == 'Integer'){
			$("#form-field-radio2").attr("checked",true);
			$("#form-field-radio2").click();
			$("#dtype").val('Integer');
			$("#dict").attr("disabled",true);
		}else if(efieldarray[1] == 'Double'){
			$("#form-field-radio33").attr("checked",true);
			$("#form-field-radio33").click();
			$("#dtype").val('Double');
			$("#dict").attr("disabled",true);
		}else if(efieldarray[1] == 'Dictionary'){
			$("#form-field-radio22").attr("checked",true);
			$("#form-field-radio22").click();
			$("#dtype").val('Dictionary');
			$("#dict").attr("disabled",false);
			$("#dict").val(efieldarray[8]);
		}else{
			$("#form-field-radio3").attr("checked",true);
			$("#form-field-radio3").click();
			$("#dtype").val('Date');
		}
		if(efieldarray[3] == '是'){
			$("#form-field-radio4").attr("checked",true);
			$("#form-field-radio4").click();
			$("#isQian").val('是');
		}else{
			$("#form-field-radio5").attr("checked",true);
			$("#form-field-radio5").click();
			$("#isQian").val('否');
		}
		if(efieldarray[9] == '是'){
			$("#form-field-radio8").attr("checked",true);
			$("#form-field-radio8").click();
			$("#isShow").val('是');
		}else{
			$("#form-field-radio9").attr("checked",true);
			$("#form-field-radio9").click();
			$("#isShow").val('否');
		}
		//if(efieldarray[3] == '是'){
		//	$("#form-field-radio6").attr("checked",true);
		//	$("#form-field-radio6").click();
		//	$("#isRequired").val('是');
		//}else{
		//	$("#form-field-radio7").attr("checked",true);
		//	$("#form-field-radio7").click();
		//	$("#isRequired").val('否');
		//}
		$("#flength").val(efieldarray[5]);	//长度
		$("#decimal").val(efieldarray[6]);	//小数点
	}
	
	//关闭编辑属性
	function cancel_pl(){
		$("#dialog-add").css("display","none");
	}
	
	//赋值类型
	function setType(value){
		$("#dtype").val(value);
		$("#decimal").val('');
		$("#decimal").attr("disabled",true);
		$("#dict").attr("disabled",true);
		 if(value == 'Integer'){
			if(Number($("#flength").val())-0>11){
				$("#flength").val(11);
			}
		}else if(value == 'Date'){
			$("#flength").val(32);
		}else if(value == 'Double'){
			if(Number($("#flength").val())-0>11){
				$("#flength").val(11);
			}
			$("#decimal").val(2);
			$("#decimal").attr("disabled",false);
		}else if(value == 'Dictionary'){
			 $("#dict").val('');
			 $("#dict").attr("disabled",false);
		 }else{
			$("#flength").val(255);
		}
	}
	
	//赋值是否前台录入
	function isQian(value){
		if(value == '是'){
			$("#isQian").val('是');
			$("#ddefault").val("无");
			$("#ddefault").attr("disabled",true);
		}else{
			$("#isQian").val('否');
			$("#ddefault").val('');
			$("#ddefault").attr("disabled",false);
		}
	}

	//赋值是否必输
	function isRequired(value){
		if(value == '是'){
			$("#isRequired").val('是');
		}else{
			$("#isRequired").val('否');
		}
	}
	//赋值是否列表显示
	function isShow(value){
		if(value == '是'){
			$("#isShow").val('是');
		}else{
			$("#isShow").val('否');
		}
	}

	var arField = new Array();
	var index = 0;
	//追加属性列表
	function appendC(value){
		var fieldarray = value.split('|@|');
		$("#fields").append(
            '<tr>'+
            '<td class="center">'+Number(index+1)+'</td>'+
            '<td class="center">'+fieldarray[10]+'<input type="hidden" name="fieldx10'+index+'" value="'+fieldarray[10]+'"></td>'+	//表名
            '<td class="center">'+fieldarray[0]+'<input type="hidden" name="fieldx0'+index+'" value="'+fieldarray[0]+'"></td>'+	//属性名称
            '<td class="center">'+fieldarray[2]+'<input type="hidden" name="fieldx2'+index+'" value="'+fieldarray[2]+'"></td>'+	//中文说明
            '<td class="center">'+fieldarray[1]+'<input type="hidden" name="fieldx1'+index+'" value="'+fieldarray[1]+'"></td>'+	//字段类型
            '<td class="center">'+fieldarray[8]+'<input type="hidden" name="fieldx8'+index+'" value="'+fieldarray[8]+'"></td>'+	//字段码值
            '<td class="center">'+fieldarray[5]+'<input type="hidden" name="fieldx5'+index+'" value="'+fieldarray[5]+'"></td>'+	//长度
            '<td class="center">'+fieldarray[6]+'<input type="hidden" name="fieldx6'+index+'" value="'+fieldarray[6]+'"></td>'+	//小数点
            '<td>' +
            '<label class="pull-right inline">' +
            '<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
            '<input id="field3'+index+'" name="fieldx3'+index+'" type="checkbox" '+ (fieldarray[3] == '是'? 'checked value="是"':'value="否"') + ' onclick="updateFilesO(this,index)" class="ace ace-switch ace-switch-4">' +
            '<span class="lbl middle"></span>' +
            '</label>' +
            '</td>' +
            /*'<td class="center">'+fieldarray[3]+'<input type="hidden" name="field3'+index+'" value="'+fieldarray[3]+'"></td>'+	//表单录入*/
            '<td>' +
            '<label class="pull-right inline">' +
            '<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
            '<input  id="field9'+index+'" name="fieldx9'+index+'" type="checkbox" '+ (fieldarray[9] == '是'? 'checked value="是"':'value="否"') + 'onclick="updateFilesO(this,index)" class="ace ace-switch ace-switch-4">' +
            '<span class="lbl middle"></span>' +
            '</label>' +
            '</td>' +
            /*'<td class="center">'+fieldarray[9]+'<input type="hidden" name="field8'+index+'" value="'+fieldarray[9]+'"></td>'+	//列表显示*/
            '<td>' +
            '<label class="pull-right inline">' +
            '<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
            '<input id="field7'+index+'" name="fieldx7'+index+'" type="checkbox" '+ (fieldarray[7] == '是'? 'checked value="是"':'value="否"') + 'onclick="updateFilesO(this,index)" class="ace ace-switch ace-switch-4">' +
            '<span class="lbl middle"></span>' +
            '</label>' +
            '</td>' +
            /*'<td class="center">'+fieldarray[7]+'<input type="hidden" name="field3'+index+'" value="'+fieldarray[7]+'"></td>'+	//是否必输*/
            '<td class="center">'+fieldarray[4]+'<input type="hidden" name="fieldx4'+index+'" value="'+fieldarray[4]+'"></td>'+	//默认值
            '<td class="center" style="width:100px;">'+
            '<input type="hidden" name="field'+ index +'" value="'+ value +'">'+
            '<a class="btn btn-mini btn-info" title="编辑" onclick="editField(\''+value+'\',\''+index+'\')"><i class="ace-icon fa fa-pencil-square-o bigger-120"></i></a>&nbsp;'+
            '<a class="btn btn-mini btn-danger" title="删除" onclick="removeField(\''+ index +'\')"><i class="ace-icon fa fa-trash-o bigger-120"></i></a> '+
            '</td>'+
            '</tr>'
		);
		index++;
		$("#zindex").val(index);
	}
	
	//保存属性后往数组添加元素
	function arrayField(value){
		arField[index] = value;
		appendC(value);
	}
	
	//修改属性
	function editArrayField(value,msgIndex){
		arField[msgIndex] = value;
		index = 0;
		$("#fields").html('');
		for(var i=0;i<arField.length;i++){
			appendC(arField[i]);
		}
	}
	
	//删除数组添加元素并重组列表
	function removeField(value){
		index = 0;
		$("#fields").html('');
		arField.splice(value,1);
		for(var i=0;i<arField.length;i++){
			appendC(arField[i]);
		}
	}
	
	//判断属性名是否重复
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
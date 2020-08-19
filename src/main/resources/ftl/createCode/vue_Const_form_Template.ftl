export const formOption =
{
	size: 'mini',
	mock: true,
	submitText: '确定',
	column:
	[
<#list fieldList as var>
	<#if var[3] == "是">
		{
			label: '${var[2]}',
			prop: '${var[0]}',
		<#if var[1] == "Text">
			type: 'text',
		<#elseif var[1] == "Number">
			type: 'number',
		<#elseif var[1] == "Dictionary">
			type: 'select',
			dicUrl: 'http://localhost:9527/#/testForm',
			dicData: '${var[8]}',
		<#elseif var[1] == "Tree">
			type: 'tree',
			dicUrl: 'http://localhost:9527/#/testForm',
			dicData: '${var[8]}',
		<#elseif var[1] == "Phone">
			type: 'phone',
		<#elseif var[1] == "Textarea">
			type: 'textarea',
		<#elseif var[1] == "Time">
			type: 'time',
			format: 'hh:mm:ss',
			valueFormat: 'hh:mm:ss',
		<#elseif var[1] == "Date">
			type: 'date',
			format: 'yyyy-MM-dd',
			valueFormat: 'yyyy-MM-dd',
		<#elseif var[1] == "Datetime">
			type: 'datetime',
			format: 'yyyy-MM-dd hh:mm:ss',
			valueFormat: 'yyyy-MM-dd hh:mm:ss',
		<#elseif var[1] == "Radio">
			type: 'radio',
		<#elseif var[1] == "Checkbox">
			type: 'checkbox',
		<#elseif var[1] == "Switch">
			type: 'switch',
		</#if>
			tip: '这是信息提示',
			span: 12,
			maxlength: ${var[5]},
			minlength: 0,
		<#if var[7] == "是">
			rules: [{
				required: true,
				message: '请输入${var[2]}',
				trigger: 'blur'
			}]
		<#else>
			rules: [{
				required: false,
				message: '请输入${var[2]}',
				trigger: 'blur'
			}]
		</#if>
		}<#if var?has_next>,</#if>
	</#if>

</#list>
	]
}

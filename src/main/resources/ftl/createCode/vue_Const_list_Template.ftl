export const tableOption =
{
	border: true,
	stripe: true,
	menuAlign: 'center',
	align: 'center',
	editBtn: true,
	delBtn: false,
	addBtn: true,
	disabled: true,
	viewBtn: true,
	selection: true,
	size: 'mini',
	column:
	[
<#list fieldList as var>
		{
			label: '${var[2]}',
			prop: '${var[0]}',
		<#if var[1] == "Text">
			type: 'text',
		<#elseif var[1] == "Number">
			type: 'number',
		<#elseif var[1] == "Dictionary">
			type: 'select',
			dicUrl: 'http://localhost:9527/#/testForm/index',
			editDisplay: false,
		<#elseif var[1] == "Tree">
			type: 'tree',
			dicUrl: 'http://localhost:9527/#/testForm/index',
			editDisplay: false,
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
	<#if var[9] == "是"><#--是否列表显示9-->
			hide: false,
	<#else>
			hide: true,
	</#if>
		<#if var[11] == "是"><#--是否查询11-->
			search: true
		<#else>
			search: false
		</#if>
		}<#if var?has_next>,</#if>

</#list>
	]
}

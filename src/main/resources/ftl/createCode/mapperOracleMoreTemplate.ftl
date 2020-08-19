<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE ${"mapper"} PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${objectName}Mapper">
	<#function dashedToCamel(s)>
		<#return s
		?lower_case
		?replace('\\_+(\\w)?', ' $1', 'r')
		?capitalize
		?replace('(^_+)|(_+$)', '', 'r')
		?replace('(\\_+)', '', 'r')
		?replace(' ' , '')
		?uncap_first
		>
	</#function>

	<!--表名 -->
	<sql id="tableName">
		${tabletop}
	</sql>

	<!-- 字段 -->
	<sql id="Field">
	<#list fieldList as var>
		<#if var?has_next>
		${var[10]}.${var[0]} "${var[10]}.${var[0]}",
		<#else>
		${var[10]}.${var[0]} "${var[10]}.${var[0]}"
		</#if>
	</#list>
	</sql>
	

	<!-- 列表 -->
	<select id="datalistPage" parameterType="page" resultType="pd">
		select
		<include refid="Field"></include>
		from 
		<include refid="tableName"></include>
		where 1=1
		<#list whereis as var>
		and ${var}
		</#list>
<#list fieldList as var>
	<#if var[11] == "是">
		<#if var[12] == "是">
		<if test="pd.${dashedToCamel(var[0])}!= null and pd.${dashedToCamel(var[0])} != ''">
		and ${var[10]}.${var[0]} like CONCAT(CONCAT('%',${r"#{"}pd.${dashedToCamel(var[0])}${r"}"}),'%')
		</if>
		<#else>
		<if test="pd.${dashedToCamel(var[0])}!= null and pd.${dashedToCamel(var[0])} != ''">
		and ${var[10]}.${var[0]} = ${r"#{"}pd.${dashedToCamel(var[0])}${r"}"}
		</if>
		</#if>
	</#if>
</#list>
	</select>
	
	<!-- 列表(全部) -->
	<select id="listAll" parameterType="pd" resultType="pd">
		select
		<include refid="Field"></include>
		from 
		<include refid="tableName"></include>
		where 1=1
		<#list whereis as var>
		and ${var}
		</#list>
<#list fieldList as var>
	<#if var[11] == "是">
		<#if var[12] == "是">
		<if test="${dashedToCamel(var[0])}!= null and ${dashedToCamel(var[0])} != ''">
		and ${var[10]}.${var[0]} like CONCAT(CONCAT('%',${r"#{"}${dashedToCamel(var[0])}${r"}"}),'%')
		</if>
		<#else>
		<if test="${dashedToCamel(var[0])}!= null and ${dashedToCamel(var[0])} != ''">
		and ${var[10]}.${var[0]} = ${r"#{"}${dashedToCamel(var[0])}${r"}"}
		</if>
		</#if>
	</#if>
</#list>
	</select>
	
</mapper>
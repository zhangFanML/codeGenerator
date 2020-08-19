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
		${var[0]},
		<#else>
		${var[0]}
		</#if>
	</#list>
	</sql>
	
	<!-- 字段值 -->
	<sql id="FieldValue">
	<#list fieldList as var>
		<#if var?has_next>
		${r"#{"}${dashedToCamel(var[0])}${r"}"},
		<#else>
		${r"#{"}${dashedToCamel(var[0])}${r"}"}
		</#if>
	</#list>
	</sql>

	<!-- 批量保存字段值 -->
	<sql id="BatchFieldValue">
	<#list fieldList as var>
		<#if var?has_next>
		${r"#{data."}${dashedToCamel(var[0])}${r"}"},
		<#else>
		${r"#{data."}${dashedToCamel(var[0])}${r"}"}
		</#if>
	</#list>
	</sql>

	<!-- 新增-->
	<insert id="save" parameterType="pd">
		insert into
		<include refid="tableName"></include>
		(
		<include refid="Field"></include>
		) values (
		<include refid="FieldValue"></include>
		)
	</insert>

	<!-- 批量保存-->
	<insert id="batchSave" parameterType="java.util.List" useGeneratedKeys="false">
		insert all
		<foreach collection="list" item="data">
			into <include refid="tableName"></include>
			( <include refid="Field"></include> )
			values
			( <include refid="BatchFieldValue"></include> )
		</foreach>
		SELECT 1 FROM DUAL
	</insert>


	<!-- 删除-->
	<delete id="delete" parameterType="String">
		delete from
		<include refid="tableName"></include>
		where
		<#if PKColumns?exists && PKColumns?size gte 1>
			${PKColumns[0]} = ${r"#{"}${dashedToCamel(PKColumns[0])}${r"}"}
		<#else>
			UUID = ${r"#{"}uuid${r"}"}
		</#if>
	</delete>

	<!-- 修改 -->
	<update id="editPatch" parameterType="pd">
		update
		<include refid="tableName"></include>
		<set>
	<#list fieldList as var>
		<#if var?has_next>
			<#if dashedToCamel(var[0]) == "truncNo">
			<if test="${dashedToCamel(var[0])}!= null and ${dashedToCamel(var[0])} != ''">
				${var[0]} = ${r"#{"}${dashedToCamel(var[0])}${r"}"} + 1,
			</if>
			<#elseif dashedToCamel(var[0]) == "uuid">
			<#else>
			<if test="${dashedToCamel(var[0])}!= null and ${dashedToCamel(var[0])} != ''">
				${var[0]} = ${r"#{"}${dashedToCamel(var[0])}${r"}"},
			</if>
			</#if>
		<#else>
			<#if var[0] == "truncNo">
			<if test="${dashedToCamel(var[0])}!= null and ${dashedToCamel(var[0])} != ''">
				${var[0]} = ${r"#{"}${dashedToCamel(var[0])}${r"}"} + 1
			</if>
			<#elseif dashedToCamel(var[0]) == "uuid">
			<#else>
			<if test="${dashedToCamel(var[0])}!= null and ${dashedToCamel(var[0])} != ''">
				${var[0]} = ${r"#{"}${dashedToCamel(var[0])}${r"}"}
			</if>
			</#if>
		</#if>
	</#list>
		</set>
		where
		<#if PKColumns?exists && PKColumns?size gte 1>
			${PKColumns[0]} = ${r"#{"}${dashedToCamel(PKColumns[0])}${r"}"}
		<#else>
			UUID = ${r"#{"}uuid${r"}"}
		</#if>
		and TRUNC_NO = ${r"#{"}truncNo${r"}"}
	</update>

	<!-- 批量修改-->
	<update id="batchUpdate" parameterType="java.util.List">
		<foreach collection="list" item="item" index="index" open="begin" close=";end;" separator=";">
			UPDATE
			<include refid="tableName"></include>
			<set>
	<#list fieldList as var>
		<#if var?has_next>
			<#if dashedToCamel(var[0]) == "truncNo">
				<if test="item.${dashedToCamel(var[0])}!= null and item.${dashedToCamel(var[0])} != ''">
					${var[0]} = ${r"#{item."}${dashedToCamel(var[0])}${r"}"} + 1,
				</if>
			<#elseif dashedToCamel(var[0]) == "uuid">
			<#else>
				<if test="item.${dashedToCamel(var[0])}!= null and item.${dashedToCamel(var[0])} != ''">
					${var[0]} = ${r"#{item."}${dashedToCamel(var[0])}${r"}"},
				</if>
			</#if>
		<#else>
			<#if var[0] == "truncNo">
				<if test="item.${dashedToCamel(var[0])}!= null and item.${dashedToCamel(var[0])} != ''">
					${var[0]} = ${r"#{item."}${dashedToCamel(var[0])}${r"}"} + 1
				</if>
			<#elseif dashedToCamel(var[0]) == "uuid">
			<#else>
				<if test="item.${dashedToCamel(var[0])}!= null and item.${dashedToCamel(var[0])} != ''">
					${var[0]} = ${r"#{item."}${dashedToCamel(var[0])}${r"}"}
				</if>
			</#if>
		</#if>
	</#list>
			</set>
			where
			<#if PKColumns?exists && PKColumns?size gte 1>
				${PKColumns[0]} = ${r"#{item."}${dashedToCamel(PKColumns[0])}${r"}"}
			<#else>
				UUID = ${r"#{item."}uuid${r"}"}
			</#if>
		</foreach>
	</update>

	<!-- 通过ID获取数据 -->
	<select id="findById" parameterType="String" resultType="pd">
		select
		<include refid="Field"></include>
		from 
		<include refid="tableName"></include>
		where
		<#if PKColumns?exists &&  PKColumns?size gte 1>
			${PKColumns[0]} = ${r"#{"}${dashedToCamel(PKColumns[0])}${r"}"}
		<#else>
			UUID = ${r"#{"}uuid${r"}"}
		</#if>
	</select>
	
	<!-- 列表 -->
	<select id="datalistPage" parameterType="page" resultType="pd">
		select
		<include refid="Field"></include>
		from 
		<include refid="tableName"></include>
		where 1=1
<#list fieldList as var>
	<#if var[11] == "是">
		<#if var[12] == "是">
		<if test="pd.${dashedToCamel(var[0])}!= null and pd.${dashedToCamel(var[0])} != ''">
			and ${var[0]} like CONCAT(CONCAT('%',${r"#{"}pd.${dashedToCamel(var[0])}${r"}"}),'%')
		</if>
		<#else>
		<if test="pd.${dashedToCamel(var[0])}!= null and pd.${dashedToCamel(var[0])} != ''">
			and ${var[0]} = ${r"#{"}pd.${dashedToCamel(var[0])}${r"}"}
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
<#list fieldList as var>
	<#if var[11] == "是">
		<#if var[12] == "是">
		<if test="${dashedToCamel(var[0])}!= null and ${dashedToCamel(var[0])} != ''">
			and ${var[0]} like CONCAT(CONCAT('%',${r"#{"}${dashedToCamel(var[0])}${r"}"}),'%')
		</if>
		<#else>
		<if test="${dashedToCamel(var[0])}!= null and ${dashedToCamel(var[0])} != ''">
			and ${var[0]} = ${r"#{"}${dashedToCamel(var[0])}${r"}"}
		</if>
		</#if>
	</#if>
</#list>
	</select>
	
	<!-- 批量删除 -->
	<delete id="deleteAll" parameterType="String">
		delete from
		<include refid="tableName"></include>
		where
		<#if PKColumns?exists && PKColumns?size gte 1>
			${PKColumns[0]} in
		<#else>
			UUID in
		</#if>
		<foreach item="item" index="index" collection="array" open="(" separator="," close=")">
                 ${r"#{item}"}
		</foreach>
	</delete>
	
</mapper>
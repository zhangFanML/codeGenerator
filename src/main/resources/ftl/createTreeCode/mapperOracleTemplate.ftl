<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${objectName}Mapper">
	
	<resultMap type="${objectName}" id="${objectNameLower}ResultMap">
		<id column="UUID" property="UUID"/>
		<result column="NAME" property="NAME"/>
		<result column="PARENT_ID" property="PARENT_ID"/>
	</resultMap>
	
	<!--表名 -->
	<sql id="tableName">
		"${tabletop}"
	</sql>
	
	<!-- 字段 -->
	<sql id="Field">
	<#list fieldList as var>
		"${var[0]}",	
	</#list>
		"UUID",
		"PARENT_ID",
		"NAME"
	</sql>
	
	<!-- 字段值 -->
	<sql id="FieldValue">
	<#list fieldList as var>
			${r"#{"}${var[0]}${r"}"},	
	</#list>
			${r"#{"}UUID${r"}"},
			${r"#{"}PARENT_ID${r"}"},
			${r"#{"}NAME${r"}"}
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
	
	<!-- 删除-->
	<delete id="delete" parameterType="pd">
		delete from
		<include refid="tableName"></include>
		where 
			"UUID" = ${r"#{"}UUID${r"}"}
	</delete>
	
	<!-- 修改 -->
	<update id="edit" parameterType="pd">
		update
		<include refid="tableName"></include>
			set 
	<#list fieldList as var>
			<#if var[3] == "是">
				"${var[0]}" = ${r"#{"}${var[0]}${r"}"},	
			</#if>
	</#list>
			"NAME" = ${r"#{"}NAME${r"}"},
			"UUID" = "UUID"
			where 
				"UUID" = ${r"#{"}UUID${r"}"}
	</update>
	
	<!-- 通过ID获取数据 -->
	<select id="findById" parameterType="pd" resultType="pd">
		select 
		<include refid="Field"></include>
		from 
		<include refid="tableName"></include>
		where 
			"UUID" = ${r"#{"}UUID${r"}"}
	</select>
	
	<!-- 列表 -->
	<select id="datalistPage" parameterType="page" resultType="pd">
		select
		<include refid="Field"></include>
		from 
		<include refid="tableName"></include>
		where 1=1
		<if test="pd.UUID!= null and pd.UUID != ''"><!-- 检索 -->
		and "PARENT_ID" = ${r"#{"}pd.UUID${r"}"}
		</if>
		<if test="pd.keywords!= null and pd.keywords != ''"><!-- 关键词检索 -->
			and
				(
				<!--	根据需求自己加检索条件
					字段1 LIKE CONCAT(CONCAT('%', ${r"#{pd.keywords})"},'%')
					 or 
					字段2 LIKE CONCAT(CONCAT('%', ${r"#{pd.keywords})"},'%') 
				-->
				)
		</if>
	</select>
	
	<!-- 通过ID获取其子级列表 -->
	<select id="listByParentId" parameterType="String" resultMap="${objectNameLower}ResultMap">
		select 
		<include refid="Field"></include>
		from 
		<include refid="tableName"></include>
		where 
			"PARENT_ID" = ${r"#{parentId}"}  order by "NAME" 
	</select>
	
	<!-- 列表(全部) -->
	<select id="listAll" parameterType="pd" resultType="pd">
		select
		<include refid="Field"></include>
		from 
		<include refid="tableName"></include>
	</select>
	
	
	<!-- fh 3 1359679 0 qq(青苔) -->
</mapper>
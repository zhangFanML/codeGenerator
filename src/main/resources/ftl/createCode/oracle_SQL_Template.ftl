-- ----------------------------
-- Table structure for ${tabletop}
-- ----------------------------
-- DROP TABLE ${tabletop};
CREATE TABLE ${tabletop} (
	UUID VARCHAR2(32) NOT NULL,
	BANK_CODE VARCHAR2(20) NOT NULL,
<#list fieldList as var>
	<#if var[1] == 'Integer'>
	${var[0]} NUMBER(${var[5]}) NULL ,
	<#elseif var[1] == 'Double'>
	${var[0]} NUMBER(${var[5]},${var[6]}) NULL ,
	<#else>
	${var[0]} VARCHAR2(${var[5]}) NULL ,
	</#if>
</#list>
	CREATE_TIME VARCHAR2(50) NOT NULL,
	UPDATE_TIME VARCHAR2(50) NOT NULL,
	DEL_FLAG VARCHAR2(1) DEFAULT '0' NOT NULL,
	DATA_CHAN VARCHAR2(10),
	TRUNC_NO NUMBER NOT NULL
);

COMMENT ON COLUMN ${tabletop}.UUID IS '物理主键';
COMMENT ON COLUMN ${tabletop}.BANK_CODE IS '银行代码';
<#list fieldList as var>
COMMENT ON COLUMN ${tabletop}.${var[0]} IS '${var[2]}';
</#list>
COMMENT ON COLUMN ${tabletop}.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN ${tabletop}.UPDATE_TIME IS '更新时间';
COMMENT ON COLUMN ${tabletop}.DEL_FLAG IS '删除标志';
COMMENT ON COLUMN ${tabletop}.DATA_CHAN IS '数据来源';
COMMENT ON COLUMN ${tabletop}.TRUNC_NO IS '版本跟踪号';

-- ----------------------------
-- Indexes structure for table ${tabletop}
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table "${tabletop}"
-- ----------------------------
ALTER TABLE ${tabletop} ADD PRIMARY KEY ("UUID");

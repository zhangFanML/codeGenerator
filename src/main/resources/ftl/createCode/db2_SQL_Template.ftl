-- ----------------------------
-- Table structure for ${tabletop}
-- ----------------------------
-- DROP TABLE ${tabletop};
CREATE TABLE ${tabletop} (
<#list fieldList as var>
	<#if var[1] == 'Integer'>
	${var[0]} int NULL ,
	<#elseif var[1] == 'Double'>
	${var[0]} DECIMAL(${var[5]},${var[6]}) NULL ,
	<#else>
	${var[0]} VARCHAR(${var[5]}) NULL ,
	</#if>
</#list>
UUID VARCHAR(32) NOT NULL
);

COMMENT ON TABLE ${tabletop} is '${TITLE}';
<#list fieldList as var>
COMMENT ON COLUMN ${tabletop}.${var[0]} IS '${var[2]}';
</#list>
COMMENT ON COLUMN ${tabletop}.UUID IS 'ID';

-- ----------------------------
-- Indexes structure for table "${tabletop}"
-- ----------------------------

-- ----------------------------
-- Checks structure for table "${tabletop}"

-- ----------------------------

ALTER TABLE ${tabletop} ADD CHECK (UUID IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table "${tabletop}"
-- ----------------------------
ALTER TABLE ${tabletop} ADD PRIMARY KEY (UUID);

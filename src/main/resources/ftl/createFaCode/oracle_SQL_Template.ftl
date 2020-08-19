-- ----------------------------
-- Table structure for "C##FHADMIN"."${tabletop}"
-- ----------------------------
-- DROP TABLE "C##FHADMIN"."${tabletop}";
CREATE TABLE "C##FHADMIN"."${tabletop}" (
<#list fieldList as var>
	<#if var[1] == 'Integer'>
	"${var[0]}" NUMBER(${var[5]}) NULL ,
	<#elseif var[1] == 'Double'>
	"${var[0]}" NUMBER(${var[5]},${var[6]}) NULL ,
	<#else>
	"${var[0]}" VARCHAR2(${var[5]} BYTE) NULL ,
	</#if>
</#list>
	"UUID" VARCHAR2(100 BYTE) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;

<#list fieldList as var>
COMMENT ON COLUMN "C##FHADMIN"."${tabletop}"."${var[0]}" IS '${var[2]}';
</#list>
COMMENT ON COLUMN "C##FHADMIN"."${tabletop}"."UUID" IS 'ID';

-- ----------------------------
-- Indexes structure for table ${tabletop}
-- ----------------------------

-- ----------------------------
-- Checks structure for table "C##FHADMIN"."${tabletop}"

-- ----------------------------

ALTER TABLE "C##FHADMIN"."${tabletop}" ADD CHECK ("UUID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table "C##FHADMIN"."${tabletop}"
-- ----------------------------
ALTER TABLE "C##FHADMIN"."${tabletop}" ADD PRIMARY KEY ("UUID");

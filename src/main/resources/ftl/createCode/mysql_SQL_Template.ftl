
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `${tabletop}`
-- ----------------------------
DROP TABLE IF EXISTS `${tabletop}`;
CREATE TABLE `${tabletop}` (
 		`UUID` varchar(32) NOT NULL,
	<#list fieldList as var>
		<#if var[1] == 'Integer'>
		`${var[0]}` int(${var[5]}) NOT NULL COMMENT '${var[2]}',
		<#elseif var[1] == 'Double'>
		`${var[0]}` double(${var[5]},${var[6]}) DEFAULT NULL COMMENT '${var[2]}',
		<#else>
		`${var[0]}` varchar(${var[5]}) DEFAULT NULL COMMENT '${var[2]}',
		</#if>
	</#list>
  		PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

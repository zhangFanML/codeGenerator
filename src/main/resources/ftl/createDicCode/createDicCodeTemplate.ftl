package com.git.easyloan.constants;

/**
* 说明：${TITLE}
* 创建人：
* 创建时间：${nowDate?string("yyyy-MM-dd")}
*/

public interface ${objectName} {

<#list fieldList as var>
    public static final String ${var[0]}="${var[1]}";//${var[2]}

</#list>

}

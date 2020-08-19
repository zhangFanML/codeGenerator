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
package ${basePackageName}.controller.${packageName};

<#if tableMore>
<#else>
</#if>
import javacommon.coreframe.base.BaseController;
import javacommon.coreframe.base.Page;
import javacommon.coreframe.util.PageData;
import ${basePackageName}.service.${packageName}.${objectName}Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javacommon.easytools.core.collection.DTDMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

/**
 * 说明：${TITLE}
 * 创建人：
 * 创建时间：${nowDate?string("yyyy-MM-dd")}
 */
@RestController
@RequestMapping(value="/${objectNameLower}")
<#if tableMore>
@Api(value = "${TITLE}【多表】Controller", tags = {"${TITLE}【多表】操作"})
<#else>
@Api(value = "${TITLE}Controller", tags = {"${TITLE}操作"})
</#if>
public class ${objectName}Controller extends BaseController{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ${objectName}Service ${objectNameLower}Service;

    /**保存
    * @param pd
    * @throws Exception
    */
    @PostMapping
<#if tableMore>
    @ApiOperation(value = "【多表】新增${TITLE}", notes = "【多表】新增${TITLE}")
<#else>
    @ApiOperation(value = "新增${TITLE}", notes = "新增${TITLE}")
</#if>
    public void save(
        @NotEmpty(message = "${TITLE}信息不能为空")
        @ApiParam(name = "data", value = "${TITLE}信息", required = true)
        @RequestBody
        PageData pd) throws Exception{

        logger.info("新增${TITLE}");
        ${objectNameLower}Service.save(pd);
    }

    /**
    * 删除${TITLE}
    *
    * @param id ${TITLE}编号
    * @throws Exception
    */
    @DeleteMapping("/{id}")
<#if tableMore>
    @ApiOperation(value = "【多表】删除${TITLE}", notes = "根据${TITLE}编号,【多表】删除${TITLE}数据")
<#else>
    @ApiOperation(value = "删除${TITLE}", notes = "根据${TITLE}编号,删除${TITLE}数据")
</#if>
    public void delete(
        @PathVariable
        @NotBlank(message = "${TITLE}删除时编号不能为空")
        @ApiParam(name = "id", value = "${TITLE}编号", required = true)
        String id) throws Exception {

        logger.info("${TITLE}删除操作");
        ${objectNameLower}Service.delete(id);
    }


    /**
    * ${TITLE}修改
    *
    * @param pd ${TITLE}数据
    * @throws Exception
    */
    @PatchMapping
<#if tableMore>
    @ApiOperation(value = "【多表】修改${TITLE}", notes = "根据${TITLE}编号,【多表】${TITLE}数据")
<#else>
    @ApiOperation(value = "修改${TITLE}", notes = "根据${TITLE}编号,修改${TITLE}数据")
</#if>
    public void editPatch(
        @RequestBody
        @NotEmpty(message = "待修改${TITLE}信息不能为空")
        @ApiParam(name = "collateralInfo", value = "待修改${TITLE}信息", required = true)
        PageData pd) throws Exception {

        logger.info("修改${TITLE}");
        ${objectNameLower}Service.editPatch(pd);
    }

    /**
    * ${TITLE}查询列表
    *
    * @param data    查询条件
    * @param pageNum 页码
    * @return
    * @throws Exception
    */
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
<#if tableMore>
    @ApiOperation(value = "【多表】分页获取${TITLE}信息", notes = "【多表】分页获取${TITLE}信息：查询条件，页面")
<#else>
    @ApiOperation(value = "分页获取${TITLE}信息", notes = "分页获取${TITLE}信息：查询条件，页面")
</#if>
    public PageData list(
        @RequestParam
        @ApiParam(name = "data", value = "查询条件")
        Map<String, String> data,
        @RequestParam(defaultValue = "1", required = false)
        @Min(value = 1, message = "页码只能为正整数")
        @ApiParam(name = "pageNum", value = "页码")
        int pageNum,
        @RequestParam(defaultValue = "10", required = false)
        @Min(value = 10, message = "条数只能为正整数")
        @ApiParam(name = "size", value = "条数")
        int size) throws Exception {

        logger.info("${TITLE}查询");
        PageData pd = new PageData();
        DTDMap dicMap = new DTDMap();//反显列值为字典 begin
<#list fieldList as var>
    <#if var[1] == "Dictionary">
        <#if tableMore>
        dicMap.put("${dashedToCamel(var[10])}.${dashedToCamel(var[0])}","${var[8]}");
        <#else>
        dicMap.put("${dashedToCamel(var[0])}","${var[8]}");
        </#if>
    </#if>
</#list>
        dicMap.put("字段","字典编码");  //反显列时翻译某列为字典值
        pd.put(DICMAP,dicMap);// 反显列值为字典 end
        pd.putAll(data);
        Page page = new Page();
        page.setCurrentPage(pageNum);
        page.setShowCount(size);
        page.setPd(pd);
        return ${objectNameLower}Service.list(page);
    }


    /**
    * ${TITLE}根据ID获取数据
    *
    * @param id   ${TITLE}ID
    * @return
    * @throws Exception
    */
    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
<#if tableMore>
    @ApiOperation(value = "【多表】查询${TITLE}信息", notes = "根据${TITLE}编号,【多表】查询${TITLE}数据")
<#else>
    @ApiOperation(value = "查询${TITLE}信息", notes = "根据${TITLE}编号,查询${TITLE}数据")
</#if>
    public PageData findById(
        @PathVariable
        @NotBlank(message = "${TITLE}编号不能为空")
        @ApiParam(name = "id", value = "${TITLE}编号", required = true)
        String id ) throws Exception {

        logger.info("${TITLE}根据ID获取数据");
        return ${objectNameLower}Service.findById(id);
    }

    /**
    * 批量删除
    * @param
    * @throws Exception
    */
    @DeleteMapping
<#if tableMore>
    @ApiOperation(value = "【多表】批量删除${TITLE}信息", notes = "【多表】批量删除${TITLE}信息")
<#else>
    @ApiOperation(value = "批量删除${TITLE}信息", notes = "批量删除${TITLE}信息")
</#if>
    public void batchDelete(
        @RequestParam
        @NotBlank(message = "${TITLE}编号不能为空")
        @ApiParam(name = "ids", value = "主键列表")
        String ids) throws Exception {

        logger.info("批量删除${TITLE}");
        ${objectNameLower}Service.deleteAll(ids);
    }
}

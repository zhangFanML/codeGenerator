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
package ${basePackageName}.service.${packageName}.impl;

import com.git.easyloan.commons.base.AlsBaseMessage;
import javacommon.coreframe.base.Page;
import javacommon.coreframe.util.PageData;
<#if tableMore>
import ${basePackageName}.core.${packageName}.${objectName}Core;
import org.springframework.beans.factory.annotation.Autowired;
<#else>
import javacommon.coreframe.util.KeyGenerator;
import javax.annotation.Resource;
import javacommon.coreframe.dao.DaoSupport;
</#if>
import org.springframework.transaction.annotation.Transactional;
import ${basePackageName}.service.${packageName}.${objectName}Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

/** 
 * 说明： ${TITLE}
 * 创建人：
 * 创建时间：${nowDate?string("yyyy-MM-dd")}
 * @version
 */
@Service("${objectNameLower}Service")
public class ${objectName}ServiceImpl extends AlsBaseMessage implements ${objectName}Service{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

<#if tableMore>
	@Autowired
	private ${objectName}Core ${objectNameLower}Core;
<#else>
	@Resource(name = "daoSupport")
	private DaoSupport dao;
</#if>

	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	@Override
	@Transactional
	public void save(PageData pd)throws Exception{
	<#if tableMore>
		${objectNameLower}Core.save(pd);
	<#else>
	<#if PKColumns?exists && PKColumns?size gte 1>
		pd.put("${dashedToCamel(PKColumns[0])}", KeyGenerator.get32UUID());
	<#else>
		pd.put("uuid", KeyGenerator.get32UUID());
	</#if>
		dao.save("${objectName}Mapper.save", pd);
	</#if>

	}
	
	/**删除
	 * @param id
	 * @throws Exception
	 */
	@Override
	@Transactional
	public void delete(String id)throws Exception{
	<#if tableMore>
		${objectNameLower}Core.delete(id);
	<#else>
		dao.delete("${objectName}Mapper.delete", id);
	</#if>

	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	@Override
	@Transactional
	public void editPatch(PageData pd)throws Exception{
	<#if tableMore>
		${objectNameLower}Core.editPatch(pd);
	<#else>
		dao.update("${objectName}Mapper.editPatch", pd);
	</#if>
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public PageData list(Page page)throws Exception{
	<#if tableMore>
		return ${objectNameLower}Core.list(page);
	<#else>
		PageData pd = new PageData();
		List<PageData> pageList = (List<PageData>)dao.findForList("${objectName}Mapper.datalistPage", page);
		pd.put("page",page);
		pd.put("list",pageList);
		return pd;
	</#if>
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
	<#if tableMore>
		return ${objectNameLower}Core.listAll(pd);
	<#else>
			return (List<PageData>)dao.findForList("${objectName}Mapper.listAll", pd);
	</#if>
	}
	
	/**通过id获取数据
	 * @param id
	 * @throws Exception
	 */
    @Override
	public PageData findById(String id)throws Exception{
	<#if tableMore>
		return ${objectNameLower}Core.findById(id);
	<#else>
		return (PageData)dao.findForObject("${objectName}Mapper.findById", id);
	</#if>
	}
	
	/**批量删除
	 * @param ids
	 * @throws Exception
	 */
    @Override
	@Transactional
	public void deleteAll(String ids)throws Exception{
	<#if tableMore>
		${objectNameLower}Core.deleteAll(ids);
	<#else>
		if (null != ids && !"".equals(ids)) {
			String[] idArray = ids.split(",");
			dao.delete("${objectName}Mapper.deleteAll", idArray);
		}
	</#if>
	}
	
}


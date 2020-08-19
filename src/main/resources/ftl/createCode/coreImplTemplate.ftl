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
package ${basePackageName}.core.${packageName}.impl;

import com.git.easyloan.commons.base.AlsBaseMessage;
import javacommon.coreframe.dao.DaoSupport;
import javacommon.coreframe.base.Page;
import javacommon.coreframe.util.PageData;
import ${basePackageName}.core.${packageName}.${objectName}Core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javacommon.coreframe.util.KeyGenerator;
import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** 
 * 说明： ${TITLE}
 * 创建人：
 * 创建时间：${nowDate?string("yyyy-MM-dd")}
 * @version
 */
@Service("${objectNameLower}Core")
public class ${objectName}CoreImpl extends AlsBaseMessage implements ${objectName}Core{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void save(PageData pd)throws Exception{
	<#list PKColumns as var>
		pd.put("${dashedToCamel(var)}", KeyGenerator.get32UUID());
	<#else>
		pd.put("uuid", KeyGenerator.get32UUID());
	</#list>
	<#list mappers>
		<#items  as mapper>
		dao.save("${mapper}Mapper.save", getPdForTableName(pd,"${mapper?uncap_first}"));
		</#items>
	</#list>
	}
	
	/**删除
	 * @param id
	 * @throws Exception
	 */
	@Override
	public void delete(String id)throws Exception{
	<#list mappers as mapper>
		dao.delete("${mapper}Mapper.delete", id);
	</#list>

	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void editPatch(PageData pd)throws Exception{
<#list mappers>
	<#items  as mapper>
		dao.save("${mapper}Mapper.editPatch", getPdForTableName(pd,"${mapper?uncap_first}"));
	</#items>
</#list>

	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public PageData list(Page page)throws Exception{
		PageData pd = new PageData();
		List<PageData> pageList = (List<PageData>)dao.findForList("${objectName}Mapper.datalistPage", page);
		pd.put("page",page);
		pd.put("list",pageList);
		return pd;
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("${objectName}Mapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param id
	 * @throws Exception
	 */
    @Override
	public PageData findById(String id)throws Exception{
		PageData pd = new PageData();
<#list mappers>
	<#items  as mapper>
		PageData pd${mapper?index+1} = (PageData)dao.findForObject("${mapper}Mapper.findById", id);
		pd.putAll(getPdForTableNameRe(pd${mapper?index+1},"${mapper?uncap_first}"));
	</#items>
</#list>
		return pd;
	}
	
	/**批量删除
	 * @param ids
	 * @throws Exception
	 */
    @Override
	public void deleteAll(String ids)throws Exception{
	<#list mappers as mapper>
		if (null != ids && !"".equals(ids)) {
			String[] idArray = ids.split(",");
			dao.delete("${mapper}Mapper.deleteAll", idArray);
		}
	</#list>
	}

}


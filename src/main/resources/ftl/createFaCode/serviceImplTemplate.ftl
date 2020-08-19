package ${basePackageName}.service.${packageName}.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import javacommon.coreframe.dao.DaoSupport;
import javacommon.coreframe.entity.Page;
import javacommon.coreframe.util.PageData;
import ${basePackageName}.service.${packageName}.${objectName}Service;

/** 
 * 说明： ${TITLE}
 * 创建人：
 * 创建时间：${nowDate?string("yyyy-MM-dd")}
 * @version
 */
@Service("${objectNameLower}Service")
public class ${objectName}ServiceImpl implements ${objectName}Service{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("${objectName}Mapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("${objectName}Mapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("${objectName}Mapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("${objectName}Mapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("${objectName}Mapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("${objectName}Mapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("${objectName}Mapper.deleteAll", ArrayDATA_IDS);
	}
	
}


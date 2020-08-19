package ${basePackageName}.core.${packageName};

import javacommon.coreframe.base.Page;
import javacommon.coreframe.util.PageData;

import java.util.List;

/** 
 * 说明： ${TITLE}接口
 * 创建人：
 * 创建时间：${nowDate?string("yyyy-MM-dd")}
 * @version
 */
public interface ${objectName}Core{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	void save(PageData pd)throws Exception;
	
	/**删除
	 * @param id
	 * @throws Exception
	 */
	void delete(String id)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	void editPatch(PageData pd)throws Exception;

	/**列表
	 * @param page
	 * @throws Exception
	 */
	PageData list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param id
	 * @throws Exception
	 */
	PageData findById(String id)throws Exception;
	
	/**批量删除
	 * @param ids
	 * @throws Exception
	 */
	void deleteAll(String ids)throws Exception;
	
}


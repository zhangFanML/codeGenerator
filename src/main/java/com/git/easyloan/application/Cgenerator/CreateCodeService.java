package com.git.easyloan.application.Cgenerator;

import com.git.easyloan.entity.Page;
import com.git.easyloan.entity.PageData;

import java.util.List;


/**
 * 类名称：代码生成器接口类
 * 创建人：
 * 修改时间：2015年11月24日
 * @version
 */
public interface CreateCodeService {

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	void save(PageData pd)throws Exception;

	/**删除
	 * @param pd
	 * @throws Exception
	 */
	void delete(PageData pd)throws Exception;

	/**列表
	 * @param page
	 * @throws Exception
	 */
	List<PageData> list(Page page)throws Exception;

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	PageData findById(PageData pd)throws Exception;

	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	/**列表(主表)
	 * @throws Exception
	 */
	List<PageData> listFa()throws Exception;

}

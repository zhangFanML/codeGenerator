package com.git.easyloan.application.Cgenerator.impl;

import com.git.easyloan.application.Cgenerator.CreateCodeService;
import com.git.easyloan.entity.Page;
import com.git.easyloan.entity.PageData;
import com.git.easyloan.utils.dao.DaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 类名称：CreateCodeService 代码生成器
 * 创建人：
 * 修改时间：2015年11月24日
 * @version
 */
@Service("createCodeService")
public class CreateCodeServiceImpl implements CreateCodeService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void save(PageData pd)throws Exception{
		dao.save("CreateCodeMapper.save", pd);
	}

	/**删除
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void delete(PageData pd)throws Exception{
		dao.delete("CreateCodeMapper.delete", pd);
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CreateCodeMapper.datalistPage", page);
	}

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CreateCodeMapper.findById", pd);
	}

	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	@Override
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("CreateCodeMapper.deleteAll", ArrayDATA_IDS);
	}

	/**列表(主表)
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listFa() throws Exception {
		return (List<PageData>)dao.findForList("CreateCodeMapper.listFa", "");
	}
}


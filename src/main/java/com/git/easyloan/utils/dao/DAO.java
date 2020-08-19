package com.git.easyloan.utils.dao;
/**
 * @author 
 * 修改时间：2015、12、11
 */
public interface DAO<E, PK> {
	
	/**
	 * 保存对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	Object save(String str, E obj) throws Exception;
	
	/**
	 * 修改对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	Object update(String str, E obj) throws Exception;
	
	/**
	 * 删除对象 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	Object delete(String str, E obj) throws Exception;

	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	Object findForObject(String str, E obj) throws Exception;

	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	Object findForList(String str, E obj) throws Exception;
	
	/**
	 * 查找对象封装成Map
	 * @param sql
	 * @param obj
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	Object findForMap(String sql, E obj, String key, String value) throws Exception;
	
}

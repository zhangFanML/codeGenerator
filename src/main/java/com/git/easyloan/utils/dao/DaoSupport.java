package com.git.easyloan.utils.dao;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @author
 * 修改时间：2017-09-02
 */
@Repository("daoSupport")
public class DaoSupport<E, PK extends Serializable> implements DAO<E, PK> {

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 保存对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object save(String str, E obj) throws Exception {
//		prepareObjectForSave(obj);
		return sqlSessionTemplate.insert(str, obj);
	}

	/**
	 * 批量更新
	 * @param str
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public Object batchSave(String str, List<E> objs )throws Exception{
//		prepareObjectForSave(objs);
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
		//批量执行器
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
		try{
			if(objs!=null) {
				for (E entity : objs) {
					sqlSession.insert(str, entity);
				}
				sqlSession.flushStatements();
				sqlSession.commit();
				sqlSession.clearCache();
			}
		}catch (Exception e){
			sqlSession.rollback();
			e.printStackTrace();
		}finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return objs.size();
	}

	/**
	 * 修改对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object update(String str, E obj) throws Exception {
//		prepareObjectForUpdate(obj);
		return sqlSessionTemplate.update(str, obj);
	}

	/**
	 * 批量更新
	 * @param str
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public void batchUpdate(String str, List<E> objs )throws Exception{
//		prepareObjectForUpdate(objs);
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
		//批量执行器
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
		try{
			if(objs!=null){
				for (E entity : objs) {
//					prepareObjectForUpdate(entity);
					sqlSession.update(str, entity);
				}
				sqlSession.flushStatements();
				sqlSession.commit();
				sqlSession.clearCache();
			}
			sqlSession.commit();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

	/**
	 * 批量删除
	 * @param str
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public Object batchDelete(String str, List<E> objs )throws Exception{
		return sqlSessionTemplate.delete(str, objs);
	}

	/**
	 * 删除对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object delete(String str, E obj) throws Exception {
		return sqlSessionTemplate.delete(str, obj);
	}

	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object findForObject(String str, E obj) throws Exception {
		return sqlSessionTemplate.selectOne(str, obj);
	}

	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object findForList(String str, E obj) throws Exception {
		return sqlSessionTemplate.selectList(str, obj);
	}

	public static String dashedToCamel(String str){
		String[] mapper = str.toUpperCase().split("_");
		String sb = "";
		for(int i = 0; i < mapper.length; i++){
			if(mapper[i].length() > 1){
				sb += (mapper[i].substring(0,1) + mapper[i].substring(1).toLowerCase());
			}else{
				sb += (mapper[i]);
			}
		}
		return sb.substring(0,1).toLowerCase() + sb.substring(1);
	}

	/**
	 *
	 * @param str
	 * @param obj
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object findForMap(String str, E obj, String key, String value) throws Exception {
		return sqlSessionTemplate.selectMap(str, obj, key);
	}

//	/**
//	 *
//	 * @Title: prepareObjectForSave
//	 * @Description: 用于子类覆盖,在insert之前调用，为对象设置时间戳
//	 * @param o    设定文件
//	 * @return void    返回类型
//	 * @throws
//	 * @author GIT-Sunny
//	 * @date 2012-9-12 下午05:03:09
//	 * @version V1.0
//	 */
//	protected void prepareObjectForSave(Object o) {
//		try {
//			//获取当前时间,精确到毫秒:yyyy-MM-dd HH:mm:ss.SSS
//			String businessTime = DateUtil.date().toMsStr();
//			if (o instanceof List) {
//				for (E entity : (List<E>)o) {
//					if(entity instanceof Map){
//						((DTDMap) entity).put("CREATE_TIME",businessTime);
//						((DTDMap) entity).put("UPDATE_TIME",businessTime);
//						((DTDMap) entity).put("DEL_FLAG","0");
//						((DTDMap) entity).put("TRUNC_NO",1);
//					}else {
//						ReflectUtil.setFieldValue(entity, "CreateTime", businessTime);
//						ReflectUtil.setFieldValue(entity, "UpdateTime", businessTime);
//						ReflectUtil.setFieldValue(entity, "DelFlag", "0");
//						ReflectUtil.setFieldValue(entity, "TruncNo", 1);
//					}
//				}
//			}else{
//				if(o instanceof Map){
//					((DTDMap) o).put("CREATE_TIME",businessTime);
//					((DTDMap) o).put("UPDATE_TIME",businessTime);
//					((DTDMap) o).put("DEL_FLAG","0");
//					((DTDMap) o).put("TRUNC_NO",1);
//				}else {
//					ReflectUtil.setFieldValue(o,"CreateTime",businessTime);
//					ReflectUtil.setFieldValue(o,"UpdateTime",businessTime);
//					ReflectUtil.setFieldValue(o, "DelFlag", "0");
//					ReflectUtil.setFieldValue(o,"TruncNo",1);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new DataAccessException("保存数据赋默认值失败，错误信息："+e.getMessage());
//		}
//	}
//
//	/**
//	 *
//	 * @Title: prepareObjectForSave
//	 * @Description: 用于子类覆盖,在insert之前调用，为Map设置时间戳和乐观锁版本号
//	 * @param map    需要赋值的Map
//	 * @return void    返回类型
//	 * @throws
//	 * @author GIT-Sunny
//	 * @date 2012-9-12 下午05:03:09
//	 * @version V1.0
//	 */
//	protected void prepareObjectForSave(Map map) {
//		try {
//			//获取当前时间,精确到毫秒:yyyy-MM-dd HH:mm:ss.SSS
//			String businessTime = DateUtil.date().toMsStr();
//			map.put("CREATE_TIME",businessTime);
//			map.put("UPDATE_TIME",businessTime);
//			map.put("DEL_FLAG","0");
//			map.put("TRUNC_NO",1);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new DataAccessException("保存数据赋默认值失败，错误信息："+e.getMessage());
//		}
//	}
//
//	/**
//	 *
//	 * @Title: prepareObjectForUpdate
//	 * @Description: 用于子类覆盖,在update之前调用,为对象设置时间戳
//	 * @param o    设定文件
//	 * @return void    返回类型
//	 * @throws
//	 * @author GIT-Sunny
//	 * @date 2012-9-12 下午05:02:40
//	 * @version V1.0
//	 */
//	protected void prepareObjectForUpdate(Object o) {
//		try {
//			//获取当前时间,精确到毫秒:yyyy-MM-dd HH:mm:ss.SSS
//			String businessTime = DateUtil.date().toMsStr();
//			if(o instanceof List){
//				for (E entity : (List<E>)o) {
//					if(entity instanceof Map) {
//						((DTDMap) entity).put("UPDATE_TIME", businessTime);
//					}else{
//						ReflectUtil.setFieldValue(entity, "UpdateTime", businessTime);
//					}
//				}
//			}else{
//				if(o instanceof Map){
//					((DTDMap) o).put("UPDATE_TIME", businessTime);
//				}else {
//					ReflectUtil.setFieldValue(o, "UpdateTime", businessTime);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new DataAccessException("保存数据赋默认值失败，错误信息："+e.getMessage());
//		}
//	}
//
//	/**
//	 *
//	 * @Title: prepareObjectForDeleteLogic
//	 * @Description: 用于子类覆盖，在deleteLogic之前调用，为对象设置逻辑删除标志
//	 * @param o    设定文件
//	 * @return void    返回类型
//	 * @throws
//	 * @author GIT-Sunny
//	 * @date 2013-1-3 下午02:16:50
//	 * @version V1.0
//	 */
//	protected void prepareObjectForDeleteLogic(E o) {
//		try {
//			//获取当前时间,精确到毫秒:yyyy-MM-dd HH:mm:ss.SSS
//			String businessTime = DateUtil.date().toMsStr();
//			if(o instanceof Map){
//				((Map) o).put("DEL_FLAG","1");
//				((Map) o).put("UPDATE_TIME",businessTime);
//			}else{
//				ReflectUtil.setFieldValue(o,"DelFlag","1");
//				ReflectUtil.setFieldValue(o,"UpdateTime",businessTime);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new DataAccessException("保存数据赋默认值失败，错误信息："+e.getMessage());
//		}
//	}
//
//	/**
//	 *
//	 * @Title: prepareObjectForUpdate
//	 * @Description: 用于子类覆盖,在update之前调用,为对象设置时间戳
//	 * @param map    设定文件
//	 * @return void    返回类型
//	 * @throws
//	 * @author GIT-Sunny
//	 * @date 2012-9-12 下午05:02:40
//	 * @version V1.0
//	 */
//	protected void prepareObjectForUpdate(Map map) {
//		try {
//			//获取当前时间,精确到毫秒:yyyy-MM-dd HH:mm:ss.SSS
//			String businessTime = DateUtil.date().toMsStr();
//			map.put("UPDATE_TIME",businessTime);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new DataAccessException("保存数据赋默认值失败，错误信息："+e.getMessage());
//		}
//	}


}



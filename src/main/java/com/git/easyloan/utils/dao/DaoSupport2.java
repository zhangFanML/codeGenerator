package com.git.easyloan.utils.dao;////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package javacommon.coreframe.dao;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.io.Serializable;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import javacommon.coreframe.dao.redis.impl.RedisDaoImpl;
//import javacommon.coreframe.util.KeyGenerator;
//import javacommon.coreframe.util.PageData;
//import javacommon.easytools.core.collection.DTDMap;
//import javacommon.easytools.log.Log;
//import javacommon.easytools.log.LogFactory;
//import javacommon.exception.DataAccessException;
//import javax.annotation.Resource;
//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.session.ExecutorType;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Repository;
//
//@Repository("daoSupport2")
//public class DaoSupport2<E, PK extends Serializable> implements DAO<E, PK> {
//	protected static Log logger = LogFactory.get();
//	@Autowired
//	@Resource(
//			name = "slaveSqlSessionTemplate"
//	)
//	private SqlSessionTemplate sqlSessionTemplate2;
//	@Autowired
//	@Resource(
//			name = "redisDaoImpl"
//	)
//	private RedisDaoImpl redisDaoImpl;
//	ObjectMapper objectMapper = new ObjectMapper();
//
//	public DaoSupport2() {
//	}
//
//	public Object save(String str, E obj) throws Exception {
//		this.prepareObjectForSave(obj);
//		return this.sqlSessionTemplate2.insert(str, obj);
//	}
//
//	public Object batchSave(String str, List<E> objs) throws Exception {
//		this.prepareObjectForSave((Object)objs);
//		SqlSessionFactory sqlSessionFactory = this.sqlSessionTemplate2.getSqlSessionFactory();
//		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
//
//		try {
//			if (objs != null) {
//				Iterator var5 = objs.iterator();
//
//				while(var5.hasNext()) {
//					E entity = var5.next();
//					sqlSession.insert(str, entity);
//				}
//
//				sqlSession.flushStatements();
//				sqlSession.commit();
//				sqlSession.clearCache();
//			}
//		} catch (Exception var10) {
//			sqlSession.rollback();
//			var10.printStackTrace();
//		} finally {
//			if (sqlSession != null) {
//				sqlSession.close();
//			}
//
//		}
//
//		return objs.size();
//	}
//
//	public Object update(String str, E obj) throws Exception {
//		this.prepareObjectForUpdate(obj);
//		return this.sqlSessionTemplate2.update(str, obj);
//	}
//
//	public void batchUpdate(String str, List<E> objs) throws Exception {
//		this.prepareObjectForUpdate((Object)objs);
//		SqlSessionFactory sqlSessionFactory = this.sqlSessionTemplate2.getSqlSessionFactory();
//		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
//
//		try {
//			if (objs != null) {
//				Iterator var5 = objs.iterator();
//
//				while(var5.hasNext()) {
//					E entity = var5.next();
//					this.prepareObjectForUpdate(entity);
//					sqlSession.update(str, entity);
//				}
//
//				sqlSession.flushStatements();
//				sqlSession.commit();
//				sqlSession.clearCache();
//			}
//
//			sqlSession.commit();
//		} catch (Exception var10) {
//			var10.printStackTrace();
//		} finally {
//			if (sqlSession != null) {
//				sqlSession.close();
//			}
//
//		}
//
//	}
//
//	public Object deleteLogic(String str, E obj, String checkflag) throws Exception {
//		int rint = 0;
//		if (checkflag != null && !"".equals(checkflag)) {
//			if ("true".equals(checkflag)) {
//				this.prepareObjectForDeleteLogic(obj);
//				rint = this.sqlSessionTemplate2.update(str, obj);
//				if (rint <= 0) {
//					throw new Exception("请退出当前界面，重新操作！");
//				}
//			} else {
//				rint = (Integer)this.deleteLogic(str, obj);
//			}
//		}
//
//		return rint;
//	}
//
//	public Object deleteLogic(String str, E obj) throws Exception {
//		this.prepareObjectForDeleteLogic(obj);
//		int rint = this.sqlSessionTemplate2.update(str, obj);
//		if (rint <= 0) {
//			throw new Exception("请退出当前界面，重新操作！");
//		} else {
//			return rint;
//		}
//	}
//
//	public Object batchDelete(String str, List<E> objs) throws Exception {
//		int rint = this.sqlSessionTemplate2.delete(str, objs);
//		if (rint <= 0) {
//			throw new Exception("当前操作删除的数据未找到，请联系管理员或退出当前界面，重新操作！");
//		} else {
//			this.saveDelLog(str, this.objectMapper.writeValueAsString(objs), rint);
//			return rint;
//		}
//	}
//
//	public Object delete(String str, E obj) throws Exception {
//		int rint = this.sqlSessionTemplate2.delete(str, obj);
//		if (rint <= 0) {
//			throw new Exception("当前操作删除的数据未找到，请联系管理员或退出当前界面，重新操作！");
//		} else {
//			this.saveDelLog(str, this.objectMapper.writeValueAsString(obj), rint);
//			return rint;
//		}
//	}
//
//	public Object delete(String str, E obj, String checkflag) throws Exception {
//		int rint = 0;
//		if (checkflag != null && !"".equals(checkflag)) {
//			if ("true".equals(checkflag)) {
//				this.sqlSessionTemplate2.delete(str, obj);
//				this.saveDelLog(str, this.objectMapper.writeValueAsString(obj), rint);
//			}
//		} else {
//			this.delete(str, obj);
//		}
//
//		return Integer.valueOf(rint);
//	}
//
//	public Object findForObject(String str, E obj) throws Exception {
//		Object robj = this.sqlSessionTemplate2.selectOne(str, obj);
//		if (robj == null) {
//			return robj;
//		} else if (!(robj instanceof PageData)) {
//			return robj;
//		} else {
//			PageData pdn = new PageData();
//			Iterator i = ((PageData)robj).entrySet().iterator();
//			if (!i.hasNext()) {
//				return null;
//			} else {
//				while(i.hasNext()) {
//					Entry e = (Entry)i.next();
//					String oldName = (String)e.getKey();
//					String newName = dashedToCamel(oldName);
//					pdn.put(newName, e.getValue());
//				}
//
//				return pdn;
//			}
//		}
//	}
//
//	public static String dashedToCamel(String str) {
//		String[] mapper = str.toUpperCase().split("_");
//		String sb = "";
//
//		for(int i = 0; i < mapper.length; ++i) {
//			if (mapper[i].length() > 1) {
//				sb = sb + mapper[i].substring(0, 1) + mapper[i].substring(1).toLowerCase();
//			} else {
//				sb = sb + mapper[i];
//			}
//		}
//
//		return sb.substring(0, 1).toLowerCase() + sb.substring(1);
//	}
//
//	public Object findForList(String str, E obj) throws Exception {
//		List pageList = this.sqlSessionTemplate2.selectList(str, obj);
//
//		for(int x = 0; x < pageList.size(); ++x) {
//			Object robj = pageList.get(x);
//			if (robj instanceof PageData) {
//				Iterator i = ((PageData)robj).entrySet().iterator();
//				if (!i.hasNext()) {
//					return null;
//				}
//
//				PageData pdn = new PageData();
//
//				while(i.hasNext()) {
//					Entry e = (Entry)i.next();
//					String oldName = (String)e.getKey();
//					String newName = dashedToCamel(oldName);
//					pdn.put(newName, e.getValue());
//				}
//
//				pageList.set(x, pdn);
//			}
//		}
//
//		return pageList;
//	}
//
//	public Object findForMap(String str, E obj, String key, String value) throws Exception {
//		return this.sqlSessionTemplate2.selectMap(str, obj, key);
//	}
//
//	protected void prepareObjectForSave(Object o) {
//		try {
//			String businessTime = this.getBusinessTime();
//			if (o instanceof List) {
//				Iterator var3 = ((List)o).iterator();
//
//				while(true) {
//					while(var3.hasNext()) {
//						E entity = var3.next();
//						String uuid;
//						if (entity instanceof Map) {
//							uuid = (String)((DTDMap)entity).get("uuid");
//							if (uuid == null || uuid.equals("")) {
//								((DTDMap)entity).put("uuid", KeyGenerator.get32UUID());
//							}
//
//							((DTDMap)entity).put("createTime", businessTime);
//							((DTDMap)entity).put("updateTime", businessTime);
//							((DTDMap)entity).put("delFlag", "0");
//							((DTDMap)entity).put("truncNo", 1);
//						} else {
//							uuid = BeanUtils.getProperty(entity, "uuid");
//							if (uuid == null || uuid.equals("")) {
//								BeanUtils.setProperty(entity, "uuid", KeyGenerator.get32UUID());
//							}
//
//							BeanUtils.setProperty(entity, "createTime", businessTime);
//							BeanUtils.setProperty(entity, "updateTime", businessTime);
//							BeanUtils.setProperty(entity, "delFlag", "0");
//							BeanUtils.setProperty(entity, "truncNo", 1);
//						}
//					}
//
//					return;
//				}
//			} else {
//				String uuid;
//				if (o instanceof Map) {
//					uuid = (String)((DTDMap)o).get("uuid");
//					if (uuid == null || uuid.equals("")) {
//						((DTDMap)o).put("uuid", KeyGenerator.get32UUID());
//					}
//
//					((DTDMap)o).put("createTime", businessTime);
//					((DTDMap)o).put("updateTime", businessTime);
//					((DTDMap)o).put("delFlag", "0");
//					((DTDMap)o).put("truncNo", 1);
//				} else {
//					uuid = BeanUtils.getProperty(o, "uuid");
//					if (uuid == null || uuid.equals("")) {
//						BeanUtils.setProperty(o, "uuid", KeyGenerator.get32UUID());
//					}
//
//					BeanUtils.setProperty(o, "createTime", businessTime);
//					BeanUtils.setProperty(o, "updateTime", businessTime);
//					BeanUtils.setProperty(o, "delFlag", "0");
//					BeanUtils.setProperty(o, "truncNo", 1);
//				}
//			}
//
//		} catch (Exception var6) {
//			var6.printStackTrace();
//			throw new DataAccessException("保存数据赋默认值失败，错误信息：" + var6.getMessage());
//		}
//	}
//
//	private String getBusinessTime() {
//		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
//		return LocalDateTime.now().format(df);
//	}
//
//	protected void prepareObjectForSave(Map map) {
//		try {
//			if (map.get("uuid") == null || "".equals(map.get("uuid"))) {
//				map.put("uuid", KeyGenerator.get32UUID());
//			}
//
//			String businessTime = this.getBusinessTime();
//			map.put("updateTime", businessTime);
//			map.put("truncNo", 1);
//			map.put("createTime", businessTime);
//			map.put("delFlag", "0");
//			map.put("truncNo", 1);
//		} catch (Exception var3) {
//			var3.printStackTrace();
//			throw new DataAccessException("保存数据赋默认值失败，错误信息：" + var3.getMessage());
//		}
//	}
//
//	protected void prepareObjectForUpdate(Object o) {
//		try {
//			String businessTime = this.getBusinessTime();
//			if (o instanceof List) {
//				Iterator var3 = ((List)o).iterator();
//
//				while(var3.hasNext()) {
//					E entity = var3.next();
//					if (entity instanceof Map) {
//						((DTDMap)entity).put("updateTime", businessTime);
//					} else {
//						BeanUtils.setProperty(entity, "updateTime", businessTime);
//					}
//				}
//			} else if (o instanceof Map) {
//				((DTDMap)o).put("updateTime", businessTime);
//			} else {
//				BeanUtils.setProperty(o, "updateTime", businessTime);
//			}
//
//		} catch (Exception var5) {
//			var5.printStackTrace();
//			throw new DataAccessException("保存数据赋默认值失败，错误信息：" + var5.getMessage());
//		}
//	}
//
//	protected void prepareObjectForDeleteLogic(E o) {
//		try {
//			String businessTime = this.getBusinessTime();
//			if (o instanceof Map) {
//				((Map)o).put("delFlag", "1");
//				((Map)o).put("updateTime", businessTime);
//			} else {
//				BeanUtils.setProperty(o, "delFlag", "1");
//				BeanUtils.setProperty(o, "updateTime", businessTime);
//			}
//
//		} catch (Exception var3) {
//			var3.printStackTrace();
//			throw new DataAccessException("保存数据赋默认值失败，错误信息：" + var3.getMessage());
//		}
//	}
//
//	protected void prepareObjectForUpdate(Map map) {
//		try {
//			String businessTime = this.getBusinessTime();
//			map.put("updateTime", businessTime);
//		} catch (Exception var3) {
//			var3.printStackTrace();
//			throw new DataAccessException("保存数据赋默认值失败，错误信息：" + var3.getMessage());
//		}
//	}
//
//	protected void saveDelLog(String str, String obj, int rint) throws Exception {
//		UserDetails userDetails = null;
//		PageData pageData = new PageData();
//
//		try {
//			MappedStatement ms = this.sqlSessionTemplate2.getSqlSessionFactory().getConfiguration().getMappedStatement(str);
//			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			pageData.put("UUID", KeyGenerator.get32UUID());
//			pageData.put("TRAN_SQL", ms.getSqlSource().getBoundSql(str).getSql());
//			pageData.put("TRANS_DATA", obj);
//			userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			Map user = this.redisDaoImpl.getMap("Login-" + userDetails.getUsername());
//			if (user != null && !user.isEmpty()) {
//				Map logorg = (Map)user.get("loginOrg");
//				String busiDate = this.getBusiDate((String)logorg.get("legalOrgCode"));
//				pageData.put("BANK_CODE", (String)logorg.get("legalOrgCode"));
//				pageData.put("ORG_CODE", (String)logorg.get("orgCode"));
//				pageData.put("TRUNC_NO", logorg.get("truncNo"));
//				pageData.put("DATA_CHAN", (String)logorg.get("dataChan"));
//				pageData.put("USER_CODE", (String)user.get("userCode"));
//				pageData.put("DEL_FLAG", "0");
//				pageData.put("TRANS_TIME", busiDate);
//				pageData.put("CREATE_TIME", busiDate);
//				pageData.put("UPDATE_TIME", busiDate);
//			}
//
//			this.save("TbPubDelLogMapper.save", pageData);
//		} catch (Exception var11) {
//			logger.error("记录物理删除记录失败，原因：" + var11.getMessage(), new Object[0]);
//		}
//
//	}
//
//	protected String getBusiDate(String bankCode) throws Exception {
//		String busiDate = "";
//		PageData buDate = (PageData)this.redisDaoImpl.getObject("bussiDate-" + bankCode);
//		if (buDate != null && !buDate.isEmpty()) {
//			busiDate = (String)buDate.get("dataDate");
//		} else {
//			busiDate = this.getBusinessTime().substring(0, 8);
//		}
//
//		return busiDate;
//	}
//}

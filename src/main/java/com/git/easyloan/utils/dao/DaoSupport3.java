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
//import java.util.HashMap;
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
//import javacommon.exception.BussinessException;
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
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@Repository("daoSupport")
//public class DaoSupport<E, PK extends Serializable> implements DAO<E, PK> {
//	protected static Log logger = LogFactory.get();
//	@Autowired
//	@Resource(
//			name = "masterSqlSessionTemplate"
//	)
//	private SqlSessionTemplate sqlSessionTemplate;
//	@Autowired
//	@Resource(
//			name = "redisDaoImpl"
//	)
//	private RedisDaoImpl redisDaoImpl;
//	ObjectMapper objectMapper = new ObjectMapper();
//
//	public DaoSupport() {
//	}
//
//	@ExceptionHandler({Exception.class})
//	public Object errorHandler(Exception e) throws Exception {
//		logger.error(e);
//		throw new Exception("数据库操作失败");
//	}
//
//	public Object save(String str, E obj) throws Exception {
//		int rint = false;
//		MappedStatement ms = this.sqlSessionTemplate.getSqlSessionFactory().getConfiguration().getMappedStatement(str);
//		if (!this.checkWhiteList(obj, ms)) {
//			this.prepareObjectForSave(obj);
//		}
//
//		int rint;
//		try {
//			rint = this.sqlSessionTemplate.insert(str, obj);
//		} catch (Exception var6) {
//			logger.error(var6);
//			throw new Exception("数据库操作异常！");
//		}
//
//		return rint;
//	}
//
//	public Object batchSave(String str, List<E> objs) throws Exception {
//		SqlSessionFactory sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();
//		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
//		MappedStatement ms = this.sqlSessionTemplate.getSqlSessionFactory().getConfiguration().getMappedStatement(str);
//		if (!this.checkWhiteList(objs, ms)) {
//			this.prepareObjectForSave((Object)objs);
//		}
//
//		try {
//			if (objs != null) {
//				Iterator var6 = objs.iterator();
//
//				while(var6.hasNext()) {
//					E entity = var6.next();
//					sqlSession.insert(str, entity);
//				}
//
//				sqlSession.flushStatements();
//				sqlSession.commit();
//				sqlSession.clearCache();
//			}
//		} catch (Exception var11) {
//			sqlSession.rollback();
//			logger.error(var11);
//			throw new Exception("数据库操作异常！");
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
//		int rint = false;
//		this.prepareObjectForUpdate(obj);
//
//		int rint;
//		try {
//			rint = this.sqlSessionTemplate.update(str, obj);
//			if (rint == 0) {
//				throw new Exception("当前操作修改的数据未找到，请联系管理员或退出当前界面，重新操作！");
//			}
//		} catch (Exception var5) {
//			logger.error(var5);
//			throw new Exception("数据库操作异常！");
//		}
//
//		return rint;
//	}
//
//	public Object deleteLogic(String str, E obj, String checkflag) throws Exception {
//		int rint = 0;
//
//		try {
//			if (checkflag != null && !"".equals(checkflag)) {
//				if ("true".equals(checkflag)) {
//					this.prepareObjectForDeleteLogic(obj);
//					rint = this.sqlSessionTemplate.update(str, obj);
//				} else {
//					rint = (Integer)this.deleteLogic(str, obj);
//				}
//			}
//		} catch (Exception var6) {
//			logger.error(var6);
//			throw new Exception("数据库操作异常！");
//		}
//
//		return rint;
//	}
//
//	public Object deleteLogic(String str, E obj) throws Exception {
//		int rint = false;
//		this.prepareObjectForDeleteLogic(obj);
//
//		int rint;
//		try {
//			rint = this.sqlSessionTemplate.update(str, obj);
//			if (rint <= 0) {
//				throw new Exception("当前操作删除的数据未找到，请联系管理员或退出当前界面，重新操作！");
//			}
//		} catch (Exception var5) {
//			logger.error(var5);
//			throw new Exception("数据库操作异常！");
//		}
//
//		return rint;
//	}
//
//	public void batchUpdate(String str, List<E> objs) throws Exception {
//		this.prepareObjectForUpdate((Object)objs);
//		SqlSessionFactory sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();
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
//			logger.error(var10);
//			throw new Exception("数据库操作异常！");
//		} finally {
//			if (sqlSession != null) {
//				sqlSession.close();
//			}
//
//		}
//
//	}
//
//	public Object batchDelete(String str, List<E> objs) throws Exception {
//		boolean var3 = false;
//
//		int rint;
//		try {
//			rint = this.sqlSessionTemplate.delete(str, objs);
//			if (rint <= 0) {
//				throw new Exception("当前操作删除的数据未找到，请联系管理员或退出当前界面，重新操作！");
//			}
//
//			this.saveDelLog(str, this.objectMapper.writeValueAsString(objs), rint);
//		} catch (Exception var5) {
//			logger.error(var5);
//			throw new Exception("数据库操作异常！");
//		}
//
//		return rint;
//	}
//
//	public Object delete(String str, E obj) throws Exception {
//		boolean var3 = false;
//
//		int rint;
//		try {
//			rint = this.sqlSessionTemplate.delete(str, obj);
//			if (rint <= 0) {
//				throw new Exception("当前操作删除的数据未找到，请联系管理员或退出当前界面，重新操作！");
//			}
//
//			this.saveDelLog(str, this.objectMapper.writeValueAsString(obj), rint);
//		} catch (Exception var5) {
//			logger.error(var5);
//			throw new Exception("数据库操作异常！");
//		}
//
//		return rint;
//	}
//
//	public Object delete(String str, E obj, String checkflag) throws Exception {
//		int rint = 0;
//		if (checkflag != null && !"".equals(checkflag)) {
//			if ("true".equals(checkflag)) {
//				this.sqlSessionTemplate.delete(str, obj);
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
//		Object robj = null;
//
//		try {
//			robj = this.sqlSessionTemplate.selectOne(str, obj);
//			if (robj == null) {
//				return robj;
//			} else if (!(robj instanceof PageData)) {
//				return robj;
//			} else {
//				PageData pdn = new PageData();
//				Iterator i = ((PageData)robj).entrySet().iterator();
//				if (!i.hasNext()) {
//					return null;
//				} else {
//					while(i.hasNext()) {
//						Entry e = (Entry)i.next();
//						String oldName = (String)e.getKey();
//						String newName = dashedToCamel(oldName);
//						pdn.put(newName, e.getValue());
//					}
//
//					return pdn;
//				}
//			}
//		} catch (Exception var9) {
//			logger.error(var9);
//			throw new Exception("数据库操作异常！");
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
//		List pageList = null;
//
//		try {
//			pageList = this.sqlSessionTemplate.selectList(str, obj);
//
//			for(int x = 0; x < pageList.size(); ++x) {
//				Object robj = pageList.get(x);
//				if (robj instanceof PageData) {
//					Iterator i = ((PageData)robj).entrySet().iterator();
//					if (!i.hasNext()) {
//						return null;
//					}
//
//					PageData pdn = new PageData();
//
//					while(i.hasNext()) {
//						Entry e = (Entry)i.next();
//						String oldName = (String)e.getKey();
//						String newName = dashedToCamel(oldName);
//						pdn.put(newName, e.getValue());
//					}
//
//					pageList.set(x, pdn);
//				}
//			}
//
//			return pageList;
//		} catch (Exception var11) {
//			logger.error(var11);
//			throw new Exception("数据库操作异常！");
//		}
//	}
//
//	public Object findForMap(String str, E obj, String key, String value) throws Exception {
//		Map robj = null;
//
//		try {
//			robj = this.sqlSessionTemplate.selectMap(str, obj, key);
//			return robj;
//		} catch (Exception var7) {
//			logger.error(var7);
//			throw new Exception("数据库操作异常！");
//		}
//	}
//
//	protected void prepareObjectForSave(Object o) {
//		try {
//			String businessTime = this.getBusinessTime();
//			String bankCode = "";
//			String creatime = "";
//			String uuid;
//			if (o instanceof List) {
//				Iterator var13 = ((List)o).iterator();
//
//				while(true) {
//					while(var13.hasNext()) {
//						E entity = var13.next();
//						Map map;
//						String bankCodes;
//						String ncreatime;
//						if (entity instanceof Map) {
//							uuid = (String)((DTDMap)entity).get("uuid");
//							bankCodes = (String)((DTDMap)entity).get("bankCode");
//							if (bankCodes != null && !bankCodes.equals("")) {
//								bankCode = bankCodes;
//							} else {
//								map = (Map)((DTDMap)entity).get("loginUserFromWorkflow");
//								if (map != null) {
//									bankCode = (String)map.get("bankCode");
//								}
//
//								if (bankCode == null || "".equals(bankCode)) {
//									Map map = this.getBankCodeAndData();
//									bankCode = (String)map.get("bankCode");
//								}
//
//								if (bankCode == null || "".equals(bankCode)) {
//									throw new BussinessException("法人机构号bankCode为空，请检查操作项！");
//								}
//							}
//
//							creatime = this.getBusiDate(bankCode);
//							((DTDMap)entity).put("bankCode", bankCode);
//							if (uuid == null || uuid.equals("")) {
//								((DTDMap)entity).put("uuid", KeyGenerator.get32UUID());
//							}
//
//							ncreatime = creatime + businessTime.substring(8);
//							((DTDMap)entity).put("createTime", ncreatime);
//							((DTDMap)entity).put("updateTime", ncreatime);
//							((DTDMap)entity).put("delFlag", "0");
//							((DTDMap)entity).put("truncNo", 1);
//						} else {
//							uuid = BeanUtils.getProperty(entity, "uuid");
//							bankCodes = BeanUtils.getProperty(entity, "bankCode");
//							if (uuid == null || uuid.equals("")) {
//								BeanUtils.setProperty(entity, "uuid", KeyGenerator.get32UUID());
//							}
//
//							if (bankCodes != null && !bankCodes.equals("")) {
//								bankCode = bankCodes;
//							} else {
//								map = this.getBankCodeAndData();
//								bankCode = (String)map.get("bankCode");
//							}
//
//							creatime = this.getBusiDate(bankCode);
//							ncreatime = creatime + businessTime.substring(8);
//							BeanUtils.setProperty(entity, "createTime", ncreatime);
//							BeanUtils.setProperty(entity, "updateTime", ncreatime);
//							BeanUtils.setProperty(entity, "delFlag", "0");
//							BeanUtils.setProperty(entity, "truncNo", 1);
//							BeanUtils.setProperty(entity, "bankCode", bankCode);
//						}
//					}
//
//					return;
//				}
//			} else {
//				String uuid;
//				String bankCodes;
//				Map logmap;
//				if (!(o instanceof Map)) {
//					uuid = BeanUtils.getProperty(o, "uuid");
//					bankCodes = BeanUtils.getProperty(o, "bankCode");
//					if (bankCodes != null && !bankCodes.equals("")) {
//						bankCode = bankCodes;
//					} else {
//						logmap = this.getBankCodeAndData();
//						bankCode = (String)logmap.get("bankCode");
//					}
//
//					if (uuid == null || uuid.equals("")) {
//						BeanUtils.setProperty(o, "uuid", KeyGenerator.get32UUID());
//					}
//
//					creatime = this.getBusiDate(bankCode);
//					uuid = creatime + businessTime.substring(8);
//					BeanUtils.setProperty(o, "createTime", uuid);
//					BeanUtils.setProperty(o, "updateTime", uuid);
//					BeanUtils.setProperty(o, "delFlag", "0");
//					BeanUtils.setProperty(o, "truncNo", 1);
//					BeanUtils.setProperty(o, "bankCode", bankCode);
//				} else {
//					uuid = (String)((DTDMap)o).get("uuid");
//					bankCodes = (String)((DTDMap)o).get("bankCode");
//					if (bankCodes != null && !bankCodes.equals("")) {
//						bankCode = bankCodes;
//					} else {
//						logmap = (Map)((DTDMap)o).get("loginUserFromWorkflow");
//						if (logmap != null) {
//							bankCode = (String)logmap.get("bankCode");
//						}
//
//						if (bankCode == null || "".equals(bankCode)) {
//							Map map = this.getBankCodeAndData();
//							bankCode = (String)map.get("bankCode");
//						}
//
//						if (bankCode == null || "".equals(bankCode)) {
//							throw new BussinessException("法人机构号bankCode为空，请检查操作项！");
//						}
//					}
//
//					((DTDMap)o).put("bankCode", bankCode);
//					creatime = this.getBusiDate(bankCode);
//					uuid = creatime + businessTime.substring(8);
//					if (uuid == null || uuid.equals("")) {
//						((DTDMap)o).put("uuid", KeyGenerator.get32UUID());
//					}
//
//					((DTDMap)o).put("createTime", uuid);
//					((DTDMap)o).put("updateTime", uuid);
//					((DTDMap)o).put("delFlag", "0");
//					((DTDMap)o).put("truncNo", 1);
//				}
//			}
//
//		} catch (BussinessException var11) {
//			throw new DataAccessException(var11.getMessage());
//		} catch (Exception var12) {
//			var12.printStackTrace();
//			throw new DataAccessException("保存数据赋默认值失败，错误信息：" + var12.getMessage());
//		}
//	}
//
//	private String getBusinessTime() {
//		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
//		return LocalDateTime.now().format(df);
//	}
//
//	protected void prepareObjectForSave(Map map) {
//		String bankCode = "";
//		String creatime = "";
//
//		try {
//			if (map.get("uuid") == null || "".equals(map.get("uuid"))) {
//				map.put("uuid", KeyGenerator.get32UUID());
//			}
//
//			String businessTime = this.getBusinessTime();
//			String bankCodes = (String)map.get("bankCode");
//			if (bankCodes != null && !bankCodes.equals("")) {
//				bankCode = bankCodes;
//			} else {
//				Map logmap = (Map)map.get("loginUserFromWorkflow");
//				if (logmap != null) {
//					bankCode = (String)logmap.get("bankCode");
//				}
//
//				if (bankCode == null || "".equals(bankCode)) {
//					Map maps = this.getBankCodeAndData();
//					bankCode = (String)maps.get("bankCode");
//				}
//
//				if (bankCode == null || "".equals(bankCode)) {
//					throw new BussinessException("法人机构号bankCode为空，请检查操作项！");
//				}
//			}
//
//			creatime = this.getBusiDate(bankCode);
//			String ncreatime = creatime + businessTime.substring(8);
//			map.put("updateTime", ncreatime);
//			map.put("truncNo", 1);
//			map.put("createTime", ncreatime);
//			map.put("delFlag", "0");
//			map.put("bankCode", bankCode);
//		} catch (BussinessException var8) {
//			throw new DataAccessException(var8.getMessage());
//		} catch (Exception var9) {
//			var9.printStackTrace();
//			throw new DataAccessException("保存数据赋默认值失败，错误信息：" + var9.getMessage());
//		}
//	}
//
//	protected void prepareObjectForUpdate(Object o) {
//		try {
//			String businessTime = this.getBusinessTime();
//			String bankCode = "";
//			String creatime = "";
//			if (o instanceof List) {
//				Iterator var5 = ((List)o).iterator();
//
//				while(true) {
//					while(var5.hasNext()) {
//						E entity = var5.next();
//						String bankCodes;
//						Map map;
//						String ncreatime;
//						if (entity instanceof Map) {
//							bankCodes = (String)((DTDMap)entity).get("bankCode");
//							if (bankCodes != null && !bankCodes.equals("")) {
//								bankCode = bankCodes;
//							} else {
//								map = this.getBankCodeAndData();
//								bankCode = (String)map.get("bankCode");
//							}
//
//							creatime = this.getBusiDate(bankCode);
//							ncreatime = creatime + businessTime.substring(8);
//							((DTDMap)entity).put("updateTime", ncreatime);
//						} else {
//							bankCodes = BeanUtils.getProperty(o, "bankCode");
//							if (bankCodes != null && !bankCodes.equals("")) {
//								bankCode = bankCodes;
//							} else {
//								map = this.getBankCodeAndData();
//								bankCode = (String)map.get("bankCode");
//							}
//
//							creatime = this.getBusiDate(bankCode);
//							ncreatime = creatime + businessTime.substring(8);
//							BeanUtils.setProperty(entity, "updateTime", ncreatime);
//						}
//					}
//
//					return;
//				}
//			} else {
//				String bankCodes;
//				Map maps;
//				String ncreatime;
//				if (o instanceof Map) {
//					bankCodes = (String)((DTDMap)o).get("bankCode");
//					if (bankCodes != null && !bankCodes.equals("")) {
//						bankCode = bankCodes;
//					} else {
//						maps = this.getBankCodeAndData();
//						bankCode = (String)maps.get("bankCode");
//					}
//
//					creatime = this.getBusiDate(bankCode);
//					ncreatime = creatime + businessTime.substring(8);
//					((DTDMap)o).put("updateTime", ncreatime);
//				} else {
//					bankCodes = BeanUtils.getProperty(o, "bankCode");
//					if (bankCodes != null && !bankCodes.equals("")) {
//						bankCode = bankCodes;
//					} else {
//						maps = this.getBankCodeAndData();
//						bankCode = (String)maps.get("bankCode");
//					}
//
//					creatime = this.getBusiDate(bankCode);
//					ncreatime = creatime + businessTime.substring(8);
//					BeanUtils.setProperty(o, "updateTime", ncreatime);
//				}
//			}
//
//		} catch (BussinessException var9) {
//			throw new DataAccessException(var9.getMessage());
//		} catch (Exception var10) {
//			var10.printStackTrace();
//			throw new DataAccessException("保存数据赋默认值失败，错误信息：" + var10.getMessage());
//		}
//	}
//
//	protected void prepareObjectForDeleteLogic(E o) {
//		try {
//			String businessTime = this.getBusinessTime();
//			Map maps = this.getBankCodeAndData();
//			String creatime = (String)maps.get("createTime");
//			String ncreatime = creatime + businessTime.substring(8);
//			if (o instanceof Map) {
//				((Map)o).put("delFlag", "1");
//				((Map)o).put("updateTime", ncreatime);
//			} else {
//				BeanUtils.setProperty(o, "delFlag", "1");
//				BeanUtils.setProperty(o, "updateTime", ncreatime);
//			}
//
//		} catch (Exception var6) {
//			var6.printStackTrace();
//			throw new DataAccessException("保存数据赋默认值失败，错误信息：" + var6.getMessage());
//		}
//	}
//
//	protected void prepareObjectForUpdate(Map map) {
//		try {
//			String businessTime = this.getBusinessTime();
//			String bankCode = "";
//			String creatime = "";
//			String bankCodes = (String)map.get("bankCode");
//			if (bankCodes != null && !bankCodes.equals("")) {
//				bankCode = bankCodes;
//			} else {
//				Map maps = this.getBankCodeAndData();
//				bankCode = (String)maps.get("bankCode");
//			}
//
//			creatime = this.getBusiDate(bankCode);
//			String ncreatime = creatime + businessTime.substring(8);
//			map.put("updateTime", ncreatime);
//		} catch (BussinessException var7) {
//			throw new BussinessException(var7.getMessage());
//		} catch (Exception var8) {
//			var8.printStackTrace();
//			throw new DataAccessException("保存数据赋默认值失败，错误信息：" + var8.getMessage());
//		}
//	}
//
//	protected Map getBankCodeAndData() {
//		Map codeData = new HashMap();
//		UserDetails userDetails = null;
//
//		try {
//			userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			Map user = this.redisDaoImpl.getMap("Login-" + userDetails.getUsername());
//			if (user != null && !user.isEmpty()) {
//				Map logorg = (Map)user.get("loginOrg");
//				String bankCode = (String)logorg.get("legalOrgCode");
//				codeData.put("bankCode", bankCode);
//				codeData.put("createTime", user.get("bussiDate"));
//			} else {
//				logger.error("获取用户法人机构失败，原因：redis未找到用户登陆信息！", new Object[0]);
//				codeData.put("bankCode", "");
//			}
//		} catch (Exception var6) {
//			logger.error("获取用户法人机构失败，原因：" + var6.getMessage(), new Object[0]);
//			codeData.put("bankCode", "");
//		}
//
//		return codeData;
//	}
//
//	protected String getBusiDate(String bankCode) throws Exception {
//		String busiDate = "";
//		Map buDate = (Map)this.redisDaoImpl.getObject("bussiDate-01121");
//		if (buDate != null && !buDate.isEmpty()) {
//			busiDate = (String)buDate.get("dataDate");
//			return busiDate;
//		} else {
//			throw new Exception("获取用户法人营业日期失败，原因：redis未找到机构：01121 的信息，请联系管理员配置！");
//		}
//	}
//
//	protected void saveDelLog(String str, String obj, int rint) throws Exception {
//		UserDetails userDetails = null;
//		PageData pageData = new PageData();
//
//		try {
//			MappedStatement ms = this.sqlSessionTemplate.getSqlSessionFactory().getConfiguration().getMappedStatement(str);
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
//	boolean checkWhiteList(Object o, MappedStatement mappedStatement) {
//		Boolean isallow = null;
//
//		try {
//			isallow = false;
//			Map map = this.redisDaoImpl.getMap("WriteList");
//			Iterator var5 = map.keySet().iterator();
//
//			while(var5.hasNext()) {
//				Object str = var5.next();
//				if (mappedStatement.getBoundSql(o).getSql().toUpperCase().contains(str.toString().toUpperCase())) {
//					isallow = true;
//					break;
//				}
//			}
//		} catch (Exception var7) {
//			return false;
//		}
//
//		return isallow;
//	}
//
//	public static void main(String[] args) {
//		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
//		System.out.println(LocalDateTime.now().format(df));
//	}
//}

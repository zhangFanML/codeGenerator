package com.git.easyloan.controller;

import com.git.easyloan.application.Cgenerator.CreateCodeService;
import com.git.easyloan.entity.Const;
import com.git.easyloan.entity.Error;
import com.git.easyloan.entity.PageData;
import com.git.easyloan.utils.base.BaseController;
import com.git.easyloan.utils.db.Column;
import com.git.easyloan.utils.db.Db;
import com.git.easyloan.utils.db.Table;
import com.git.easyloan.utils.utils.*;
import com.git.easyloan.utils.utils.DBFactory.MyTableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;


/** 
 * 类名称： 反向生成
 * 创建人：
 * 修改时间：2016年4月15日
 * @version
 */
@Controller
@RequestMapping(value="/recreateCode")
public class ReverseCreateCodeController extends BaseController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name="createCodeService")
	private CreateCodeService createCodeService;
	
	/**列表
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView list() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("pd", pd);
		mv.setViewName("generator/recreateCode_list");
		return mv;
	}
	
	 /**列出所有表
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/listAllTable")
	@ResponseBody
	public Object listAllTable(){
		PageData pd = this.getPageData();
		Map<String,Object> map = new HashMap<>();
		List<PageData> pdList = new ArrayList<>();
		List<String> tblist = new ArrayList<>();
		try {
			Object[] arrOb = Db.getTables(pd);
			tblist = (List<String>)arrOb[1];
			pd.put("msg", "ok");
		} catch (ClassNotFoundException e) {
			pd.put("msg", e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			pd.put("msg", e.getMessage());
			e.printStackTrace();
		}
		pdList.add(pd);
		map.put("tblist", tblist);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**去代码生成器页面(进入弹窗)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goProductCode")
	public ModelAndView goProductCode() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String info = "&dbtype="+pd.getString("dbtype")+"&dbAddress="+pd.getString("dbAddress")+"&dbport="+pd.getString("dbport")+
				"&username="+pd.getString("username")+"&password="+pd.getString("password")+"&databaseName="+pd.getString("databaseName");
		String fieldType;
		StringBuffer sb = new StringBuffer();
		Connection conn = Db.getCon(pd);
		MyTableFactory tableFactory;
		String[] tblists = pd.getString("tblists").split(",");
		if(Const.DBTYPE_ORACLE.equals(pd.getString("dbType")) || Const.DBTYPE_DB2.equals(pd.getString("dbTpye"))){
			String schema = pd.getString("username").toUpperCase();
			tableFactory =  new MyTableFactory(conn,schema);
		} else {
			tableFactory = MyTableFactory.getInstance(conn);
		}
		boolean sw = false;  //判断前后端
		if(pd.get("flag") != null && !pd.getString("flag").equals("") && !pd.getString("flag").equals("full")){
			if(pd.getString("flag").equals("front")){
				sw = true;
			}else if(pd.getString("flag").equals("backend")){
				sw = false;
			}
		}else{
			sw = false;
		}

		Table table = tableFactory.getTable(pd.getString("table"));
		LinkedHashSet<Column> columns = table.getColumns();
		for (Column column : columns) {
			log.debug("the column name is :"+ column.getConstantName());
			//字段名称
			if(sw){
				sb.append(CreateCodeController.dashedToCamel(column.toString()));
			}else{
				sb.append(column);
			}
			sb.append("|@|");
			//字段类型
			fieldType = column.getJdbcType().toLowerCase();
			if(sw){
				if(fieldType.contains("int") || fieldType.contains("number") || fieldType.contains("long") || fieldType.contains("double") || fieldType.contains("numeric") || fieldType.contains("decimal")){
					sb.append("Number");
				}else if(fieldType.contains("date")){
					sb.append("Date");
				}else if(fieldType.contains("time")){
					sb.append("Time");
				}else{
					sb.append("Text");
				}
			}else{
				if(fieldType.contains("int") || fieldType.contains("number") || fieldType.contains("long")){
					sb.append("Integer");
				}else if(fieldType.contains("double") || fieldType.contains("numeric") || fieldType.contains("decimal")){
					sb.append("Double");
				}else if(fieldType.contains("date")||fieldType.contains("time")){
					sb.append("Date");
				}else{
					sb.append("String");
				}
			}
			sb.append("|@|");
			//前台名称
			sb.append(column.getColumnAlias());
			sb.append("|@|");
			//是否前台录入s
			sb.append("是");
			sb.append("|@|");
			//默认值
			sb.append("无");
			sb.append("|@|");
			//长度
			sb.append(column.getSize());
			sb.append("|@|");
			//小数点右边的位数
			sb.append(column.getDecimalDigits());
			sb.append("|@|");
			//是否必输
			sb.append(column.isNullable() == true ? "否":"是");
			sb.append("|@|");
			//字典编码,反向生成时无法识别字典代码,需要手工修改
			sb.append("");
			sb.append("|@|");
			//是否列表显示
			sb.append("是");
			sb.append("|@|");
			//表名
			sb.append(pd.getString("table"));
			sb.append("|@|");
			//是否查询
			sb.append("否");
			sb.append("|@|");
			//是否模糊查询
			sb.append("否");
			sb.append("|@@|");
		}
		StringBuffer mappers = new StringBuffer();
		String[] mapper = pd.getString("table").split("_");
		for(int ii = 0; ii < mapper.length; ii++){
			if(mapper[ii].length() > 1){
				mappers.append(mapper[ii].substring(0,1) + mapper[ii].substring(1).toLowerCase());
			}else{
				mappers.append(mapper[ii]);
			}
		}
		pd.put("KEY", table.getPkColumns().size() == 0 ? "uuid" : table.getPkColumns().get(0));
		pd.put("OBJECTNAME", mappers);
		pd.put("TABLENAME", pd.getString("table"));
		pd.put("FIELDLIST", sb.toString());
		pd.put("BUSINESSLINE", "");
		pd.put("PACKAGENAME", "");
		pd.put("FHTYPE", "single");
		pd.put("TITLE",table.getTableAlias());
		mv.addObject("msg", "edit");
		mv.addObject("info", info);
		mv.addObject("pd", pd);
		List<PageData> varList = createCodeService.listFa(); //列出所有主表结构的
		mv.addObject("tblist", tblists);
		mv.addObject("varList", varList);
		mv.addObject("basePackageName","com.git.easyLoan");
		mv.setViewName("generator/all/forwardProductCode");
		if (!pd.getString("flag").equals("full")){
			if(sw){
				mv.setViewName("generator/front/forwardProductCode");
			}else{
				mv.setViewName("generator/backend/forwardProductCode");
			}
		}
		return mv;
	}


	/**批量生成mapper
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/batchProCode")
	public void proCode(HttpServletResponse response) throws Exception{
		log.info("执行代码生成器批量生成mapper");
		PageData pd = this.getPageData();
		List<String> tblist = new ArrayList<>();
		try {
			Object[] arrOb = Db.getTables(pd);
			tblist = (List<String>)arrOb[1];
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String dbType = "oracle";
		String fieldType;
		String filePath = "admin/ftl/code/";						//存放路径
		Connection conn = Db.getCon(pd);
		MyTableFactory tableFactory;
		if(Const.DBTYPE_ORACLE.equals(pd.getString("dbType")) || Const.DBTYPE_DB2.equals(pd.getString("dbTpye"))){
			String schema = pd.getString("username").toUpperCase();
			tableFactory = new MyTableFactory(conn,schema);
		} else {
			tableFactory = MyTableFactory.getInstance(conn);
		}
		DelAllFile.delFolder(PathUtil.getClassResources()+"admin/ftl"); //生成代码前,先清空之前生成的代码
		List PKColumns;
		for (String tableName:tblist) {
			Table table = tableFactory.getTable(tableName);
			PKColumns = table.getPkColumns();
			LinkedHashSet<Column> columns = table.getColumns();
			List<String[]> fieldList = new ArrayList<>();   					//属性集合
			for (Column column : columns) {
				String[] field = new String[13];
				log.debug("the column name is :"+ column.getConstantName());
				//字段名称
				field[0] = column.toString();
				//字段类型
				fieldType = column.getJdbcType().toLowerCase();
				if(fieldType.contains("int") || fieldType.contains("number") || fieldType.contains("long")){
					fieldType = "Integer";
				}else if(fieldType.contains("double") || fieldType.contains("numeric") || fieldType.contains("decimal")){
					fieldType = "Double";
				}else if(fieldType.contains("date")||fieldType.contains("time")){
					fieldType = "Date";
				}else{
					fieldType = "String";
				}
				field[1] = fieldType;
				//前台名称
				field[2] = column.getColumnAlias();
				//是否前台录入
				field[3] = "是";
				//默认值
				field[4] = "无";
				//长度
				field[5] = String.valueOf(column.getSize());
				//小数点右边的位数
				field[6] = String.valueOf(column.getDecimalDigits());
				//是否必输
				field[7] = (column.isNullable() == true ? "否":"是");
				//字典编码,反向生成时无法识别字典代码,需要手工修改
				field[8] = "";
				//是否列表显示
				field[9] = "是";
				//表名
				field[10] = tableName;
				//是否查询
				field[11] = "是";
				//是否模糊查询
				field[12] = "否";
				fieldList.add(field);
			}
			String mapper = dashedToCamel(tableName);
			Map<String,Object> root = new HashMap<>();		//创建数据模型
			String objectName = table.getClassName();
			String tabletop = tableName;
			root.put("PKColumns", PKColumns);
			root.put("objectName", objectName);
			root.put("fieldList", fieldList);
			root.put("tabletop", tableName);								//表名
			root.put("mappers",mapper);								//所有表对应的mapper
			assemblyFile(dbType, objectName, tabletop, root, filePath);//存放路径
		}
		/*生成的全部代码压缩成zip文件*/
		if(FileZip.zip(filePath, "admin/ftl/Mappers.zip")){
			/*下载代码*/
			FileDownload.fileDownload(response,"admin/ftl/Mappers.zip", "Mappers.zip");
		}
	}

	private void assemblyFile(String dbType, String objectName, String tabletop, Map<String, Object> root, String filePath) throws Exception {
		String ftlPath = "createCode";
		createMapper(dbType, objectName, tabletop, root, filePath, ftlPath);
	}

	private void createMapper(String dbType, String objectName, String tabletop, Map<String, Object> root, String filePath, String ftlPath) throws Exception {
		if(Const.DBTYPE_MYSQL.equals(dbType)){
			/*生成mybatis xml*/
			Freemarker.printFile("mapperMysqlTemplate.ftl", root, objectName+"Mapper.xml", filePath, ftlPath);
			/*生成SQL脚本*/
			Freemarker.printFile("mysql_SQL_Template.ftl", root, tabletop.toUpperCase()+".sql", filePath, ftlPath);
		}else if(Const.DBTYPE_ORACLE.equals(dbType)){
			/*生成mybatis xml*/
			Freemarker.printFile("mapperOracleTemplate.ftl", root, objectName+"Mapper.xml", filePath, ftlPath);
			/*生成SQL脚本*/
			//Freemarker.printFile("oracle_SQL_Template.ftl", root, tabletop.toUpperCase()+".sql", filePath, ftlPath);
		}else if(Const.DBTYPE_SQLSERVER.equals(dbType)){
			/*生成mybatis xml*/
			Freemarker.printFile("mapperSqlserverTemplate.ftl", root, objectName+"Mapper.xml", filePath, ftlPath);
			/*生成SQL脚本*/
			Freemarker.printFile("sqlserver_SQL_Template.ftl", root, tabletop.toUpperCase()+".sql", filePath, ftlPath);
		}else if(Const.DBTYPE_DB2.equals(dbType)){
			/*生成mybatis xml*/
			Freemarker.printFile("mapperDB2Template.ftl", root, objectName+"Mapper.xml", filePath, ftlPath);
			/*生成SQL脚本*/
			Freemarker.printFile("db2_SQL_Template.ftl", root, tabletop.toUpperCase()+".sql", filePath, ftlPath);
		}else{
			//数据库类型未知
			throw error(Error.ERROR_UNKNOWN);
		}
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


}

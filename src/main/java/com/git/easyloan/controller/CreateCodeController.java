package com.git.easyloan.controller;

import com.git.easyloan.application.Cgenerator.CreateCodeService;
import com.git.easyloan.entity.Const;
import com.git.easyloan.entity.Error;
import com.git.easyloan.entity.Page;
import com.git.easyloan.entity.PageData;
import com.git.easyloan.utils.base.BaseController;
import com.git.easyloan.utils.db.Column;
import com.git.easyloan.utils.db.Db;
import com.git.easyloan.utils.db.Table;
import com.git.easyloan.utils.utils.*;
import com.git.easyloan.utils.utils.DBFactory.MyTableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;


/**
 * 类名称： 代码生成器
 * 创建人：
 * 修改时间：2015年11月23日
 * @version
 */
@Controller
@RequestMapping(value="/createCode")
public class CreateCodeController extends BaseController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

//	@Resource(name="createCodeService")
	@Autowired
	private CreateCodeService createCodeService;

	@GetMapping
	@ResponseBody
	@RequestMapping("/testSql")
	public PageData returnTest() throws Exception {
		PageData pd = new PageData();
		pd.put("CREATECODE_ID","1");
		return createCodeService.findById(pd);
	}



	/**列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String keywords = pd.getString("keywords");	//检索条件
		if(null != keywords && !"".equals(keywords)){
			keywords = keywords.trim();
			pd.put("keywords", keywords);
		}
		page.setPd(pd);
		List<PageData>	varList = createCodeService.list(page);	//列出CreateCode列表
		mv.setViewName("generator/createCode_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		return mv;
	}

	/**获取(多)表字段
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getTableRow")
	@ResponseBody
	public StringBuffer getTableRow() throws Exception{
		PageData pd = this.getPageData();
		String tabletop = pd.getString("tabletop");
		String username;
		String dbtype;
		Connection conn;
		if(pd.get("dbtype") != null){
			username = pd.getString("username");
			dbtype = pd.getString("dbtype");
			conn = Db.getCon(pd);
		}else{
			InputStream inputStream = CreateCodeController.class.getClassLoader().getResourceAsStream("dbconfig-oracle.properties");
			Properties p = new Properties();
			p.load(inputStream);
			Class.forName(p.getProperty("driverClassName"));
			username = p.getProperty("username");
			conn = DriverManager.getConnection(p.getProperty("url"),username , p.getProperty("password"));
			dbtype = "oracle";
		}

		MyTableFactory tableFactory ;
		if(Const.DBTYPE_ORACLE.equals(dbtype) || Const.DBTYPE_DB2.equals(dbtype)){
			String schema = username.toUpperCase();
			tableFactory =  new MyTableFactory(conn,schema);
		} else {
			tableFactory = MyTableFactory.getInstance(conn);
		}
		StringBuffer sb = new StringBuffer();
		String[] tables = tabletop.split(",");
		String field;
		boolean sw = false;  //判断前后端字段类型

		if(pd.getString("flag").equals("front") && tables.length == 1){  //前端 单表    表名  字段名  字段类型
			sw = true;
		}else if(pd.getString("flag").equals("front") && tables.length > 1){  //前端 多表  表名  字段名  字段类型
			sw = true;
		}else if(pd.getString("flag").equals("backend") && tables.length == 1){  //后端 单表  表名  字段名  字段类型
			sw = false;
		}else if(pd.getString("flag").equals("backend") && tables.length > 1){  //后端 多表  表名  字段名  字段类型
			sw = false;
		}
		StringBuffer PKColumns = new StringBuffer();
		boolean key = false;
		for(String tableName : tables){
			Table table = tableFactory.getTable(tableName);
			if(table.getPkColumns().size() > 0 && !(table.getPkColumns().get(0).toString().equalsIgnoreCase("UUID"))){
				key = true;
			}
			PKColumns.append(table.getPkColumns().size() == 0 ? "uuid" : table.getClassNameFirstLower() + "." + table.getPkColumns().get(0).toString() + ";");
			LinkedHashSet<Column> columns = table.getColumns();
			String fieldType;
			for (Column column : columns) {
				log.debug("The column name is :"+ column.getConstantName());
				//字段名称
				if(pd.getString("flag").equals("front") && tables.length == 1){  //前端 单表    表名  字段名  字段类型
					field = dashedToCamel(column.toString());
				}else if(pd.getString("flag").equals("front") && tables.length > 1){  //前端 多表  表名  字段名  字段类型
					field = table.getClassNameFirstLower() + "-" + dashedToCamel(column.toString());
				}else if(pd.getString("flag").equals("backend") && tables.length == 1){  //后端 单表  表名  字段名  字段类型
					field = column.toString();
				}else if(pd.getString("flag").equals("backend") && tables.length > 1){  //后端 多表  表名  字段名  字段类型
					field = column.toString();
				}else{
					field = column.toString();
				}
				sb.append(field);
				sb.append("|@|");
				//字段类型  由前后端区分
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
				//是否前台录入
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
				if(sw){
					sb.append(table.getClassNameFirstLower());
				}else{
					sb.append(tableName);
				}
				sb.append("|@|");
				//是否查询
				sb.append("否");
				sb.append("|@|");
				//是否模糊查询
				sb.append("否");
				sb.append("|@@|");
			}
		}
		if(key){
			return sb.append(PKColumns);
		}else{
			return sb.append("UUID");
		}
	}
	/** 去代码生成器页面(进入弹窗)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goProductCode")
	public ModelAndView goProductCode() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageData pds;
		String CREATECODE_ID = pd.getString("CREATECODE_ID");
		if(!"add".equals(CREATECODE_ID)){
			pds = createCodeService.findById(pd);
			pds.put("FIELDLIST",pds.getString("FIELDLIST"));
			pds.put("flag",pd.getString("flag"));
			mv.addObject("pd", pds);
			mv.addObject("msg", "edit");

		}else{
			mv.addObject("msg", "add");
		}
//		List<PageData> varList = createCodeService.listFa(); //列出所有主表结构的

//		InputStream inputStream = CreateCodeController.class.getClassLoader().getResourceAsStream("dbconfig-oracle.properties");
//		Properties p = new Properties();
//		p.load(inputStream);
//		Class.forName(p.getProperty("driverClassName"));
//		String username = p.getProperty("username");
//		Connection conn = DriverManager.getConnection(p.getProperty("url"),username , p.getProperty("password"));
//		String dbtype = "oracle";
//		String databaseName = "xe";   //默认 xe
//		if ("oracle".equals(dbtype) || "db2".equals(dbtype)) {
//			databaseName = username.toUpperCase();
//		}
//		List<String> tblist;
//		Object[] arrOb = new Object[]{databaseName, Db.getTablesByCon(conn, "sqlserver".equals(dbtype) ? null : databaseName), dbtype};
//		tblist = (List<String>)arrOb[1];
//		mv.addObject("tblist", tblist);
		mv.setViewName("generator/all/forwardProductCode");
		if(pd.get("flag") != null && !pd.getString("flag").equals("")){
			if(pd.getString("flag").equals("front")){
				mv.setViewName("generator/front/forwardProductCode");
			}else if(pd.getString("flag").equals("backend")){
				mv.setViewName("generator/backend/forwardProductCode");
			}
		}
//		mv.addObject("varList", varList);
		mv.addObject("basePackageName","com.git.easyLoan");
		return mv;
	}

	/**去代码生成器页面(进入弹窗)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/productPositiveCode")
	public ModelAndView productPositiveCode() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String info = "&dbtype="+pd.getString("dbtype")+"&dbAddress="+pd.getString("dbAddress")+"&dbport="+pd.getString("dbport")+
				"&username="+pd.getString("username")+"&password="+pd.getString("password")+"&databaseName="+pd.getString("databaseName");
		String fieldType;
		Connection conn = Db.getCon(pd);
		MyTableFactory tableFactory;
		if(Const.DBTYPE_ORACLE.equals(pd.getString("dbType")) || Const.DBTYPE_DB2.equals(pd.getString("dbTpye"))){
			String schema = pd.getString("username").toUpperCase();
			tableFactory =  new MyTableFactory(conn,schema);
		} else {
			tableFactory = MyTableFactory.getInstance(conn);
		}
		boolean sw = false;  //判断前后端
		if(pd.get("flag") != null && !pd.getString("flag").equals("")){
			if(pd.getString("flag").equals("front")){
				sw = true;
			}else if(pd.getString("flag").equals("backend")){
				sw = false;
			}
		}else{
			sw = false;
		}

		List<String> tblists = tableFactory.getTableNames(pd.getString("username").toUpperCase());

		pd.put("BUSINESSLINE", "");
		pd.put("PACKAGENAME", "");
		mv.addObject("msg", "edit");
		mv.addObject("info", info);
		mv.addObject("pd", pd);
		mv.addObject("tblist", tblists);
		mv.addObject("basePackageName","com.git.easyLoan");
		mv.setViewName("generator/all/forwardProductCode");
		if(sw){
			mv.setViewName("generator/front/forwardProductCode");
		}else{
			mv.setViewName("generator/backend/forwardProductCode");
		}
		return mv;
	}


	/**生成代码
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/proCode")
	public void proCode(HttpServletResponse response) throws Exception{
		PageData pd = this.getPageData();

		String dbType;
		dbType= "oracle";
		String faobject = pd.getString("faobject");  				    //主表名			========参数0-1 主附结构用
		String FHTYPE = pd.getString("FHTYPE");  					    //模块类型			========参数0-2 类型，单表、树形结构、主表明细表
		String TITLE = pd.getString("TITLE");  						//说明				========参数0
		String[] KEY = (pd.get("KEY") != null &&  !pd.getString("KEY").equals("")) ?  pd.getString("KEY").split(";") : "UUID".split(";");  						//说明				========参数0
		//String basePackageName = "com.git.easyloan."+ConfigManager.getStringProperty("basePackageName")+".person";	//基础包名
		String belongSystem = pd.getString("belongSystem");  			//包名				========参数1
		Object businessLine = pd.get("businessLine");  			            //包名				========参数1
		String basePackageName = "com.git.easyloan."+belongSystem + businessLine;//基础包名
		String packageName = pd.getString("packageName");  			//所属模块		    ========参数1
		String objectName = pd.getString("objectName");	   			//类名				========参数2
		String zindext = pd.getString("zindex");	   	   			    //属性总数
		int zindex = 0;
		if(null != zindext && !"".equals(zindext)){
			zindex = Integer.parseInt(zindext);
		}
		List tables = new ArrayList();
		List mappers = new ArrayList();
		List<String[]> fieldList = new ArrayList<>();   					//属性集合			========参数4
		String tabletop = "";
		for(int i=0; i< zindex; i++){
			String[] field = pd.getString("field"+i).split("\\|@\\|");
			if(tables == null || !tables.contains(field[10])){
				tables.add(field[10]);
				tabletop += field[10] + ",";
				String sb = dashedToCamelToUpper(field[10]);
				mappers.add(sb);
			}
			fieldList.add(field);	//属性放到集合里面
		}
		tabletop = tabletop.substring(0,tabletop.length()-1);
		pd.put("tabletop",tabletop);
		save(pd);	//将本次生成信息保存到数据库
		Map<String,Object> root = new HashMap<>();		//创建数据模型
		root.put("fieldList", fieldList);
		root.put("faobject", faobject.toUpperCase());				//主附结构用，主表名
		root.put("TITLE", TITLE);									//说明
		root.put("PKColumns", KEY);									//说明
		root.put("belongSystem", belongSystem);
		root.put("basePackageName", basePackageName);
		root.put("packageName", packageName);						//包名
		root.put("objectName", objectName);							//类名
		root.put("objectNameLower", objectName.substring(0,1).toLowerCase() + objectName.substring(1));		//类名(首字母小写)
		root.put("objectNameUpper", objectName.toUpperCase());		//类名(全大写)
		root.put("tabletop", tabletop);								//表前缀
		root.put("nowDate", new Date());							//当前日期
		root.put("tables",tables);									//所有表
		root.put("mappers",mappers);								//所有表对应的mapper
		if(tables.size() > 1){
			root.put("tableMore",true);							    //是否多表
			root.put("whereis",pd.get("whereis")!= null ? pd.getString("whereis").split(";") : "");		//多表关联关系
		}else{
			root.put("tableMore",false);                            //是否多表
		}
        String filePath = "admin/ftl/code/";						//存放路径
		String dir;
		String flag;
        if(pd.get("flag") != null && !pd.getString("flag").equals("") && pd.getString("flag").equals("front")){
            flag = "front";
            dir = "frontCode";
        }else if(pd.get("flag") != null && !pd.getString("flag").equals("") && pd.getString("flag").equals("backend")){
            flag = "backend";
            dir = "BackCode";
        }else{
            flag = "full";
            dir = "fullCode";
        }
		String filePh1 = filePath + dir + "/" + objectName + "_Code/";
        assemblyFile(flag,dbType, FHTYPE, packageName, objectName, tables, tabletop, root, filePh1);//存放路径
        //this.print("oracle_SQL_Template.ftl", root);  控制台打印
		/*生成的全部代码压缩成zip文件*/
		String filePh2 = filePath + dir + "/";
		if(FileZip.zip(filePh2, "admin/ftl/"+dir+".zip")){
			/*下载代码*/
			FileDownload.fileDownload(response, "admin/ftl/"+dir+".zip", dir+".zip");
		}
	}

    private void assemblyFile(String flag,String dbType, String FHTYPE, String packageName, String objectName, List tables, String tabletop, Map<String, Object> root, String filePath) throws Exception {
        DelAllFile.delFolder("admin/ftl"); //生成代码前,先清空之前生成的代码
		log.info("清空路径:{}",PathUtil.getClassResources()+"admin/ftl");
	    String ftlPath = "ftl/";								//ftl路径
	    ftlPath += "createCode";								//ftl路径
        if("tree".equals(FHTYPE)){
            ftlPath += "createTreeCode";
            /*生成实体类*/
            Freemarker.printFile("entityTemplate.ftl", root, "entity/"+packageName+"/"+objectName+".java", filePath, ftlPath);
            /*生成jsp_tree页面*/
            Freemarker.printFile("jsp_tree_Template.ftl", root, "jsp/"+packageName+"/"+objectName.toLowerCase()+"/"+objectName.toLowerCase()+"_tree.jsp", filePath, ftlPath);
        }else if("fathertable".equals(FHTYPE)){
            ftlPath += "createFaCode";	//主表
        }else if("sontable".equals(FHTYPE)){
            ftlPath += "createSoCode";	//明细表
        }

        if(flag.equals("front")){
            createHtml(packageName, objectName, root, filePath, ftlPath);
        }else if(flag.equals("backend")){
            createJavaS(dbType, packageName, objectName, tables, tabletop, root, filePath, ftlPath);
        }else{
            createHtml(packageName, objectName, root, filePath, ftlPath);
            createJavaS(dbType, packageName, objectName, tables, tabletop, root, filePath, ftlPath);
        }


    }

    private void createJavaS(String dbType, String packageName, String objectName, List tables, String tabletop, Map<String, Object> root, String filePath, String ftlPath) throws Exception {
        /*生成controller*/
        Freemarker.printFile("controllerTemplate.ftl", root, "controller/"+packageName+"/"+objectName+"Controller.java", filePath, ftlPath);
        /*生成service*/
        Freemarker.printFile("serviceImplTemplate.ftl", root, "service/"+packageName+"/"+"impl/"+objectName+"ServiceImpl.java", filePath, ftlPath);
        /*生成manager*/
        Freemarker.printFile("serviceTemplate.ftl", root, "service/"+packageName+"/"+objectName+"Service.java", filePath, ftlPath);
        if(tables.size() > 1){
            Freemarker.printFile("coreImplTemplate.ftl", root, "core/"+packageName+"/"+"impl/"+objectName+"CoreImpl.java", filePath, ftlPath);
            Freemarker.printFile("coreTemplate.ftl", root, "core/"+packageName+"/"+objectName+"Core.java", filePath, ftlPath);	//多表时生成Core
        }


        if(Const.DBTYPE_MYSQL.equals(dbType) || Const.DBTYPE_H2.equals(dbType)){
            /*生成mybatis xml*/
            Freemarker.printFile("mapperMysqlTemplate.ftl", root, "mybatis_mysql/"+packageName+"/"+objectName+"Mapper.xml", filePath, ftlPath);
            /*生成SQL脚本*/
            Freemarker.printFile("mysql_SQL_Template.ftl", root, "mysql_dbscript/"+tabletop.toUpperCase()+".sql", filePath, ftlPath);
        }else if(Const.DBTYPE_ORACLE.equals(dbType)){
            if(tables.size() > 1){
                /*生成mybatis xml*/
                Freemarker.printFile("mapperOracleMoreTemplate.ftl", root, "mybatis_oracle/"+packageName+"/"+objectName+"Mapper.xml", filePath, ftlPath);
            }else{
                /*生成mybatis xml*/
                Freemarker.printFile("mapperOracleTemplate.ftl", root, "mybatis_oracle/"+packageName+"/"+objectName+"Mapper.xml", filePath, ftlPath);
            }
            /*生成SQL脚本*/
            //Freemarker.printFile("oracle_SQL_Template.ftl", root, "oracle_dbscript/"+tabletop.toUpperCase()+".sql", filePath, ftlPath);
        }else if(Const.DBTYPE_SQLSERVER.equals(dbType)){
            /*生成mybatis xml*/
            Freemarker.printFile("mapperSqlserverTemplate.ftl", root, "mybatis_sqlserver/"+packageName+"/"+objectName+"Mapper.xml", filePath, ftlPath);
            /*生成SQL脚本*/
            Freemarker.printFile("sqlserver_SQL_Template.ftl", root, "sqlserver_dbscript/"+tabletop.toUpperCase()+".sql", filePath, ftlPath);
        }else if(Const.DBTYPE_DB2.equals(dbType)){
            /*生成mybatis xml*/
            Freemarker.printFile("mapperDB2Template.ftl", root, "mybatis_db2/"+packageName+"/"+objectName+"Mapper.xml", filePath, ftlPath);
            /*生成SQL脚本*/
            Freemarker.printFile("db2_SQL_Template.ftl", root, "db2_dbscript/"+tabletop.toUpperCase()+".sql", filePath, ftlPath);
        }else{
            //数据库类型未知
            throw error(Error.ERROR_UNKNOWN);
        }
    }

    private void createHtml(String packageName, String objectName, Map<String, Object> root, String filePath, String ftlPath) throws Exception {
		objectName = objectName.substring(0,1).toLowerCase() + objectName.substring(1);
		root.put("objectName", objectName);
		/*生成jsp页面*/
        Freemarker.printFile("vue_Api_Template.ftl", root, "view/"+objectName+"/api/"+root.get("belongSystem")+"/"+packageName+"/"+objectName+"_form.js", filePath, ftlPath);
        Freemarker.printFile("vue_Const_form_Template.ftl", root, "view/"+objectName+"/const/"+root.get("belongSystem")+"/"+packageName+"/"+objectName+"_form.js", filePath, ftlPath);
        Freemarker.printFile("vue_Const_list_Template.ftl", root, "view/"+objectName+"/const/"+root.get("belongSystem")+"/"+packageName+"/"+objectName+"_list.js", filePath, ftlPath);
        Freemarker.printFile("vue_View_form_Template.ftl", root, "view/"+objectName+"/views/"+root.get("belongSystem")+"/"+packageName+"/"+objectName+"Form/index.vue", filePath, ftlPath);
        Freemarker.printFile("vue_View_list_Template.ftl", root, "view/"+objectName+"/views/"+root.get("belongSystem")+"/"+packageName+"/"+objectName+"List/index.vue", filePath, ftlPath);
        /*生成说明文档*/
        //Freemarker.printFile("docTemplate.ftl", root, "install_help.doc", filePath, ftlPath);
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

    public static String dashedToCamelToUpper(String str){
		String[] mapper = str.toUpperCase().split("_");
		String sb = "";
		for(int i = 0; i < mapper.length; i++){
			if(mapper[i].length() > 1){
				sb += (mapper[i].substring(0,1) + mapper[i].substring(1).toLowerCase());
			}else{
				sb += (mapper[i]);
			}
		}
		return sb;
	}

    /**保存到数据库
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception{
		pd.put("BELONGSYSTEM", pd.getString("belongSystem"));	//所属子系统  需要修改表结构  添加字段
		pd.put("PACKAGENAME", pd.getString("packageName"));	//所属模块
		pd.put("TITLE", pd.getString("TITLE"));				//模块名称
		pd.put("OBJECTNAME", pd.getString("objectName"));	    //类名
		pd.put("TABLENAME", pd.getString("tabletop").toUpperCase());	//表名
		pd.put("FIELDLIST", pd.getString("FIELDLIST"));		//属性集合
		pd.put("WHEREIS", pd.getString("whereis"));		    //查询条件    需要修改表结构 添加字段

		pd.put("CREATETIME", DateUtil.getTime());			//创建时间
		pd.put("CREATECODE_ID", this.get32UUID());			//主键
		createCodeService.save(pd);
	}

	/**
	 * 通过ID获取数据
	 */
	@RequestMapping(value="/findById")
	@ResponseBody
	public Object findById() throws Exception {
		PageData pd = this.getPageData();
		Map<String,Object> map = new HashMap<>();
		try {
			pd = createCodeService.findById(pd);
		} catch (Exception e) {
			log.error(e.toString(), e);
		}
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}

	/**删除
	 * @param out
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		PageData pd = this.getPageData();
		createCodeService.delete(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				createCodeService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			log.error(e.toString(), e);
		}
		return AppUtil.returnObject(pd, map);
	}

}

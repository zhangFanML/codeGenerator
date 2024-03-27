package com.git.easyloan.entity;

import org.springframework.context.ApplicationContext;

public class Const {
    public static final String SESSION_SECURITY_CODE = "sessionSecCode";
    public static final String SESSION_USER = "sessionUser";
    public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
    public static final String sSESSION_ROLE_RIGHTS = "sessionRoleRights";
    public static final String SESSION_menuList = "menuList";
    public static final String SESSION_allmenuList = "allmenuList";
    public static final String SESSION_QX = "QX";
    public static final String SESSION_userpds = "userpds";
    public static final String SESSION_USERROL = "USERROL";
    public static final String SESSION_USERNAME = "USERNAME";
    public static final String BANK_CODE = "BANKCODE";
    public static final String DEPARTMENT_IDS = "DEPARTMENT_IDS";
    public static final String DEPARTMENT_ID = "DEPARTMENT_ID";
    public static final String DEPARTMENT_BIANMA = "BIANMA";
    public static final String DEPARTMENT_NAME = "DEPARTMENT_NAME";
    public static final String NAME = "NAME";
    public static final String POST = "POST";
    public static final String TEL = "TEL";
    public static final String TRUE = "T";
    public static final String FALSE = "F";
    public static final String LOGIN = "/login_toLogin.do";
    public static final String SYSNAME = "admin/config/SYSNAME.txt";
    public static final String LEGALPERSON = "admin/config/LEGALPERSON.txt";
    public static final String PAGE = "admin/config/PAGE.txt";
    public static final String EMAIL = "admin/config/EMAIL.txt";
    public static final String SMS1 = "admin/config/SMS1.txt";
    public static final String SMS2 = "admin/config/SMS2.txt";
    public static final String FWATERM = "admin/config/FWATERM.txt";
    public static final String IWATERM = "admin/config/IWATERM.txt";
    public static final String WEIXIN = "admin/config/WEIXIN.txt";
    public static final String WEBSOCKET = "admin/config/WEBSOCKET.txt";
    public static final String LOGINEDIT = "admin/config/LOGIN.txt";
    public static final String FILEPATHIMG = "uploadFiles/uploadImgs/";
    public static final String FILEPATHFILE = "uploadFiles/file/";
    public static final String FILEPATHFILEOA = "uploadFiles/uploadFile/";
    public static final String FILEPATHTWODIMENSIONCODE = "uploadFiles/twoDimensionCode/";
    public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(app)|(weixin)|(static)|(main)|(websocket)).*";
    public static ApplicationContext WEB_APP_CONTEXT = null;
    public static final String[] SYSUSER_REGISTERED_PARAM_ARRAY = new String[]{"USERNAME", "PASSWORD", "NAME", "EMAIL", "rcode"};
    public static final String[] SYSUSER_REGISTERED_VALUE_ARRAY = new String[]{"用户名", "密码", "姓名", "邮箱", "验证码"};
    public static final String[] APP_GETAPPUSER_PARAM_ARRAY = new String[]{"USERNAME"};
    public static final String[] APP_GETAPPUSER_VALUE_ARRAY = new String[]{"用户名"};
    public static final String DBTYPE_MYSQL = "mysql";
    public static final String DBTYPE_ORACLE = "oracle";
    public static final String DBTYPE_SQLSERVER = "sqlserver";
    public static final String DBTYPE_DB2 = "db2";
    public static final String DBTYPE_H2 = "H2";
    public static final String DBTYPE_POSTGRESQL = "PostgreSQL";

}

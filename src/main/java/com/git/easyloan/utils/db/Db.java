package com.git.easyloan.utils.db;

import com.git.easyloan.entity.PageData;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class Db {
    private static Db db = new Db();

    public Db() {
    }


    public static Db getDb() {
        return db;
    }

    public static Object[] getTables(PageData pd) throws ClassNotFoundException, SQLException {
        String dbtype = pd.getString("dbtype");
        String username = pd.getString("username");
        String password = pd.getString("password");
        String address = pd.getString("dbAddress");
        String dbport = pd.getString("dbport");
        String databaseName = pd.getString("databaseName");
        Connection conn = getCon(dbtype, username, password, address + ":" + dbport, databaseName);
        if ("oracle".equals(dbtype) || "db2".equals(dbtype)) {
            databaseName = username.toUpperCase();
        }

        Object[] arrOb = new Object[]{databaseName, getTablesByCon(conn, "sqlserver".equals(dbtype) ? null : databaseName), dbtype};
        return arrOb;
    }

    public static Connection getCon(PageData pd) throws ClassNotFoundException, SQLException {
        String dbtype = pd.getString("dbtype");
        String username = pd.getString("username");
        String password = pd.getString("password");
        String address = pd.getString("dbAddress");
        String dbport = pd.getString("dbport");
        String databaseName = pd.getString("databaseName");
        return getCon(dbtype, username, password, address + ":" + dbport, databaseName);
    }

    public static Connection getCon(String dbtype, String username, String password, String dburl, String databaseName) throws SQLException, ClassNotFoundException {
        if ("mysql".equalsIgnoreCase(dbtype)) {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://" + dburl + "/" + databaseName + "?user=" + username + "&password=" + password);
        } else if ("oracle".equalsIgnoreCase(dbtype)) {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dburl + ":" + databaseName, username, password);
            return conn;
        } else if ("sqlserver".equalsIgnoreCase(dbtype)) {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection("jdbc:sqlserver://" + dburl + "; DatabaseName=" + databaseName, username, password);
        } else if ("db2".equalsIgnoreCase(dbtype)) {
            Class.forName("com.ibm.db2.jcc.DB2Driver");
            return DriverManager.getConnection("jdbc:db2://" + dburl + "/" + databaseName, username, password);
        } else {
            throw new SQLException("暂不支持该类型的数据库！");
        }
    }

    public static List<String> getTablesByCon(Connection conn, String schema) {
        try {
            ArrayList<String> listTb = new ArrayList<>();
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, schema, "%", new String[]{"TABLE"});
            while(rs.next()) {
                listTb.add(rs.getString("TABLE_NAME"));
            }
            ArrayList var5 = listTb;
            return var5;
        } catch (Exception var15) {
            var15.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException var14) {
                var14.printStackTrace();
            }
        }
        return null;
    }

    public static String getExecStr(String dbtype, String dbpath, String address, String username, String password, String sqlpath, String tableName, String databaseName, String ffilename) {
        StringBuffer sb = new StringBuffer();
        if ("mysql".equals(dbtype)) {
            address = "localhost";
            sb.append(dbpath);
            sb.append("mysqldump ");
            sb.append("--opt ");
            sb.append("-h ");
            sb.append(address);
            sb.append(" ");
            sb.append("--user=");
            sb.append(username);
            sb.append(" ");
            sb.append("--password=");
            sb.append(password);
            sb.append(" ");
            sb.append("--lock-all-tables=true ");
            sb.append("--result-file=");
            sb.append(sqlpath);
            sb.append(("".equals(tableName) ? databaseName + "_" + ffilename : tableName + "_" + ffilename) + ".sql");
            sb.append(" ");
            sb.append("--default-character-set=utf8 ");
            sb.append(databaseName);
            sb.append(" ");
            sb.append(tableName);
        } else if ("oracle".equals(dbtype)) {
            if ("".equals(tableName)) {
                sb.append("EXP " + username + "/" + password + " BUFFER=880000 FILE=" + sqlpath + username + "_" + ffilename + ".DMP LOG=" + sqlpath + username + "_" + ffilename + ".LOG OWNER=" + username);
            } else {
                sb.append("EXP " + username + "/" + password + " BUFFER=880000 FILE=" + sqlpath + tableName + "_" + ffilename + ".DMP LOG=" + sqlpath + tableName + "_" + ffilename + ".LOG TABLES=(" + username + "." + tableName + ")");
            }
        }

        return sb.toString();
    }

//    public static Object[] executeQueryFH(String sql) throws Exception {
//        new ArrayList();
//        List<List<Object>> dataList = new ArrayList();
//        Statement stmt = null;
//        ResultSet rs = null;
//        Connection conn = null;
//        conn = getFHCon();
//        stmt = conn.createStatement();
//        rs = stmt.executeQuery(sql);
//        List columnList = getFieldLsit(conn, sql);
//
//        while(rs.next()) {
//            List<Object> onedataList = new ArrayList();
//
//            for(int i = 1; i < columnList.size() + 1; ++i) {
//                onedataList.add(rs.getObject(i));
//            }
//            dataList.add(onedataList);
//        }
//        Object[] arrOb = new Object[]{columnList, dataList};
//        conn.close();
//        return arrOb;
//    }
//
//    public static void executeUpdateFH(String sql) throws ClassNotFoundException, SQLException {
//        Statement stmt = null;
//        Connection conn = null;
//        conn = getFHCon();
//        stmt = conn.createStatement();
//        stmt.executeUpdate(sql);
//        conn.close();
//    }

    public static List<String> getFieldLsit(Connection conn, String table) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(table);
        pstmt.execute();
        List<String> columnList = new ArrayList();
        ResultSetMetaData rsmd = pstmt.getMetaData();

        for(int i = 1; i < rsmd.getColumnCount() + 1; ++i) {
            columnList.add(rsmd.getColumnName(i));
        }

        return columnList;
    }

    public static List<Map<String, String>> getFieldParameterLsit(Connection conn, String table) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("select * from " + table);
        pstmt.execute();
        List<Map<String, String>> columnList = new ArrayList();
        ResultSetMetaData rsmd = pstmt.getMetaData();

        for(int i = 1; i < rsmd.getColumnCount() + 1; ++i) {
            Map<String, String> fmap = new HashMap();
            fmap.put("fieldNanme", rsmd.getColumnName(i));
            fmap.put("fieldType", rsmd.getColumnTypeName(i));
            fmap.put("fieldLength", String.valueOf(rsmd.getColumnDisplaySize(i)));
            fmap.put("fieldSccle", String.valueOf(rsmd.getScale(i)));
            fmap.put("fieldCommont", "");
            columnList.add(fmap);
        }

        return columnList;
    }

    public static Properties getPprVue() {
        InputStream inputStream = Db.class.getClassLoader().getResourceAsStream("dbfh.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
            inputStream.close();
        } catch (IOException var3) {
            var3.printStackTrace();
        }
        return p;
    }
}

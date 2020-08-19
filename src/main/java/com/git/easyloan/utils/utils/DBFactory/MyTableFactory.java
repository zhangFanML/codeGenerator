package com.git.easyloan.utils.utils.DBFactory;

import com.git.easyloan.utils.db.Column;
import com.git.easyloan.utils.db.Table;
import com.git.easyloan.utils.utils.BeanHelper;
import com.git.easyloan.utils.utils.FileHelper;
import com.git.easyloan.utils.utils.GLogger;
import com.git.easyloan.utils.utils.XMLHelper;

import java.io.File;
import java.sql.*;
import java.util.*;

public class MyTableFactory {

    private MyTableFactory.DbHelper dbHelper = new MyTableFactory.DbHelper();
    private Connection conn;
    private String schema;
    private String catalog;

    private MyTableFactory() {
    }

    private MyTableFactory(Connection conn) {
        this.conn = conn;
    }

    public static synchronized MyTableFactory getInstance() {
        return  new MyTableFactory();
    }

    public static synchronized MyTableFactory getInstance(Connection conn) {
        return new MyTableFactory(conn);
    }

    public MyTableFactory(Connection conn, String schema) {
        this.schema = schema;
        this.conn = conn;
    }

    public MyTableFactory(Connection conn, String schema, String catalog) {
        this.schema = schema;
        this.catalog = catalog;
        this.conn = conn;
    }

    public String getSchema() {
        return this.schema;
    }

    public String getCatalog() {
        return this.catalog;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public Table getTable(String tableName) {
        return this.getTable(this.getSchema(), tableName);
    }

    private Table getTable(String schema, String tableName) {
        return this.getTable(this.getCatalog(), schema, tableName);
    }

    private Table getTable(String catalog, String schema, String tableName) {
        Table table;
        try {
            // 第一次获取 table
            table = this._getTable(catalog, schema, tableName);
            //增加可用性 变换表名的大小写再次进行搜索
            if (table == null && !tableName.equals(tableName.toUpperCase())) {
                table = this._getTable(catalog, schema, tableName.toUpperCase());
            }
            if (table == null && !tableName.equals(tableName.toLowerCase())) {
                table = this._getTable(catalog, schema, tableName.toLowerCase());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (table == null) {
            throw new NotFoundTableException("not found table with give name:" + tableName + (this.dbHelper.isOracleDataBase() ? " \n databaseStructureInfo:" + this.getDatabaseStructureInfo() : ""));
        } else {
            return table;
        }
    }

    private Table _getTable(String catalog, String schema, String tableName) throws SQLException {
        if (tableName != null && tableName.trim().length() != 0) {
            DatabaseMetaData dbMetaData = conn.getMetaData();
            //types 典型的类型是“TABLE”，“VIEW”，“SYSTEM TABLE”，“GLOBAL TEMPORARY”，“LOCAL TEMPORARY”，“ALIAS”，“SYNONYM
            ResultSet rs = dbMetaData.getTables(catalog, schema, tableName, new String[] { "TABLE" });
            if (rs.next()) {
                Table table = this.createTable(conn, rs);
                return table;
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException("tableName must be not empty");
        }
    }

    private Table createTable(Connection conn, ResultSet rs) throws SQLException {
        String realTableName = null;
        try {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            if (rs.getString("TABLE_SCHEM") == null) {
                String var10000 = "";
            } else {
                rs.getString("TABLE_SCHEM");
            }
            realTableName = rs.getString("TABLE_NAME");
            String tableType = rs.getString("TABLE_TYPE");
            String remarks = rs.getString("REMARKS");
            if (remarks == null && this.dbHelper.isOracleDataBase()) {
                remarks = this.getOracleTableComments(realTableName);
            }
            Table table = new Table();
            table.setSqlName(realTableName);
            table.setRemarks(remarks);
            //表的类型是同义词 设置其 同义词的表名
            if ("SYNONYM".equals(tableType) && this.dbHelper.isOracleDataBase()) {
                table.setOwnerSynonymName(this.getSynonymOwner(realTableName));
            }

            this.retriveTableColumns(table);
//            table.initExportedKeys(conn.getMetaData());
//            table.initImportedKeys(conn.getMetaData());
            BeanHelper.copyProperties(table, TableOverrideValuesProvider.getTableOverrideValues(table.getSqlName()));
            return table;
        } catch (SQLException var9) {
            throw new RuntimeException("create table object error,tableName:" + realTableName, var9);
        }
    }

    /**
     * 获取数据库下的所有表名 列出所有表
     */
    public List<String> getTableNames(String schema) {
        // 当数据库为 Oracle 或者 DB2 时 schema 为大写
        List<String> tableNames = new ArrayList<>();
        ResultSet rs = null;
        try {
            //获取数据库的元数据
            DatabaseMetaData db = this.conn.getMetaData();
            //从元数据中获取到所有的表名
            rs = db.getTables(null, schema, null, new String[] { "TABLE" });
            while(rs.next()) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tableNames;
    }

    /**
     * 关闭数据库连接
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }


    private List getAllTables(Connection conn) throws SQLException {
        DatabaseMetaData dbMetaData = conn.getMetaData();
        ResultSet rs = dbMetaData.getTables(this.getCatalog(), this.getSchema(), null, new String[]{ "TABLE" });
        ArrayList tables = new ArrayList();
        while(rs.next()) {
            tables.add(this.createTable(conn, rs));
        }
        return tables;
    }

    private String getSynonymOwner(String synonymName) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String ret = null;

        try {
            ps = conn.prepareStatement("select table_owner from sys.all_synonyms where table_name=? and owner=?");
            ps.setString(1, synonymName);
            ps.setString(2, this.getSchema());
            rs = ps.executeQuery();
            if (!rs.next()) {
                String databaseStructure = this.getDatabaseStructureInfo();
                throw new RuntimeException("Wow! Synonym " + synonymName + " not found. How can it happen? " + databaseStructure);
            }

            ret = rs.getString(1);
        } catch (SQLException var10) {
            String databaseStructure = this.getDatabaseStructureInfo();
            GLogger.error(var10.getMessage(), var10);
            throw new RuntimeException("Exception in getting synonym owner " + databaseStructure);
        } finally {
            this.dbHelper.close(rs, ps);
        }

        return ret;
    }

    private String getDatabaseStructureInfo() {
        ResultSet schemaRs = null;
        ResultSet catalogRs = null;
        String nl = System.getProperty("line.separator");
        StringBuffer sb = new StringBuffer(nl);
        sb.append("Configured schema:").append(this.getSchema()).append(nl);
        sb.append("Configured catalog:").append(this.getCatalog()).append(nl);

        try {
            schemaRs = conn.getMetaData().getSchemas();
            sb.append("Available schemas:").append(nl);

            while(schemaRs.next()) {
                sb.append("  ").append(schemaRs.getString("TABLE_SCHEM")).append(nl);
            }
        } catch (SQLException var18) {
            GLogger.warn("Couldn't get schemas", var18);
            sb.append("  ?? Couldn't get schemas ??").append(nl);
        } finally {
            this.dbHelper.close(schemaRs, (PreparedStatement)null);
        }

        try {
            catalogRs = conn.getMetaData().getCatalogs();
            sb.append("Available catalogs:").append(nl);

            while(catalogRs.next()) {
                sb.append("  ").append(catalogRs.getString("TABLE_CAT")).append(nl);
            }
        } catch (SQLException var16) {
            GLogger.warn("Couldn't get catalogs", var16);
            sb.append("  ?? Couldn't get catalogs ??").append(nl);
        } finally {
            this.dbHelper.close(catalogRs, (PreparedStatement)null);
        }

        return sb.toString();
    }

    private void retriveTableColumns(Table table) throws SQLException {
        GLogger.trace("-------setColumns(" + table.getSqlName() + ")");
        List primaryKeys = this.getTablePrimaryKeys(table);
        table.setPrimaryKeyColumns(primaryKeys);
        List indices = new LinkedList();
        Map uniqueIndices = new HashMap();
        Map uniqueColumns = new HashMap();
        ResultSet indexRs = null;

        try {
            //判断表的类型是否是同义词
            if (table.getOwnerSynonymName() != null) {
                indexRs = conn.getMetaData().getIndexInfo(this.getCatalog(), table.getOwnerSynonymName(), table.getSqlName(), false, true);
            } else {
                indexRs = conn.getMetaData().getIndexInfo(this.getCatalog(), this.getSchema(), table.getSqlName(), false, true);
            }

            while(indexRs.next()) {
                String columnName = indexRs.getString("COLUMN_NAME");
                if (columnName != null) {
                    GLogger.trace("index:" + columnName);
                    indices.add(columnName);
                }

                String indexName = indexRs.getString("INDEX_NAME");
                boolean nonUnique = indexRs.getBoolean("NON_UNIQUE");
                if (!nonUnique && columnName != null && indexName != null) {
                    List l = (List)uniqueColumns.get(indexName);
                    if (l == null) {
                        l = new ArrayList();
                        uniqueColumns.put(indexName, l);
                    }

                    (l).add(columnName);
                    uniqueIndices.put(columnName, indexName);
                    GLogger.trace("unique:" + columnName + " (" + indexName + ")");
                }
            }
        } catch (Throwable var14) {
        } finally {
            this.dbHelper.close(indexRs, (PreparedStatement)null);
        }

        List columns = this.getTableColumns(table, primaryKeys, indices, uniqueIndices, uniqueColumns);
        Iterator i = columns.iterator();

        while(i.hasNext()) {
            Column column = (Column)i.next();
            table.addColumn(column);
        }

        if (primaryKeys.size() == 0) {
            GLogger.warn("WARNING: The JDBC driver didn't report any primary key columns in " + table.getSqlName());
        }

    }

    private List getTableColumns(Table table, List primaryKeys, List indices, Map uniqueIndices, Map uniqueColumns) throws SQLException {
        List columns = new LinkedList();
        ResultSet columnRs = this.getColumnsResultSet(table);

        while(columnRs.next()) {
            int sqlType = columnRs.getInt("DATA_TYPE");
            String sqlTypeName = columnRs.getString("TYPE_NAME");
            String columnName = columnRs.getString("COLUMN_NAME");
            String columnDefaultValue = columnRs.getString("COLUMN_DEF");
            String remarks = columnRs.getString("REMARKS");
            if (remarks == null && this.dbHelper.isOracleDataBase()) {
                remarks = this.getOracleColumnComments(table.getSqlName(), columnName);
            }

            boolean isNullable = 1 == columnRs.getInt("NULLABLE");
            int size = columnRs.getInt("COLUMN_SIZE");
            int decimalDigits = columnRs.getInt("DECIMAL_DIGITS");
            boolean isPk = primaryKeys.contains(columnName);
            boolean isIndexed = indices.contains(columnName);
            String uniqueIndex = (String)uniqueIndices.get(columnName);
            List columnsInUniqueIndex = null;
            if (uniqueIndex != null) {
                columnsInUniqueIndex = (List)uniqueColumns.get(uniqueIndex);
            }

            boolean isUnique = columnsInUniqueIndex != null && columnsInUniqueIndex.size() == 1;
            if (isUnique) {
                GLogger.trace("unique column:" + columnName);
            }

            Column column = new Column(table, sqlType, sqlTypeName, columnName, size, decimalDigits, isPk, isNullable, isIndexed, isUnique, columnDefaultValue, remarks);
            BeanHelper.copyProperties(column, MyTableFactory.TableOverrideValuesProvider.getColumnOverrideValues(table, column));
            columns.add(column);
        }

        columnRs.close();
        return columns;
    }

    private ResultSet getColumnsResultSet(Table table) throws SQLException {
        ResultSet columnRs = null;
        if (table.getOwnerSynonymName() != null) {
            columnRs = conn.getMetaData().getColumns(this.getCatalog(), table.getOwnerSynonymName(), table.getSqlName(), (String)null);
        } else {
            columnRs = conn.getMetaData().getColumns(this.getCatalog(), this.getSchema(), table.getSqlName(), (String)null);
        }

        return columnRs;
    }

    private List<String> getTablePrimaryKeys(Table table) throws SQLException {
        List primaryKeys = new LinkedList();
        ResultSet primaryKeyRs = null;
        GLogger.debug("table.getSqlName()---->" + table.getSqlName());
        if (table.getOwnerSynonymName() != null) {
            primaryKeyRs = conn.getMetaData().getPrimaryKeys(this.getCatalog(), table.getOwnerSynonymName(), table.getSqlName());
        } else {
            primaryKeyRs = conn.getMetaData().getPrimaryKeys(this.getCatalog(), this.getSchema(), table.getSqlName());
        }

        while(primaryKeyRs.next()) {
            String columnName = primaryKeyRs.getString("COLUMN_NAME");
            GLogger.trace("primary key:" + columnName);
            primaryKeys.add(columnName);
        }

        primaryKeyRs.close();
        return primaryKeys;
    }

    private String getOracleTableComments(String table) {
        String sql = "SELECT comments FROM user_tab_comments WHERE table_name='" + table + "'";
        return this.dbHelper.queryForString(sql);
    }

    private String getOracleColumnComments(String table, String column) {
        String sql = "SELECT comments FROM user_col_comments WHERE table_name='" + table + "' AND column_name = '" + column + "'";
        return this.dbHelper.queryForString(sql);
    }

    public static class DatabaseMetaDataUtils {
        public DatabaseMetaDataUtils() {
        }

        public static boolean isOracleDataBase(DatabaseMetaData metadata) {
            try {
                boolean ret = false;
                ret = metadata.getDatabaseProductName().toLowerCase().indexOf("oracle") != -1;
                return ret;
            } catch (SQLException var2) {
                return false;
            }
        }

        public static boolean isHsqlDataBase(DatabaseMetaData metadata) {
            try {
                boolean ret = false;
                ret = metadata.getDatabaseProductName().toLowerCase().indexOf("hsql") != -1;
                return ret;
            } catch (SQLException var2) {
                return false;
            }
        }
    }

    class DbHelper {
        DbHelper() {
        }

        public void close(ResultSet rs, PreparedStatement ps, Statement... statements) {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (rs != null) {
                    rs.close();
                }

                Statement[] arr$ = statements;
                int len$ = statements.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    Statement s = arr$[i$];
                    s.close();
                }
            } catch (Exception var8) {
            }

        }

        public boolean isOracleDataBase() {
            try {
                return MyTableFactory.DatabaseMetaDataUtils.isOracleDataBase(conn.getMetaData());
            } catch (SQLException var2) {
                throw new RuntimeException(var2);
            }
        }

        public String queryForString(String sql) {
            Statement s = null;
            ResultSet rs = null;

            String var4;
            try {
                s = conn.createStatement();
                rs = s.executeQuery(sql);
                if (!rs.next()) {
                    var4 = null;
                    return var4;
                }

                var4 = rs.getString(1);
            } catch (SQLException var9) {
                var9.printStackTrace();
                Object var5 = null;
                return (String)var5;
            } finally {
                this.close(rs, (PreparedStatement)null, s);
            }

            return var4;
        }
    }

    public static class TableOverrideValuesProvider {
        public TableOverrideValuesProvider() {
        }

        private static Map getTableOverrideValues(String tableSqlName) {
            XMLHelper.NodeData nd = getTableConfigXmlNodeData(tableSqlName);
            if (nd == null) {
                return new HashMap();
            } else {
                return (Map)(nd == null ? new HashMap() : nd.attributes);
            }
        }

        private static Map getColumnOverrideValues(Table table, Column column) {
            XMLHelper.NodeData root = getTableConfigXmlNodeData(table.getSqlName());
            if (root != null) {
                Iterator i$ = root.childs.iterator();

                while(i$.hasNext()) {
                    XMLHelper.NodeData item = (XMLHelper.NodeData)i$.next();
                    if (item.nodeName.equals("column") && column.getSqlName().equalsIgnoreCase((String)item.attributes.get("sqlName"))) {
                        return item.attributes;
                    }
                }
            }

            return new HashMap();
        }

        private static XMLHelper.NodeData getTableConfigXmlNodeData(String tableSqlName) {
            XMLHelper.NodeData nd = getTableConfigXmlNodeData0(tableSqlName);
            if (nd == null) {
                nd = getTableConfigXmlNodeData0(tableSqlName.toLowerCase());
                if (nd == null) {
                    nd = getTableConfigXmlNodeData0(tableSqlName.toUpperCase());
                }
            }

            return nd;
        }

        private static XMLHelper.NodeData getTableConfigXmlNodeData0(String tableSqlName) {
            try {
                File file = FileHelper.getFileByClassLoader("generator_config/table/" + tableSqlName + ".xml");
                GLogger.trace("getTableConfigXml() load nodeData by tableSqlName:" + tableSqlName + ".xml");
                return (new XMLHelper()).parseXML(file);
            } catch (Exception var2) {
                GLogger.trace("not found config xml for table:" + tableSqlName + ", exception:" + var2);
                return null;
            }
        }
    }

    public static class NotFoundTableException extends RuntimeException {
        private static final long serialVersionUID = 5976869128012158628L;

        public NotFoundTableException(String message) {
            super(message);
        }
    }
}

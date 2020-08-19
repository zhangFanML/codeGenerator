package com.git.easyloan.utils.db;


import com.git.easyloan.utils.utils.DBFactory.MyTableFactory;
import com.git.easyloan.utils.utils.PropertiesHelper;
import com.git.easyloan.utils.utils.StringHelper;

import java.io.Serializable;
import java.util.*;

public class Table implements Serializable, Cloneable {
    // 表名
    String sqlName;
    // 表注释
    String remarks;
    String className;
    private String ownerSynonymName = null;
    private String tableSynonymName = null;
    LinkedHashSet<Column> columns = new LinkedHashSet();
    List<Column> primaryKeyColumns = new ArrayList();
    String catalog = MyTableFactory.getInstance().getCatalog();
    String schema = MyTableFactory.getInstance().getSchema();
    private String tableAlias;
//    private ForeignKeys exportedKeys;
//    private ForeignKeys importedKeys;
    public static final String PKTABLE_NAME = "PKTABLE_NAME";
    public static final String PKCOLUMN_NAME = "PKCOLUMN_NAME";
    public static final String FKTABLE_NAME = "FKTABLE_NAME";
    public static final String FKCOLUMN_NAME = "FKCOLUMN_NAME";
    public static final String KEY_SEQ = "KEY_SEQ";

    public Table() {
    }

    public Table(Table t) {
        this.setSqlName(t.getSqlName());
        this.remarks = t.getRemarks();
        this.className = t.getClassName();
        this.ownerSynonymName = t.getOwnerSynonymName();
        this.columns = t.getColumns();
        this.primaryKeyColumns = t.getPrimaryKeyColumns();
        this.tableAlias = t.getTableAlias();
//        this.exportedKeys = t.exportedKeys;
//        this.importedKeys = t.importedKeys;
    }

    public LinkedHashSet<Column> getColumns() {
        return this.columns;
    }

    public void setColumns(LinkedHashSet<Column> columns) {
        this.columns = columns;
    }

    public String getOwnerSynonymName() {
        return this.ownerSynonymName;
    }

    public void setOwnerSynonymName(String ownerSynonymName) {
        this.ownerSynonymName = ownerSynonymName;
    }

    public String getTableSynonymName() {
        return this.tableSynonymName;
    }

    public void setTableSynonymName(String tableSynonymName) {
        this.tableSynonymName = tableSynonymName;
    }

    public List<Column> getPrimaryKeyColumns() {
        return this.primaryKeyColumns;
    }

    public void setPrimaryKeyColumns(List<Column> primaryKeyColumns) {
        this.primaryKeyColumns = primaryKeyColumns;
    }

    public String getSqlName() {
        return this.sqlName;
    }

    public void setSqlName(String sqlName) {
        this.sqlName = sqlName;
    }

    public static String removeTableSqlNamePrefix(String sqlName) {
        PropertiesHelper props = new PropertiesHelper(new Properties(), true);
        String prefixs = PropertiesHelper.getProperty("tableRemovePrefixes", "");
        String[] arr$ = prefixs.split(",");
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String prefix = arr$[i$];
            String removedPrefixSqlName = StringHelper.removePrefix(sqlName, prefix, true);
            if (!removedPrefixSqlName.equals(sqlName)) {
                return removedPrefixSqlName;
            }
        }

        return sqlName;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void addColumn(Column column) {
        this.columns.add(column);
    }

    public void setClassName(String customClassName) {
        this.className = customClassName;
    }

    public String getClassName() {
        if (StringHelper.isBlank(this.className)) {
            String removedPrefixSqlName = removeTableSqlNamePrefix(this.sqlName);
            return StringHelper.makeAllWordFirstLetterUpperCase(StringHelper.toUnderscoreName(removedPrefixSqlName));
        } else {
            return this.className;
        }
    }

    public String getTableAlias() {
        return StringHelper.isNotBlank(this.tableAlias) ? this.tableAlias : StringHelper.removeCrlf(StringHelper.defaultIfEmpty(this.getRemarks(), this.getClassName()));
    }

    public void setTableAlias(String v) {
        this.tableAlias = v;
    }

    public String getClassNameLowerCase() {
        return this.getClassName().toLowerCase();
    }

    public String getUnderscoreName() {
        return StringHelper.toUnderscoreName(this.getClassName()).toLowerCase();
    }

    public String getClassNameFirstLower() {
        return StringHelper.uncapitalize(this.getClassName());
    }

    public String getConstantName() {
        return StringHelper.toUnderscoreName(this.getClassName()).toUpperCase();
    }

    /** @deprecated */
    @Deprecated
    public boolean isSingleId() {
        return this.getPkCount() == 1;
    }

    /** @deprecated */
    @Deprecated
    public boolean isCompositeId() {
        return this.getPkCount() > 1;
    }

    /** @deprecated */
    @Deprecated
    public boolean isNotCompositeId() {
        return !this.isCompositeId();
    }

    public int getPkCount() {
        int pkCount = 0;
        Iterator i$ = this.columns.iterator();

        while(i$.hasNext()) {
            Column c = (Column)i$.next();
            if (c.isPk()) {
                ++pkCount;
            }
        }

        return pkCount;
    }

    /** @deprecated */
    public List getCompositeIdColumns() {
        return this.getPkColumns();
    }

    public List<Column> getPkColumns() {
        List results = new ArrayList();
        Iterator i$ = this.getColumns().iterator();

        while(i$.hasNext()) {
            Column c = (Column)i$.next();
            if (c.isPk()) {
                results.add(c);
            }
        }

        return results;
    }

    public List<Column> getNotPkColumns() {
        List results = new ArrayList();
        Iterator i$ = this.getColumns().iterator();

        while(i$.hasNext()) {
            Column c = (Column)i$.next();
            if (!c.isPk()) {
                results.add(c);
            }
        }

        return results;
    }

    public Column getPkColumn() {
        if (this.getPkColumns().isEmpty()) {
            throw new IllegalStateException("not found primary key on table:" + this.getSqlName());
        } else {
            return (Column)this.getPkColumns().get(0);
        }
    }

    /** @deprecated */
    @Deprecated
    public Column getIdColumn() {
        return this.getPkColumn();
    }

//    public List<Column> getEnumColumns() {
//        List results = new ArrayList();
//        Iterator i$ = this.getColumns().iterator();
//
//        while(i$.hasNext()) {
//            Column c = (Column)i$.next();
//            if (!c.isEnumColumn()) {
//                results.add(c);
//            }
//        }
//
//        return results;
//    }

    public Column getColumnByName(String name) {
        Column c = this.getColumnBySqlName(name);
        if (c == null) {
            c = this.getColumnBySqlName(StringHelper.toUnderscoreName(name));
        }

        return c;
    }

    public Column getColumnBySqlName(String sqlName) {
        Iterator i$ = this.getColumns().iterator();

        Column c;
        do {
            if (!i$.hasNext()) {
                return null;
            }

            c = (Column)i$.next();
        } while(!c.getSqlName().equalsIgnoreCase(sqlName));

        return c;
    }

    public Column getRequiredColumnBySqlName(String sqlName) {
        if (this.getColumnBySqlName(sqlName) == null) {
            throw new IllegalArgumentException("not found column with sqlName:" + sqlName + " on table:" + this.getSqlName());
        } else {
            return this.getColumnBySqlName(sqlName);
        }
    }

    public List<Column> getIgnoreKeywordsColumns(String ignoreKeywords) {
        List results = new ArrayList();
        Iterator i$ = this.getColumns().iterator();

        while(i$.hasNext()) {
            Column c = (Column)i$.next();
            String sqlname = c.getSqlName().toLowerCase();
            if (!StringHelper.contains(sqlname, ignoreKeywords.split(","))) {
                results.add(c);
            }
        }

        return results;
    }

//    public void initImportedKeys(DatabaseMetaData dbmd) throws SQLException {
//        ResultSet fkeys = dbmd.getImportedKeys(this.catalog, this.schema, this.sqlName);
//
//        while(fkeys.next()) {
//            String pktable = fkeys.getString("PKTABLE_NAME");
//            String pkcol = fkeys.getString("PKCOLUMN_NAME");
//            String fktable = fkeys.getString("FKTABLE_NAME");
//            String fkcol = fkeys.getString("FKCOLUMN_NAME");
//            String seq = fkeys.getString("KEY_SEQ");
//            Integer iseq = new Integer(seq);
//            this.getImportedKeys().addForeignKey(pktable, pkcol, fkcol, iseq);
//        }
//
//        fkeys.close();
//    }
//
//    public void initExportedKeys(DatabaseMetaData dbmd) throws SQLException {
//        ResultSet fkeys = dbmd.getExportedKeys(this.catalog, this.schema, this.sqlName);
//
//        while(fkeys.next()) {
//            String pktable = fkeys.getString("PKTABLE_NAME");
//            String pkcol = fkeys.getString("PKCOLUMN_NAME");
//            String fktable = fkeys.getString("FKTABLE_NAME");
//            String fkcol = fkeys.getString("FKCOLUMN_NAME");
//            String seq = fkeys.getString("KEY_SEQ");
//            Integer iseq = new Integer(seq);
//            this.getExportedKeys().addForeignKey(fktable, fkcol, pkcol, iseq);
//        }
//
//        fkeys.close();
//    }

//    public ForeignKeys getExportedKeys() {
//        if (this.exportedKeys == null) {
//            this.exportedKeys = new ForeignKeys(this);
//        }
//
//        return this.exportedKeys;
//    }
//
//    public ForeignKeys getImportedKeys() {
//        if (this.importedKeys == null) {
//            this.importedKeys = new ForeignKeys(this);
//        }
//
//        return this.importedKeys;
//    }
//
    public String toString() {
        return "Database Table:" + this.getSqlName() + " to ClassName:" + this.getClassName();
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException var2) {
            return null;
        }
    }
}

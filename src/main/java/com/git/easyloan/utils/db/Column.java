package com.git.easyloan.utils.db;

import com.git.easyloan.entity.JdbcType;
import com.git.easyloan.utils.utils.DatabaseDataTypesUtils;
import com.git.easyloan.utils.utils.GLogger;
import com.git.easyloan.utils.utils.PropertiesHelper;
import com.git.easyloan.utils.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Column {
    private Table _table;
    private int _sqlType;
    private String _sqlTypeName;
    private String _sqlName;
    private boolean _isPk;
    private boolean _isFk;
    private int _size;
    private int _decimalDigits;
    private boolean _isNullable;
    private boolean _isIndexed;
    private boolean _isUnique;
    private String _defaultValue;
    private String _remarks;
    //    private ReferenceKey hasOne;
//    private ReferenceKey hasMany;
    private String enumString;
    private String javaType;
    private String columnAlias;
    private String columnName;
    private String asType;
    private String enumClassName;
    private boolean updatable;
    private boolean insertable;
    private String hibernateValidatorExprssion;

    public Column(Table table, int sqlType, String sqlTypeName, String sqlName, int size, int decimalDigits, boolean isPk, boolean isNullable, boolean isIndexed, boolean isUnique, String defaultValue, String remarks) {
//        this.hasMany = null;
        this.enumString = "";
        this.updatable = true;
        this.insertable = true;
        if (sqlName == null) {
            throw new NullPointerException();
        } else {
            this._table = table;
            this._sqlType = sqlType;
            this._sqlName = sqlName;
            this._sqlTypeName = sqlTypeName;
            this._size = size;
            this._decimalDigits = decimalDigits;
            this._isPk = isPk;
            this._isNullable = isNullable;
            this._isIndexed = isIndexed;
            this._isUnique = isUnique;
            this._defaultValue = defaultValue;
            this._remarks = remarks;
            GLogger.trace(sqlName + " isPk -> " + this._isPk);
            this.initOtherProperties();
        }
    }

    public Column(Column c) {
        this(c.getTable(), c.getSqlType(), c.getSqlTypeName(), c.getSqlName(), c.getSize(), c.getDecimalDigits(), c.isPk(), c.isNullable(), c.isIndexed(), c.isUnique(), c.getDefaultValue(), c.getRemarks());
    }

    public Column() {
//        this.hasMany = null;
        this.enumString = "";
        this.updatable = true;
        this.insertable = true;
    }

    public int getSqlType() {
        return this._sqlType;
    }

    public Table getTable() {
        return this._table;
    }

    public int getSize() {
        return this._size;
    }

    public int getDecimalDigits() {
        return this._decimalDigits;
    }

    public String getSqlTypeName() {
        return this._sqlTypeName;
    }

    public String getSqlName() {
        if (this._sqlName == null) {
            throw new NullPointerException();
        } else {
            return this._sqlName;
        }
    }

    public boolean isPk() {
        return this._isPk;
    }

    public boolean isFk() {
        return this._isFk;
    }

    public boolean isNullable() {
        return this._isNullable;
    }

    public boolean isIndexed() {
        return this._isIndexed;
    }

    public boolean isUnique() {
        return this._isUnique;
    }

    public String getDefaultValue() {
        return this._defaultValue;
    }

    public String getRemarks() {
        return this._remarks;
    }

    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    public void setInsertable(boolean insertable) {
        this.insertable = insertable;
    }

    public void setNullable(boolean v) {
        this._isNullable = v;
    }

    public void setUnique(boolean unique) {
        this._isUnique = unique;
    }

    public void setPk(boolean v) {
        this._isPk = v;
    }

    public int hashCode() {
        return this.getTable() != null ? (this.getTable().getSqlName() + "#" + this.getSqlName()).hashCode() : this.getSqlName().hashCode();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else {
            if (o instanceof Column) {
                Column other = (Column) o;
                if (this.getSqlName().equals(other.getSqlName())) {
                    return true;
                }
            }

            return false;
        }
    }

    public String toString() {
        return this.getSqlName();
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException var2) {
            return null;
        }
    }

    protected String prefsPrefix() {
        return "tables/" + this.getTable().getSqlName() + "/columns/" + this.getSqlName();
    }

    void setFk(boolean flag) {
        this._isFk = flag;
    }

    public String getUnderscoreName() {
        return this.getSqlName().toLowerCase();
    }

    public String getColumnName() {
        return this.columnName;
    }

    public String getColumnNameFirstLower() {
        return StringHelper.uncapitalize(this.getColumnName());
    }

    public String getColumnNameLowerCase() {
        return this.getColumnName().toLowerCase();
    }

    /**
     * @deprecated
     */
    public String getColumnNameLower() {
        return this.getColumnNameFirstLower();
    }

    public String getJdbcSqlTypeName() {
        return this.getJdbcType();
    }

    public String getJdbcType() {
        String result = JdbcType.getJdbcSqlTypeName(this.getSqlType());
        return result;
    }

    public String getColumnAlias() {
        return this.columnAlias;
    }

    public String getConstantName() {
        return StringHelper.toUnderscoreName(this.getColumnName()).toUpperCase();
    }

    /**
     * @deprecated
     */
    public boolean getIsNotIdOrVersionField() {
        return !this.isPk();
    }

//    public String getValidateString() {
//        return this.isNullable() ? this.getNoRequiredValidateString() : "required " + this.getNoRequiredValidateString();
//    }

//    public String getNoRequiredValidateString() {
//        return ColumnHelper.getRapidValidation(this);
//    }
//
//    public String[] getHibernateValidatorConstraintNames() {
//        return ColumnHelper.removeHibernateValidatorSpecialTags(this.getHibernateValidatorExprssion());
//    }

    public String getHibernateValidatorExprssion() {
        return this.hibernateValidatorExprssion;
    }

    public void setHibernateValidatorExprssion(String v) {
        this.hibernateValidatorExprssion = v;
    }

//    public boolean getIsStringColumn() {
//        return DatabaseDataTypesUtils.isString(this.getJavaType());
//    }
//
//    public boolean getIsDateTimeColumn() {
//        return DatabaseDataTypesUtils.isDate(this.getJavaType());
//    }
//
//    public boolean getIsNumberColumn() {
//        return DatabaseDataTypesUtils.isFloatNumber(this.getJavaType()) || DatabaseDataTypesUtils.isIntegerNumber(this.getJavaType());
//    }

    public boolean contains(String keywords) {
        if (keywords == null) {
            throw new IllegalArgumentException("'keywords' must be not null");
        } else {
            return StringHelper.contains(this.getSqlName(), keywords.split(","));
        }
    }

    public boolean isHtmlHidden() {
        return this.isPk() && this._table.isSingleId();
    }

    public String getJavaType() {
        return this.javaType;
    }

    public String getSimpleJavaType() {
        return StringHelper.getJavaClassSimpleName(this.getJavaType());
    }

    public String getPossibleShortJavaType() {
        return this.getJavaType().startsWith("java.lang.") ? this.getSimpleJavaType() : this.getJavaType();
    }

//    public boolean isPrimitive() {
//        return JavaPrimitiveTypeMapping.getWrapperTypeOrNull(this.getJavaType()) != null;
//    }
//
//    public String getPrimitiveJavaType() {
//        return JavaPrimitiveTypeMapping.getPrimitiveType(this.getSimpleJavaType());
//    }

    public String getAsType() {
        return this.asType;
    }

//    public String getTestData() {
//        return (new TestDataGenerator()).getDBUnitTestData(this.getColumnName(), this.getJavaType(), this.getSize());
//    }

    public boolean isUpdatable() {
        return this.updatable;
    }

    public boolean isInsertable() {
        return this.insertable;
    }

    public String getEnumClassName() {
        return this.enumClassName;
    }

    public void setEnumString(String str) {
        this.enumString = str;
    }

    public String getEnumString() {
        return this.enumString;
    }

//    public List<EnumMetaDada> getEnumList() {
//        return StringHelper.string2EnumMetaData(this.getEnumString());
//    }

//    public boolean isEnumColumn() {
//        return this.getEnumList() != null && !this.getEnumList().isEmpty();
//    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public void setColumnAlias(String columnAlias) {
        this.columnAlias = columnAlias;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setAsType(String asType) {
        this.asType = asType;
    }

    public void setEnumClassName(String enumClassName) {
        this.enumClassName = enumClassName;
    }

//    public String getHasOne() {
//        return ReferenceKey.toString(this.hasOne);
//    }
//
//    public String getNullValue() {
//        return JavaPrimitiveTypeMapping.getDefaultValue(this.getJavaType());
//    }
//
//    public boolean isHasNullValue() {
//        return JavaPrimitiveTypeMapping.getWrapperTypeOrNull(this.getJavaType()) != null;
//    }
//
//    public void setHasOne(String foreignKey) {
//        this.hasOne = ReferenceKey.fromString(foreignKey);
//        if (this.hasOne != null && this._table != null) {
//            this._table.getImportedKeys().addForeignKey(this.hasOne.tableName, this.hasOne.columnSqlName, this.getSqlName(), this.hasOne.columnSqlName.toLowerCase().hashCode());
//        }
//
//    }
//
//    public String getHasMany() {
//        return ReferenceKey.toString(this.hasMany);
//    }
//
//    public void setHasMany(String foreignKey) {
//        this.hasMany = ReferenceKey.fromString(foreignKey);
//        if (this.hasMany != null && this._table != null) {
//            this._table.getExportedKeys().addForeignKey(this.hasMany.tableName, this.hasMany.columnSqlName, this.getSqlName(), this.hasMany.columnSqlName.toLowerCase().hashCode());
//        }
//
//    }
//

    static PropertiesHelper props = new PropertiesHelper(new Properties(), true);

    private static PropertiesHelper getHelper() {
        return props;
    }
    public static String getProperty(String key, String defaultValue) {
        return getHelper().getProperty(key, defaultValue);
    }

    private void initOtherProperties() {
        String normalJdbcJavaType = getPreferredJavaType(this.getSqlType(), this.getSize(), this.getDecimalDigits());
        this.javaType = getProperty("java_typemapping." + normalJdbcJavaType, normalJdbcJavaType).trim();
        this.columnName = StringHelper.makeAllWordFirstLetterUpperCase(StringHelper.toUnderscoreName(this.getSqlName()));
        this.enumClassName = this.getColumnName() + "Enum";
        this.asType = getPreferredAsType(this.getJavaType());
        this.columnAlias = StringHelper.removeCrlf(StringHelper.defaultIfEmpty(this.getRemarks(), this.getColumnNameFirstLower()));
        this.setHibernateValidatorExprssion(getHibernateValidatorExpression(this));
    }


    public static String getHibernateValidatorExpression(Column c) {
        if (!c.isPk() && !c.isNullable()) {
            return DatabaseDataTypesUtils.isString(c.getJavaType()) ? "@NotBlank " + getNotRequiredHibernateValidatorExpression(c) : "@NotNull " + getNotRequiredHibernateValidatorExpression(c);
        } else {
            return getNotRequiredHibernateValidatorExpression(c);
        }
    }

    public static String getNotRequiredHibernateValidatorExpression(Column c) {
        String result = "";
        if (c.getSqlName().indexOf("mail") >= 0) {
            result = result + "@Email ";
        }

        if (DatabaseDataTypesUtils.isString(c.getJavaType())) {
            result = result + String.format("@Length(max=%s)", c.getSize());
        }

        if (DatabaseDataTypesUtils.isIntegerNumber(c.getJavaType())) {
            String javaType = DatabaseDataTypesUtils.getPreferredJavaType(c.getSqlType(), c.getSize(), c.getDecimalDigits());
            if (javaType.toLowerCase().indexOf("short") >= 0) {
                result = result + " @Max(32767)";
            } else if (javaType.toLowerCase().indexOf("byte") >= 0) {
                result = result + " @Max(127)";
            }
        }

        return result.trim();
    }

    private static final Map<String, String> preferredAsTypeForJavaType = new HashMap();

    public static String getPreferredAsType(String javaType) {
        String result = (String)preferredAsTypeForJavaType.get(javaType);
        if (result == null) {
            result = javaType;
        }

        return result;
    }

    public static String getPreferredJavaType(int sqlType, int size, int decimalDigits) {
        if ((sqlType == 3 || sqlType == 2) && decimalDigits == 0) {
            if (size == 1) {
                return "java.lang.Boolean";
            } else if (size < 3) {
                return "java.lang.Byte";
            } else if (size < 5) {
                return "java.lang.Short";
            } else if (size < 10) {
                return "java.lang.Integer";
            } else {
                return size < 19 ? "java.lang.Long" : "java.math.BigDecimal";
            }
        } else {
            String result = new Integer(sqlType).toString();
            if (result == null) {
                result = "java.lang.Object";
            }

            return result;
        }
    }

    public static String removeAggregationColumnChars(String columSqlName) {
        return columSqlName.replace('(', '_').replace(")", "").replace("*", "");
    }

    public static class EnumMetaDada {
        private String enumAlias;
        private String enumKey;
        private String enumDesc;

        public EnumMetaDada(String enumAlias, String enumKey, String enumDesc) {
            this.enumAlias = enumAlias;
            this.enumKey = enumKey;
            this.enumDesc = enumDesc;
        }

        public String getEnumAlias() {
            return this.enumAlias;
        }

        public String getEnumKey() {
            return this.enumKey;
        }

        public String getEnumDesc() {
            return this.enumDesc;
        }
    }
}
package com.git.easyloan.entity;

public enum JdbcType {
    ARRAY(2003),
    BIT(-7),
    TINYINT(-6),
    SMALLINT(5),
    INTEGER(4),
    BIGINT(-5),
    FLOAT(6),
    REAL(7),
    DOUBLE(8),
    NUMERIC(2),
    DECIMAL(3),
    CHAR(1),
    VARCHAR(12),
    LONGVARCHAR(-1),
    DATE(91),
    TIME(92),
    TIMESTAMP(93),
    BINARY(-2),
    VARBINARY(-3),
    LONGVARBINARY(-4),
    NULL(0),
    OTHER(1111),
    BLOB(2004),
    CLOB(2005),
    BOOLEAN(16),
    CURSOR(-10),
    UNDEFINED(-2147482648),
    NVARCHAR(-9),
    NCHAR(-15),
    NCLOB(2011);

    public final int TYPE_CODE;

    private JdbcType(int code) {
        this.TYPE_CODE = code;
    }

    public static String getJdbcSqlTypeName(int code) {
        JdbcType[] arr$ = values();
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            JdbcType type = arr$[i$];
            if (type.TYPE_CODE == code) {
                return type.name();
            }
        }

        return null;
    }
}

package com.git.easyloan.utils.utils;

import java.util.HashMap;

public class DatabaseDataTypesUtils {

    private static final DatabaseDataTypesUtils.IntStringMap _preferredJavaTypeForSqlType = new DatabaseDataTypesUtils.IntStringMap();

    public DatabaseDataTypesUtils() {
    }

    public static boolean isFloatNumber(String javaType) {
        if (!javaType.endsWith("Float") && !javaType.endsWith("Double") && !javaType.endsWith("BigDecimal") && !javaType.endsWith("BigInteger")) {
            return javaType.endsWith("float") || javaType.endsWith("double") || javaType.endsWith("BigDecimal") || javaType.endsWith("BigInteger");
        } else {
            return true;
        }
    }

    public static boolean isIntegerNumber(String javaType) {
        if (!javaType.endsWith("Long") && !javaType.endsWith("Integer") && !javaType.endsWith("Short") && !javaType.endsWith("Byte")) {
            return javaType.endsWith("long") || javaType.endsWith("int") || javaType.endsWith("short") || javaType.endsWith("byte");
        } else {
            return true;
        }
    }

    public static boolean isDate(String javaType) {
        return javaType.endsWith("Date") || javaType.endsWith("Timestamp") || javaType.endsWith("Time");
    }

    public static boolean isString(String javaType) {
        return javaType.endsWith("String");
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
            String result = _preferredJavaTypeForSqlType.getString(sqlType);
            if (result == null) {
                result = "java.lang.Object";
            }

            return result;
        }
    }

    static {
        _preferredJavaTypeForSqlType.put(-6, (String)"java.lang.Byte");
        _preferredJavaTypeForSqlType.put(5, (String)"java.lang.Short");
        _preferredJavaTypeForSqlType.put(4, (String)"java.lang.Integer");
        _preferredJavaTypeForSqlType.put(-5, (String)"java.lang.Long");
        _preferredJavaTypeForSqlType.put(7, (String)"java.lang.Float");
        _preferredJavaTypeForSqlType.put(6, (String)"java.lang.Double");
        _preferredJavaTypeForSqlType.put(8, (String)"java.lang.Double");
        _preferredJavaTypeForSqlType.put(3, (String)"java.math.BigDecimal");
        _preferredJavaTypeForSqlType.put(2, (String)"java.math.BigDecimal");
        _preferredJavaTypeForSqlType.put(-7, (String)"java.lang.Boolean");
        _preferredJavaTypeForSqlType.put(16, (String)"java.lang.Boolean");
        _preferredJavaTypeForSqlType.put(1, (String)"java.lang.String");
        _preferredJavaTypeForSqlType.put(12, (String)"java.lang.String");
        _preferredJavaTypeForSqlType.put(-1, (String)"java.lang.String");
        _preferredJavaTypeForSqlType.put(-2, (String)"byte[]");
        _preferredJavaTypeForSqlType.put(-3, (String)"byte[]");
        _preferredJavaTypeForSqlType.put(-4, (String)"byte[]");
        _preferredJavaTypeForSqlType.put(91, (String)"java.sql.Date");
        _preferredJavaTypeForSqlType.put(92, (String)"java.sql.Time");
        _preferredJavaTypeForSqlType.put(93, (String)"java.sql.Timestamp");
        _preferredJavaTypeForSqlType.put(2005, (String)"java.sql.Clob");
        _preferredJavaTypeForSqlType.put(2004, (String)"java.sql.Blob");
        _preferredJavaTypeForSqlType.put(2003, (String)"java.sql.Array");
        _preferredJavaTypeForSqlType.put(2006, (String)"java.sql.Ref");
        _preferredJavaTypeForSqlType.put(2002, (String)"java.lang.Object");
        _preferredJavaTypeForSqlType.put(2000, (String)"java.lang.Object");
    }

    private static class IntStringMap extends HashMap {
        private IntStringMap() {
        }

        public String getString(int i) {
            return (String)this.get(new Integer(i));
        }

        public String[] getStrings(int i) {
            return (String[])((String[])this.get(new Integer(i)));
        }

        public void put(int i, String s) {
            this.put(new Integer(i), s);
        }

        public void put(int i, String[] sa) {
            this.put(new Integer(i), sa);
        }
    }
}

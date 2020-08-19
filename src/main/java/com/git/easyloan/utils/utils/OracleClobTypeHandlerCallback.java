package com.git.easyloan.utils.utils;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import javax.sql.rowset.serial.SerialClob;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;

public class OracleClobTypeHandlerCallback implements TypeHandler<Object> {
    public OracleClobTypeHandlerCallback() {
    }

    public Object valueOf(String param) {
        return null;
    }

    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        Clob s = rs.getClob(columnName);
        if (rs.wasNull()) {
            return "";
        } else {
            String clobStr = "";
            Reader inStream = s.getCharacterStream();
            char[] c = new char[(int)s.length()];

            try {
                inStream.read(c);
                clobStr = new String(c);
                inStream.close();
            } catch (IOException var8) {
                var8.printStackTrace();
            }

            return clobStr;
        }
    }

    public Object getResult(ResultSet arg0, int arg1) throws SQLException {
        return null;
    }

    public Object getResult(CallableStatement arg0, int arg1) throws SQLException {
        return null;
    }

    public void setParameter(PreparedStatement arg0, int arg1, Object arg2, JdbcType arg3) throws SQLException {
        SerialClob clob = null;

        try {
            if (arg2 == null) {
                arg2 = "";
            }

            clob = new SerialClob(arg2.toString().toCharArray());
        } catch (Exception var7) {
        }

        arg0.setClob(arg1, clob);
    }
}

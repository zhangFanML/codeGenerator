package com.git.easyloan.entity;


import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PageData extends DTDMap {
    private static final long serialVersionUID = 1L;
    DTDMap map;
    HttpServletRequest request;

    public PageData(HttpServletRequest request) {
        this.request = request;
        Map properties = request.getParameterMap();
        DTDMap returnMap = new DTDMap();
        Iterator entries = properties.entrySet().iterator();
        String name;

        for(String value = ""; entries.hasNext(); returnMap.put(name, value)) {
            Map.Entry entry = (Map.Entry)entries.next();
            name = (String)entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (!(valueObj instanceof String[])) {
                value = valueObj.toString();
            } else {
                String[] values = ((String[])valueObj);

                for(int i = 0; i < values.length; ++i) {
                    value = values[i] + ",";
                }

                value = value.substring(0, value.length() - 1);
            }
        }

        this.map = RequestUtil.parentMap(returnMap);
    }

    public PageData() {
        this.map = new DTDMap();
    }

    public Object get(Object key) {
        Object obj;
        if (this.map.get(key) instanceof Object[]) {
            Object[] arr = ((Object[])this.map.get(key));
            obj = this.request == null ? arr : (this.request.getParameter((String)key) == null ? arr : arr[0]);
        } else {
            obj = this.map.get(key);
        }

        return obj;
    }

    public String getString(Object key) {
        Object obj = this.get(key);
        if (obj instanceof Clob) {
            return this.clobToString((Clob)obj);
        } else {
            return obj instanceof Integer ? obj.toString() : (String)this.get(key);
        }
    }

    public Object put(String key, Object value) {
        return this.map.put(key, value);
    }

    public Object remove(Object key) {
        return this.map.remove(key);
    }

    public void clear() {
        this.map.clear();
    }

    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    public Set<Map.Entry<Object, Object>> entrySet() {
        return this.map.entrySet();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public Set keySet() {
        return this.map.keySet();
    }

    public void putAll(Map t) {
        this.map.putAll(t);
    }

    public int size() {
        return this.map.size();
    }

    public Collection values() {
        return this.map.values();
    }

    private String clobToString(Clob clob) {
        String reString = "";
        BufferedReader br = null;

        try {
            Reader is = clob.getCharacterStream();
            br = new BufferedReader(is);
            String s = br.readLine();

            StringBuffer sb;
            for(sb = new StringBuffer(); s != null; s = br.readLine()) {
                sb.append(s);
            }

            reString = sb.toString();
        } catch (Exception var15) {
            var15.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException var14) {
                    var14.printStackTrace();
                }
            }

        }

        return reString;
    }
}

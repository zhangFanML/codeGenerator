package com.git.easyloan.entity;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.Map.Entry;

public class RequestUtil {

    public RequestUtil() {}

    public static String getValue() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        new PageDatas(request);
        Map properties = request.getParameterMap();
        new HashMap();
        Iterator entries = properties.entrySet().iterator();
        String name = "";

        Entry entry;
        for(String var7 = ""; entries.hasNext(); name = (String)entry.getKey()) {
            entry = (Entry)entries.next();
        }

        return name;
    }

    /**
     *  Map 转换为 DTDMap
     * @param map
     * @return
     */
    public static DTDMap parentMap(Map map) {
        DTDMap dMap = new DTDMap();
        List<String> keyLst = new ArrayList();
        Iterator iterator = map.entrySet().iterator();

        while(iterator.hasNext()) {
            Entry entry = (Entry)iterator.next();
            String key = (String)entry.getKey();
            if (key.indexOf(".") > 0) {
                String[] keys = key.split("[.]");
                if (!keyLst.contains(keys[0])) {
                    dMap.put(keys[0], new PageDatas());
                    keyLst.add(keys[0]);
                }

                ((Map)dMap.get(keys[0])).put(keys[1], entry.getValue());
            } else {
                dMap.put(key, entry.getValue());
            }
        }

        return dMap;
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("AAA.aaa", "11111");
        map.put("AAA.bbb", "22222");
        map.put("AAA.ccc", "33333");
        map.put("BBB.aaa", "44444");
        map.put("BBB.bbb", "55555");
        map.put("BBB.ccc", "66666");
        Map map1 = new HashMap();
        map1.put("AAAA", "11111");
        map1.put("BBBB", "22222");
        map1.put("CCCC", "33333");
        map1.put("DDDD", "44444");
        System.out.println(parentMap(map));
        System.out.println(parentMap(map1));
    }
}


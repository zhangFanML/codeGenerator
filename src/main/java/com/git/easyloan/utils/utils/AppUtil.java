package com.git.easyloan.utils.utils;

import com.git.easyloan.entity.Const;
import com.git.easyloan.entity.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppUtil {
    protected static Logger logger = LoggerFactory.getLogger(AppUtil.class);

    public AppUtil() {
    }

    public static boolean checkParam(String method, PageData pd) {
        boolean result = false;
        int falseCount = 0;
        String[] paramArray = new String[20];
        String[] valueArray = new String[20];
        String[] tempArray = new String[20];
        if ("registerSysUser".equals(method)) {
            paramArray = Const.SYSUSER_REGISTERED_PARAM_ARRAY;
            valueArray = Const.SYSUSER_REGISTERED_VALUE_ARRAY;
        } else if ("getAppuserByUsernmae".equals(method)) {
            paramArray = Const.APP_GETAPPUSER_PARAM_ARRAY;
            valueArray = Const.APP_GETAPPUSER_VALUE_ARRAY;
        }

        int size = paramArray.length;

        int j;
        for(j = 0; j < size; ++j) {
            String param = paramArray[j];
            if (!pd.containsKey(param)) {
                tempArray[falseCount] = valueArray[j] + "--" + param;
                ++falseCount;
            }
        }

        if (falseCount > 0) {
            logger.error(method + "接口，请求协议中缺少 " + falseCount + "个 参数");

            for(j = 1; j <= falseCount; ++j) {
                logger.error("   第" + j + "个：" + tempArray[j - 1]);
            }
        } else {
            result = true;
        }

        return result;
    }

    public static PageData setPageParam(PageData pd) {
        String page_now_str = pd.get("page_now").toString();
        int pageNowInt = Integer.parseInt(page_now_str) - 1;
        String page_size_str = pd.get("page_size").toString();
        int pageSizeInt = Integer.parseInt(page_size_str);
        String page_now = pageNowInt + "";
        String page_start = pageNowInt * pageSizeInt + "";
        pd.put("page_now", page_now);
        pd.put("page_start", page_start);
        return pd;
    }

    public static List<PageData> setListDistance(List<PageData> list, PageData pd) {
        List<PageData> listReturn = new ArrayList();
        String user_longitude = "";
        String user_latitude = "";

        try {
            user_longitude = pd.get("user_longitude").toString();
            user_latitude = pd.get("user_latitude").toString();
        } catch (Exception var11) {
            logger.error("缺失参数--user_longitude和user_longitude");
            logger.error("lost param：user_longitude and user_longitude");
        }

        new PageData();
        int size = list.size();

        for(int i = 0; i < size; ++i) {
            PageData pdTemp = (PageData)list.get(i);
            String longitude = pdTemp.get("longitude").toString();
            String latitude = pdTemp.get("latitude").toString();
            String distance = MapDistance.getDistance(user_longitude, user_latitude, longitude, latitude);
            pdTemp.put("distance", distance);
            pdTemp.put("size", distance.length());
            listReturn.add(pdTemp);
        }

        return listReturn;
    }

    public static Object returnObject(PageData pd, Map map) {
        if (pd.containsKey("callback")) {
//            String callback = pd.get("callback").toString();
//            return new JSONPObject(callback, map);
            return map;
        } else {
            return map;
        }
    }
}

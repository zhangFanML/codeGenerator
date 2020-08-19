package com.git.easyloan.utils.utils;

import java.util.HashMap;
import java.util.Map;

public class MapDistance {
    private static double EARTH_RADIUS = 6378.137D;

    public MapDistance() {
    }

    private static double rad(double d) {
        return d * 3.141592653589793D / 180.0D;
    }

    public static String getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {
        Double lat1 = Double.parseDouble(lat1Str);
        Double lng1 = Double.parseDouble(lng1Str);
        Double lat2 = Double.parseDouble(lat2Str);
        Double lng2 = Double.parseDouble(lng2Str);
        double patm = 2.0D;
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double difference = radLat1 - radLat2;
        double mdifference = rad(lng1) - rad(lng2);
        double distance = patm * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / patm), patm) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(mdifference / patm), patm)));
        distance *= EARTH_RADIUS;
        String distanceStr = String.valueOf(distance);
        return distanceStr;
    }

    public static Map getAround(String latStr, String lngStr, String raidus) {
        Map map = new HashMap();
        Double latitude = Double.parseDouble(latStr);
        Double longitude = Double.parseDouble(lngStr);
        Double degree = 111293.63611111112D;
        double raidusMile = Double.parseDouble(raidus);
        Double mpdLng = Double.parseDouble((degree * Math.cos(latitude * 0.017453292519943295D) + "").replace("-", ""));
        Double dpmLng = 1.0D / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        Double minLat = longitude - radiusLng;
        Double maxLat = longitude + radiusLng;
        Double dpmLat = 1.0D / degree;
        Double radiusLat = dpmLat * raidusMile;
        Double minLng = latitude - radiusLat;
        Double maxLng = latitude + radiusLat;
        map.put("minLat", minLat + "");
        map.put("maxLat", maxLat + "");
        map.put("minLng", minLng + "");
        map.put("maxLng", maxLng + "");
        return map;
    }

    public static void main(String[] args) {
        System.out.println(getDistance("116.97265", "36.694514", "116.597805", "36.738024"));
        System.out.println(getAround("117.11811", "36.68484", "13000"));
    }
}

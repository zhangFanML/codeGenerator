package com.git.easyloan.utils.base;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

public class KeyGenerator {
    public KeyGenerator() {
    }

    public static synchronized String genTransFlowNo() {
        Calendar cal = Calendar.getInstance();
        return Integer.toString(cal.get(11) * 10000 + cal.get(12) * 100 + cal.get(13));
    }

    public static synchronized String genTransFlowNo(int i) {
        Calendar cal = Calendar.getInstance();
        return Integer.toString(250000 + cal.get(12) * 100 + cal.get(13) + i);
    }

    public static synchronized int genIntTransFlowNo(int i) {
        Calendar cal = Calendar.getInstance();
        return Integer.parseInt(Integer.toString(250000 + cal.get(12) * 100 + cal.get(13) + i));
    }

    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().trim();
        return uuid;
    }

    public static void main(String[] args) {
        System.out.println(new Timestamp(System.currentTimeMillis()));

        for(int i = 0; i < 1000000; ++i) {
            get32UUID();
        }

        System.out.println(new Timestamp(System.currentTimeMillis()));
    }
}
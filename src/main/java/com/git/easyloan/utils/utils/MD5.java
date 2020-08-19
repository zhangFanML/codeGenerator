package com.git.easyloan.utils.utils;

import java.security.MessageDigest;

public class MD5 {
    public MD5() {
    }

    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] b = md.digest();
            StringBuffer buf = new StringBuffer("");

            for(int offset = 0; offset < b.length; ++offset) {
                int i = b[offset];
                if (i < 0) {
                    i += 256;
                }

                if (i < 16) {
                    buf.append("0");
                }

                buf.append(Integer.toHexString(i));
            }

            str = buf.toString();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return str;
    }

    public static void main(String[] args) {
        System.out.println(md5("31119@qq.com123456"));
        System.out.println(md5("mj1"));
    }
}

package com.git.easyloan.utils.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileZip {
    public FileZip() {
    }

    public static Boolean zip(String inputFileName, String zipFileName) throws Exception {
        zip(zipFileName, new File(inputFileName));
        return true;
    }

    private static void zip(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        zip(out, inputFile, "");
        out.flush();
        out.close();
    }

    private static void zip(ZipOutputStream out, File f, String base) throws Exception {
        int i;
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            out.putNextEntry(new ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";

            for(i = 0; i < fl.length; ++i) {
                zip(out, fl[i], base + fl[i].getName());
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream in = new FileInputStream(f);

            while((i = in.read()) != -1) {
                out.write(i);
            }

            in.close();
        }

    }

    public static void main(String[] temp) {
        try {
            zip("E:\\ftl", "E:\\test.zip");
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }
}
